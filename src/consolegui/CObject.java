/**
 * @file CObject.java
 * @author T22CS044 Itsuki Hosaka
 * @version 1.0.0
 */
package consolegui;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * CDisplayに登録するオブジェクトのクラス．
 */
public class CObject {

    private Map<String,ArrayList<ArrayList<DrawCell>>> CostumeList ; // 2次元配列の宣言
    private String CostumeName; // コスチューム名
    private CDisplay CView; // 登録先コンソールビュー

    /**
     * モデルのx座標
     */
    public int X = 0;

    /**
     * モデルのy座標
     */
    public int Y = 0;
    
    /**
     * 表示フラグ
     */
    public boolean IsVisible = true;


    /// モデルの情報を取得するメソッド群
    /**
     * モデルのX座標を取得
     * @return モデルのX座標
     */
    public int getX() {
        return this.X;
    }
    /**
     * モデルのY座標を取得
     * @return モデルのY座標
     */
    public int getY() {
        return this.Y;
    }
    /**
     * 現在のコスチューム名を取得
     * @return 現在のコスチューム名
     */
    public String getCostumeName(){
        return this.CostumeName;
    }
    /**
     * 現在のコスチュームデータを取得
     * @return 現在のコスチュームデータ
     */
    public ArrayList<ArrayList<DrawCell>> GetCostumeData(){
        return this.CostumeList.get(this.CostumeName);
    }

    /**
     * 登録先のCViewを取得
     * @return 登録先のCView
     */
    public CDisplay getCView(){
        return this.CView;
    }
    /**
     * 表示フラグを取得
     * @return 表示フラグ
     */
    public boolean getIsVisible(){
        return this.IsVisible;
    }

    /// コンストラクター群
    /**
     * コンストラクター．初期のコスチュームを設定する．
     * @param CView 表示先
     * @param CostumeName コスチュームの名前
     * @param CostumeData コスチュームのデータ
     * @param x x座標
     * @param y y座標
     * @param IsVisible 表示フラグ
     */
    public CObject(CDisplay CView, String CostumeName, ArrayList<ArrayList<DrawCell>> CostumeData,int x,int y,boolean IsVisible) {
        this.CostumeList = new HashMap<String, ArrayList<ArrayList<DrawCell>>>();
        AddCostume(CostumeName, CostumeData);
        this.CView = CView;
        this.CostumeName = CostumeName;
        this.IsVisible = IsVisible;
        this.SetLocation(x, y);
        CView.AddObject(this);
    }
    /**
     * コンストラクター．初期のコスチュームを設定する．なお，コスチューム名は"Default"となる
     * @param CView 表示先
     * @param CostumeData コスチュームのデータ
     * @param x x座標
     * @param y y座標
     * @param IsVisible 表示フラグ
     */
    public CObject(CDisplay CView,ArrayList<ArrayList<DrawCell>> CostumeData,int x,int y,boolean IsVisible) {
        this(CView,"Default", CostumeData, x, y,IsVisible);
    }

    /**
     * コスチュームを追加する
     * @param CostumeName コスチュームの名前
     * @param SpriteData コスチュームのデータ
     */
    public void AddCostume(String CostumeName, ArrayList<ArrayList<DrawCell>> SpriteData){
        if(this.CostumeList.containsKey(CostumeName))throw new IllegalArgumentException(this.getClass().getName()+" : 既に存在するコスチューム名です");
        for(int i=0;i<SpriteData.size();i++){
            for(int j=0;j<SpriteData.get(i).size();j++){
                int w = (int)SpriteData.get(i).get(j).getW();
                if ((w >= 0x0021 && w <= 0x007E)) {
                    var cell = new DrawCell((char) (w + 0xFF00), SpriteData.get(i).get(j).getWc(), SpriteData.get(i).get(j).getBgc());
                    SpriteData.get(i).set(j, cell);
                }
            }
        }
        this.CostumeList.put(CostumeName, SpriteData);
        return;
    }

    /**
     * コスチュームを切り替える
     * @param CostumeName 切り替え先のコスチューム名
     */
    public void SwitchCostume(String CostumeName){
        if(!this.CostumeList.containsKey(CostumeName))throw new IllegalArgumentException(this.getClass().getName()+" : 存在しないコスチューム名です");
        this.CostumeName = CostumeName;
        return;
    }
    /**
     * コスチュームを削除する
     * @param CostumeName 削除するコスチューム名
     */
    public void RemoveCostume(String CostumeName){
        if(!this.CostumeList.containsKey(CostumeName))throw new IllegalArgumentException(this.getClass().getName()+" : 存在しないコスチューム名です");
        this.CostumeList.remove(CostumeName);
        return;
    }

    /// モデルの操作群
    /**
     * モデルの座標を指定した分，移動させる
     * @param x x座標の移動量
     * @param y y座標の移動量
     */
    public void MoveLocation(int x, int y) {
        SetLocation(this.X+x, this.Y+y);
    }
    /**
     * モデルの座標を指定した座標に移動させる
     * @param x x座標
     * @param y y座標
     */
    public void SetLocation(int x, int y) {
        this.X = x;
        this.Y = y;
    }
    /**
     * モデルの表示フラグを設定する
     * @param IsVisible 表示フラグ Ture:表示 False:非表示
     */
    public void SetVisible(boolean IsVisible){
        this.IsVisible = IsVisible;
    }
    /**
     * モデルの描画順序を上げる
     */
    public void ChangeDrawingOrderUp(){
        this.CView.ChangeObjectDrawingOrder(this, this.CView.getObjectDrawingOrder(this) + 1);
    }
    /**
     * モデルの描画順序を下げる
     */
    public void ChangeDrawingOrderDown(){
        var t =this.CView.getObjectDrawingOrder(this)-1;
        if(t<0)t=0;
        this.CView.ChangeObjectDrawingOrder(this, t);
    }
    /**
     * モデルの描画順序を変更する
     * @param order 描画順序
     */
    public void ChangeDrawingOrder(int order){
        this.CView.ChangeObjectDrawingOrder(this, order);
    }

    /**
     * モデルの表示先を変更する
     * @param CView 表示先
     */
    public void ChangeDrawingDisplay(CDisplay CView){
        CView.RemoveObject(this);
        this.CView = CView;
        CView.AddObject(this);
        return ;
    }

    /**
     * モデルを削除する
     */
    public void RemoveMe(){
        this.CView.RemoveObject(this);
    }

    /// モデル同士の衝突判定群
    /**
     * 他のモデルと衝突しているかを判定する
     * @param other 衝突判定を行うモデル
     * @return 衝突しているかどうか
     */
    public boolean IsHit(CObject other){
        int TmpX = 0;
        int TmpY = 0;
        for (int i = 0; i < this.CostumeList.get(this.CostumeName).size(); i++) {
            for (int j = 0; j < this.CostumeList.get(this.CostumeName).get(i).size(); j++) {
                TmpX = this.X + j;
                TmpY = this.Y + i;
                if(TmpX >= other.getX() && TmpX < other.getX() + other.GetCostumeData().get(0).size() && 
                    TmpY >= other.getY() && TmpY < other.getY() + other.GetCostumeData().size())
                {
                    if(this.CostumeList.get(this.CostumeName).get(i).get(j).getW() != ' ' && 
                        other.GetCostumeData().get(TmpY - other.getY()).get(TmpX - other.getX()).getW() != ' ')
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /**
     * 他のモデルと指定した文字で衝突しているかを判定する
     * @param other 衝突判定を行うモデル
     * @param Word 衝突判定を行う文字
     * @return 衝突しているかどうか
     */
    public boolean IsHit(CObject other,char Word)
    {
        int TmpX = 0;
        int TmpY = 0;
        for (int i = 0; i < this.CostumeList.get(this.CostumeName).size(); i++) {
            for (int j = 0; j < this.CostumeList.get(this.CostumeName).get(i).size(); j++) {
                TmpX = this.X + j;
                TmpY = this.Y + i;
                if(TmpX >= other.getX() && TmpX < other.getX() + other.GetCostumeData().size() && TmpY >= other.getY() && TmpY < other.getY() + other.GetCostumeData().size())
                {
                    if(this.CostumeList.get(this.CostumeName).get(i).get(j).getW() == Word && other.GetCostumeData().get(TmpY - other.getY()).get(TmpX - other.getX()).getW() != ' ')
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
