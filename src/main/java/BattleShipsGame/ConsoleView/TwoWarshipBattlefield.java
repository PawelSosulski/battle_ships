package BattleShipsGame.ConsoleView;

import BattleShipsGame.Logic.PrepareCompGame;
import BattleShipsGame.Logic.PrepareMultiGame;

public class TwoWarshipBattlefield {

    private WarshipBattlefield oneBattleField;
    private WarshipBattlefield twoBattleField;
    private String[][] oneBoard;
    private String[][] twoBoard;
    private String oneName;
    private String twoName;
    private String oneFieldText;
    private String twoFieldText;


    public TwoWarshipBattlefield(PrepareMultiGame multigame) {
        this.oneBattleField = multigame.getOneBattleField();
        this.twoBattleField = multigame.getTwoBattleField();
        oneBoard = oneBattleField.getBoard();
        twoBoard = twoBattleField.getBoard();
        this.oneName = multigame.getPlayerOneName();
        this.twoName = multigame.getPlayerTwoName();
        oneFieldText = "Player one: " + oneName + " field.";
        twoFieldText = "Player two: " + twoName + " field.";
    }

    public TwoWarshipBattlefield(PrepareCompGame compGame) {
        this.oneBattleField = compGame.getPlayerBattleField();
        this.twoBattleField = compGame.getCompBattleField();
        oneBoard = oneBattleField.getBoard();
        twoBoard = twoBattleField.getBoard();
        this.oneName = compGame.getPlayerName();
        this.twoName = "Computer";
        oneFieldText = oneName + " field.";
        twoFieldText = "\t\t"+twoName + " field.";
    }


    public void showFields() {
        System.out.println();
        System.out.print(oneFieldText);
        for (int i = 0; i < oneBoard.length-1; i++)
            System.out.print("\t");
        System.out.print(twoFieldText);
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
