/**
 * @file SpriteBuildService.java
 * @author T22CS044 Itsuki Hosaka
 * @version 1.0.0
 */
package consolegui;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
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
     * 
     * @param SpriteData スプライトデータ
     * @return 構築済みスプライト
     */
    public static Vector<Vector<DrawCell>> BuildModel(Vector<Vector<Character>> SpriteData) {
        return BuildModel(SpriteData, 0, 0);
    }

    /**
     * スプライトの構築．
     * 
     * @param SpriteData      スプライトデータ
     * @param BackGroundColor 背景色
     * @param SpriteColor     文字色
     * @return 構築済みスプライト
     */
    public static Vector<Vector<DrawCell>> BuildModel(Vector<Vector<Character>> SpriteData,
            Vector<Vector<Integer>> SpriteColor, Vector<Vector<Integer>> BackGroundColor) {
        // 引数のサイズチェック
        if (SpriteData.size() != BackGroundColor.size() || SpriteData.size() != SpriteColor.size()) {
            throw new IllegalArgumentException(
                    SpriteBuildService.class.getName() + " : SpriteData, SpriteColor, BackGroundColorのサイズが一致しません");
        }
        for (int i = 0; i < SpriteData.size(); i++) {
            if (SpriteData.get(i).size() != BackGroundColor.get(i).size()
                    || SpriteData.get(i).size() != SpriteColor.get(i).size()) {
                throw new IllegalArgumentException(
                        SpriteBuildService.class.getName() + " : SpriteData, SpriteColor, BackGroundColorのサイズが一致しません");
            }
        }

        Vector<Vector<DrawCell>> Cell_tmp = new Vector<Vector<DrawCell>>(); // 2次元配列の初期化
        for (int i = 0; i < SpriteData.size(); i++) {
            Vector<DrawCell> row = new Vector<DrawCell>();
            for (int j = 0; j < SpriteData.get(i).size(); j++) {
                row.add(new DrawCell(SpriteData.get(i).get(j), SpriteColor.get(i).get(j),
                        BackGroundColor.get(i).get(j)));
            }
            Cell_tmp.add(row);
        }
        return Cell_tmp;
    }

    /**
     * スプライトの構築．背景色及び文字色は色番号を指定し，単一色スプライトを構築する
     * 
     * @param SpriteData      スプライトデータ
     * @param BackGroundColor 背景色番号
     * @param SpriteColor     文字色番号
     * @return 構築済みスプライト
     */
    public static Vector<Vector<DrawCell>> BuildModel(Vector<Vector<Character>> SpriteData,int SpriteColor, int BackGroundColor) {
        Vector<Vector<Integer>> BackGroundColor_tmp = new Vector<Vector<Integer>>();
        Vector<Vector<Integer>> SpriteColor_tmp = new Vector<Vector<Integer>>();
        for (int i = 0; i < SpriteData.size(); i++) {
            Vector<Integer> BackGroundColor_row = new Vector<Integer>();
            Vector<Integer> SpriteColor_row = new Vector<Integer>();
            for (int j = 0; j < SpriteData.get(i).size(); j++) {
                BackGroundColor_row.add(BackGroundColor);
                SpriteColor_row.add(SpriteColor);
            }
            BackGroundColor_tmp.add(BackGroundColor_row);
            SpriteColor_tmp.add(SpriteColor_row);
        }
        return BuildModel(SpriteData, SpriteColor_tmp, BackGroundColor_tmp);
    }

    private static Vector<Vector<Character>> LineConverterToCharacter(List<String> lines) {
        Vector<Vector<Character>> tmp = new Vector<Vector<Character>>();
        for (int i = 0; i < lines.size(); i++) {
            Vector<Character> tmp2 = new Vector<Character>();
            for (int j = 0; j < lines.get(i).length(); j++) {
                tmp2.add(lines.get(i).charAt(j));
            }
            tmp.add(tmp2);
        }
        return tmp;
    }

    private static Vector<Vector<Integer>> LineConverterToInteger(List<String> lines) {
        Vector<Vector<Integer>> tmp = new Vector<Vector<Integer>>();
        for (int i = 0; i < lines.size(); i++) {
            Vector<Integer> tmp2 = new Vector<Integer>();
            for (int j = 0; j < lines.get(i).length(); j++) {
                tmp2.add(Integer.parseInt(lines.get(i).substring(j, j + 1)));
            }
            tmp.add(tmp2);
        }
        return tmp;
    }
    
    private static List<String> FileReader(String FilePath) {
        Path File_Path = Path.of(FilePath);
        try {
            List<String> lines = Files.readAllLines(File_Path, StandardCharsets.UTF_8);

            return lines;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(SpriteBuildService.class.getName() + " : ファイルの読み込みに失敗しました．ファイル名 : "+FilePath);
        }
    }

    /**
     * ファイルからスプライトを構築する．ここで入れるファイルは，スプライトデータ，文字色，背景色の順に行で並んでいること
     * @param AllSpriteFilePath スプライトデータ・文字色・背景色ファイル
     * @return 構築済みスプライト
     */
    public static Vector<Vector<DrawCell>> BuildModel(String AllSpriteFilePath) {
        List<String> lines = FileReader(AllSpriteFilePath);
        if(lines.size()%3!=0)
        {
            throw new IllegalArgumentException(SpriteBuildService.class.getName() + " : ファイルの内容が不正です．ファイル名 : "+AllSpriteFilePath);
        }
        Vector<Vector<Character>> SpriteData = LineConverterToCharacter(lines.subList(0, lines.size()/3));
        Vector<Vector<Integer>> SpriteColor = LineConverterToInteger(lines.subList(lines.size()/3, lines.size()/3*2));
        Vector<Vector<Integer>> BackGroundColor = LineConverterToInteger(lines.subList(lines.size()/3*2, lines.size()));
        return BuildModel(SpriteData, SpriteColor, BackGroundColor);
    }

    /**
     * ファイルからスプライトを構築する．
     * @param SpriteFilePath スプライトデータファイル
     * @param SpriteColor 文字色
     * @param BackGroundColor 背景色
     * @return 構築済みスプライト
     */
    public static Vector<Vector<DrawCell>> BuildModel(String SpriteFilePath,int SpriteColor, int BackGroundColor) {
        List<String> lines = FileReader(SpriteFilePath);
        Vector<Vector<Character>> SpriteData = LineConverterToCharacter(lines);
        return BuildModel(SpriteData,SpriteColor, BackGroundColor);
    }
    /**
     * ファイルからスプライトを構築する．
     * @param SpriteFilePath スプライトデータファイル
     * @param SpriteColorFilePath 文字色ファイル
     * @param BackGroundColorFilePath 背景色ファイル
     * @return 構築済みスプライト
     */
    public static Vector<Vector<DrawCell>> BuildModel(String SpriteFilePath, String SpriteColorFilePath,String BackGroundColorFilePath) {
        Vector<Vector<Character>> SpriteData = LineConverterToCharacter(FileReader(SpriteFilePath));
        Vector<Vector<Integer>> SpriteColor = LineConverterToInteger(FileReader(SpriteColorFilePath));
        Vector<Vector<Integer>> BackGroundColor = LineConverterToInteger(FileReader(BackGroundColorFilePath));
        return BuildModel(SpriteData, SpriteColor, BackGroundColor);
    }
}
