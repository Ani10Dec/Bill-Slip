package com.example.challengeMarketGad;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.room.Room;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private static final int IMAGE_CAPTURE = 100;
    private TextView btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    private ImageView btnNext, btnDelete;
    private TextView tvCurrentAmount, tvTotalAmount;
    private LinearLayout btnFinal, camera;
    private HorizontalScrollView hzv;
    private File photoFile;
    private String pathToFile, stCurrentAmt, stTotalAmt;
    private Uri imageUri;
    private ImageView image;
    private DataEntity dataEntity;
    private Bitmap orientedBitmap;
    private int dataID;
    private SharedPreferences sharedPreferences;
    private SharedPreferences statusPreference;
    private boolean status;
    private List<DataEntity> dataList;

    @Override
    protected void onResume() {
        super.onResume();
        /*if (status) {
            tvTotalAmount.setText("0");
        }*/
/*
        if (checkCameraPermission()) {
            takePhoto();
        } else
            requestPermission();
*/

        try {
            dataList = new getData().execute().get();
            if (dataList.size() == 0) {
                tvTotalAmount.setText("0");
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("lastAmount", MODE_PRIVATE);
        statusPreference = getSharedPreferences("clearStatus", MODE_PRIVATE);
        status = sharedPreferences.getBoolean("status", false);
        btn0 = findViewById(R.id.btn_zero);
        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);
        btn5 = findViewById(R.id.btn_5);
        btn6 = findViewById(R.id.btn_6);
        btn7 = findViewById(R.id.btn_7);
        btn8 = findViewById(R.id.btn_8);
        btn9 = findViewById(R.id.btn_9);
        btnNext = findViewById(R.id.btn_next);
        btnFinal = findViewById(R.id.btn_final);
        btnDelete = findViewById(R.id.btn_delete);
        tvCurrentAmount = findViewById(R.id.current_amount);
        tvTotalAmount = findViewById(R.id.total_amount);
        camera = findViewById(R.id.camera_btn);
        hzv = findViewById(R.id.hzw);
        image = findViewById(R.id.image);

        camera.setOnClickListener(v -> {
            if (checkCameraPermission()) {
                takePhoto();
            } else
                requestPermission();
        });

        btn0.setOnClickListener(v -> {
            tvCurrentAmount.append("0");
            hzv.post(() -> hzv.fullScroll(View.FOCUS_RIGHT));
        });
        btn1.setOnClickListener(v -> {
            tvCurrentAmount.append("1");
            hzv.post(() -> hzv.fullScroll(View.FOCUS_RIGHT));
        });
        btn2.setOnClickListener(v -> {
            tvCurrentAmount.append("2");
            hzv.post(() -> hzv.fullScroll(View.FOCUS_RIGHT));
        });
        btn3.setOnClickListener(v -> {
            tvCurrentAmount.append("3");
            hzv.post(() -> hzv.fullScroll(View.FOCUS_RIGHT));
        });
        btn4.setOnClickListener(v -> {
            tvCurrentAmount.append("4");
            hzv.post(() -> hzv.fullScroll(View.FOCUS_RIGHT));
        });
        btn5.setOnClickListener(v -> {
            tvCurrentAmount.append("5");
            hzv.post(() -> hzv.fullScroll(View.FOCUS_RIGHT));
        });
        btn6.setOnClickListener(v -> {
            tvCurrentAmount.append("6");
            hzv.post(() -> hzv.fullScroll(View.FOCUS_RIGHT));
        });
        btn7.setOnClickListener(v -> {
            tvCurrentAmount.append("7");
            hzv.post(() -> hzv.fullScroll(View.FOCUS_RIGHT));
        });
        btn8.setOnClickListener(v -> {
            tvCurrentAmount.append("8");
            hzv.post(() -> hzv.fullScroll(View.FOCUS_RIGHT));
        });
        btn9.setOnClickListener(v -> {
            tvCurrentAmount.append("9");
            hzv.post(() -> hzv.fullScroll(View.FOCUS_RIGHT));
        });

        btnDelete.setOnClickListener(v -> {
            String s = tvCurrentAmount.getText().toString();
            if (!s.isEmpty()) {
                s = s.substring(0, s.length() - 1);
                tvCurrentAmount.setText(s);
            }
        });

        btnNext.setOnClickListener(v -> {
            stCurrentAmt = tvCurrentAmount.getText().toString().trim();
            stTotalAmt = tvTotalAmount.getText().toString().trim();
            if (!tvCurrentAmount.getText().toString().isEmpty() && imageUri != null) {
                if (tvCurrentAmount.getText().toString() != null) {
                    dataEntity = new DataEntity(dataID, imageUri.toString(), stCurrentAmt);
                    dataID++;
                    try {
                        if (new DBDataAsync().execute(1).get()) {
                            Toast.makeText(this, "Already there", Toast.LENGTH_SHORT).show();
                        } else {
                            new DBDataAsync().execute(2).get();
                            image.setImageResource(R.drawable.ic_baseline_camera_alt_24);
                            int t = Integer.parseInt(stCurrentAmt) + Integer.parseInt(stTotalAmt);
                            tvTotalAmount.setText(String.valueOf(t));
                            tvCurrentAmount.setText("");
                  /*          if (status) {
                                tvTotalAmount.setText("0");
                            } else {
                                int t = Integer.parseInt(stCurrentAmt) + Integer.parseInt(stTotalAmt);
                                tvTotalAmount.setText(String.valueOf(t));
                                tvCurrentAmount.setText("");
                            }*/
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        btnFinal.setOnClickListener(v -> {
            int t = 0;
            stCurrentAmt = tvCurrentAmount.getText().toString().trim();
            stTotalAmt = tvTotalAmount.getText().toString().trim();
            if (tvCurrentAmount.getText().toString().isEmpty()) {
                stCurrentAmt = "0";
            }
            Log.e("MainActivity", stCurrentAmt + ", " + stTotalAmt);
            if (!tvCurrentAmount.getText().toString().isEmpty() && imageUri != null) {
                Log.e("Yo", tvCurrentAmount.getText().toString());
                if (!stCurrentAmt.equals("0")) {
                    dataEntity = new DataEntity(dataID, imageUri.toString(), stCurrentAmt);
                    dataID++;
                    try {
                        if (new DBDataAsync().execute(1).get()) {
                            Toast.makeText(this, "Already there", Toast.LENGTH_SHORT).show();
                        } else {
                            new DBDataAsync().execute(2).get();
                            image.setImageResource(R.drawable.ic_baseline_camera_alt_24);
                            t = Integer.parseInt(stCurrentAmt) + Integer.parseInt(stTotalAmt);
                            tvTotalAmount.setText(String.valueOf(t));
                            tvCurrentAmount.setText("");
                   /*         if (status) {
                                tvTotalAmount.setText("0");
                            } else {
                                t = Integer.parseInt(stCurrentAmt) + Integer.parseInt(stTotalAmt);
                                tvTotalAmount.setText(String.valueOf(t));
                                tvCurrentAmount.setText("");
                            }*/
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            Intent i = new Intent(this, ReceiptActivity.class);
            i.putExtra("totalAmount", String.valueOf(t));
            startActivity(i);
        });
    }

    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                123);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private void takePhoto() {
        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePic.resolveActivity(getPackageManager()) != null) {
            photoFile = createPhotoFile();
            if (photoFile != null) {
                pathToFile = photoFile.getAbsolutePath();
                imageUri = FileProvider.getUriForFile(getApplicationContext(), "marketgad.fileprovider", photoFile);
                takePic.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(takePic, IMAGE_CAPTURE);
                addImageToGallery(pathToFile, getApplicationContext());
            }
        }
    }

    private File createPhotoFile() {
        String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(name, ".jpg", storageDir);
        } catch (Exception e) {
            Log.e("MainActivity", "Exception while creating image file: ", e);
        }
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(pathToFile);
            orientedBitmap = ExifUtil.rotateBitmap(pathToFile, bitmap);
            Log.e("orientedBitmap", String.valueOf(orientedBitmap));
                image.setImageBitmap(orientedBitmap);
            try {
                Uri uri = imageUri;
                String scheme = uri.getScheme();
                if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
                    InputStream fileInputStream = getApplicationContext().getContentResolver().openInputStream(uri);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addImageToGallery(final String filePath, final Context context) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, filePath);
        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    class DBDataAsync extends AsyncTask<Integer, Void, Boolean> {
        DataBase db = Room.databaseBuilder(getApplicationContext(), DataBase.class, "data_db").build();

        @Override
        protected Boolean doInBackground(Integer... ints) {
            if (ints[0] == 1) {
                DataEntity entity = db.dataDao().getDataByID(String.valueOf(dataEntity.data_id));
                db.close();
                return entity != null;
            } else if (ints[0] == 2) {
                db.dataDao().insert(dataEntity);
                db.close();
                return true;
            }
            return false;
        }
    }

    class getData extends AsyncTask<Void, Void, List<DataEntity>> {
        DataBase db = Room.databaseBuilder(getApplicationContext(), DataBase.class, "data_db").build();

        @Override
        protected List<DataEntity> doInBackground(Void... voids) {
            return db.dataDao().getAllData();
        }
    }
}
