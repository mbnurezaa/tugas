package com.example.spdeteksibumil.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.spdeteksibumil.database.DatabaseHelper;
import com.example.spdeteksibumil.model.Gejala;
import com.example.spdeteksibumil.model.ModelDaftarPenyakit;
import com.example.spdeteksibumil.model.Rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetectorPenyakit {

    private Map<String, String> penyakitMap;
    private Map<String, String> gejalaMap;
    private List<Rule> rules;
    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper databaseHelper;

    public DetectorPenyakit(Context context) {

        databaseHelper = new DatabaseHelper(context);
        if (databaseHelper.openDatabase())
            sqLiteDatabase = databaseHelper.getReadableDatabase();

        ArrayList<ModelDaftarPenyakit> listPenyakit = databaseHelper.getDaftarPenyakit();
        ArrayList<Gejala> listGejala = databaseHelper.getListGejala();

        // Inisialisasi data penyakit
        penyakitMap = new HashMap<>();
        listPenyakit.forEach(p -> penyakitMap.put(p.getStrKode(), p.getStDaftarPenyakit()));

        // Inisialisasi data gejala
        gejalaMap = new HashMap<>();
        listGejala.forEach(p -> gejalaMap.put(p.getKodeGejala(), p.getNamaGejala()));

        // Inisialisasi data aturan
        rules = databaseHelper.getRules();
    }

    @Nullable
    public String determinePenyakit(List<String> gejala) {
        List<String> matchedPenyakit = new ArrayList<>();
        for (Rule rule : rules) {
            if (gejala.contains(rule.getKodeGejala())) {
                matchedPenyakit.add(rule.getKodePenyakit());
            }
        }

        matchedPenyakit.removeIf(kodePenyakit -> !gejala.containsAll(getGejalaByPenyakit(kodePenyakit)));

        if (matchedPenyakit.isEmpty()) {
            return null;
        } else {
            return matchedPenyakit.get(0);
        }
    }

    private List<String> getGejalaByPenyakit(String kodePenyakit) {
        Log.i("Penyakit", kodePenyakit);
        List<String> gejalaList = new ArrayList<>();
        for (Rule rule : rules) {
            if (rule.getKodePenyakit().equals(kodePenyakit)) {
                gejalaList.add(rule.getKodeGejala());
            }
        }

        return gejalaList;
    }
}
