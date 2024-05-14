/**
 * @file KeyBoardService.java
 * @author T22CS044 Itsuki Hosaka
 * @version 1.0.0
 */
package consolegui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Vector;

/**
 * キーボード入力を取得するクラス
 */
public class KeyBoardService extends Thread {
    // private Timer timer;
    private KeyBoardService instance;
    // private static final int DELEY = 50;
    private static final int BUFFER =3;
    private static Vector<Character> keyList = new Vector<Character>();
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private boolean fastmode = false;
    private static boolean isRunning = false;
    // private static Scanner scanner = new Scanner(System.in);

    public KeyBoardService(boolean fastmode) {
        if(isRunning)throw new RuntimeException("KeyBoardService is already running");
        isRunning = true;
        this.fastmode = fastmode;
        instance = new KeyBoardService();
        instance.start();
    }

    private KeyBoardService() {
    }

    public boolean isKeyPressed(char keyCode) {
        return keyList.contains(keyCode);
    }

    public char GetKey() {
        if (keyList.size() > 0) {
            char key = keyList.get(0);
            if(fastmode)keyList.clear();
            else keyList.remove(0);
            return key;
        } else {
            return '\0';
        }
    }

    public void run() {
        try {
            readkey();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void readkey() throws IOException {
        String str = null;
        while((str = reader.readLine())!=null){
            keyList.clear();
            for (int i = 0; i < str.length(); i++) {
                keyList.add(str.charAt(i));
                if(keyList.size() > BUFFER) keyList.remove(0);
            }
        }
    }
    public int getsize(){
        return keyList.size();
    }

}