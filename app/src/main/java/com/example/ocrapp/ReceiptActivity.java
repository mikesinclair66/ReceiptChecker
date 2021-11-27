package com.example.ocrapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.BreakIterator;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class ReceiptActivity extends AppCompatActivity {

    //LinearLayout text_Container = findViewById(R.id.text_container);
    TextView pricev;
    TextView datev;
    TextView totalPricev;
    ScrollView receiptScrollv;
    int receipt_Iterator = 1;
    Button button_cap;

    public static final String MyPREFERENCES = "Receipt";
    SaveReceipt receiptSaver;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        SharedPreferences sp = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SaveReceipt.createInstance(sp);
        receiptSaver = SaveReceipt.getInstance();

//        datev = findViewById(R.id.date);
//        pricev = findViewById(R.id.price);
//
//        String total = Double.toString(MainActivity.getTotal());
//        datev.setText(MainActivity.getDate());
//        pricev.setText(total);

        ArrayList<String> keys = new ArrayList<>();
        ArrayList<Pair<Double, String>> receipts = new ArrayList<>();

        keys = receiptSaver.GetAllKeys();

        for (String i : keys) {
            receipts.add(receiptSaver.GetReceipt(i));
        }

        LinearLayout linearLayout = findViewById(R.id.text_container);
        button_cap = findViewById(R.id.button_new_receipt);
        button_cap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenMainActivity();
            }
        });
        //setContentView(linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);


        for (Pair<Double, String> j : receipts) {
            System.out.println("Receipt Price: " + j.first);
            System.out.println("Receipt Date: " + j.second);

            Button buttonReceipt = new Button(this);
            //buttonReceipt.generateViewId();
            //buttonReceipt.setId(Iterator);
            //buttonReceipt = findViewById(Iterator);
            buttonReceipt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openActivityEdit(j.first,j.second,buttonReceipt.getText().toString()
                    );
                }
            });





            TextView receiptNumView = new TextView(this);
            receiptNumView.setText("Receipt Number: " + Integer.toString(receipt_Iterator));
            receiptNumView.setTextColor(getResources().getColor(R.color.white));


            TextView textView = new TextView(this);
            textView.setText("Price: " + j.first);
            textView.setTextColor(getResources().getColor(R.color.white));

            TextView dateview = new TextView(this);
            dateview.setText("Date: " + j.second);
            dateview.setTextColor(getResources().getColor(R.color.white));
//            linearLayout.addView(receiptNumView);
//            linearLayout.addView(textView);
//            linearLayout.addView(dateview);
            buttonReceipt.setText("Receipt Number: " + Integer.toString(receipt_Iterator) + "\nPrice: " + j.first + "\nDate: " + j.second );
            buttonReceipt.setTextColor(getResources().getColor(R.color.black));
            linearLayout.addView(buttonReceipt);


            receipt_Iterator++;

        }
        DecimalFormat df = new DecimalFormat("0.00");
        String totalPriceText = df.format(receiptSaver.GetTotal());
        totalPricev = findViewById(R.id.price_total);
        totalPricev.setText(totalPriceText);
        //receiptScrollv.addView(linearLayout);


    }

    public void openActivityEdit(double price, String date, String iterator) {
        //DecimalFormat df = new DecimalFormat("0.00");
        System.out.println("openActivity Price: "+ price);
        System.out.println("openActivity date: "+ date);
        System.out.println("openActivity iterator: "+ iterator);

        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("price_pass",price);
        intent.putExtra( "date_pass",date);
        intent.putExtra( "iterator_pass", iterator);
        startActivity(intent);
    }

    private void showAllReceipts() {
        HashMap<String, String> entries = receiptSaver.GetAllReceipts();
    }

    private void setOnClickGet(final Button btn, final String str){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receiptSaver.GetReceipt(str);
            }
        });
    }

    public void OpenMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}