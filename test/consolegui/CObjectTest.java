package consolegui;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.beans.Transient;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import org.junit.Test;

public class CObjectTest {

    @Test
    public void CObjectの生成(){
        ArrayList<ArrayList<DrawCell>> tmp = new ArrayList<ArrayList<DrawCell>>();
        for(int i = 0; i < 10; i++){
            ArrayList<DrawCell> tmp2 = new ArrayList<DrawCell>();
            for(int j = 0; j < 10; j++){
                tmp2.add(new DrawCell(' ', 0, 0));
            }
            tmp.add(tmp2);
        }
        CDisplay disp = new CDisplay(100, 50, 1, 8, true, true);
        CObject obj = new CObject(disp, "test", tmp, 0, 0, false);
    }

    @Test
    public void CObjectの座標がセットできるか(){
        Random rand = new Random();      
        CDisplay disp = new CDisplay(100, 50, 1, 8, true, true);
        for(int i=-100;i<=100;i++){
            for(int j=-100;j<=100;j++){
                CObject obj = new CObject(disp, "test", SpriteBuildService.BuildModel("./testdata/consolegui/Character1.txt", 1, 1), i, j, false);
                assertEquals(obj.X, i);
                assertEquals(obj.Y, j);
            }
        }
    }

    @Test
    public void CObjectの座標を変更できるか1(){
        CDisplay disp = new CDisplay(100, 50, 1, 8, true, true);
        CObject obj = new CObject(disp, "test", SpriteBuildService.BuildModel("./testdata/consolegui/Character1.txt", 1, 1), 0, 0, false);
        for(int i=-100;i<=100;i++){
            for(int j=-100;j<=100;j++){
                obj.SetLocation(i, j);
                assertEquals(obj.X, i);
                assertEquals(obj.Y, j);
            }
        }
    }

    @Test
    public void CObjectを移動させることができるか(){
        CDisplay disp = new CDisplay(100, 50, 1, 8, true, true);
        CObject obj = new CObject(disp, "test", SpriteBuildService.BuildModel("./testdata/consolegui/Character1.txt", 1, 1), 0, 0, false);
        for(int i=-100;i<=100;i++){
            for(int j=-100;j<=100;j++){
                obj.SetLocation(0, 0);
                obj.MoveLocation(i, j);
                assertEquals(obj.X, i);
                assertEquals(obj.Y, j);
            }
        }
    }

    @Test
    public void 表示フラグを変えることはできるか(){
        CDisplay disp = new CDisplay(100, 50, 1, 8, true, true);
        CObject obj = new CObject(disp, "test", SpriteBuildService.BuildModel("./testdata/consolegui/Character1.txt", 1, 1), 0, 0, false);
        obj.SetVisible(true);
        assertEquals(obj.IsVisible, true);
        obj.SetVisible(false);
        assertEquals(obj.IsVisible, false);
    }

    @Test
    public void コスチュームを追加できるか(){
        CDisplay disp = new CDisplay(100, 50, 1, 8, false, true);
        CObject obj = new CObject(disp, "test", SpriteBuildService.BuildModel("./testdata/consolegui/Character1.txt", 1, 1), 0, 0, false);
        obj.AddCostume("test2",SpriteBuildService.BuildModel("./testdata/consolegui/Character2.txt", 1, 1));
        obj.SwitchCostume("test2");
        var t = obj.GetCostumeData();
        String s = "";
        for(int i = 0; i < t.size(); i++){
            for(int j = 0; j < t.get(i).size(); j++){
                s += t.get(i).get(j).word;
            }
            s += "\n";
        }
        assertEquals(s, "***\n***\n**+\n");
    }

    @Test
    public void コスチュームを削除できるか(){
        CDisplay disp = new CDisplay(100, 50, 1, 8, false, true);
        CObject obj = new CObject(disp, "test", SpriteBuildService.BuildModel("./testdata/consolegui/Character1.txt", 1, 1), 0, 0, false);
        obj.AddCostume("test2",SpriteBuildService.BuildModel("./testdata/consolegui/Character2.txt", 1, 1));
        obj.SwitchCostume("test2");
        assertEquals(obj.CostumeName, "test2");
        assertThrows(IllegalArgumentException.class, () -> {
            obj.RemoveCostume("test2");
        });
        obj.SwitchCostume("test");
        obj.RemoveCostume("test2");
    }

    @Test
    public void 存在しないコスチュームを指定した場合に例外が発生するか(){
        CDisplay disp = new CDisplay(100, 50, 1, 8, false, true);
        CObject obj = new CObject(disp, "test", SpriteBuildService.BuildModel("./testdata/consolegui/Character1.txt", 1, 1), 0, 0, false);
        assertThrows(IllegalArgumentException.class, () -> {
            obj.SwitchCostume("test2");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            obj.RemoveCostume("test2");
        });
    }

    @Test
    public void 描画順序に関するチェック(){
        CDisplay disp = new CDisplay(100, 50, 1, 8, false, true);
        CObject obj1 = new CObject(disp, "test", SpriteBuildService.BuildModel("./testdata/consolegui/Character1.txt", 1, 1), 0, 0, false);
        CObject obj2 = new CObject(disp, "test", SpriteBuildService.BuildModel("./testdata/consolegui/Character1.txt", 1, 1), 0, 0, false);
        CObject obj3 = new CObject(disp, "test", SpriteBuildService.BuildModel("./testdata/consolegui/Character1.txt", 1, 1), 0, 0, false);
        CObject obj4 = new CObject(disp, "test", SpriteBuildService.BuildModel("./testdata/consolegui/Character1.txt", 1, 1), 0, 0, false);
        obj1.ChangeDrawingOrder(0);
        obj2.ChangeDrawingOrder(1);
        obj3.ChangeDrawingOrder(2);
        obj4.ChangeDrawingOrder(3);
        assertEquals(obj1.getthisDrawingOrder(), 0);
        assertEquals(obj2.getthisDrawingOrder(), 1);
        assertEquals(obj3.getthisDrawingOrder(), 2);
        assertEquals(obj4.getthisDrawingOrder(), 3);
        obj1.ChangeDrawingOrder(2);
        obj2.ChangeDrawingOrder(1);
        obj3.ChangeDrawingOrder(0);
        obj4.ChangeDrawingOrder(3);
        assertEquals(obj1.getthisDrawingOrder(), 2);
        assertEquals(obj2.getthisDrawingOrder(), 1);
        assertEquals(obj3.getthisDrawingOrder(), 0);
        assertEquals(obj4.getthisDrawingOrder(), 3);
        obj1.ChangeDrawingOrder(0);
        assertEquals(obj1.getthisDrawingOrder(), 0);
        obj1.ChangeDrawingOrder(-1);
        assertEquals(obj1.getthisDrawingOrder(), 3);
        assertThrows(IllegalArgumentException.class, () -> {
            obj1.ChangeDrawingOrder(-2);
        });

    }

    @Test
    public void モデルの表示先が変わるか(){
        CDisplay disp1 = new CDisplay(100, 50, 1, 8, false, true);
        CDisplay disp2 = new CDisplay(100, 50, 1, 8, false, true);
        CObject obj = new CObject(disp1, "test", SpriteBuildService.BuildModel("./testdata/consolegui/Character1.txt", 1, 1), 0, 0, false);
        obj.ChangeDrawingDisplay(disp2);
        assertEquals(obj.CView, disp2);
    }

    @Test
    public void モデルを削除できるか(){
        CDisplay disp = new CDisplay(100, 50, 1, 8, false, true);
        CObject obj = new CObject(disp, "test", SpriteBuildService.BuildModel("./testdata/consolegui/Character1.txt", 1, 1), 0, 0, false);
        obj.RemoveMe();
        assertEquals(obj.CView, null);
        assertEquals(obj.CostumeList, null);
        assertEquals(obj.CostumeName, null);
    }

    @Test
    public void モデル同士の衝突判定ができるか1(){
        CDisplay disp = new CDisplay(100, 50, 1, 8, false, true);
        CObject obj1 = new CObject(disp, "test", SpriteBuildService.BuildModel("./testdata/consolegui/Character1.txt", 1, 1), 0, 0, false);
        CObject obj2 = new CObject(disp, "test", SpriteBuildService.BuildModel("./testdata/consolegui/Character1.txt", 1, 1), 0, 0, false);
        assertEquals(obj1.IsHit(obj2), true);
        obj2.SetLocation(10, 10);
        assertEquals(obj1.IsHit(obj2), false);
    }

    @Test
    public void モデル同士の衝突判定ができるか2(){
        CDisplay disp = new CDisplay(100, 50, 1, 8, false, true);
        CObject obj1 = new CObject(disp, "test", SpriteBuildService.BuildModel("./testdata/consolegui/Character1.txt", 1, 1), 0, 0, false);
        CObject obj2 = new CObject(disp, "test", SpriteBuildService.BuildModel("./testdata/consolegui/Character2.txt", 1, 1), 0, 0, false);
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                obj1.SetLocation(i, j);
                assertEquals(obj1.IsHit(obj2), true);
                if(i==2 && j==2){
                    assertEquals(obj1.IsHit(obj2,'*'), false);
                    assertEquals(obj1.IsHit(obj2,'+'), true);
                }else{
                    assertEquals(obj1.IsHit(obj2,'*'), true);
                }
            }
        }
    }
}
