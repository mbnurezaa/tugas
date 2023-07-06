package com.example.spdeteksibumil.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.example.spdeteksibumil.R;
import com.example.spdeteksibumil.adapter.DeteksiAdapter;
import com.example.spdeteksibumil.database.DatabaseHelper;
import com.example.spdeteksibumil.model.ModelDeteksi;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class DeteksiActivity extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabase;
    DeteksiAdapter deteksiAdapter;
    ArrayList<ModelDeteksi> modelDeteksiArrayList = new ArrayList<>();
    DatabaseHelper databaseHelper;
    Toolbar toolbar;
    RecyclerView rvDeteksiPenyakit;
    MaterialButton btnHasilDeteksi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deteksi);

        setStatusBar();

        databaseHelper = new DatabaseHelper(this);
        if (databaseHelper.openDatabase())
            sqLiteDatabase = databaseHelper.getReadableDatabase();

        toolbar = findViewById(R.id.toolbar);
        rvDeteksiPenyakit = findViewById(R.id.rvDeteksiPenyakit);
        btnHasilDeteksi = findViewById(R.id.btnHasilDeteksi);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        rvDeteksiPenyakit.setLayoutManager(new LinearLayoutManager(this));
        deteksiAdapter = new DeteksiAdapter(this, modelDeteksiArrayList);
        rvDeteksiPenyakit.setAdapter(deteksiAdapter);
        rvDeteksiPenyakit.setHasFixedSize(true);

        btnHasilDeteksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuffer gejalaPilihan = new StringBuffer();

                ArrayList<ModelDeteksi> gejalaList = modelDeteksiArrayList;
                for (int i = 0; i <gejalaList.size(); i++) {
                    ModelDeteksi gejala = gejalaList.get(i);
                    if (gejala.isSelected()) {
                        gejalaPilihan.append(gejala.getStGejala()).append("#");
                    }
                }

                if (gejalaPilihan.toString().equals("")) {
                    Toast.makeText(DeteksiActivity.this, "Silahkan pilih gejala terlebih dahulu!", Toast.LENGTH_SHORT).show();
                } else {
                    // menampilkan activity hasil deteksi
                    Intent intent = new Intent(view.getContext(), HasilDeteksiActivity.class);
                    intent.putExtra("HASIL", gejalaPilihan.toString());
                    startActivity(intent);
                }
            }
        });

        getListData();
    }

    private void getListData() {
        modelDeteksiArrayList = databaseHelper.getDaftarGejala();
        if (modelDeteksiArrayList.size() == 0) {
            rvDeteksiPenyakit.setVisibility(View.GONE);
        } else {
            rvDeteksiPenyakit.setVisibility(View.VISIBLE);
            deteksiAdapter = new DeteksiAdapter(this, modelDeteksiArrayList);
            rvDeteksiPenyakit.setAdapter(deteksiAdapter);
        }
    }

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (on) {
            layoutParams.flags |= bits;
        } else {
            layoutParams.flags &= ~bits;
        }
        window.setAttributes(layoutParams);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume(){
        super.onResume();
        getListData();
    }

}