package BattleShipsGame.Logic;

import BattleShipsGame.Models.SetOfShips;
import BattleShipsGame.Models.Ship;
import BattleShipsGame.ConsoleView.WarshipBattlefield;

import java.util.Scanner;

public class PrepareSingleGame {
    private int[] shipsLengthTable;
    private SetOfShips myShips;
    private int battlefieldSize;
    private Scanner scanner = new Scanner(System.in);
    private WarshipBattlefield fieldBattleField;
    private boolean isDone;

    public void prepareSetOfShips() {
        do {
            System.out.println("Gra jednoosobowa. Wybierz: '1' - gra customowa\t'2' - własne ustawienia'\t'3' - wyjście");
            String text = scanner.nextLine();
            switch (text) {
                case "1":
                    battlefieldSize = 10;
                    shipsLengthTable = new int[]{5, 4, 3, 3, 2};
                    //shipsLengthTable = new int[]{5};
                    myShips = new SetOfShips(battlefieldSize);
                    myShips.setRandomShips(shipsLengthTable);
                    isDone = true;
                    break;
                case "2":
                    battlefieldSize = specifyBattlefieldSize();
                    myShips = new SetOfShips(battlefieldSize);
                    fieldBattleField = new WarshipBattlefield(battlefieldSize, myShips);
                    int shipAmount = getShipAmount();
                    for (int i = 0; i < shipAmount; i++) {
                        boolean isAdd = false;
                        while (!isAdd) {
                            System.out.println("Podaj punkt początkowy");
                            String startingPoint = scanner.next();
                            if (startingPoint.equalsIgnoreCase("exit")) {
                                System.out.println("Wyjście z programu");
                                System.exit(1);
                            }
                            System.out.println("Podaj długość statku");
                            int shipLength = scanner.nextInt();
                            System.out.println("Podaj kierunek 'v' bądź 'h'");
                            String direction = scanner.next().toLowerCase();
                            Ship newShip = new Ship(startingPoint, shipLength, direction, battlefieldSize);
                            isAdd = myShips.add(newShip);
                            if (isAdd) {
                                newShip.setUpShip();
                                fieldBattleField.showBoardWhileAdding();
                                System.out.println();
                            } else {
                                System.out.println("Nie można było dodać statku");
                            }
                        }
                    }
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

    private int getShipAmount() {
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
        return size;
    }


    public SetOfShips getMyShips() {
        return myShips;
    }


    public int getBattlefieldSize() {
        return battlefieldSize;
    }
}
