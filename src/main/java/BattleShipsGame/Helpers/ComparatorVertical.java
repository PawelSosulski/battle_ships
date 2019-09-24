package BattleShipsGame.Helpers;

import BattleShipsGame.Models.Coordinates;

import java.util.Comparator;

public class ComparatorVertical implements Comparator<Coordinates> {
    @Override
    public int compare(Coordinates o1, Coordinates o2) {
        if (o1.getyCoordinate()<o2.getyCoordinate())
            return -1;
        else if (o1.getyCoordinate()>o2.getyCoordinate())
            return 1;
        else
            return 0;
    }
}
