package BattleShipsGame.Logic;


import java.util.Scanner;

public class WarshipGame {
    Scanner scanner = new Scanner(System.in);

    public void startGame() {
        boolean isFillGood = false;
        System.out.println("\n\t~\t~\tB A T T L E S H I P\t\tG A M E\t~\t~\n");
        do {
            System.out.println("Rozpoczynamy gre, wybierz: '1' - gra jednosobowa\t'2' - gra dwuosobowa\t'3' - info\t'4' - exit");
            String text = scanner.nextLine();
            switch (text) {
                case "1":
                    //startSingleGame
                    System.out.println("Wybrano grę jednoosobowa, zaczynamy !");
                    SingleGame singleGame = new SingleGame();
                    singleGame.startGame();
                    isFillGood = true;
                    break;
                case "2":
                    System.out.println("Wybrano gre dwuosobową, zaczynamy !");
                    //startMultiGame
                    MultiGame game = new MultiGame();
                    game.startGame();
                    isFillGood = true;
                    break;
                case "3":
                    for (int i =0;i<25;i++)
                        System.out.print("***\t");
                    System.out.println("\n");
                    System.out.print("X - niecelny strzał\t\tO - trafiony statek\t\t# - statek zatopiony\n");
                    System.out.println("'Gra customowa':\tplansza 10x10,\t5 statków o wielkości: 5,4,3,3,2");
                    System.out.println("'Własne ustawienia':\twielkość planszy,\tilość statków,\t" +
                            "wielkość statków,\tustawienie statków\t");
                    System.out.println("Autor: Paweł Sosulski\n");
                    for (int i =0;i<25;i++)
                        System.out.print("***\t");
                    System.out.println("\n\n\n");
                    break;
                case "4":
                    System.out.println("Wyjście z programu");
                    System.exit(1);
                    break;
                default:
                    System.out.println("Zły wybór, spróbuj ponownie");
                    isFillGood = false;
            }
        } while (!isFillGood);
    }

}
