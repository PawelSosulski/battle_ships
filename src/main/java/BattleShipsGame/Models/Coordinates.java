package BattleShipsGame.Models;

import java.util.List;

public class Coordinates {
    private int xCoordinate;
    private int yCoordinate;

    public Coordinates(int yCoordinate, int xCoordinate) {
        this.yCoordinate = yCoordinate;
        this.xCoordinate = xCoordinate;
    }

    public Coordinates() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinates that = (Coordinates) o;

        if (xCoordinate != that.xCoordinate) return false;
        return yCoordinate == that.yCoordinate;
    }

    @Override
    public int hashCode() {
        int result = xCoordinate;
        result = 31 * result + yCoordinate;
        return result;
    }

    public boolean checkPosition(List<Coordinates> table) {

        for (Coordinates point : table) {
            if (getxCoordinate() == point.getxCoordinate() && getyCoordinate() == point.getyCoordinate())
                return true;
        }
        return false;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
}


