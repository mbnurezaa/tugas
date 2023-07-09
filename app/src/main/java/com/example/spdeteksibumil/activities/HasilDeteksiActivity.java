package com.example.spdeteksibumil.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.spdeteksibumil.R;
import com.example.spdeteksibumil.database.DatabaseHelper;
import com.example.spdeteksibumil.model.Penyakit;

public class HasilDeteksiActivity extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper databaseHelper;
    TextView tvNamaPenyakit;
    TextView tvSaran;
    Toolbar toolbar;

    private Penyakit penyakit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_deteksi);

        toolbar = findViewById(R.id.toolbar);
        tvNamaPenyakit = findViewById(R.id.tvPenyakit);
        tvSaran = findViewById(R.id.tvPenanganan);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        Intent intent = getIntent();
        databaseHelper = new DatabaseHelper(this);
        if (databaseHelper.openDatabase())
            sqLiteDatabase = databaseHelper.getReadableDatabase();

        String hasil = intent.getStringExtra("HASIL");
        penyakit = databaseHelper.getPenyakitBykode(hasil);

//        cek apakah ada data penyakit
        if (penyakit == null) {
            Toast.makeText(HasilDeteksiActivity.this, "Penyakit tidak ditemukan!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            tvNamaPenyakit.setText(penyakit.getNama());
            tvSaran.setText(penyakit.getSaran());
//        Log.i("Deskripsi", penyakit.getDeskripsi());
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