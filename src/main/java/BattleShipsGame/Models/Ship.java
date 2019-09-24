package BattleShipsGame.Models;

import BattleShipsGame.Helpers.Alphabet;

import java.util.*;
import java.util.regex.Pattern;

public class Ship {
    private Coordinates startPointCoordinates;
    private int shipSize;
    private int boardSize;
    private String direction;
    private String shipFulfillment;
    private List<Coordinates> shipCoordinates;
    private List<Coordinates> shipImpossiblePosition;
    private Alphabet alphabet = new Alphabet();
    private Map<Coordinates, Boolean> shipCoordinatesDuringShooting;
    private boolean isSink;


    public Ship(String startPoint, int shipSize, String directionHorV, int boardSize) {
        this.shipSize = shipSize;
        this.direction = directionHorV.toLowerCase();
        this.boardSize = boardSize;
        shipCoordinates = new ArrayList<>();
        shipFulfillment = "O";
        startPointCoordinates = startingPoint(startPoint);
        if (startPointCoordinates != null) {
            fillCoordinates(startPointCoordinates, shipSize, direction);
            shipImpossiblePosition = new ArrayList<>();
            shipCoordinatesDuringShooting = new HashMap<>();
        } else {
            shipCoordinates = null;
        }
    }

    public void setUpShip() {
        completeImpossiblePosition();
        completeCoordinatesDuringShooting();
    }

    private void completeCoordinatesDuringShooting() {
        for (int i = 0; i < shipCoordinates.size(); i++) {
            shipCoordinatesDuringShooting.put(shipCoordinates.get(i), false);
        }
    }

    public boolean checkIfSink() {
        Set<Coordinates> keySet = shipCoordinatesDuringShooting.keySet();
        for (Coordinates point : keySet) {
            if (!shipCoordinatesDuringShooting.get(point))
                return false;
        }
        shipFulfillment = "#";
        isSink = true;

        return true;
    }

    private Coordinates getStartingPoint() {
        return startPointCoordinates;
    }

    private Coordinates startingPoint(String startPoint) {
        Pattern pattern = Pattern.compile("[A-Z][1-9]");
        if (pattern.matcher(startPoint).find()) {

            Coordinates point = new Coordinates();
            int yValue = Integer.valueOf(startPoint.substring(1));
            int xValue = alphabet.getNumberFromLetter(startPoint.substring(0, 1));
            if (xValue > boardSize + 1 || yValue > boardSize + 1) {
                return null;
            } else {
                point.setyCoordinate(yValue);
                point.setxCoordinate(xValue);
            }
            return point;

            //System.out.printf("Nie wykonano; błędny format %s, wymagany format SYMBOL & CYFRA\n", startPoint);
        }

        return null;
    }

    public String getDirection() {
        return direction;
    }

    public String getShipFulfillment() {
        return shipFulfillment;
    }

    public int getShipSize() {
        return shipSize;
    }

    private void fillCoordinates(Coordinates startingPoint, int shipSize, String direction) {

        if (direction.toLowerCase().equals("h")) {
            if (startingPoint.getxCoordinate() + shipSize > boardSize + 1) {
                shipCoordinates = null;
                return;
            } else {
                for (int i = 0; i < shipSize; i++) {
                    shipCoordinates.add(
                            new Coordinates(startPointCoordinates.getyCoordinate(),
                                    startPointCoordinates.getxCoordinate() + i));
                }
            }
        } else if (direction.toLowerCase().equals("v")) {
            if (startingPoint.getyCoordinate() + shipSize > boardSize + 1) {
                shipCoordinates = null;
                return;
            } else {
                for (int i = 0; i < shipSize; i++) {
                    shipCoordinates.add(
                            new Coordinates(startPointCoordinates.getyCoordinate() + i,
                                    startPointCoordinates.getxCoordinate()));
                }
            }
        } else {
            shipCoordinates = null;
        }

    }


    public List<Coordinates> getShipCoordinates() {
        return shipCoordinates;
    }

    public List<Coordinates> getShipImpossiblePosition() {
        return shipImpossiblePosition;
    }

    public Map<Coordinates, Boolean> getShipCoordinatesDuringShooting() {
        return shipCoordinatesDuringShooting;
    }

    public void setRightShoot(Coordinates point) {
        shipCoordinatesDuringShooting.replace(point, true);
    }

    public void completeImpossiblePosition() {
        for (int position = 0; position < shipCoordinates.size(); position++) {
            Coordinates point = shipCoordinates.get(position);
            if (direction.equals("h")) {
                if (point.getyCoordinate() == 1) {
                    shipImpossiblePosition.add(new Coordinates(point.getyCoordinate() + 1, point.getxCoordinate()));
                } else if (point.getyCoordinate() == boardSize) {
                    shipImpossiblePosition.add(new Coordinates(point.getyCoordinate() - 1, point.getxCoordinate()));
                } else {
                    shipImpossiblePosition.add(new Coordinates(point.getyCoordinate() - 1, point.getxCoordinate()));
                    shipImpossiblePosition.add(new Coordinates(point.getyCoordinate() + 1, point.getxCoordinate()));
                }

                if (shipCoordinates.get(position).getxCoordinate() != 1 && position == 0) {
                    shipImpossiblePosition.add(new Coordinates(point.getyCoordinate(),
                            point.getxCoordinate() - 1));
                    shipImpossiblePosition.add(new Coordinates(point.getyCoordinate() - 1,
                            point.getxCoordinate() - 1));
                    shipImpossiblePosition.add(new Coordinates(point.getyCoordinate() + 1,
                            point.getxCoordinate() - 1));
                }
                if (point.getxCoordinate() != boardSize && position == shipSize - 1) {
                    shipImpossiblePosition.add(new Coordinates(point.getyCoordinate(),
                            point.getxCoordinate() + 1));
                    shipImpossiblePosition.add(new Coordinates(point.getyCoordinate() - 1,
                            point.getxCoordinate() + 1));
                    shipImpossiblePosition.add(new Coordinates(point.getyCoordinate() + 1,
                            point.getxCoordinate() + 1));
                }

            } else if (direction.equals("v")) {
                if (point.getxCoordinate() == 1) {
                    shipImpossiblePosition.add(new Coordinates(point.getyCoordinate(),
                            point.getxCoordinate() + 1));

                } else if (point.getxCoordinate() == boardSize) {
                    shipImpossiblePosition.add(new Coordinates(point.getyCoordinate(),
                            point.getxCoordinate() - 1));

                } else {
                    shipImpossiblePosition.add(new Coordinates(point.getyCoordinate(),
                            point.getxCoordinate() - 1));
                    shipImpossiblePosition.add(new Coordinates(point.getyCoordinate(),
                            point.getxCoordinate() + 1));
                }

                if (point.getyCoordinate() != 1 && position == 0) {
                    shipImpossiblePosition.add(new Coordinates(point.getyCoordinate() - 1,
                            point.getxCoordinate()));
                    shipImpossiblePosition.add(new Coordinates(point.getyCoordinate() - 1,
                            point.getxCoordinate() - 1));
                    shipImpossiblePosition.add(new Coordinates(point.getyCoordinate() - 1,
                            point.getxCoordinate() + 1));
                }
                if (shipCoordinates.get(position).getyCoordinate() != boardSize && position == shipSize - 1) {
                    shipImpossiblePosition.add(new Coordinates(point.getyCoordinate() + 1,
                            point.getxCoordinate()));
                    shipImpossiblePosition.add(new Coordinates(point.getyCoordinate() + 1,
                            point.getxCoordinate() - 1));
                    shipImpossiblePosition.add(new Coordinates(point.getyCoordinate() + 1,
                            point.getxCoordinate() + 1));
                }
            }
        }

    }


}



