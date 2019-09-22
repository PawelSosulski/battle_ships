package BattleShipsGame.Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MultiGame {

    private Scanner scanner = new Scanner(System.in);
    private Alphabet alphabet = new Alphabet();
    private PrepareMultiGame multiGame = new PrepareMultiGame();

    private int[] numberOfShoots;
    private int battlefieldSize;
    private SetOfShips oneShips;
    private SetOfShips twoShips;
    private WarshipBattlefield oneBattlefield;
    private WarshipBattlefield twoBattlefield;
    private TwoWarshipBattlefield fields;
    private List<Coordinates> oneMissedShoots = new ArrayList<>();
    private List<Coordinates> twoMissedShoots = new ArrayList<>();


    public void startGame() {
        multiGame.prepareSetting();
        battlefieldSize = multiGame.getBattlefieldSize();
        oneShips = multiGame.getOneShips();
        twoShips = multiGame.getTwoShips();
        oneBattlefield = multiGame.getOneBattleField();
        twoBattlefield = multiGame.getTwoBattleField();
        fields = new TwoWarshipBattlefield(multiGame);
        numberOfShoots = new int[2];
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
                        System.out.println("\n!!!!   " + multiGame.getPlayerOneName() + " WIN  !!!!\n");
                        System.out.println(multiGame.getPlayerTwoName() + " field:");
                        twoBattlefield.showBoardWhenWin(oneMissedShoots);
                    } else {
                        System.out.println("\n!!!!   " + multiGame.getPlayerTwoName() + " WIN  !!!!\n");
                        System.out.println(multiGame.getPlayerOneName() + " field:");
                        oneBattlefield.showBoardWhenWin(twoMissedShoots);
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
        String playerName = "";
        SetOfShips otherPlayerShips=null;
        List<Coordinates> playerMissShoots = null;
        WarshipBattlefield otherPlayerField = null;
        if (turn == 1) {
            playerName = multiGame.getPlayerOneName();
            playerMissShoots = oneMissedShoots;
            otherPlayerField = twoBattlefield;
            otherPlayerShips = twoShips;
        } else if (turn == 2) {
            playerName = multiGame.getPlayerTwoName();
            playerMissShoots = twoMissedShoots;
            otherPlayerField = oneBattlefield;
            otherPlayerShips = oneShips;
        } else {
            System.out.println("Something goes wrong");
            System.exit(1);
        }
        // END

        System.out.println("Player " + turn + ": " + playerName + " turn! Shoots: " + numberOfShoots[turn - 1]);
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
                System.out.println("Brawo " + playerName + ", statek zatopiony\n");
            else
                System.out.println(playerName + ", statek trafiony!\n");
        } else if (duplicateShoot) {
            System.out.println(playerName + ", już tutaj strzelałeś :(\n");

        } else {
            System.out.println("Nie trafiłeś " + playerName + "\n");
            playerMissShoots.add(point);
        }
        otherPlayerField.update(playerMissShoots);
        if (rightShoot) {
            if (!checkIfWin(turn)) {
                System.out.println("Player " + playerName + " kontynuuje");
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
            point = getPointDuringTurn(multiGame.getPlayerOneName(), multiGame.getPlayerTwoName(), twoBattlefield);
        } else if (turn == 2) {
            point = getPointDuringTurn(multiGame.getPlayerTwoName(), multiGame.getPlayerOneName(), oneBattlefield);
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
                    System.out.println("Player " + turnPlayer + " capitulate!!");
                    System.out.println("Player " + otherPlayer + " field:");
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
            for (Ship ship : twoShips.getShipList()) {
                if (!ship.checkIfSink())
                    return false;
            }
            return true;
        } else if (turn == 2) {
            for (Ship ship : oneShips.getShipList()) {
                if (!ship.checkIfSink())
                    return false;
            }
            return true;
        }
        return false;
    }

}