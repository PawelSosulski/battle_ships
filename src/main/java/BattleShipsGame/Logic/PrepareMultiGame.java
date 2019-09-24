package BattleShipsGame.Logic;


import BattleShipsGame.Models.SetOfShips;
import BattleShipsGame.Models.Ship;
import BattleShipsGame.ConsoleView.WarshipBattlefield;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrepareMultiGame {
    private int[] shipsLengthTable;
    private SetOfShips oneShips;
    private SetOfShips twoShips;
    private int battlefieldSize;
    private Scanner scanner = new Scanner(System.in);
    private WarshipBattlefield oneBattleField;
    private WarshipBattlefield twoBattleField;
    private boolean isDone;
    private String playerOneName;
    private String playerTwoName;
    private List<Integer> shipsLengthArray;


    public void prepareSetting() {
        do {
            System.out.println();
            System.out.println("Podaj nazwę pierwszego gracza:");
            setPlayerOneName(scanner.nextLine());
            System.out.println("Podaj nazwę drugiego gracza:");
            setPlayerTwoName(scanner.nextLine());
            System.out.println("Gra dwuosobowa. Wybierz: '1' - gra customowa\t'2' - własne ustawienia'\t'3' - wyjście");
            String text = scanner.nextLine();
            switch (text) {
                case "1":
                    battlefieldSize = 10;
                    shipsLengthTable = new int[]{5, 4, 3, 3, 2};
                    oneShips = new SetOfShips(battlefieldSize);
                    oneShips.setRandomShips(shipsLengthTable);
                    //System.out.println("Wielkość zestawu statków 1: " + oneShips.getShipList().size());
                    twoShips = new SetOfShips(battlefieldSize);
                    twoShips.setRandomShips(shipsLengthTable);
                    //System.out.println("Wielkość zestawu statków 2: " + twoShips.getShipList().size());
                    oneBattleField = new WarshipBattlefield(battlefieldSize, oneShips);
                    twoBattleField = new WarshipBattlefield(battlefieldSize, twoShips);
                    isDone = true;
                    break;
                case "2":
                    battlefieldSize = specifyBattlefieldSize();
                    getShipsSizes();
                    //System.out.println("Wielkość tablicy statków " + shipsLengthArray.size());
                    oneShips = fillShipsByPlayer(playerOneName);
                    oneBattleField = new WarshipBattlefield(battlefieldSize, oneShips);
                    twoShips = fillShipsByPlayer(playerTwoName);
                    twoBattleField = new WarshipBattlefield(battlefieldSize, twoShips);
                    isDone = true;
                    break;
                case "3":
                    System.exit(1);
                    break;
                default:
                    System.out.println("Źle wybrano, spróbuj ponownie");
                    break;
            }
        } while (!isDone);

    }

    private SetOfShips fillShipsByPlayer(String name) {
        SetOfShips myShips = new SetOfShips(battlefieldSize);
        WarshipBattlefield field = new WarshipBattlefield(battlefieldSize, myShips);

        for (int i = 1; i <= shipsLengthArray.size(); i++) {
            System.out.println("Player " + name + " uzupełnia statki.");
            boolean isAdd = false;
            System.out.println("Statek nr " + i + " o wielkości " + shipsLengthArray.get(i - 1));
            while (!isAdd) {
                System.out.println("Podaj punkt początkowy");
                String startingPoint = scanner.next();
                if (startingPoint.equalsIgnoreCase("exit")) {
                    System.out.println("Wyjście z programu");
                    System.exit(1);
                }
                System.out.println("Podaj kierunek 'v' bądź 'h'");
                String direction = scanner.next().toLowerCase();
                Ship newShip = new Ship(startingPoint, shipsLengthArray.get(i - 1), direction, battlefieldSize);
                isAdd = myShips.add(newShip);
                if (isAdd) {
                    newShip.setUpShip();
                    field.showBoardWhileAdding();
                    System.out.println();
                } else {
                    System.out.println("Nie można było dodać statku");
                }
            }
        }
        for (int i = 0; i < 30; i++)
            System.out.println();
        return myShips;
    }

       private int specifyBattlefieldSize() {
        boolean isFillGood = false;
        int size;
        do {
            System.out.println("Podaj wielkość planszy n x n; (od 5 do 26)");
            size = scanner.nextInt();
            if (size > 4 && size < 27)
                isFillGood = true;
            else
                System.out.println("Nieprawidłowa wartość");
        } while (!isFillGood);
        return size;
    }


    private void getShipsSizes() {
        boolean isFillGood = false;
        int size;
        do {
            System.out.println("Podaj ilość statków (max 8)");
            size = scanner.nextInt();
            if (size > 0 && size < 8)
                isFillGood = true;
            else
                System.out.println("Nieprawidłowa wartość");

        } while (!isFillGood);

        int counter = 1;
        shipsLengthArray = new ArrayList<>();
        isFillGood = false;
        int shipSize;
        scanner.reset();
        System.out.println("wielkość statków size: " + size);
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < size; i++) {

            //while (!isFillGood) {
                System.out.println("Podaj wielkość " + counter + " statku.");
                shipSize = sc.nextInt();
                if (shipSize > 0 && shipSize <= battlefieldSize) {
              //      isFillGood = true;
                    shipsLengthArray.add(shipSize);
                } else {
                    System.out.println("Nieprawidłowa wartość");
                }
            //}

            counter++;
        }

    }


    public int getBattlefieldSize() {
        return battlefieldSize;
    }

    public String getPlayerOneName() {
        return playerOneName;
    }

    public void setPlayerOneName(String playerOneName) {
        this.playerOneName = playerOneName;
    }

    public String getPlayerTwoName() {
        return playerTwoName;
    }

    public void setPlayerTwoName(String playerTwoName) {
        this.playerTwoName = playerTwoName;
    }

    public SetOfShips getOneShips() {
        return oneShips;
    }

    public SetOfShips getTwoShips() {
        return twoShips;
    }

    public WarshipBattlefield getOneBattleField() {
        return oneBattleField;
    }

    public WarshipBattlefield getTwoBattleField() {
        return twoBattleField;
    }

}
