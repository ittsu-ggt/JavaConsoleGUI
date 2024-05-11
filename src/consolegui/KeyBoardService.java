/**
 * @file KeyBoardService.java
 * @author T22CS044 Itsuki Hosaka
 * @version 1.0.0
 */
package consolegui;

import org.jline.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class KeyBoardService extends Thread{
    private int DelayTime;
    private static LinkedList<Character> KeyInput = new LinkedList<Character>();
    private static boolean IsRun=false;
    private static KeyBoardService InputThread;
    private static int BufferSize;
    private static int threadnum=0;

    public KeyBoardService(int BufferSize)
    {
        if(!IsRun){
            IsRun=true;
            KeyBoardService.BufferSize = BufferSize;
            InputThread = new KeyBoardService();
            InputThread.start();
        }
    }

    public char GetKey()
    {
        if(KeyInput.size() == 0)
        {
            return ' ';
        }
        return KeyInput.removeFirst();
    }

    public void run()
    {
        if(threadnum==0)
        {
        //     BufferedReader Reader = new BufferedReader(new InputStreamReader(System.in));
        //     String line =null;
        //     while ((line = Reader.readLine()) != null)
        //     {
        //         for(int i = 0; i < line.length(); i++)
        //         {
        //             KeyInput.add(line.charAt(i));
        //             if(KeyInput.size() > BufferSize)
        //             {
        //                 KeyInput.removeFirst();
        //             }
        //         }
        //     }
        // }else{
            

        }

    }

}
