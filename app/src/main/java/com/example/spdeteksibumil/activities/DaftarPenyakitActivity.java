package com.example.spdeteksibumil.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spdeteksibumil.R;
import com.example.spdeteksibumil.adapter.PenyakitAdapter;
import com.example.spdeteksibumil.database.DatabaseHelper;
import com.example.spdeteksibumil.model.ModelDaftarPenyakit;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

public class DaftarPenyakitActivity extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper databaseHelper;
    ArrayList<ModelDaftarPenyakit> modelDaftarPenyakitList = new ArrayList<>();
    PenyakitAdapter penyakitAdapter;
    RecyclerView rvDaftarPenyakit;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_penyakit);

        setStatusBar();

        toolbar = findViewById(R.id.toolbar);
        rvDaftarPenyakit = findViewById(R.id.rvDaftarPenyakit);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        //buat set databasenya
        databaseHelper = new DatabaseHelper(this);
        if (databaseHelper.openDatabase())
            sqLiteDatabase = databaseHelper.getReadableDatabase();

        rvDaftarPenyakit.setLayoutManager(new LinearLayoutManager(this));
        penyakitAdapter = new PenyakitAdapter(this, modelDaftarPenyakitList);
        rvDaftarPenyakit.setAdapter(penyakitAdapter);
        rvDaftarPenyakit.setHasFixedSize(true);

        getListdata();

    }

    private void getListdata() {
        modelDaftarPenyakitList = databaseHelper.getDaftarPenyakit();
        if (modelDaftarPenyakitList.size() == 0) {
            rvDaftarPenyakit.setVisibility(View.GONE);
        } else {
            rvDaftarPenyakit.setVisibility(View.VISIBLE);
            penyakitAdapter = new PenyakitAdapter(this, modelDaftarPenyakitList);
            rvDaftarPenyakit.setAdapter(penyakitAdapter);
        }
    }

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        if (Build.VERSION.SDK_INT >= 24) {
            setWindowFlag (this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on){
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
}