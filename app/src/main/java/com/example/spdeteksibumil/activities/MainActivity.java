package com.example.spdeteksibumil.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.spdeteksibumil.R;


public class MainActivity extends AppCompatActivity {
    CardView cvDeteksi, cvDaftarPenyakit, cvArtikel, cvBantuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cvDeteksi = findViewById(R.id.cvDeteksi);
        cvDaftarPenyakit = findViewById(R.id.cvDaftarPenyakit);
        cvArtikel = findViewById(R.id.cvArtikel);
        cvBantuan = findViewById(R.id.cvBantuan);

        cvDeteksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DeteksiActivity.class);
                startActivity(intent);
            }
        });

        cvDaftarPenyakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DaftarPenyakitActivity.class);
                startActivity(intent);
            }
        });

        cvArtikel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ArtikelActivity.class);
                startActivity(intent);
            }
        });

        cvBantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BantuanActivity.class);
                startActivity(intent);
            }
        });

    }

}