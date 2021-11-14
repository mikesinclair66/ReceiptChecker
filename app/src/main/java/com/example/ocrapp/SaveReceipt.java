package com.example.ocrapp;

import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class SaveReceipt {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean AddReceipt(SharedPreferences pref, double receiptValue, String date) {
        SharedPreferences.Editor editor = pref.edit(); // pref
        double total = pref.getFloat("total", 0); // pref
        total += receiptValue;
        LocalDateTime current = LocalDateTime.now();
        editor.putString(current.toString(), date + "\n" + receiptValue);
        editor.putFloat("total", (float) total);
        return editor.commit();
    }

    public static Pair<Double, String> GetReceipt(SharedPreferences pref, String key) {
        String entry = pref.getString(key, ""); // pref
        String[] dateValue = entry.split("\n");
        Pair<Double, String> dateValuePair = new Pair<>(Double.parseDouble(dateValue[1]), dateValue[0]);
        return dateValuePair;
    }

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

    public static boolean DeleteReceipt(SharedPreferences pref, String key) {
        SharedPreferences.Editor editor = pref.edit(); // pref
        double total = pref.getFloat("total", 0); // pref
        double oldReceiptValue = GetReceipt(pref, key).first; // pref
        total -= oldReceiptValue;
        editor.remove(key);
        editor.putFloat("total", (float) total);
        return editor.commit();
    }

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
}
