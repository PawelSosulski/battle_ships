package BattleShipsGame.Logic;

import BattleShipsGame.Helpers.Alphabet;
import BattleShipsGame.Models.Coordinates;
import BattleShipsGame.Models.SetOfShips;
import BattleShipsGame.Models.Ship;
import BattleShipsGame.ConsoleView.TwoWarshipBattlefield;
import BattleShipsGame.ConsoleView.WarshipBattlefield;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CompGame {

    private Scanner scanner = new Scanner(System.in);
    private Alphabet alphabet = new Alphabet();
    private PrepareCompGame compGame = new PrepareCompGame();

    private int[] numberOfShoots;
    private int battlefieldSize;
    private SetOfShips playerShips;
    private SetOfShips compShips;
    private WarshipBattlefield playerBattlefield;
    private WarshipBattlefield compBattlefield;
    private TwoWarshipBattlefield fields;
    private List<Coordinates> playerMissedShoots = new ArrayList<>();
    private List<Coordinates> compMissedShoots = new ArrayList<>();
    private String playerName;
    private CompLogic compLogic;

    public void startGame() {

        battlefieldSize = compGame.getBoardSize();
        playerName = compGame.getPlayerName();

        playerShips = compGame.getPlayerShips();
        playerBattlefield = compGame.getPlayerBattleField();

        compShips = compGame.getCompShips();
        compBattlefield = compGame.getCompBattleField();

        fields = new TwoWarshipBattlefield(compGame);
        numberOfShoots = new int[2];
        compLogic = new CompLogic(battlefieldSize);

        System.out.println("!!!\tGra rozpoczęta\t!!!");
        System.out.println("'exit' - wyjście \t 'capitulate' - poddanie");
        shootAtShip();
    }

    private void shootAtShip() {
        while (true) {
            for (int i = 1; i <= 2; i++) {
                shootAtShipsTurns(i);
                if (checkIfWin(i)) {
                    if (i == 1) {
                        System.out.println("\n!!!!   " + playerName + " WIN  !!!!\n");
                        System.out.println("Computer field:");
                        compBattlefield.showBoardWhenWin(playerMissedShoots);
                    } else {
                        System.out.println("\n!!!! COMPUTER WIN  !!!!\n");
                        System.out.println(playerName + " field:");
                        playerBattlefield.showBoardWhenWin(compMissedShoots);
                    }
                    System.out.println("Liczba strzałów: " + numberOfShoots[i - 1]);
                    System.exit(1);
                }
            }
        }
    }

    private void shootAtShipsTurns(int turn) {
        //set turn-data
        fields.showFields();
        String name = "";
        SetOfShips otherPlayerShips = null;
        List<Coordinates> playerMissShoots = null;
        WarshipBattlefield otherPlayerField = null;
        if (turn == 1) {
            name = playerName;
            playerMissShoots = playerMissedShoots;
            otherPlayerField = compBattlefield;
            otherPlayerShips = compShips;
        } else if (turn == 2) {
            name = "Computer";
            playerMissShoots = compMissedShoots;
            otherPlayerField = playerBattlefield;
            otherPlayerShips = playerShips;
        } else {
            System.out.println("Something goes wrong");
            System.exit(1);
        }
        // END
        System.out.println(name + " turn! Shoots: " + numberOfShoots[turn - 1]);
        Coordinates point = getPoint(turn);
        boolean rightShoot = false;
        boolean duplicateShoot = false;
        boolean isShipSink = false;
        for (Ship ship : otherPlayerShips.getShipList()) {
            if (checkPosition(point, ship.getShipCoordinates())) {
                Map<Coordinates, Boolean> shipCoordinates = ship.getShipCoordinatesDuringShooting();
                if (!shipCoordinates.get(point)) {
                    rightShoot = true;
                    ship.setRightShoot(point);
                    isShipSink = ship.checkIfSink();
                } else {
                    duplicateShoot = true;
                }
            }
        }
        if (checkPosition(point, playerMissShoots))
            duplicateShoot = true;
        numberOfShoots[turn - 1]++;
        if (rightShoot && !duplicateShoot) {
            if (isShipSink)
                System.out.println("Brawo " + name + ", statek zatopiony\n");
            else
                System.out.println(name + ", statek trafiony!\n");
        } else if (duplicateShoot) {
            System.out.println(name + ", już tutaj strzelałeś :(\n");
        } else {
            System.out.println("Nie trafiłeś " + name + "\n");
            playerMissShoots.add(point);
        }
        otherPlayerField.update(playerMissShoots);
        if (turn == 2) {
            compLogic.setPotentiallyPlace(rightShoot, isShipSink,point);
        }
        if (rightShoot) {
            if (!checkIfWin(turn)) {
                System.out.println(name + " kontynuuje");
                shootAtShipsTurns(turn);
            }
        }
    }


    private boolean checkPosition(Coordinates point, List<Coordinates> table) {
        for (Coordinates points : table) {
            if (points.getxCoordinate() == point.getxCoordinate() && points.getyCoordinate() == point.getyCoordinate())
                return true;
        }
        return false;
    }


    private Coordinates getPoint(int turn) {
        Coordinates point = new Coordinates();
        if (turn == 1) {
            point = getPointDuringTurn(playerName, "Computer", compBattlefield);
        } else if (turn == 2) {
            //todo
            System.out.println("Strzela komputer");
            point = compLogic.getPoint();
        } else {
            System.out.println("Something goes wrong");
        }
        return point;
    }

    private Coordinates getPointDuringTurn(String turnPlayer, String otherPlayer, WarshipBattlefield otherPlayerField) {
        Boolean outBoard = true;
        Coordinates point = new Coordinates();
        Scanner sc = new Scanner(System.in);
        while (outBoard) {
            System.out.println(turnPlayer + " podaj koordynaty: ");
            String text = sc.nextLine();
            switch (text.toLowerCase()) {
                case "exit":
                    System.out.println("Wyjście z programu");
                    System.exit(1);
                case "capitulate":
                    System.out.println(turnPlayer + " capitulate!!");
                    System.out.println(otherPlayer + " field:");
                    otherPlayerField.showBoardCapitulate();
                    System.exit(1);
            }
            Pattern pattern = Pattern.compile("[A-Z][1-9]");
            if (!pattern.matcher(text).find()) {
                System.out.println("Błędny format, spróbuj ponownie");
            } else {
                int yValue;
                String number = text.substring(1);
                Pattern pattern1 = Pattern.compile("[A-Z]");
                if (!pattern1.matcher(number).find()) {
                    yValue = Integer.valueOf(number);
                    int xValue = alphabet.getNumberFromLetter(text.substring(0, 1));
                    if (xValue >= battlefieldSize + 1 || yValue >= battlefieldSize + 1) {
                        System.out.println("Poza zakresem, spróbuj ponownie");
                    } else {
                        point.setxCoordinate(xValue);
                        point.setyCoordinate(yValue);
                        outBoard = false;
                    }
                }
            }
        }
        return point;
    }

    private boolean checkIfWin(int turn) {
        if (turn == 1) {
            for (Ship ship : compShips.getShipList()) {
                if (!ship.checkIfSink())
                    return false;
            }
            return true;
        } else if (turn == 2) {
            for (Ship ship : playerShips.getShipList()) {
                if (!ship.checkIfSink())
                    return false;
            }
            return true;
        }
        return false;
    }

}