package consolegui;
import java.beans.Transient;
import java.util.ArrayList;
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
}
