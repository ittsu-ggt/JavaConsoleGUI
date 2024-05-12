import java.util.Vector;
import consolegui.*;

public class App {
    public static void main(String[] args) throws Exception {
        CDisplay view = new CDisplay(101, 30);
        SpriteBuildService service = new SpriteBuildService();
        Vector<Vector<Character>> spriteData = new Vector<Vector<Character>>();
        Vector<Character> row = new Vector<Character>();
        row.add('A');
        row.add('B');
        row.add('C');
        spriteData.add(row);
        CObject obj = new CObject(view, service.BuildModel(spriteData,1,8), 0,0,true);
        view.Update();
        int a=0;
        while(a<2){
            for(int i=0;i<90;i++){
                Thread.sleep(50);
                obj.MoveLocation(1, 0);
                view.Update();
            }
            for(int i=0;i<20;i++){
                Thread.sleep(50);
                obj.MoveLocation(0, 1);
                view.Update();
            }
            for(int i=0;i<90;i++){
                Thread.sleep(50);
                obj.MoveLocation(-1, 0);
                view.Update();
            }
            for(int i=0;i<20;i++){
                Thread.sleep(50);
                obj.MoveLocation(0, -1);
                view.Update();
            }
            a++;
        }
        obj.RemoveMe();
    }
}

