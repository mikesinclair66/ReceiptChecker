package com.example.ocrapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;

public class ReceiptActivity extends AppCompatActivity {

    //LinearLayout text_Container = findViewById(R.id.text_container);
    TextView pricev;
    TextView datev;



    public static final String MyPREFERENCES = "Receipt" ;
    SharedPreferences sp = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
//        datev = findViewById(R.id.date);
//        pricev = findViewById(R.id.price);
//
//        String total = Double.toString(MainActivity.getTotal());
//        datev.setText(MainActivity.getDate());
//        pricev.setText(total);

        ArrayList<String> keys = new ArrayList<>();
        ArrayList<Pair<Double, String>> receipts = new ArrayList<>();
        if (MainActivity.getTotal() != 0 && MainActivity.getDate() != null){
            SaveReceipt.AddReceipt(sp,MainActivity.getTotal(),MainActivity.getDate());
        }
        keys = SaveReceipt.GetAllKeys(sp);

        for (String i : keys) {
            receipts.add(SaveReceipt.GetReceipt(sp, i));
        }

        for (Pair<Double, String> j : receipts) {
            System.out.println(j.first);
            System.out.println(j.second);
        }

    }

    private void showAllReceipts() {
        HashMap<String, String> entries = SaveReceipt.GetAllReceipts(sp);
    }

    private void setOnClickGet(final Button btn, final String str){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveReceipt.GetReceipt(sp, str);
            }
        });
    }
}