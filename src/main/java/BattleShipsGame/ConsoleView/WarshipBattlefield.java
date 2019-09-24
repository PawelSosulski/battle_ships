package BattleShipsGame.ConsoleView;

import BattleShipsGame.Models.Coordinates;
import BattleShipsGame.Models.SetOfShips;
import BattleShipsGame.Models.Ship;

import java.util.List;
import java.util.Map;

public class WarshipBattlefield {

    private int boardSize;
    private SetOfShips myShips;
    private String[][] board;

    public WarshipBattlefield(int boardSize, SetOfShips myShips) {
        this.boardSize = boardSize;
        this.myShips = myShips;
        board = new String[boardSize + 1][boardSize + 1];
        fillBoard();
    }


    private void fillBoard() {
        int asciiX = 65;
        int asciiY = 1;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (i == 0 && j == 0) {
                    board[i][j] = "#";
                } else if (i == 0) {
                    board[i][j] = (String.valueOf((char) asciiX));
                    asciiX++;
                } else if (j == 0) {
                    board[i][j] = String.valueOf(asciiY);
                    asciiY++;
                } else
                    board[i][j] = "·";
            }
        }
    }


    private void ImpossibleLocation() {
        for (Ship myShip : myShips.getShipList()) {
            for (Coordinates takenPoints : myShip.getShipImpossiblePosition()) {
                if (takenPoints.getxCoordinate() <= boardSize && takenPoints.getyCoordinate() <= boardSize
                        && takenPoints.getxCoordinate() >= 1 && takenPoints.getyCoordinate() >= 1)
                    board[takenPoints.getyCoordinate()][takenPoints.getxCoordinate()] = "X";
            }
        }
    }

    private void ImpossibleLocation(Ship ship) {
        for (Coordinates takenPoints : ship.getShipImpossiblePosition()) {
            if (takenPoints.getxCoordinate() <= boardSize && takenPoints.getyCoordinate() <= boardSize
                    && takenPoints.getxCoordinate() >= 1 && takenPoints.getyCoordinate() >= 1)
                board[takenPoints.getyCoordinate()][takenPoints.getxCoordinate()] = "X";
        }
    }


    public void showBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + "\t");
            }
            System.out.println();
        }
    }


    public void showBoardWhileAdding() {
        boardCapitulate();
        ImpossibleLocation();
        showBoard();
    }


    private void boardCapitulate() {
        fillBoard();
        for (Ship myShip : myShips.getShipList()) {
            for (Coordinates point : myShip.getShipCoordinates()) {
                board[point.getyCoordinate()][point.getxCoordinate()] = myShip.getShipFulfillment();
            }
        }
    }

    public void showBoardWhenWin(List<Coordinates> missedShoots) {
        boardCapitulate();
        boardMissedShoots(missedShoots);
        showBoard();
    }


    public void showBoardCapitulate() {
        boardCapitulate();
        showBoard();
    }

    private void boardMissedShoots(List<Coordinates> missedShoots) {
        for (Coordinates point : missedShoots) {
            board[point.getyCoordinate()][point.getxCoordinate()] = "X";
        }
    }

    public void update(List<Coordinates> missedShoots) {
        boardMissedShoots(missedShoots);
        for (Ship ship : myShips.getShipList()) {
            Map<Coordinates, Boolean> shipCoordinates = ship.getShipCoordinatesDuringShooting();
            for (Coordinates point : shipCoordinates.keySet()) {
                if (shipCoordinates.get(point)) {
                    board[point.getyCoordinate()][point.getxCoordinate()] = ship.getShipFulfillment();
                }
            }
            /* Można dodać pokazywanie obrysów
            if (ship.checkIfSink()) {
                ImpossibleLocation(ship);
            }*/
        }

    }

    public String[][] getBoard() {
        return board;
    }
}
