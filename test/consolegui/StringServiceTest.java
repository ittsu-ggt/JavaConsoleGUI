package consolegui;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Vector;

import org.junit.Test;

public class StringServiceTest {
    @Test
    public void StringServiceの生成(){
        CDisplay disp = new CDisplay(100, 50, 1, 8, true, true);
        StringService str1 = new StringService(disp,"test",0,0,0,0,true);
    }

    @Test
    public void StringServiceの座標がセットできるか(){
        CDisplay disp = new CDisplay(100, 50, 1, 8, true, true);
        for(int i=-100;i<=100;i++){
            for(int j=-100;j<=100;j++){
                StringService str1 = new StringService(disp,"test",i,j,0,0,true);
                assertEquals(str1.X, i);
                assertEquals(str1.Y, j);
            }
        }
    }

    @Test
    public void ChangeStringのテスト(){
        CDisplay disp = new CDisplay(100, 50, 1, 8, false, true);
        StringService str1 = new StringService(disp,"test",0,0,0,0,true);
        str1.ChangeString("test2");
        assertEquals(str1.getString(), "test2");
        var cos = str1.GetCostumeData();
        String tmp = "";
        for(int i=0;i<cos.size();i++){
            for(int j=0;j<cos.get(i).size();j++){
                tmp += cos.get(i).get(j).word;
            }
        }
        assertEquals(tmp, "test2");
    }
}
