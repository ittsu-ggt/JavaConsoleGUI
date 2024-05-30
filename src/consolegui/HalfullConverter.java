/**
 * @file HalfullConverter.java
 * @author T22CS044 Itsuki Hosaka
 * @version 1.0.0
 */
package consolegui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * 半角文字と全角文字を相互に変換するクラス．
 */
public class HalfullConverter {
    private static class Halfull {
        private char half;
        private char full;

        public Halfull(char half, char full) {
            this.half = half;
            this.full = full;
        }

        public char getHalf() {
            return this.half;
        }

        public char getFull() {
            return this.full;
        }
    }

    private static ArrayList<Halfull> halfullList = new ArrayList<Halfull>() {
        {
            add(new Halfull('0', '０'));
            add(new Halfull('1', '１'));
            add(new Halfull('2', '２'));
            add(new Halfull('3', '３'));
            add(new Halfull('4', '４'));
            add(new Halfull('5', '５'));
            add(new Halfull('6', '６'));
            add(new Halfull('7', '７'));
            add(new Halfull('8', '８'));
            add(new Halfull('9', '９'));
            add(new Halfull('A', 'Ａ'));
            add(new Halfull('B', 'Ｂ'));
            add(new Halfull('C', 'Ｃ'));
            add(new Halfull('D', 'Ｄ'));
            add(new Halfull('E', 'Ｅ'));
            add(new Halfull('F', 'Ｆ'));
            add(new Halfull('G', 'Ｇ'));
            add(new Halfull('H', 'Ｈ'));
            add(new Halfull('I', 'Ｉ'));
            add(new Halfull('J', 'Ｊ'));
            add(new Halfull('K', 'Ｋ'));
            add(new Halfull('L', 'Ｌ'));
            add(new Halfull('M', 'Ｍ'));
            add(new Halfull('N', 'Ｎ'));
            add(new Halfull('O', 'Ｏ'));
            add(new Halfull('P', 'Ｐ'));
            add(new Halfull('Q', 'Ｑ'));
            add(new Halfull('R', 'Ｒ'));
            add(new Halfull('S', 'Ｓ'));
            add(new Halfull('T', 'Ｔ'));
            add(new Halfull('U', 'Ｕ'));
            add(new Halfull('V', 'Ｖ'));
            add(new Halfull('W', 'Ｗ'));
            add(new Halfull('X', 'Ｘ'));
            add(new Halfull('Y', 'Ｙ'));
            add(new Halfull('Z', 'Ｚ'));
            add(new Halfull('a', 'ａ'));
            add(new Halfull('b', 'ｂ'));
            add(new Halfull('c', 'ｃ'));
            add(new Halfull('d', 'ｄ'));
            add(new Halfull('e', 'ｅ'));
            add(new Halfull('f', 'ｆ'));
            add(new Halfull('g', 'ｇ'));
            add(new Halfull('h', 'ｈ'));
            add(new Halfull('i', 'ｉ'));
            add(new Halfull('j', 'ｊ'));
            add(new Halfull('k', 'ｋ'));
            add(new Halfull('l', 'ｌ'));
            add(new Halfull('m', 'ｍ'));
            add(new Halfull('n', 'ｎ'));
            add(new Halfull('o', 'ｏ'));
            add(new Halfull('p', 'ｐ'));
            add(new Halfull('q', 'ｑ'));
            add(new Halfull('r', 'ｒ'));
            add(new Halfull('s', 'ｓ'));
            add(new Halfull('t', 'ｔ'));
            add(new Halfull('u', 'ｕ'));
            add(new Halfull('v', 'ｖ'));
            add(new Halfull('w', 'ｗ'));
            add(new Halfull('x', 'ｘ'));
            add(new Halfull('y', 'ｙ'));
            add(new Halfull('z', 'ｚ'));
            add(new Halfull('!', '！'));
            add(new Halfull('"', '”'));
            add(new Halfull('#', '＃'));
            add(new Halfull('$', '＄'));
            add(new Halfull('%', '％'));
            add(new Halfull('&', '＆'));
            add(new Halfull('\'', '’'));
            add(new Halfull('(', '（'));
            add(new Halfull(')', '）'));
            add(new Halfull('*', '＊'));
            add(new Halfull('+', '＋'));
            add(new Halfull(',', '，'));
            add(new Halfull('-', '－'));
            add(new Halfull('.', '．'));
            add(new Halfull('/', '／'));
            add(new Halfull(':', '：'));
            add(new Halfull(';', '；'));
            add(new Halfull('<', '＜'));
            add(new Halfull('=', '＝'));
            add(new Halfull('>', '＞'));
            add(new Halfull('?', '？'));
            // add(new Halfull('^', '∧'));
            add(new Halfull('@', '＠'));
            add(new Halfull('[', '［'));
            add(new Halfull('\\', '￥'));
            add(new Halfull(']', '］'));
            add(new Halfull('^', '＾'));
            add(new Halfull('_', '＿'));
            add(new Halfull('`', '‘'));
            add(new Halfull('{', '｛'));
            add(new Halfull('|', '｜'));
            add(new Halfull('}', '｝'));
            add(new Halfull('~', '～'));
            add(new Halfull(' ', '　'));
        }
    };

    private static Map<Character, Character> HalfToFullMap = new HashMap<Character, Character>() {
        {
            for (Halfull h : halfullList) {
                put(h.getHalf(), h.getFull());
            }
        }
    };
    private static Map<Character, Character> FullToHalfMap = new HashMap<Character, Character>() {
        {
            for (Halfull h : halfullList) {
                put(h.getFull(), h.getHalf());
            }
        }
    };

    /**
     * 半角文字列を全角文字列に変換する．なお変換できない文字はそのまま出力する．
     * 
     * @param half 半角文字列
     * @return 全角文字列
     */
    public static String halfToFull(String half) {
        StringBuilder full = new StringBuilder();
        for (int i = 0; i < half.length(); i++) {
            char c = half.charAt(i);
            full.append(halfToFull(c));
        }
        return full.toString();
    }

    /**
     * 全角文字列を半角文字列に変換する．なお変換できない文字はそのまま出力する．
     * 
     * @param full 全角文字列
     * @return 半角文字列
     */
    public static String fullToHalf(String full) {
        StringBuilder half = new StringBuilder();
        for (int i = 0; i < full.length(); i++) {
            char c = full.charAt(i);
            half.append(fullToHalf(c));
        }
        return half.toString();
    }

    /**
     * 半角文字列を全角文字列に変換する．なお変換できない文字が含まれている場合は例外を投げる．
     * 
     * @param half 半角文字列
     * @return 全角文字列
     */
    public static String halfToFullStrict(String half) {
        StringBuilder full = new StringBuilder();
        for (int i = 0; i < half.length(); i++) {
            char c = half.charAt(i);
            full.append(halfToFullStrict(c));
        }
        return full.toString();
    }

    /**
     * 全角文字列を半角文字列に変換する．なお変換できない文字が含まれている場合は例外を投げる．
     * 
     * @param full 全角文字列
     * @return 半角文字列
     */
    public static String fullToHalfStrict(String full) {
        StringBuilder half = new StringBuilder();
        for (int i = 0; i < full.length(); i++) {
            char c = full.charAt(i);
            half.append(fullToHalfStrict(c));
        }
        return half.toString();
    }

    /**
     * 半角文字を全角文字に変換する
     * 
     * @param half 半角文字
     * @return 全角文字
     */
    public static char halfToFull(char half) {
        return HalfToFullMap.getOrDefault(half, half);
    }

    /**
     * 全角文字を半角文字に変換する
     * 
     * @param full 全角文字
     * @return 半角文字
     */
    public static char fullToHalf(char full) {
        return FullToHalfMap.getOrDefault(full, full);
    }

    /**
     * 半角文字を全角文字に変換する．変換できない文字が含まれている場合は例外を投げる．
     * 
     * @param half 半角文字
     * @return 全角文字
     */
    public static char halfToFullStrict(char half) {
        var t = HalfToFullMap.get(half);
        if (t != null) {
            return t;
        }
        throw new IllegalArgumentException("HalfToFullConverter :  変換できない文字です" + half);
    }

    /**
     * 全角文字を半角文字に変換する．変換できない文字が含まれている場合は例外を投げる．
     * 
     * @param full 全角文字
     * @return 半角文字
     */
    public static char fullToHalfStrict(char full) {
        var t = FullToHalfMap.get(full);
        if (t != null) {
            return t;
        }
        throw new IllegalArgumentException("HalfToFullConverter :  変換できない文字です" + full);
    }
}
