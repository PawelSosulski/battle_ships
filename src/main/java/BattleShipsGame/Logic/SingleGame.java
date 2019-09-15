package BattleShipsGame.Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class SingleGame {

    private Scanner scanner = new Scanner(System.in);
    private PrepareSingleGame singleGame = new PrepareSingleGame();
    private int numberOfShoots = 0;
    private SetOfShips myShips;
    private Alphabet alphabet = new Alphabet();
    private WarshipBattlefield battlefield;
    private int battlefieldSize;
    private List<Coordinates> missedShoots = new ArrayList<>();


    public void startGame() {
        singleGame.prepareSetOfShips();
        myShips = singleGame.getMyShips();
        battlefieldSize = singleGame.getBattlefieldSize();
        battlefield = new WarshipBattlefield(battlefieldSize, myShips);

        System.out.println("Gra rozpoczęta !");
        while (true) {
            System.out.println("'exit' - wyjście \t 'capitulate' - poddanie");
            battlefield.showBoard();
            System.out.println("Liczba strzałów " + numberOfShoots);
            shootAtShip();

            if (checkIfWin()) {
                System.out.println("\n!!!!   WYGRAŁEŚ  !!!!\n");
                battlefield.showBoardWhenWin(missedShoots);
                System.out.println("Liczba strzałów: " + numberOfShoots);
                System.exit(1);
            }
        }

    }

    private boolean checkIfWin() {
        for (Ship ship : myShips.getShipList()) {
            if (!ship.checkIfSink())
                return false;
        }
        return true;
    }

    private void shootAtShip() {
        Coordinates point = getPoint();
        boolean rightShoot = false;
        boolean duplicateShoot = false;
        boolean isShipSink = false;
        for (Ship myShip : myShips.getShipList()) {
            if (checkPosition(point, myShip.getShipCoordinates())) {
                Map<Coordinates, Boolean> shipCoordinates = myShip.getShipCoordinatesDuringShooting();
                if (!shipCoordinates.get(point)) {
                    rightShoot = true;
                    myShip.setRightShoot(point);
                    isShipSink = myShip.checkIfSink();
                } else {
                    duplicateShoot = true;
                }
            }
            //break;
        }
        numberOfShoots++;
        if (rightShoot && !duplicateShoot) {
            if (isShipSink)
                System.out.println("Brawo, statek zatopiony\n");
            else
                System.out.println("Statek trafiony!\n");
        } else if (duplicateShoot) {
            System.out.println("Już tutaj strzelałeś :(\n");

        } else {
            System.out.println("Nie trafiłeś\n");
            missedShoots.add(point);
        }
        battlefield.update(missedShoots);

    }


    private boolean checkPosition(Coordinates point, List<Coordinates> table) {

        for (Coordinates points : table) {
            if (points.getxCoordinate() == point.getxCoordinate() && points.getyCoordinate() == point.getyCoordinate())
                return true;
        }
        return false;
    }


    private Coordinates getPoint() {
        Boolean outBoard = true;
        Coordinates point = new Coordinates();
        while (outBoard) {
            System.out.println("Podaj koordynaty: ");
            String text = scanner.nextLine();
            switch (text.toLowerCase()) {
                case "exit":
                    System.out.println("Wyjście z programu");
                    System.exit(1);
                case "capitulate":
                    battlefield.showBoardCapitulate();
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
        return point;
    }


}


