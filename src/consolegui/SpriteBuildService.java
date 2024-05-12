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
import java.util.ArrayList;

/**
 * スプライトの構築を行うクラス．ファイルやListインターフェースをもつ型のデータからスプライトを構築することができる
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
    public static ArrayList<ArrayList<DrawCell>> BuildModel(List<? extends List<Character>> SpriteData) {
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
    public static ArrayList<ArrayList<DrawCell>> BuildModel(List<? extends List<Character>> SpriteData,
                List<? extends List<Integer>> SpriteColor, List<? extends List<Integer>> BackGroundColor) {
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

        ArrayList<ArrayList<DrawCell>> Cell_tmp = new ArrayList<ArrayList<DrawCell>>(); // 2次元配列の初期化
        for (int i = 0; i < SpriteData.size(); i++) {
            ArrayList<DrawCell> row = new ArrayList<DrawCell>();
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
    public static ArrayList<ArrayList<DrawCell>> BuildModel(List<? extends List<Character>> SpriteData,int SpriteColor, int BackGroundColor) {
        ArrayList<ArrayList<Integer>> BackGroundColor_tmp = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> SpriteColor_tmp = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < SpriteData.size(); i++) {
            ArrayList<Integer> BackGroundColor_row = new ArrayList<Integer>();
            ArrayList<Integer> SpriteColor_row = new ArrayList<Integer>();
            for (int j = 0; j < SpriteData.get(i).size(); j++) {
                BackGroundColor_row.add(BackGroundColor);
                SpriteColor_row.add(SpriteColor);
            }
            BackGroundColor_tmp.add(BackGroundColor_row);
            SpriteColor_tmp.add(SpriteColor_row);
        }
        return BuildModel(SpriteData, SpriteColor_tmp, BackGroundColor_tmp);
    }

    private static ArrayList<ArrayList<Character>> LineConverterToCharacter(List<String> lines) {
        ArrayList<ArrayList<Character>> tmp = new ArrayList<ArrayList<Character>>();
        for (int i = 0; i < lines.size(); i++) {
            ArrayList<Character> tmp2 = new ArrayList<Character>();
            for (int j = 0; j < lines.get(i).length(); j++) {
                tmp2.add(lines.get(i).charAt(j));
            }
            tmp.add(tmp2);
        }
        return tmp;
    }

    private static ArrayList<ArrayList<Integer>> LineConverterToInteger(List<String> lines) {
        ArrayList<ArrayList<Integer>> tmp = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < lines.size(); i++) {
            ArrayList<Integer> tmp2 = new ArrayList<Integer>();
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
    public static ArrayList<ArrayList<DrawCell>> BuildModel(String AllSpriteFilePath) {
        List<String> lines = FileReader(AllSpriteFilePath);
        if(lines.size()%3!=0)
        {
            throw new IllegalArgumentException(SpriteBuildService.class.getName() + " : ファイルの内容が不正です．ファイル名 : "+AllSpriteFilePath);
        }
        ArrayList<ArrayList<Character>> SpriteData = LineConverterToCharacter(lines.subList(0, lines.size()/3));
        ArrayList<ArrayList<Integer>> SpriteColor = LineConverterToInteger(lines.subList(lines.size()/3, lines.size()/3*2));
        ArrayList<ArrayList<Integer>> BackGroundColor = LineConverterToInteger(lines.subList(lines.size()/3*2, lines.size()));
        return BuildModel(SpriteData, SpriteColor, BackGroundColor);
    }

    /**
     * ファイルからスプライトを構築する．
     * @param SpriteFilePath スプライトデータファイル
     * @param SpriteColor 文字色
     * @param BackGroundColor 背景色
     * @return 構築済みスプライト
     */
    public static ArrayList<ArrayList<DrawCell>> BuildModel(String SpriteFilePath,int SpriteColor, int BackGroundColor) {
        List<String> lines = FileReader(SpriteFilePath);
        ArrayList<ArrayList<Character>> SpriteData = LineConverterToCharacter(lines);
        return BuildModel(SpriteData,SpriteColor, BackGroundColor);
    }
    /**
     * ファイルからスプライトを構築する．
     * @param SpriteFilePath スプライトデータファイル
     * @param SpriteColorFilePath 文字色ファイル
     * @param BackGroundColorFilePath 背景色ファイル
     * @return 構築済みスプライト
     */
    public static ArrayList<ArrayList<DrawCell>> BuildModel(String SpriteFilePath, String SpriteColorFilePath,String BackGroundColorFilePath) {
        ArrayList<ArrayList<Character>> SpriteData = LineConverterToCharacter(FileReader(SpriteFilePath));
        ArrayList<ArrayList<Integer>> SpriteColor = LineConverterToInteger(FileReader(SpriteColorFilePath));
        ArrayList<ArrayList<Integer>> BackGroundColor = LineConverterToInteger(FileReader(BackGroundColorFilePath));
        return BuildModel(SpriteData, SpriteColor, BackGroundColor);
    }
}
