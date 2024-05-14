package consolegui;

import java.util.HashMap;
import java.util.Map;

/**
 *　CDisplayで使用する色の定義クラス 
 *  色の定数を提供し，エスケープシーケンスとの変換を行います．
 */ 
public final class CColor {
    /**
     * 色をデフォルトにします．デフォルトは使用する側の設定に従うことを要請しています.
     * CDisplayでは0を指定し，透過処理を有効化した際には，一つ下のレイヤーにある色を使用します．
     */
    public static final int DEFAULT = 0;
    public static final int BLACK = 1;
    public static final int RED = 2;
    public static final int GREEN = 3;
    public static final int YELLOW = 4;
    public static final int BLUE = 5;
    public static final int PURPLE = 6;
    public static final int CYAN = 7;
    public static final int WHITE = 8;

    private static Map<Integer, String> BackGroundColorMap = new HashMap<Integer, String>() {
        {
            put(0, "\u001B[47m"); // デフォルト
            put(1, "\u001B[40m"); // 黒
            put(2, "\u001B[41m"); // 赤
            put(3, "\u001B[42m"); // 緑
            put(4, "\u001B[43m"); // 黄
            put(5, "\u001B[44m"); // 青
            put(6, "\u001B[45m"); // 紫
            put(7, "\u001B[46m"); // 水
            put(8, "\u001B[47m"); // 白
        }
    };
    /**
     * 文字色のマップ
     */
    private static Map<Integer, String> WordColorMap = new HashMap<Integer, String>() // Mapの宣言、初期化
    {
        {
            put(0, "\u001B[30m"); // デフォルト
            put(1, "\u001B[30m"); // 黒
            put(2, "\u001B[31m"); // 赤
            put(3, "\u001B[32m"); // 緑
            put(4, "\u001B[33m"); // 黄
            put(5, "\u001B[34m"); // 青
            put(6, "\u001B[35m"); // 紫
            put(7, "\u001B[36m"); // 水
            put(8, "\u001B[37m"); // 白
        }
    };

    /**
     * 背景色のエスケープシーケンスを取得します．
     * @param color 色の定数
     * @return 背景色のエスケープシーケンス
     */
    public static String getBackGroundColor(int color) {
        return BackGroundColorMap.get(color);
    }
    
    /**
     * 文字色のエスケープシーケンスを取得します．
     * @param color 色の定数
     * @return 文字色のエスケープシーケンス
     */
    public static String getWordColor(int color) {
        return WordColorMap.get(color);
    }
}
