package com.example.ocrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;

public class ReceiptActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "Receipt" ;
    SharedPreferences sp = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
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