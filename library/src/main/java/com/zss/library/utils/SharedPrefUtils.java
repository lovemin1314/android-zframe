package com.zss.library.utils;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.zss.library.utils.cipher.Cipher;

/**
 * Android保存文件工具类
 * @author zm
 * 
 */
public class SharedPrefUtils {
    
	  
	private SharedPreferences sp;
	
    private static final String TAG  = SharedPrefUtils.class.getSimpleName();

    public SharedPrefUtils(Context context, String fileName) {
        sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }
    public SharedPreferences getSharedPreferences(){
    	return sp;
    }

    /**
     * *************** get ******************
     */

    public String get(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public boolean get(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public float get(String key, float defValue) {
        return sp.getFloat(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public long get(String key, long defValue) {
        return sp.getLong(key, defValue);
    }

    public Object get(String key) {
        return get(key, (Cipher) null);
    }

    /**
     * get解密数据
     * @param key
     * @param cipher
     * @return
     */
    public Object get(String key, Cipher cipher) {
        try {
            String hex = get(key, (String) null);
            if (hex == null) return null;
            byte[] bytes = HexUtils.decodeHex(hex.toCharArray());
            if (cipher != null) bytes = cipher.decrypt(bytes);
            Object obj = ByteUtils.byteToObject(bytes);
            Log.i(TAG, key + " get: " + obj);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * *************** put ******************
     */
    public void put(String key, Object ser) {
        put(key, ser, null);
    }

    /**
     * put加密数据
     * @param key
     * @param ser
     * @param cipher
     */
    public void put(String key, Object ser, Cipher cipher) {
        try {
            Log.i(TAG, key + " put: " + ser);
            if (ser == null) {
                sp.edit().remove(key).commit();
            } else {
                byte[] bytes = ByteUtils.objectToByte(ser);
                if (cipher != null) bytes = cipher.encrypt(bytes);
                put(key, HexUtils.encodeHexStr(bytes));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void put(String key, String value) {
        if (value == null) {
            sp.edit().remove(key).commit();
        } else {
            sp.edit().putString(key, value).commit();
        }
    }

    public void put(String key, boolean value) {
        sp.edit().putBoolean(key, value).commit();
    }

    public void put(String key, float value) {
        sp.edit().putFloat(key, value).commit();
    }

    public void put(String key, long value) {
        sp.edit().putLong(key, value).commit();
    }

    public void putInt(String key, int value) {
        sp.edit().putInt(key, value).commit();
    }
    
}
