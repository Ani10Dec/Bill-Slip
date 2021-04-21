package com.example.challengeMarketGad;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class ShareActivity extends AppCompatActivity {

    private EditText etNumber;
    private ImageView btnWhatsapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        TextView totalAmount = findViewById(R.id.total_amount);
        TextView totalItems = findViewById(R.id.total_item);
        etNumber = findViewById(R.id.et_number);
        btnWhatsapp = findViewById(R.id.btn_whatsapp);

        String tItems = getIntent().getStringExtra("totalItems");
        String tAmount = getIntent().getStringExtra("totalAmount");
        totalItems.setText(tItems);
        totalAmount.setText(tAmount);

        // Share Whatsapp Msg
        btnWhatsapp.setOnClickListener(v -> {
            String smsNumber = etNumber.getText().toString();
            if (etNumber.getText().toString().length() == 10) {
                String url = "https://api.whatsapp.com/send?text=You have just purchased items worth ₹" + tAmount + ".00 from General Store. Please find below the pdf. To proceed with your payments, click on the below link:\n \nGoogle pay \nBhim \nBharatPe \nPhonePe \nCard (razorpay)" + "&phone=91" + smsNumber;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            } else {
                Toast.makeText(this, "Need Correct Number", Toast.LENGTH_SHORT).show();
            }
        });
    }

}