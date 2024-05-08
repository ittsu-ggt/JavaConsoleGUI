/**
 * @file DrawCell.java
 * @author T22CS044 Itsuki Hosaka
 * @version 1.0.0
 */
package consolegui;

public class DrawCell {
    private char w;
    private int wc;
    private int bgc;
    /**
     * コンストラクター
     * @param w 文字  
     * @param bgc 背景色
     * @param wc 文字色
     */
    public DrawCell(char w, int wc, int bgc) {
        this.w = w;
        this.wc = wc;
        this.bgc = bgc;
    }
    /**
     * 文字を取得
     * @return 文字
     */
    public char getW() {
        return w;
    }
    /**
     * 文字色を取得
     * @return 文字色
     */
    public int getWc() {
        return wc;
    }
    /**
     * 背景色を取得
     * @return 背景色
     */
    public int getBgc() {
        return bgc;
    }
}
