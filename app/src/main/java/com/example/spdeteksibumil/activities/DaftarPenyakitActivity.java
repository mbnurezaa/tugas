package com.example.spdeteksibumil.activities;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spdeteksibumil.R;
import com.example.spdeteksibumil.adapter.PenyakitAdapter;
import com.example.spdeteksibumil.database.DatabaseHelper;
import com.example.spdeteksibumil.model.Penyakit;

import java.util.ArrayList;

public class DaftarPenyakitActivity extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper databaseHelper;
    ArrayList<Penyakit> modelDaftarPenyakitList = new ArrayList<>();
    PenyakitAdapter penyakitAdapter;
    RecyclerView rvDaftarPenyakit;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_penyakit);

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
        modelDaftarPenyakitList = databaseHelper.getListPenyakit();
        if (modelDaftarPenyakitList.size() == 0) {
            rvDaftarPenyakit.setVisibility(View.GONE);
        } else {
            rvDaftarPenyakit.setVisibility(View.VISIBLE);
            penyakitAdapter = new PenyakitAdapter(this, modelDaftarPenyakitList);
            rvDaftarPenyakit.setAdapter(penyakitAdapter);
        }
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