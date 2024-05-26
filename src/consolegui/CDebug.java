/**
 * @file CDebug.java
 * @author T22CS044 Itsuki Hosaka
 * @version 1.0.0
 */

package consolegui;
import java.util.ArrayList;

public class CDebug{

    ArrayList<StringService> str;
    CDisplay view;
    int x;
    int y;
    int max;
    public CDebug(CDisplay view, int x, int y,int max) {
        this.view = view;
        str = new ArrayList<StringService>();
        this.x = x;
        this.y = y;
        this.max = max;
    }

    public void AddLog(String log) {
        str.add(new StringService(view,log,0,0,CColor.BLACK,CColor.WHITE,true));
        if(str.size() > max) {
            RemoveFirst();
        }
        Update();
    }

    public void RemoveAll() {
        while (str.size() > 0) {
            RemoveFirst();
        }
    }

    private void RemoveFirst() {
        str.get(0).SetVisible(false);
        str.get(0).RemoveMe();
        str.remove(0);
    }

    private void Update() {
        for (int i = 0; i < str.size(); i++) {
            str.get(i).SetLocation(view.getCameraX()+ x,view.getCameraY()+ y + i);
            str.get(i).SetVisible(true);
            str.get(i).ChangeDrawingOrder(-1);
        }
    }


}
