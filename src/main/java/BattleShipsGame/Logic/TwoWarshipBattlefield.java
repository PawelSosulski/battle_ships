package BattleShipsGame.Logic;

import java.util.List;
import java.util.Map;

public class TwoWarshipBattlefield {

    private WarshipBattlefield oneBattleField;
    private WarshipBattlefield twoBattleField;
    private String[][] oneBoard;
    private String[][] twoBoard;
    private String oneName;
    private String twoName;

    public TwoWarshipBattlefield(PrepareMultiGame multigame) {
        this.oneBattleField = multigame.getOneBattleField();
        this.twoBattleField = multigame.getTwoBattleField();
        oneBoard = oneBattleField.getBoard();
        twoBoard = twoBattleField.getBoard();
        this.oneName = multigame.getPlayerOneName();
        this.twoName = multigame.getPlayerTwoName();
    }


    public void showFields() {
        System.out.println();
        System.out.print("Player one: " + oneName + " field.");
        for (int i = 0; i < oneBoard.length-1; i++)
            System.out.print("\t");
        System.out.print("Player two: " + twoName + " field.");
        System.out.println();

        for (int i = 0; i < oneBoard.length; i++) {
            for (int j = 0; j < oneBoard.length; j++) {
                System.out.print(oneBoard[i][j] + "\t");
            }
            System.out.print("\t \t \t \t");
            for (int j = 0; j < twoBoard.length; j++) {
                System.out.print(twoBoard[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }


}
