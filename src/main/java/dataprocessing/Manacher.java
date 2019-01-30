package dataprocessing;


/**
 * Manacher's algorithm
 *
 * When calculating the longest palindrome substring, only alphabetic characters are considered. E.g., "ab1ba" would give the length 4
 * A variable (maxValue) is keeping track of the longest palindrome length
 *
 * Credit:
 * https://algs4.cs.princeton.edu/53substring/Manacher.java.html
 */

public class Manacher {
    private int[] p;  // p[i] = length of longest palindromic substring of t, centered at i
    private String s;  // original string
    private char[] t;  // transformed string
    public int maxValue = 0;

    public Manacher(String s) {

        this.s = s.replaceAll("[^a-zA-Z]", "");
        preprocess();
        p = new int[t.length];

        int center = 0, right = 0;
        for (int i = 1; i < t.length - 1; i++) {
            int mirror = 2 * center - i;

            if (right > i)
                p[i] = Math.min(right - i, p[mirror]);

            // attempt to expand palindrome centered at i
            while (t[i + (1 + p[i])] == t[i - (1 + p[i])])
                p[i]++;

            if (p[i] >= maxValue) {
                maxValue = p[i];
            }

            // if palindrome centered at i expands past right,
            // adjust center based on expanded palindrome.
            if (i + p[i] > right) {
                center = i;
                right = i + p[i];
            }
        }


    }

    // Transform s into t.
    // For example, if s = "abba", then t = "$#a#b#b#a#@"
    // the # are interleaved to avoid even/odd-length palindromes uniformly
    // $ and @ are prepended and appended to each end to avoid bounds checking
    private void preprocess() {
        t = new char[s.length() * 2 + 3];
        t[0] = '$';
        t[s.length() * 2 + 2] = '@';
        for (int i = 0; i < s.length(); i++) {
            t[2 * i + 1] = '#';
            t[2 * i + 2] = s.charAt(i);
        }
        t[s.length() * 2 + 1] = '#';
    }

}
