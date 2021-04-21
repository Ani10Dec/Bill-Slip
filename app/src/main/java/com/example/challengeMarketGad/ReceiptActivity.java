package com.example.challengeMarketGad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class ReceiptActivity extends AppCompatActivity {

    private DataPOJO adapterPojo;
    private RecyclerView recyclerView;
    private List<DataEntity> dataList;
    private DataAdapter adapter;
    private TextView currentDt, noDataText, totalItems;
    private LinearLayout btnShare;
    private TextView totalAmount;
    private TextView subAmount;
    private RelativeLayout view;
    private Button clearBtn;
    private int o = 0;
    private SharedPreferences sharedPreferences;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        String t = getIntent().getStringExtra("totalAmount");
        sharedPreferences = getSharedPreferences("clearStatus", MODE_PRIVATE);

        //HOOKS
        recyclerView = findViewById(R.id.recycler_view);
        currentDt = findViewById(R.id.current_date);
        btnShare = findViewById(R.id.btn_share);
        totalAmount = findViewById(R.id.total_amount);
        subAmount = findViewById(R.id.sub_amount);
        noDataText = findViewById(R.id.tv_no_data);
        totalItems = findViewById(R.id.total_item);
        view = findViewById(R.id.view);
        clearBtn = findViewById(R.id.btn_clear_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        try {
            dataList = new getData().execute().get();
            adapter = new DataAdapter(dataList, getApplicationContext());
            recyclerView.setAdapter(adapter);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (dataList.size() == 0) {
            noDataText.setVisibility(View.VISIBLE);
        } else {
            noDataText.setVisibility(View.GONE);
        }

        for (int i = 0; i < dataList.size(); i++) {
            String s = dataList.get(i).getAmount();
            o += Integer.parseInt(s);
        }
        totalAmount.setText(String.valueOf(o));
        subAmount.setText(String.valueOf(o));
        totalItems.setText(String.valueOf(dataList.size()));
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        currentDt.setText(formattedDate);

        btnShare.setOnClickListener(v -> {
            if (!(dataList.size() == 0)) {
                Intent i = new Intent(this, ShareActivity.class);
                i.putExtra("totalItems", String.valueOf(dataList.size()));
                i.putExtra("totalAmount", String.valueOf(o));
                startActivity(i);
            }
        });

        clearBtn.setOnClickListener(v -> {
            sharedPreferences.edit().putBoolean("status", true).apply();
            new deleteData().execute();
            dataList.clear();
            try {
                dataList = new getData().execute().get();
                adapter = new DataAdapter(dataList, getApplicationContext());
                recyclerView.setAdapter(adapter);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (dataList.size() == 0) {
                noDataText.setVisibility(View.VISIBLE);
            } else {
                noDataText.setVisibility(View.GONE);
            }
            totalAmount.setText("0");
            subAmount.setText("0");
            totalItems.setText(String.valueOf(dataList.size()));
        });
    }

    private class getData extends AsyncTask<Void, Void, List<DataEntity>> {
        DataBase db = Room.databaseBuilder(getApplicationContext(), DataBase.class, "data_db").build();

        @Override
        protected List<DataEntity> doInBackground(Void... voids) {
            return db.dataDao().getAllData();
        }
    }

    class deleteData extends AsyncTask<Void, Void, Integer> {
        DataBase db = Room.databaseBuilder(getApplicationContext(), DataBase.class, "data_db").build();

        @Override
        protected Integer doInBackground(Void... voids) {
            int i = db.dataDao().delete();
            db.close();
            return i;
        }
    }
}
