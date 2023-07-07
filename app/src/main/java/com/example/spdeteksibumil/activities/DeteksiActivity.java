package com.example.spdeteksibumil.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.example.spdeteksibumil.adapter.GejalaAdapter;
import com.example.spdeteksibumil.database.DatabaseHelper;
import com.example.spdeteksibumil.model.Gejala;
import com.example.spdeteksibumil.model.ModelDeteksi;
import com.example.spdeteksibumil.services.DetectorPenyakit;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DeteksiActivity extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabase;
    GejalaAdapter gejalaAdapter;
    ArrayList<Gejala> listGejala = new ArrayList<>();
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
        gejalaAdapter = new GejalaAdapter(this, listGejala);
        rvDeteksiPenyakit.setAdapter(gejalaAdapter);
        rvDeteksiPenyakit.setHasFixedSize(true);

        btnHasilDeteksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Gejala> selected = gejalaAdapter.getSelectedGejala();

                if (selected.isEmpty()){
                    Toast.makeText(DeteksiActivity.this, "Silahkan pilih gejala terlebih dahulu!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (selected.size() < 3){
                    Toast.makeText(DeteksiActivity.this, "Minimal Harus Memilih 3 Gejala", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<String> selectedGejala = selected.stream().map(Gejala::getKodeGejala).collect(Collectors.toList());
//                selectedGejala.forEach(g -> Log.i("SELECTED_KODE", g));

                DetectorPenyakit detectorPenyakit = new DetectorPenyakit(getApplicationContext());
                String hasil = detectorPenyakit.determinePenyakit(selectedGejala);
                if (hasil == null){
                    gejalaAdapter.resetChecked();
                    Toast.makeText(DeteksiActivity.this, "Tidak ditemukan penyakit yang sesuai dengan gejala", Toast.LENGTH_SHORT).show();
                    return;
                }

                // menampilkan activity hasil deteksi
                Intent intent = new Intent(view.getContext(), HasilDeteksiActivity.class);
                intent.putExtra("HASIL", hasil);
                startActivity(intent);
            }
        });

        getListData();
    }

    private void getListData() {
        listGejala = databaseHelper.getListGejala();
        if (listGejala.size() == 0) {
            rvDeteksiPenyakit.setVisibility(View.GONE);
        } else {
            rvDeteksiPenyakit.setVisibility(View.VISIBLE);
            gejalaAdapter = new GejalaAdapter(this, listGejala);
            rvDeteksiPenyakit.setAdapter(gejalaAdapter);
        }
    }

    private void setStatusBar() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
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