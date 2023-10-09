package com.example.spdeteksibumil.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.spdeteksibumil.R;
import com.example.spdeteksibumil.database.DatabaseHelper;

public class DetailPenyakitActivity extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper databaseHelper;
    Toolbar toolbar;
    TextView tvTitle, tvPenjelasan, tvSaran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penyakit);


        //set database
        databaseHelper = new DatabaseHelper(this);
        if (databaseHelper.openDatabase())
            sqLiteDatabase = databaseHelper.getReadableDatabase();

        String strKodePenyakit = getIntent().getStringExtra("KODE_PENYAKIT");

        String selectQuery = "SELECT nama, deskripsi, saran FROM penyakit WHERE kode = '" + strKodePenyakit + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        toolbar = findViewById(R.id.toolbar);
        tvTitle = findViewById(R.id.tvTitle);
        tvPenjelasan = findViewById(R.id.tvPenjelasan);
        tvSaran = findViewById(R.id.tvSaran);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        tvTitle.setText(cursor.getString(0));
        tvPenjelasan.setText(cursor.getString(1));
        tvSaran.setText(cursor.getString(2));

        cursor.close();
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