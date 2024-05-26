
import consolegui.*;

public class App {
    // public static void main(String[] args) throws Exception {
    //     CDisplay view = new CDisplay(101, 30);
    //     SpriteBuildService service = new SpriteBuildService();
    //     Vector<Vector<Character>> spriteData = new Vector<Vector<Character>>();
    //     Vector<Character> row = new Vector<Character>();
    //     row.add('a');
    //     row.add('v');
    //     row.add('a');
    //     spriteData.add(row);
    //     CObject obj = new CObject(view, service.BuildModel(spriteData,1,8), 0,0,true);
    //     view.Update();
    //     int a=0;
    //     while(a<2){
    //         for(int i=0;i<90;i++){
    //             Thread.sleep(50);
    //             obj.MoveLocation(1, 0);
    //             view.Update();
    //         }
    //         for(int i=0;i<20;i++){
    //             Thread.sleep(50);
    //             obj.MoveLocation(0, 1);
    //             view.Update();
    //         }
    //         for(int i=0;i<90;i++){
    //             Thread.sleep(50);
    //             obj.MoveLocation(-1, 0);
    //             view.Update();
    //         }
    //         for(int i=0;i<20;i++){
    //             Thread.sleep(50);
    //             obj.MoveLocation(0, -1);
    //             view.Update();
    //         }
    //         a++;
    //     }
    //     obj.RemoveMe();
    // }
    public static void main(String[] args) throws Exception {
    CDisplay view = new CDisplay(20, 10, 0, 0, false, false);
    StringService service = new StringService(view, "Hello, World!",10,5,2,8,true);
    StringService service2 = new StringService(view, "Hello, World!",10,4,2,8,true);
    for(int i=0x10;i<65500;i++){
        String num =i+"";
        String hex = Integer.toHexString(i);
        service2.ChangeString(hex);
        String str = String.valueOf((char)i);
        service.ChangeString(str);
        view.Update();
        Thread.sleep(125);
    }
    }
}







