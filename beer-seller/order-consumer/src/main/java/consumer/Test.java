package consumer;

import java.util.Arrays;

class Solution {
    public int titleToNumber(String columnTitle) {
        int sum = 0, length = columnTitle.length();
        for (int i = 0; i < columnTitle.length(); i++) {
            int alpha = columnTitle.charAt(i) - 'A' + 1;
            sum += alpha * Math.pow(26, length - i - 1);
        }
        return sum;
    }
}

public class Test {
    public static void main(String[] args) {
        System.out.println(new Solution().titleToNumber("ABCD"));
    }
}
