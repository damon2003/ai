import java.util.*;

public class Main {
    public static void main(String[] args) {
        String[] words = { "TO", "GO", "OUT" };
        List<Character> unique = new ArrayList<>();

        for (String word : words) {
            for (char letter : word.toCharArray()) {
                if (!unique.contains(letter)) {
                    unique.add(letter);
                }
            }
        }
        int[] digits = new int[unique.size()];
        boolean[] used = new boolean[10];
        boolean found = solve(words, unique, digits, used, 0);

        if (found) {
            System.out.print("Solution Found");
            for (int i = 0; i < unique.size(); i++) {
                System.out.println(unique.get(i) + " = " + digits[i]);
            }
        } else {
            System.out.println("No Solution");
        }
    }

    static boolean solve(String[] words, List<Character> unique, int[] digits, boolean[] used, int index) {
        if (index == unique.size()) {
            return evaluate(words, unique, digits);
        }
        char letter = unique.get(index);
        for (int digit = 0; digit <= 9; digit++) {
            if (!used[digit]) {
                used[digit] = true;
                digits[index] = digit;
                if (solve(words, unique, digits, used, index + 1)) {
                    return true;
                }
                used[digit] = false;
            }
        }
        return false;
    }

    static boolean evaluate(String[] words, List<Character> unique, int[] digits) {
        int[] wordValue = new int[words.length];
        for (int i = 0; i < words.length; i++) {
            int value = 0;
            for (char letter : words[i].toCharArray()) {
                int index = unique.indexOf(letter);
                value = value * 10 + digits[index];
            }
            wordValue[i] = value;
        }
        int sum = 0;
        sum = wordValue[0] + wordValue[1];
        return (sum == wordValue[wordValue.length - 1]);
    }

}