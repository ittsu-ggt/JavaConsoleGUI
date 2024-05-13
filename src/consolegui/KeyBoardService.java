/**
 * @file KeyBoardService.java
 * @author T22CS044 Itsuki Hosaka
 * @version 1.0.0
 */
package consolegui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Vector;

/**
 * キーボード入力を取得するクラス
 */
public class KeyBoardService extends Thread {
    private int DelayTime;
    private static Vector<Character> KeyInput = new Vector<Character>();
    private static boolean IsRun = false;
    private static KeyBoardService InputThread;
    private static int BufferSize;
    private static int threadnum = 0;

    public KeyBoardService(int BufferSize) {
        if (!IsRun) {
            IsRun = true;
            KeyBoardService.BufferSize = BufferSize;
            InputThread = new KeyBoardService(BufferSize);
            InputThread.start();
        } else if (threadnum > 1) {
            throw new IllegalArgumentException("KeyBoardService : すでにインスタンスが生成されています");
        }
    }

    public char GetKey() {
        if (KeyInput.size() == 0) {
            return '\0';
        }
        var t = KeyInput.get(0);
        KeyInput.remove(0);
        return t;
        
    }

    public void run() {
        try {
            if (threadnum == 0) {
                BufferedReader Reader = new BufferedReader(new InputStreamReader(System.in));
                String line = null;
                while ((line = Reader.readLine()) != null) {
                    for (int i = 0; i < line.length(); i++) {
                        KeyInput.add(line.charAt(i));
                        if (KeyInput.size() > BufferSize) {
                            KeyInput.remove(0);
                        }
                    }
                }
            } else {

            }
        } catch (Exception e) {

        }

    }

}


