package inc.premzl.Lists;

import java.util.ArrayList;
import java.util.List;

public class ListOperations {
    public static List<Integer> findIndexes(String haystack, String needle) {
        List<Integer> indexes = new ArrayList<>();
        int index = 0;

        while (true) {
            index = haystack.indexOf(needle, index);

            if (index == -1) break;

            indexes.add(index);
            ++index;
        }

        return indexes;
    }
}
