/**
 * @file CDisplay.java
 * @author T22CS044 Itsuki Hosaka
 * @version 1.0.0
 */
package consolegui;

import java.util.LinkedList;
import java.io.IOException;
import java.util.ArrayList;

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
    private boolean IsFullWord; // 全角文字で使用するか
    private boolean Istransparent; // 透過処理を行うか
    /**
     * デフォルトの文字色．本クラスはスプライトの色指定が0の場合はこの設定を優先します
     */
    public int defaultWordColor ; 
    /**
     * デフォルトの文字色．本クラスはスプライトの色指定が0の場合はこの設定を優先します
     */
    public int defaultBackGroundColor ; 

    

    /**
     * 描画メモリのクリア
     */
    private void Clear() {
        for (int i = 0; i < Width; i++) {
            for (int j = 0; j < Height; j++) {
                if(IsFullWord) Screen[j][i] = new DrawCell('　', defaultWordColor, defaultBackGroundColor);
                else Screen[j][i] = new DrawCell(' ', defaultWordColor, defaultBackGroundColor);
            }
        }
    }

    /**
     * 各描画オブジェクトから描画情報を取得し、描画メモリに反映
     */
    private void ViewDataImport() {
        int wc,bg;
        for (CObject obj : ObjectsList) {
            if (obj.IsVisible) {
                ArrayList<ArrayList<DrawCell>> Model = obj.GetCostumeData();
                for (int i = 0; i < Model.size(); i++) {
                    for (int j = 0; j < Model.get(i).size(); j++) {
                        if (obj.getX() + j >= CameraX && obj.getX() + j < CameraX + Width && obj.getY() + i >= CameraY
                                && obj.getY() + i < CameraY + Height) {
                                    DrawCell t= Model.get(i).get(j);
                                    wc = t.wordColor;
                                    bg = t.bgColor;
                                    if(Istransparent){
                                        if(Screen[obj.getY() + i - CameraY][obj.getX() + j - CameraX] !=null){
                                            if(wc == 0 )wc = Screen[obj.getY() + i - CameraY][obj.getX() + j - CameraX].wordColor;
                                            if(bg == 0 )bg = Screen[obj.getY() + i - CameraY][obj.getX() + j - CameraX].bgColor;
                                        }
                                    }
                                    Screen[obj.getY() + i - CameraY][obj.getX() + j - CameraX] = new DrawCell(t.word,wc,bg);
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
        // Display.append("\033[H\033[2J"); // 画面クリア

        // Display.append("\f"); // 画面クリア
        Display.append("\033[" + Width + "D"); // カーソルの位置を画面の幅分戻す
        Display.append("\033[" + (Height) + "A"); // カーソルの位置を画面の高さ分戻す
        for (int j = 0; j < Height; j++) {
            int bg = -1, wc = -1;
            for (int i = 0; i < Width; i++) {
                if (bg != Screen[j][i].bgColor) {
                    bg = Screen[j][i].bgColor;
                    Display.append(CColor.getBackGroundColor(bg));

                }
                if (wc != Screen[j][i].wordColor) {
                    wc = Screen[j][i].wordColor;
                    Display.append(CColor.getWordColor(wc));
                    
                }
                Display.append(Screen[j][i].word);
            }
            Display.append("\u001B[0m"); // 色のリセット
            Display.append("\n"); // 改行
        }
        // Display.append("\033[" + Width + "D"); // カーソルの位置を画面の幅分戻す
        // Display.append("\033[" + (Height+1) + "A"); // カーソルの位置を画面の高さ分戻す
        
        System.out.flush();
        System.out.print(Display);
    }

    /**
     * コンストラクター
     * 
     * @param width  画面の幅
     * @param height 画面の高さ
     * @param defaultWordColor デフォルトの文字色
     * @param defaultBackGroundColor デフォルトの背景色
     * @param isFullWord 全角文字で使用するか．この設定を有効にすると，全角文字を使用する場合には半角文字2文字分の幅を使用します
     * @param istransparent 透過処理を行うかを指定します．色を0に指定した場合，一つ下のモデルの描画情報を参照します．
     */
    public CDisplay(int width, int height,int defaultWordColor,int defaultBackGroundColor,boolean isFullWord,boolean istransparent) throws IOException, InterruptedException{
        this.Width = width;
        this.Height = height;
        this.defaultWordColor = defaultWordColor;
        this.defaultBackGroundColor = defaultBackGroundColor;
        this.IsFullWord = isFullWord;
        this.Istransparent = istransparent;
        Screen = new DrawCell[Height][Width];
        ObjectsList = new LinkedList<CObject>();
        System.out.print("\033[?25l"); // カーソル非表示
        // System.out.print("\f"); // 画面クリア
        System.out.print("\033[H\033[2J"); // 画面クリア
        for(int i=0;i<height;i++){
            System.out.println();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(
                () -> {
                    System.out.println("\033[?25h");
                    System.out.print("\u001B[0m"); // 色のリセット
                    System.out.print("\033[H\033[2J"); // 画面クリア
                }));// シャットダウンフックの登録
    }

    /**
     * コンストラクター
     * 
     * @param width  画面の幅
     * @param height 画面の高さ
     */
    public CDisplay(int width, int height) {
        this(width, height, 0,0,false);
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
    /**
     * 全角文字を使用するかの問い合わせ
     */
    public boolean getIsFullWord(){
        return IsFullWord;
    }

}
