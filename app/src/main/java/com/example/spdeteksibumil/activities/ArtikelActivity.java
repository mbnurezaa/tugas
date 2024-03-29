package com.example.spdeteksibumil.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.spdeteksibumil.R;
import com.google.android.material.card.MaterialCardView;

public class ArtikelActivity extends AppCompatActivity {

    MaterialCardView mcSatu, mcDua, mcTiga;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artikel);

        mcSatu = findViewById(R.id.mcSatu);
        mcDua = findViewById(R.id.mcDua);
        mcTiga = findViewById(R.id.mcTiga);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        mcSatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArtikelActivity.this, ArtikelSatuActivity.class);
                startActivity(intent);
            }
        });
        mcDua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArtikelActivity.this, ArtikelDuaActivity.class);
                startActivity(intent);
            }
        });
        mcTiga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArtikelActivity.this, ArtikelTigaActivity.class);
                startActivity(intent);
            }
        });

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
