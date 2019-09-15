package BattleShipsGame.Logic;

import java.util.HashMap;
import java.util.Map;

public class Alphabet {
    private Map<String, Integer> alphabet;


    public Alphabet() {

        alphabet= new HashMap<String, Integer>();
        fillAlphabet();
    }

    private void fillAlphabet() {
        char letter = 65;
        Integer number = 1;
        for (int i = 0; i < 26; i++) {
            alphabet.put(Character.toString(letter++), number++);
        }
    }

    public Integer getNumberFromLetter (String letter) {
        return alphabet.get(letter);
    }
}
