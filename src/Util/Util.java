package Util;

import Algorithms.*;

public class Util {
    public static int findMaxValueIndex(BaseSort array, int upToIndex) {
        int maxIndex = 0;
        for (int i = 0; i < upToIndex; i++) {
            if (array.getValue(i) > array.getValue(maxIndex)) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}
