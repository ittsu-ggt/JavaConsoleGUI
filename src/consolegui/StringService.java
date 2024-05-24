/**
 * @file StringService.java
 * @author T22CS044 Itsuki Hosaka
 * @version 1.0.0
 * 
 */
package consolegui;

import java.util.ArrayList;

/**
 * Cobjectを利用して，文字列を表示します．本クラスを使用することにより，文字列に関する動作を更に簡単に行うことができます．
 */
public class StringService extends CObject {

    private String str; // 文字列
    private int SpriteColor; // 文字色
    private int BackGroundColor; // 背景色

    /**
     * コンストラクタ
     * 
     * @param CView           表示先
     * @param str             初期文字列
     * @param x               x座標
     * @param y               y座標
     * @param SpriteColor     文字色
     * @param BackGroundColor 背景色
     * @param isvisible       表示フラグ
     */
    public StringService(CDisplay CView, String str, int x, int y, int SpriteColor, int BackGroundColor,
            boolean isvisible) {
        super(CView, SpriteBuildService.BuildModel(new ArrayList<ArrayList<Character>>(' '), 0, 0), x, y, isvisible);
        this.str = str;
        this.SpriteColor = SpriteColor;
        this.BackGroundColor = BackGroundColor;
        reflection();
    }

    /**
     * コンストラクタ
     * 
     * @param CView           表示先
     * @param str             初期文字
     * @param x               x座標
     * @param y               y座標
     * @param SpriteColor     文字色
     * @param BackGroundColor 背景色
     * @param isvisible       表示フラグ
     */
    public StringService(CDisplay CView, char str, int x, int y, int SpriteColor, int BackGroundColor,
    boolean isvisible) {
        this(CView,String.valueOf(str),x,y,SpriteColor,BackGroundColor,isvisible);
    }


    private void reflection() {
        if (!this.IsExistCostume(str+SpriteColor+BackGroundColor)) {
            ArrayList<Character> tmp = new ArrayList<Character>();
            for (int i = 0; i < str.length(); i++) {
                tmp.add(str.charAt(i));
            }
            ArrayList<ArrayList<Character>> tmp2 = new ArrayList<ArrayList<Character>>();
            tmp2.add(tmp);
            this.AddCostume(str+SpriteColor+BackGroundColor, SpriteBuildService.BuildModel(tmp2, SpriteColor, BackGroundColor));
        }
        this.SwitchCostume(str+SpriteColor+BackGroundColor);
        return;
    }

    /**
     * 文字列を変更する
     * 
     * @param str 文字列
     */
    public void ChangeString(String str) {
        this.str = str;
        reflection();
    }

    /**
     * 文字を変更する
     * @param str 文字列
     */
    public void ChangeString(char str) {
        ChangeString(String.valueOf(str));
    }

    /**
     * 文字列を取得する
     * 
     * @return 文字列
     */
    public String getString() {
        return this.str;
    }

    /**
     * 文字色を取得する
     * 
     * @return 文字色
     */
    public int getSpriteColor() {
        return this.SpriteColor;
    }

    /**
     * 背景色を取得する
     * 
     * @return 背景色
     */
    public int getBackGroundColor() {
        return this.BackGroundColor;
    }

    /**
     * 文字色と背景色を変更する
     * 
     * @param SpriteColor     文字色
     * @param BackGroundColor 背景色
     */
    public void ChangeColor(int SpriteColor, int BackGroundColor) {
        this.SpriteColor = SpriteColor;
        this.BackGroundColor = BackGroundColor;
        reflection();
    }

    /**
     * 文字列と文字色と背景色を変更する
     * 
     * @param str             文字列
     * @param SpriteColor     文字色
     * @param BackGroundColor 背景色
     */
    public void ChangeAll(String str, int SpriteColor, int BackGroundColor) {
        this.str = str;
        this.SpriteColor = SpriteColor;
        this.BackGroundColor = BackGroundColor;
        reflection();
    }

}
