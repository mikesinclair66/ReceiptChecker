package com.example.ocrapp;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static String recDate;
    Button button_capture, button_copy, button_act2;
    TextView textview_data;
    Bitmap bitmap;

    private static final int REQUEST_CAMERA_CODE = 100;
    public static double recTotal = 0;
    //public String recDate = null;

    public static final String MyPREFERENCES = "Receipt" ;
    SaveReceipt receiptSaver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sp = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SaveReceipt.createInstance(sp);
        receiptSaver = SaveReceipt.getInstance();

        button_capture = findViewById(R.id.button_capture);
        button_copy = findViewById(R.id.button_copy);
        textview_data = findViewById(R.id.text_data);
        button_act2 = (Button) findViewById(R.id.button_act2);


        button_act2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.CAMERA
            },REQUEST_CAMERA_CODE );
        }

        button_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(MainActivity.this);
            }
        });

        button_copy.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
//                setDate("01/01/2011");
//                setTotal(100.00);
                saveReceipt(sp);
                openActivity2();
            }
        });
    }
    public void openActivity2() {
        Intent intent = new Intent(this, ReceiptActivity.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    getTextFromImage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getTextFromImage(Bitmap bitmap){

        double highest = 0;
        String date = null;

        TextRecognizer recognizer = new TextRecognizer.Builder(this).build();
        if (!recognizer.isOperational()){
            Toast.makeText(MainActivity.this,"Error Occurred",Toast.LENGTH_SHORT).show();
        }
        else {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> textBlockSparseArray = recognizer.detect(frame);
            StringBuilder stringBuilder = new StringBuilder();

            ArrayList<Double> prices = new ArrayList<>();
            ArrayList<String> dates = new ArrayList<>();

            for (int i=0; i<textBlockSparseArray.size(); i++){
                TextBlock textBlock = textBlockSparseArray.valueAt(i);
                stringBuilder.append(textBlock.getValue());
                stringBuilder.append("\n");
            }

            ParseTest.useRegexForPrices(stringBuilder.toString(), prices);
            if (!prices.isEmpty()) {
                highest = ParseTest.returnHighestDouble(prices);
                setTotal(highest);
                System.out.println("highest: " + highest);
            } else {
                setTotal(0);
                System.out.println("prices is empty");
            }

            ParseTest.useRegexForDates(stringBuilder.toString(), dates);
            if (!dates.isEmpty()) {
                date = dates.get(0);
                setDate(date);
                System.out.println("date: " + date);
            } else {
                setDate("empty");
                System.out.println("date is empty");
            }

            for (String i : dates) {
                i = ParseTest.formatDate(i);
            }

            textview_data.setText(stringBuilder.toString());
            System.out.println("THIS IS THE STRING: " + stringBuilder);
            button_capture.setText("Retake");
            button_copy.setVisibility(View.VISIBLE);
        }
    }

    public static void setTotal(double x){
        recTotal = x;
    }

    public static void setDate(String x){
        recDate = x;
    }

    public static double getTotal(){
        return recTotal;
    }

    public static String getDate(){ return recDate; }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveReceipt(SharedPreferences pref){
        if (getTotal() != 0 && getDate() != null){
            receiptSaver.AddReceipt(getTotal(),getDate());
        }
        setTotal(0);
        setDate(null);

    }
}