package com.example.ocrapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    EditText viewPrice;
    EditText viewDate;
    TextView viewReceiptNum;
    Button button_save, button_delete;

    public static final String MyPREFERENCES = "Receipt" ;
    SaveReceipt receiptSaver;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        SharedPreferences sp = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SaveReceipt.createInstance(sp);
        receiptSaver = SaveReceipt.getInstance();

        button_delete = findViewById(R.id.button_delete);
        button_save = findViewById(R.id.button_save);

        viewPrice = findViewById(R.id.text_price);
        viewDate = findViewById(R.id.text_date);
        viewReceiptNum = findViewById(R.id.text_receipt_num);

        Intent data = getIntent();
        double price = data.getDoubleExtra("price_pass",0);
        String date = data.getStringExtra("date_pass");
        String receipt_num = data.getStringExtra("iterator_pass");

        System.out.println("EditActivity Price: "+ price);
        System.out.println("EditActivity date: "+ date);
        System.out.println("EditActivity iterator: "+ receipt_num);

        viewPrice.setText(String.format(String.valueOf(price)));
        viewDate.setText(date);
        viewReceiptNum.setText(receipt_num);

        String[] result = receipt_num.split("\n", 2);
        System.out.println("System out Result: " + result);
        System.out.println("System out Result: " + result[0]);
        System.out.println("System out Result: " + result[1]);
        //String[] split_receipt = result[0].split(" ");
        //System.out.println("System out Result: " + result[1]);
        String rec_num = result[0].replaceAll("[^\\d.]", "");
        int rec_num_int = Integer.parseInt(rec_num) - 1;
        System.out.println("REC NUM: "+ rec_num);
        ArrayList<String> keys = new ArrayList<>();
        keys = receiptSaver.GetAllKeys();
        String key = keys.get(rec_num_int);


        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                receiptSaver.EditReceipt(key, Double.parseDouble(viewPrice.getText().toString()) , viewDate.getText().toString());
                openActivityReceipt();
            }
        });

        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                receiptSaver.DeleteReceipt(key);
                openActivityReceipt();
            }
        });



    }
    public void openActivityReceipt() {
        //DecimalFormat df = new DecimalFormat("0.00");

        Intent intent = new Intent(this, ReceiptActivity.class);
        startActivity(intent);
    }
}