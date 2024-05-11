/**
 * @file CDisplay.java
 * @author T22CS044 Itsuki Hosaka
 * @version 1.0.0
 */
package consolegui;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Vector;

/**
 * コンソール画面制御クラス
 */
public class CDisplay {
    private DrawCell[][] Screen; // 描画領域
    private int CameraX; // 表示場所x座標
    private int CameraY; // 表示場所y座標
    private int Width; // 幅
    private int Height; // 高さ
    private LinkedList<CObject> ObjectsList; // 描画オブジェクトリスト

    /**
     * 背景色のマップ
     */
    private Map<Integer, String> BackGroundColorMap = new HashMap<Integer, String>() {
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
    private Map<Integer, String> WordColorMap = new HashMap<Integer, String>() // Mapの宣言、初期化
    {
        {
            put(0, "\u001B[31m"); // デフォルト
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
     * 描画メモリのクリア
     */
    private void Clear() {
        for (int i = 0; i < Width; i++) {
            for (int j = 0; j < Height; j++) {
                Screen[j][i] = new DrawCell(' ', 0, 0);
            }
        }
    }

    /**
     * 各描画オブジェクトから描画情報を取得し、描画メモリに反映
     */
    private void ViewDataImport() {
        for (CObject obj : ObjectsList) {
            if (obj.IsVisible) {
                Vector<Vector<DrawCell>> Model = obj.GetCostumeData();
                for (int i = 0; i < Model.size(); i++) {
                    for (int j = 0; j < Model.get(i).size(); j++) {
                        if (obj.getX() + j >= CameraX && obj.getX() + j < CameraX + Width && obj.getY() + i >= CameraY
                                && obj.getY() + i < CameraY + Height) {
                            Screen[obj.getY() + i - CameraY][obj.getX() + j - CameraX] = Model.get(i).get(j);
                        }
                    }
                }
            }
        }
    }

    /**
     * 描画メモリの内容を画面に出力
     */
    private void PrintDisplay() {
        StringBuffer Display = new StringBuffer();
        Display.append("\033[H\033[2J"); // 画面クリア
        for (int j = 0; j < Height; j++) {
            int bg = -1, wc = -1;
            for (int i = 0; i < Width; i++) {
                if (bg != Screen[j][i].getBgc()) {
                    Display.append(BackGroundColorMap.get(Screen[j][i].getBgc()));
                    bg = Screen[j][i].getBgc();
                }
                if (wc != Screen[j][i].getWc()) {
                    Display.append(WordColorMap.get(Screen[j][i].getWc()));
                    wc = Screen[j][i].getWc();
                }
                Display.append(Screen[j][i].getW());
            }
            Display.append("\u001B[0m"); // 色のリセット
            Display.append("\n"); // 改行
        }
        System.out.flush();
        System.out.print(Display);
    }

    /**
     * コンストラクター
     * 
     * @param width  画面の幅
     * @param height 画面の高さ
     */
    public CDisplay(int width, int height) {
        Width = width;
        Height = height;
        Screen = new DrawCell[Height][Width];
        ObjectsList = new LinkedList<CObject>();
        System.out.print("\033[?25l"); // カーソル非表示
        Runtime.getRuntime().addShutdownHook(new Thread(
                () -> {
                    System.out.println("\033[?25h");
                    System.out.print("\u001B[0m"); // 色のリセット
                    System.out.print("\033[H\033[2J"); // 画面クリア
                }));// シャットダウンフックの登録
    }

    /**
     * 描画オブジェクトの追加．二重で追加した場合は例外を投げる
     * 
     * @param obj 追加する描画オブジェクト
     */
    public void AddObject(CObject obj) {
        if (IsRegistrationObject(obj))
            throw new IllegalArgumentException(this.getClass().getName() + " : 既に追加されているオブジェクトです");
        ObjectsList.add(obj);
        return;
    }

    /**
     * 描画オブジェクトの追加．二重で追加した場合は例外を投げる
     * 
     * @param obj   追加する描画オブジェクト
     * @param order 描画順序
     */
    public void AddObject(CObject obj, int order) {
        if (IsRegistrationObject(obj))
            throw new IllegalArgumentException(this.getClass().getName() + " : 既に追加されているオブジェクトです");
        ObjectsList.add(order, obj);
        return;
    }

    /**
     * 描画オブジェクトの削除
     * 
     * @param obj 削除する描画オブジェクト
     */
    public void RemoveObject(CObject obj) {
        if (!IsRegistrationObject(obj))
            throw new IllegalArgumentException(this.getClass().getName() + " : 管理されていないオブジェクトです");
        ObjectsList.remove(obj);
        return;
    }

    /**
     * 描画オブジェクトの描画順序の変更．管理されていないオブジェクトを変更しようとした場合は例外を投げる
     * 最背面にする際は0を入力します．最前面にするには-1を入力します．
     * 
     * @param obj   変更する描画オブジェクト
     * @param order 変更後の描画順序
     */
    public void ChangeObjectDrawingOrder(CObject obj, int order) {
        if (!IsRegistrationObject(obj))
            throw new IllegalArgumentException(this.getClass().getName() + " : 管理されていないオブジェクトです");
        else {
            RemoveObject(obj);
            if (order == 0)
                AddObject(obj, 0);
            else if (order >= ObjectsList.size() || order == -1)
                AddObject(obj);
            else if(order > 0)
                AddObject(obj, order);
            else
                throw new IllegalArgumentException(this.getClass().getName() + " : 不正な描画順序です");
        }
        return;
    }

    /**
     * 描画オブジェクトの描画順序の問い合わせ．管理されていないオブジェクトを問い合わせた場合は例外を投げる
     * 
     * @param obj 問い合わせる描画オブジェクト
     * @return 描画順序
     */
    public int getObjectDrawingOrder(CObject obj) {
        if (!IsRegistrationObject(obj))
            throw new IllegalArgumentException(this.getClass().getName() + " : 管理されていないオブジェクトです");
        return ObjectsList.indexOf(obj);
    }

    /**
     * 描画オブジェクト登録済みであるかの問い合わせ
     * 
     * @param obj 問い合わせる描画オブジェクト
     * @return 登録されているか否かを返す
     */
    public boolean IsRegistrationObject(CObject obj) {
        return ObjectsList.contains(obj);
    }

    /**
     * 画面の更新を受け付ける
     */
    public void Update() {
        Clear();
        ViewDataImport();
        PrintDisplay();
    }

    /**
     * 画面の幅を取得
     * 
     * @return 画面の幅
     */
    public int getWidth() {
        return Width;
    }

    /**
     * 画面の高さを取得
     * 
     * @return 画面の高さ
     */
    public int getHeight() {
        return Height;
    }

}
