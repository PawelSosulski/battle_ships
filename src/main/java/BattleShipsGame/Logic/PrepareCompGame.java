package BattleShipsGame.Logic;

import BattleShipsGame.Models.SetOfShips;
import BattleShipsGame.Models.Ship;
import BattleShipsGame.ConsoleView.WarshipBattlefield;

import java.util.Scanner;

public class PrepareCompGame {

    private SetOfShips playerShips;
    private SetOfShips compShips;
    private WarshipBattlefield playerBattleField;
    private WarshipBattlefield compBattleField;
    private String playerName;
    int[] shipsLengthTable = new int[]{5, 4, 3, 3, 2};
    Scanner scanner = new Scanner(System.in);
    private int boardSize = 10;

    public PrepareCompGame() {
        System.out.println("Podaj nazwę gracza: ");
        playerName = scanner.nextLine();
        initPlayer(playerName);
        initComp();
    }

    private void initPlayer(String name) {
        boolean isFillGood = false;
        while (!isFillGood) {
            System.out.println("Wybierz: '1' - własne ustawienie statków\t'2' - randomowe ustawienie\t'3' - exit");
            String text = scanner.nextLine();
            switch (text) {
                case "1":
                    playerShips = fillShipsByPlayer();
                    playerBattleField = new WarshipBattlefield(boardSize, playerShips);
                    isFillGood = true;
                    break;
                case "2":
                    playerShips = new SetOfShips(boardSize);
                    playerShips.setRandomShips(shipsLengthTable);
                    playerBattleField = new WarshipBattlefield(boardSize, playerShips);
                    isFillGood = true;
                    break;
                case "3":
                    System.exit(1);
                default:
                    System.out.println("Nie prawidłowy wybór, ponów");
                    break;
            }
        }
    }

    private void initComp() {
        compShips = new SetOfShips(boardSize);
        compShips.setRandomShips(shipsLengthTable);
        compBattleField = new WarshipBattlefield(boardSize, compShips);
    }

    public SetOfShips fillShipsByPlayer() {
        SetOfShips myShips = new SetOfShips(boardSize);
        WarshipBattlefield field = new WarshipBattlefield(boardSize, myShips);

        for (int i = 1; i <= shipsLengthTable.length; i++) {
            System.out.println("Player " + playerName + " uzupełnia statki.");
            boolean isAdd = false;
            System.out.println("Statek nr " + i + " o wielkości " + shipsLengthTable[i - 1]);
            while (!isAdd) {
                System.out.println("Podaj punkt początkowy");
                String startingPoint = scanner.next();
                if (startingPoint.equalsIgnoreCase("exit")) {
                    System.out.println("Wyjście z programu");
                    System.exit(1);
                }
                System.out.println("Podaj kierunek 'v' bądź 'h'");
                String direction = scanner.next().toLowerCase();
                Ship newShip = new Ship(startingPoint, shipsLengthTable[i - 1], direction, boardSize);
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
        return myShips;
    }


    public SetOfShips getPlayerShips() {
        return playerShips;
    }

    public SetOfShips getCompShips() {
        return compShips;
    }

    public WarshipBattlefield getPlayerBattleField() {
        return playerBattleField;
    }

    public WarshipBattlefield getCompBattleField() {
        return compBattleField;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getBoardSize() {
        return boardSize;
    }
}
