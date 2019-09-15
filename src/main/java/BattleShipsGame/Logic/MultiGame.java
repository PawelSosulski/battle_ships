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
    private int oneNumberOfShoots = 0;
    private int twoNumberOfShoots = 0;
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

        System.out.println("!!!\tGra rozpoczęta\t!!!");
        System.out.println("'exit' - wyjście \t 'capitulate' - poddanie");
        while (true) {
            for (int i = 1; i <= 2; i++) {
                shootAtShip(i);
                if (checkIfWin(i)) {
                    if (i == i) {
                        System.out.println("\n!!!!   " + multiGame.getPlayerOneName() + " WIN  !!!!\n");
                        twoBattlefield.showBoardWhenWin(oneMissedShoots);
                        System.out.println(multiGame.getPlayerTwoName() + " field:");
                        System.out.println("Liczba strzałów: " + oneNumberOfShoots);
                    } else {
                        System.out.println("\n!!!!   " + multiGame.getPlayerTwoName() + " WIN  !!!!\n");
                        System.out.println(multiGame.getPlayerOneName() + " field:");
                        oneBattlefield.showBoardWhenWin(twoMissedShoots);
                        System.out.println("Liczba strzałów: " + twoNumberOfShoots);
                    }
                    System.exit(1);
                }
            }
        }
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

    private void shootAtShip(int turn) {
        fields.showFields();
        if (turn == 1) {
            System.out.println("Player 1 " + multiGame.getPlayerOneName() + " turn! Shoots: " + oneNumberOfShoots);

            Coordinates point = getPoint(turn);
            boolean rightShoot = false;
            boolean duplicateShoot = false;
            boolean isShipSink = false;
            for (Ship ship : twoShips.getShipList()) {
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
            if (checkPosition(point, oneMissedShoots))
                duplicateShoot = true;

            oneNumberOfShoots++;
            if (rightShoot && !duplicateShoot) {
                if (isShipSink)
                    System.out.println("Brawo " + multiGame.getPlayerOneName() + ", statek zatopiony\n");
                else
                    System.out.println(multiGame.getPlayerOneName() + ", statek trafiony!\n");
            } else if (duplicateShoot) {
                System.out.println(multiGame.getPlayerOneName() + ", już tutaj strzelałeś :(\n");

            } else {
                System.out.println("Nie trafiłeś " + multiGame.getPlayerOneName() + "\n");
                oneMissedShoots.add(point);
            }
            twoBattlefield.update(oneMissedShoots);
            if (rightShoot) {
                System.out.println("Player " + multiGame.getPlayerOneName() + " kontynuuje");
                shootAtShip(turn);
            }

        } else if (turn == 2) {
            System.out.println("Player 2 " + multiGame.getPlayerTwoName() + " turn! Shoots: " + twoNumberOfShoots);
            Coordinates point = getPoint(turn);
            boolean rightShoot = false;
            boolean duplicateShoot = false;
            boolean isShipSink = false;
            for (Ship ship : oneShips.getShipList()) {
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
            if (checkPosition(point, twoMissedShoots))
                duplicateShoot = true;

            twoNumberOfShoots++;
            if (rightShoot && !duplicateShoot) {
                if (isShipSink)
                    System.out.println("Brawo " + multiGame.getPlayerTwoName() + ", statek zatopiony");
                else
                    System.out.println(multiGame.getPlayerTwoName() + ", statek trafiony!");
            } else if (duplicateShoot) {
                System.out.println(multiGame.getPlayerTwoName() + ", już tutaj strzelałeś :(\n");

            } else {
                System.out.println("Nie trafiłeś " + multiGame.getPlayerTwoName() + "\n");
                twoMissedShoots.add(point);
            }
            oneBattlefield.update(twoMissedShoots);
            if (rightShoot) {
                System.out.println("Player " + multiGame.getPlayerTwoName() + " kontynuuje");
                shootAtShip(turn);
            }
        } else
            System.out.println("Something goes wrong");

    }


    private boolean checkPosition(Coordinates point, List<Coordinates> table) {
        for (Coordinates points : table) {
            if (points.getxCoordinate() == point.getxCoordinate() && points.getyCoordinate() == point.getyCoordinate())
                return true;
        }
        return false;
    }


    private Coordinates getPoint(int turn) {
        Boolean outBoard = true;
        Coordinates point = new Coordinates();
        if (turn == 1) {
            while (outBoard) {
                System.out.println(multiGame.getPlayerOneName() + " podaj koordynaty: ");
                String text = scanner.nextLine();
                switch (text.toLowerCase()) {
                    case "exit":
                        System.out.println("Wyjście z programu");
                        System.exit(1);
                    case "capitulate":
                        System.out.println("Player " + multiGame.getPlayerOneName() + " capitulate!!");
                        System.out.println("Player " + multiGame.getPlayerTwoName() + " field:");
                        twoBattlefield.showBoardCapitulate();
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
                    } else
                        System.out.println("Błędny format");
                }
            }
        } else if (turn == 2) {
            while (outBoard) {
                System.out.println(multiGame.getPlayerTwoName() + " podaj koordynaty: ");
                String text = scanner.nextLine();
                switch (text.toLowerCase()) {
                    case "exit":
                        System.out.println("Wyjście z programu");
                        System.exit(1);
                    case "capitulate":
                        System.out.println("Player " + multiGame.getPlayerTwoName() + " capitulate!!");
                        System.out.println("Player " + multiGame.getPlayerOneName() + " field:");
                        oneBattlefield.showBoardCapitulate();
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
                    } else
                        System.out.println("Błędny format");
                }
            }
        } else {
            System.out.println("Something goes wrong");
        }

        return point;
    }


}


