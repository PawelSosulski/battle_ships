package BattleShipsGame.Helpers;

import BattleShipsGame.Models.Coordinates;

import java.util.Comparator;

public class ComparatorHorizontal implements Comparator<Coordinates> {

    @Override
    public int compare(Coordinates o1, Coordinates o2) {
        if (o1.getxCoordinate()<o2.getxCoordinate())
            return -1;
        else if (o1.getxCoordinate()>o2.getxCoordinate())
            return 1;
        else
            return 0;
    }
}
