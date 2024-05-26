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
 * メソッドは一つのみ生成可能である
 */
public class KeyBoardService extends Thread {
    private KeyBoardService instance;
    private static final int BUFFER =3;
    private static Vector<Character> keyList = new Vector<Character>();
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private boolean fastmode = false;
    private static boolean isRunning = false;
    /**
     * キーボード入力を取得するクラスのコンストラクタ
     * @param fastmode trueの場合、キーボード入力を取得するたびにバッファをクリアするため，
     */

    public KeyBoardService(boolean fastmode) {
        if(isRunning)throw new RuntimeException("KeyBoardService is already running");
        isRunning = true;
        this.fastmode = fastmode;
        instance = new KeyBoardService();
        instance.start();
    }


    private KeyBoardService() {
    }

    /**
     * キーボード入力を判定する
     * @param keyCode 入力されたキー
     * @return 入力されたキーが押されているかどうか
     */
    public boolean isKeyPressed(char keyCode) {
        boolean result = keyList.contains(keyCode);
        if(result)
        {
            if(fastmode)keyList.clear();
            else keyList.remove(0);
        }
        return result;
    }

    /**
     * キーボード入力を取得する
     * @return 入力されたキー
     */
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

    /**
     * スレッド処理
     */
    public void run() {
        try {
            readkey();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * キーボード入力を取得する
     */
    private void readkey() throws IOException {
        String str = null;
        while((str = reader.readLine())!=null){
            keyList.clear();
            for (int i = 0; i < str.length(); i++) {
                keyList.add(str.charAt(i));
                if(keyList.size() > BUFFER) keyList.remove(0);
            }
        }
    }

    /**
     * バッファのサイズを取得する
     * @return バッファのサイズ
     */
    public int getsize(){
        return keyList.size();
    }

}