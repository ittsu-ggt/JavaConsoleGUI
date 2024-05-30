/**
 * @file DrawCell.java
 * @author T22CS044 Itsuki Hosaka
 * @version 1.0.0
 */
package consolegui;

/**
 * 画面に表示する情報をセル毎保持するクラス
 */
public class DrawCell {
    /**
     * 文字
     */
    public char word;
    /**
     * 文字色
     */
    public int wordColor;
    /**
     * 背景色
     */
    public int bgColor;

    /**
     * コンストラクター
     * 
     * @param w   文字
     * @param bgc 背景色
     * @param wc  文字色
     */
    public DrawCell(char w, int wc, int bgc) {
        this.word = w;
        this.wordColor = wc;
        this.bgColor = bgc;
    }
}
