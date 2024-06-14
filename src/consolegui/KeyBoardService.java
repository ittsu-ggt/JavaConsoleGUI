/**
 * @file KeyBoardService.java
 * @author T22CS044 Itsuki Hosaka
 * @version 1.0.0
 */
package consolegui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

/**
 * キーボード入力を取得するクラス
 * メソッドは一つのみ生成可能である
 */
public class KeyBoardService extends Thread {
    private KeyBoardService instance;
    private static final int BUFFER = 3;
    private static boolean isStop = false;
    private Vector<Character> keyList = new Vector<Character>();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private boolean fastmode = false;
    private boolean isRunning = false;

    /**
     * キーボード入力を取得するクラスのコンストラクタ
     * 
     * @param fastmode trueの場合、キーボード入力を取得するたびにバッファをクリアするため，
     */

    public KeyBoardService(boolean fastmode) {
        
        this.fastmode = fastmode;
        instance = new KeyBoardService(fastmode, "");
        instance.start();
    }
    /**
     * キーボードクラスを終了する
     */
    public void RemoveMe(){
        isStop = true;
        try {
            instance.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * キーボードバッファをクリアする
     */
    public void bufferClear(){
        instance.bufferClear("");
    }

    private void bufferClear(String str){
        keyList.clear();
    }

    /**
     * キーボード入力を取得するクラスのコンストラクタ(インスタンス用)
     * @param fastmode 
     * @param str 任意の値を入れる．ただしこれは何も使われていない
     */
    private KeyBoardService(boolean fastmode, String str) {
        this.keyList = new Vector<Character>();
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.fastmode = fastmode;
    }

    /**
     * キーボード入力を判定する
     * 
     * @param keyCode 入力されたキー
     * @return 入力されたキーが押されているかどうか
     */
    public boolean isKeyPressed(char keyCode) {
        return instance.isKeyPressed(keyCode, getName());
    }

    /**
     * キーボード入力を判定する(インスタンス呼び出し用)
     * @param keyCode 入力されたキー
     * @param str 任意の値を入れる．ただしこれは何も使われていない
     * @return 入力されたキーが押されているかどうか
     */
    private boolean isKeyPressed(char keyCode,String str) {
        boolean result = keyList.contains(keyCode);
        if (result) {
            if (fastmode)
                keyList.clear();
            else
                keyList.remove(keyCode);
        }
        return result;
    }

    /**
     * キーボード入力を取得する
     * 
     * @return 入力されたキー
     */
    public char GetKey() {
        return instance.GetKey("");
    }

    /**
     * キーボード入力を取得する(インスタンス呼び出し用)
     * @param str 任意の値を入れる．ただしこれは何も使われていない
     * @return 入力されたキー
     */
    private char GetKey(String str) {
        if (keyList.size() > 0) {
            char key = keyList.get(0);
            if (fastmode)
                keyList.clear();
            else
                keyList.remove(0);
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
        while ((str = reader.readLine()) != null) {
            keyList.clear();
            for (int i = 0; i < str.length(); i++) {
                keyList.add(str.charAt(i));
                if (keyList.size() > BUFFER)
                    keyList.remove(0);
            }
            if(isStop){
                return;
            }
        }
    }

    /**
     * バッファのサイズを取得する
     * 
     * @return バッファのサイズ
     */
    public int getsize() {
        return keyList.size();
    }

}