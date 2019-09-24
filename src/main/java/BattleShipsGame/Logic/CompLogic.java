package BattleShipsGame.Logic;

import BattleShipsGame.Helpers.ComparatorHorizontal;
import BattleShipsGame.Helpers.ComparatorVertical;
import BattleShipsGame.Models.Coordinates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CompLogic {

    private int fieldSize;
    private ArrayList<Coordinates> notToShoot;
    private ArrayList<Coordinates> placeToShoot;
    private Random random = new Random();
    private ArrayList<Coordinates> currentShipUnderAttack;
    private String directionOfShipUnderAttack = "";

    public CompLogic(int fieldSize) {
        this.fieldSize = fieldSize;
        notToShoot = new ArrayList<>();
        placeToShoot = new ArrayList<>();
        currentShipUnderAttack = new ArrayList<>();
        for (int i = 0; i <= fieldSize; i++) {
            notToShoot.add(new Coordinates(i, 0));
            notToShoot.add(new Coordinates(0, i));
            notToShoot.add(new Coordinates(fieldSize + 1, i));
            notToShoot.add(new Coordinates(i, fieldSize + 1));

        }
    }

    public Coordinates getPoint() {
        boolean isComplete = false;
        Coordinates newPoint = new Coordinates();
        if (placeToShoot.size() > 0) {
            isComplete = true;
            int number = random.nextInt(placeToShoot.size());
            newPoint = placeToShoot.get(number);
            placeToShoot.remove(number);
        }
        while (!isComplete) {
            int x = random.nextInt(fieldSize) + 1;
            int y = random.nextInt(fieldSize) + 1;
            newPoint.setxCoordinate(x);
            newPoint.setyCoordinate(y);
            if (!newPoint.checkPosition(notToShoot))
                isComplete = true;
        }
        notToShoot.add(newPoint);
        return newPoint;
    }

    public void setPotentiallyPlace(Boolean isShot, Boolean isSink, Coordinates point) {
        if (isShot) {
            int x = point.getxCoordinate();
            int y = point.getyCoordinate();
            currentShipUnderAttack.add(new Coordinates(y, x));
            if (currentShipUnderAttack.size() == 1) {
                firstElementOfShipCompletePlaceToShoot(x, y);
            } else if (currentShipUnderAttack.size() == 2) {
                secondElementOfShipRefactorPlaceToShoot();
            } else {
                nextElementOfShipCompletePlaceToShoot(x, y);
            }
        }
        if (isSink) {
            placeToShoot.clear();
            completeNotToShootAfterImmersion();
            directionOfShipUnderAttack = "";
            currentShipUnderAttack.clear();
        }
    }

    private void nextElementOfShipCompletePlaceToShoot(int x, int y) {
        if (directionOfShipUnderAttack.equals("v")) {
            if (!new Coordinates(y - 1, x).checkPosition(notToShoot)) {
                placeToShoot.add(new Coordinates(y - 1, x));
            }
            if (!new Coordinates(y + 1, x).checkPosition(notToShoot)) {
                placeToShoot.add(new Coordinates(y + 1, x));
            }
        } else {
            if (!new Coordinates(y, x - 1).checkPosition(notToShoot)) {
                placeToShoot.add(new Coordinates(y, x - 1));
            }
            if (!new Coordinates(y, x + 1).checkPosition(notToShoot)) {
                placeToShoot.add(new Coordinates(y, x + 1));
            }
        }
    }

    private void secondElementOfShipRefactorPlaceToShoot() {
        placeToShoot.clear();
        if (currentShipUnderAttack.get(0).getxCoordinate() == currentShipUnderAttack.get(1).getxCoordinate()) {
            int y0;
            int y1;
            int x = currentShipUnderAttack.get(0).getxCoordinate();
            if (currentShipUnderAttack.get(0).getyCoordinate() < currentShipUnderAttack.get(1).getyCoordinate()) {
                y0 = currentShipUnderAttack.get(0).getyCoordinate();
                y1 = currentShipUnderAttack.get(1).getyCoordinate();
            } else {
                y0 = currentShipUnderAttack.get(1).getyCoordinate();
                y1 = currentShipUnderAttack.get(0).getyCoordinate();
            }
            if (y0 - 1 > 0) {
                if (!new Coordinates(y0 - 1, x).checkPosition(notToShoot)) {
                    placeToShoot.add(new Coordinates(y0 - 1, x));
                }
            }
            if (y1 + 1 <= fieldSize) {
                if (!new Coordinates(y1 + 1, x).checkPosition(notToShoot)) {
                    placeToShoot.add(new Coordinates(y1 + 1, x));
                }
            }
            directionOfShipUnderAttack = "v";
        } else {
            int x0;
            int x1;
            int y = currentShipUnderAttack.get(0).getyCoordinate();
            if (currentShipUnderAttack.get(0).getxCoordinate() < currentShipUnderAttack.get(1).getxCoordinate()) {
                x0 = currentShipUnderAttack.get(0).getxCoordinate();
                x1 = currentShipUnderAttack.get(1).getxCoordinate();
            } else {
                x0 = currentShipUnderAttack.get(1).getxCoordinate();
                x1 = currentShipUnderAttack.get(0).getxCoordinate();
            }
            if (x0 - 1 > 0) {
                if (!new Coordinates(y, x0 - 1).checkPosition(notToShoot)) {
                    placeToShoot.add(new Coordinates(y, x0 - 1));
                }
            }
            if (x1 + 1 <= fieldSize) {
                if (!new Coordinates(y, x1 + 1).checkPosition(notToShoot)) {
                    placeToShoot.add(new Coordinates(y, x1 + 1));
                }
            }
            directionOfShipUnderAttack = "h";
        }
    }


    private void firstElementOfShipCompletePlaceToShoot(int x, int y) {
        if (x - 1 > 0) {
            if (!new Coordinates(y, x - 1).checkPosition(notToShoot)) {
                placeToShoot.add(new Coordinates(y, x - 1));
            }
        }
        if (x + 1 <= fieldSize) {
            if (!new Coordinates(y, x + 1).checkPosition(notToShoot)) {
                placeToShoot.add(new Coordinates(y, x + 1));
            }
        }
        if (y - 1 > 0) {
            if (!new Coordinates(y - 1, x).checkPosition(notToShoot)) {
                placeToShoot.add(new Coordinates(y - 1, x));
            }
        }
        if (y + 1 <= fieldSize) {
            if (!new Coordinates(y + 1, x).checkPosition(notToShoot)) {
                placeToShoot.add(new Coordinates(y + 1, x));
            }
        }
    }


    private void completeNotToShootAfterImmersion() {
        if (directionOfShipUnderAttack.equals("h")) {
            Collections.sort(currentShipUnderAttack, new ComparatorHorizontal());
        } else {
            Collections.sort(currentShipUnderAttack, new ComparatorVertical());
        }

        for (int position = 0; position < currentShipUnderAttack.size(); position++) {
            Coordinates point = currentShipUnderAttack.get(position);
            if (directionOfShipUnderAttack.equals("h")) {
                if (point.getyCoordinate() == 1) {
                    notToShoot.add(new Coordinates(point.getyCoordinate() + 1, point.getxCoordinate()));
                } else if (point.getyCoordinate() == fieldSize) {
                    notToShoot.add(new Coordinates(point.getyCoordinate() - 1, point.getxCoordinate()));
                } else {
                    notToShoot.add(new Coordinates(point.getyCoordinate() - 1, point.getxCoordinate()));
                    notToShoot.add(new Coordinates(point.getyCoordinate() + 1, point.getxCoordinate()));
                }

                if (point.getxCoordinate() != 1 && position == 0) {
                    notToShoot.add(new Coordinates(point.getyCoordinate(),
                            point.getxCoordinate() - 1));
                    notToShoot.add(new Coordinates(point.getyCoordinate() - 1,
                            point.getxCoordinate() - 1));
                    notToShoot.add(new Coordinates(point.getyCoordinate() + 1,
                            point.getxCoordinate() - 1));
                }
                if (point.getxCoordinate() != fieldSize && position == currentShipUnderAttack.size() - 1) {
                    notToShoot.add(new Coordinates(point.getyCoordinate(),
                            point.getxCoordinate() + 1));
                    notToShoot.add(new Coordinates(point.getyCoordinate() - 1,
                            point.getxCoordinate() + 1));
                    notToShoot.add(new Coordinates(point.getyCoordinate() + 1,
                            point.getxCoordinate() + 1));
                }

            } else if (directionOfShipUnderAttack.equals("v")) {
                if (point.getxCoordinate() == 1) {
                    notToShoot.add(new Coordinates(point.getyCoordinate(),
                            point.getxCoordinate() + 1));
                } else if (point.getxCoordinate() == fieldSize) {
                    notToShoot.add(new Coordinates(point.getyCoordinate(),
                            point.getxCoordinate() - 1));
                } else {
                    notToShoot.add(new Coordinates(point.getyCoordinate(),
                            point.getxCoordinate() - 1));
                    notToShoot.add(new Coordinates(point.getyCoordinate(),
                            point.getxCoordinate() + 1));
                }
                if (point.getyCoordinate() != 1 && position == 0) {
                    notToShoot.add(new Coordinates(point.getyCoordinate() - 1,
                            point.getxCoordinate()));
                    notToShoot.add(new Coordinates(point.getyCoordinate() - 1,
                            point.getxCoordinate() - 1));
                    notToShoot.add(new Coordinates(point.getyCoordinate() - 1,
                            point.getxCoordinate() + 1));
                }
                if (point.getyCoordinate() != fieldSize && position == currentShipUnderAttack.size() - 1) {
                    notToShoot.add(new Coordinates(point.getyCoordinate() + 1,
                            point.getxCoordinate()));
                    notToShoot.add(new Coordinates(point.getyCoordinate() + 1,
                            point.getxCoordinate() - 1));
                    notToShoot.add(new Coordinates(point.getyCoordinate() + 1,
                            point.getxCoordinate() + 1));
                }
            }
        }

    }
}
