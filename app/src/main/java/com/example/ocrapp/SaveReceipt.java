package com.example.ocrapp;

import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SaveReceipt {

    @RequiresApi(api = Build.VERSION_CODES.O)
    /**
     * Add receipt to store, returns True if successful
     * Will also increment total by the added receipt amount.
     */
    public static String AddReceipt(SharedPreferences pref, double receiptValue, String date) {
        SharedPreferences.Editor editor = pref.edit(); // pref
        double total = pref.getFloat("total", 0); // pref
        total += receiptValue;
        LocalDateTime current = LocalDateTime.now();
        editor.putString(current.toString(), date + "\n" + receiptValue);
        editor.putFloat("total", (float) total);
        if (editor.commit()) {
            return current.toString();
        } else {
            return null;
        }
    }

    /**
     * Get a receipt by key, returns a pair representing value and date respectively.
     */
    public static Pair<Double, String> GetReceipt(SharedPreferences pref, String key) {
        String entry = pref.getString(key, ""); // pref
        String[] dateValue = entry.split("\n");
        Pair<Double, String> dateValuePair = new Pair<>(Double.parseDouble(dateValue[1]), dateValue[0]);
        return dateValuePair;
    }

    /**
     * Edit receipt. Requires the key and the new value and date, returns True if successful.
     * Will also modify total to represent new total.
     */
    public static boolean EditReceipt(SharedPreferences pref, String key, double receiptValue, String date) {
        SharedPreferences.Editor editor = pref.edit(); // pref
        double oldReceiptValue = GetReceipt(pref, key).first; // pref
        double total = pref.getFloat("total", 0); // pref
        total -= oldReceiptValue;
        total += receiptValue;
        editor.putString(key, date + "\n" + receiptValue);
        editor.putFloat("total", (float) total);
        return editor.commit();
    }

    /**
     * Delete a receipt by key, returns True if successful.
     * Will also decrement total by the deleted receipt amount.
     */
    public static boolean DeleteReceipt(SharedPreferences pref, String key) {
        SharedPreferences.Editor editor = pref.edit(); // pref
        double total = pref.getFloat("total", 0); // pref
        double oldReceiptValue = GetReceipt(pref, key).first; // pref
        total -= oldReceiptValue;
        editor.remove(key);
        editor.putFloat("total", (float) total);
        return editor.commit();
    }

    /**
     * Get all receipts. Returns them as a hashmap with key as the key
     * and value as a string representing date and receipt value, delimited by a newline character
     */
    public static HashMap<String, String> GetAllReceipts(SharedPreferences pref) {
        Map<String, ?> map = pref.getAll(); // pref
        HashMap<String, String> result = new HashMap<>();
        for(Map.Entry<String,?> entry : map.entrySet()){
            Log.d("map values",entry.getKey() + ": " + entry.getValue().toString());
            result.put(entry.getKey(), entry.getValue().toString());
        }
        result.remove("total");
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    /**
     * Get all keys in storage. Use this to iterate through the keys and call GetReceipt
     * To get all the receipts. Make sure to store all the keys somehow to for accessing them.
     */
    public static ArrayList<String> GetAllKeys(SharedPreferences pref) {
        ArrayList<String> keys = new ArrayList<>();
        HashMap<String, String> receipts = GetAllReceipts(pref); // pref
        receipts.forEach((k, v) -> keys.add(k));
        return keys;
    }

    /**
     * Get the total of all receipts. Total is constantly updated with every add, edit, and delete,
     * so this just gets the value being stored
     */
    public static double GetTotal(SharedPreferences pref) {
        return pref.getFloat("total", 0); // pref
    }
}
