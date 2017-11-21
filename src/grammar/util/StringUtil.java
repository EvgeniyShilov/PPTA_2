package grammar.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {

    public static List<Character> splitToChars(String string) {
        List<Character> charsList = new ArrayList<>();
        char[] chars = string.toCharArray();
        for (char c : chars)
            charsList.add(c);
        return charsList;
    }
}
