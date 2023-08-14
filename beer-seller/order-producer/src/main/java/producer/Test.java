package producer;

class Solution {
    public String mergeAlternately(String word1, String word2) {
        char[] word1List = word1.toCharArray();
        char[] word2List = word2.toCharArray();
        boolean word1gr = word1.length() >= word2.length();
        int i;
        StringBuilder ab = new StringBuilder();
        for (i = 0; i < word1List.length; i++) {
            if (i > word2List.length - 1) {
                break;
            }
            ab.append(word1List[i]);
            ab.append(word2List[i]);
        }
        if (word1gr) {
            while (i < word1List.length) {
                ab.append(word1List[i++]);
            }
        }
        else {
            while (i < word2List.length) {
                ab.append(word2List[i]);
            }
        }
        return ab.toString();
    }
}

public class Test {
    public static void main(String[] args) {
        System.out.println(new Solution().mergeAlternately("ab", "pqrs"));
    }
}
