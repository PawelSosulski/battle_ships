package BattleShipsGame.Logic;

import java.util.*;

public class SetOfShips {
    private List<Ship> shipList = new ArrayList<>();
    private int boardSize;
    private List<Coordinates> takenPosition = new ArrayList<>();
    Random random = new Random();
    public SetOfShips(int boardSize) {
        this.boardSize = boardSize;
    }


    public List<Ship> getShipList() {
        return shipList;
    }

    public boolean add(Ship newShip) {
        if (newShip == null) {
            System.out.println("Ship jest nullem");
            return false;
        }

        else if (checkTakenPosition(newShip)) {
            //System.out.println("Nie ma możliwości dodania statku");
            return false;
        } else {
            if (newShip.getShipCoordinates() != null) {
                shipList.add(newShip);
                newShip.setUpShip();
                takenPosition.addAll(newShip.getShipImpossiblePosition());
                takenPosition.addAll(newShip.getShipCoordinates());
                return true;
                //System.out.println("Statek dodany");
            } else {
                //System.out.println("Statek pusty, nie dodano");
                return false;
            }
        }
    }

    private boolean checkTakenPosition(Ship newShip) {
        if (takenPosition.size() > 0) {
            if (newShip.getShipCoordinates() != null) {
                for (Coordinates points : newShip.getShipCoordinates()) {
                    if (checkPosition(points, takenPosition))
                        return true;
                }
            }
        }
        return false;
    }


    private boolean checkPosition(Coordinates point, List<Coordinates> table) {

        for (Coordinates points : table) {
            if (points.getxCoordinate() == point.getxCoordinate() && points.getyCoordinate() == point.getyCoordinate())
                return true;
        }
        return false;
    }


    public void setRandomShips(int[] shipsLengthTable) {
        boolean isAdd = false;
        int counter;
        for (int i = 0; i < shipsLengthTable.length; i++) {
            counter = 0;
            while (!isAdd) {
                Ship newShip = randomShip(shipsLengthTable[i]);
                isAdd = add(newShip);
                if (counter++ > 500) {
                    System.out.println("Nie można utworzyć planszy");
                    System.exit(1);
                }
            }
            isAdd = false;
        }
    }

    private Ship randomShip(int newShipSize) {
        Ship newShip;
        String number;
        String startingPoint;
        String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int randomValue;

        do {
            if (random.nextInt(2) == 0) {
                randomValue = random.nextInt(boardSize) + 1;
                String character = abc.substring(randomValue - 1, randomValue);
                number = String.valueOf(random.nextInt(boardSize )+ 1) ;
                startingPoint = character + number;
                newShip = new Ship(startingPoint, newShipSize, "H", boardSize);
            } else {
                randomValue = random.nextInt(boardSize) + 1;
                String character = abc.substring(randomValue - 1, randomValue);
                number = String.valueOf(random.nextInt(boardSize ) + 1);
                startingPoint = character + number;
                newShip = new Ship(startingPoint, newShipSize, "V", boardSize);
            }
        } while (newShip.getShipCoordinates() == null);
        return newShip;
    }


}

