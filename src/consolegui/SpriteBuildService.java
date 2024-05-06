package consolegui;
import java.util.Vector;

/**
 * スプライトの構築を行うクラス
 */
public class SpriteBuildService {
    /**
     * コンストラクター．特に何もしない
     */
    public SpriteBuildService() {
    }
    /**
     * スプライトの構築．但し文字色と背景色は0を使用する．
     * @param SpriteData スプライトデータ
     * @return 構築済みスプライト
     */
    public Vector<Vector<DrawCell>> BuildModel(Vector<Vector<Character>> SpriteData)
    {
        return BuildModel(SpriteData, 0, 0);
    }

    /**
     * スプライトの構築．
     * @param SpriteData スプライトデータ
     * @param BackGroundColor 背景色
     * @param SpriteColor 文字色
     * @return 構築済みスプライト
     */
    public Vector<Vector<DrawCell>> BuildModel(Vector<Vector<Character>> SpriteData,Vector<Vector<Integer>> SpriteColor,Vector<Vector<Integer>> BackGroundColor)
    {
        // 引数のサイズチェック
        if(SpriteData.size() != BackGroundColor.size() || SpriteData.size() != SpriteColor.size()) {
            throw new IllegalArgumentException(this.getClass().getName()+" : SpriteData, SpriteColor, BackGroundColorのサイズが一致しません");
        }
        for(int i = 0; i < SpriteData.size(); i++) {
            if(SpriteData.get(i).size() != BackGroundColor.get(i).size() || SpriteData.get(i).size() != SpriteColor.get(i).size()) {
                throw new IllegalArgumentException(this.getClass().getName()+" : SpriteData, SpriteColor, BackGroundColorのサイズが一致しません");
            }
        }

        Vector<Vector<DrawCell>> Cell_tmp = new Vector<Vector<DrawCell>>(); // 2次元配列の初期化
        for(int i = 0; i < SpriteData.size(); i++) {
            Vector<DrawCell> row = new Vector<DrawCell>();
            for(int j = 0; j < SpriteData.get(i).size(); j++) {
                row.add(new DrawCell(SpriteData.get(i).get(j), SpriteColor.get(i).get(j), BackGroundColor.get(i).get(j)));
            }
            Cell_tmp.add(row);
        }
        return Cell_tmp;
    }

    /**
     * スプライトの構築．背景色及び文字色は色番号を指定し，単一色スプライトを構築する
     * @param SpriteData スプライトデータ
     * @param BackGroundColor 背景色番号
     * @param SpriteColor 文字色番号
     * @return 構築済みスプライト
     */
    public Vector<Vector<DrawCell>> BuildModel(Vector<Vector<Character>> SpriteData,int SpriteColor,int BackGroundColor)
    {
        Vector<Vector<Integer>> BackGroundColor_tmp = new Vector<Vector<Integer>>();
        Vector<Vector<Integer>> SpriteColor_tmp = new Vector<Vector<Integer>>();
        for(int i = 0; i < SpriteData.size(); i++) {
            Vector<Integer> BackGroundColor_row = new Vector<Integer>();
            Vector<Integer> SpriteColor_row = new Vector<Integer>();
            for(int j = 0; j < SpriteData.get(i).size(); j++) {
                BackGroundColor_row.add(BackGroundColor);
                SpriteColor_row.add(SpriteColor);
            }
            BackGroundColor_tmp.add(BackGroundColor_row);
            SpriteColor_tmp.add(SpriteColor_row);
        }
        return BuildModel(SpriteData, SpriteColor_tmp, BackGroundColor_tmp);
    }
}
