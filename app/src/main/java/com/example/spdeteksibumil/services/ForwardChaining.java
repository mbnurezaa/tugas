package com.example.spdeteksibumil.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.spdeteksibumil.database.DatabaseHelper;
import com.example.spdeteksibumil.model.Rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ForwardChaining {
    ArrayList<Rule> dataAturan = new ArrayList<>();
    private List<String> dataFaktaGejala = new ArrayList<>();

    SQLiteDatabase sqLiteDatabase;

    DatabaseHelper databaseHelper;

    public ForwardChaining(Context context, List<String> selectedList) {
        databaseHelper = new DatabaseHelper(context);
        if (databaseHelper.openDatabase())
            sqLiteDatabase = databaseHelper.getReadableDatabase();

        dataAturan = databaseHelper.getAturanPenyakit();
        dataFaktaGejala = selectedList;
    }

    @Nullable
    public String determinePenyakit() {
        // Inisialisasi pengetahuan
        Map<String, List<String>> pengetahuan = new HashMap<>();
        for (Rule aturan : dataAturan) {
            String kodeRule = aturan.getKodeRule();
            String gejala = aturan.getKodeGejala();

            if (!pengetahuan.containsKey(kodeRule)) {
                pengetahuan.put(kodeRule, new ArrayList<>());
            }

            Objects.requireNonNull(pengetahuan.get(kodeRule)).add(gejala);
        }

        // Proses Forward Chaining
        List<String> kodeAturanDitemukan = new ArrayList<>();

        for (Rule aturan : dataAturan) {
            String kodeRule = aturan.getKodeRule();
            List<String> gejalaRule = pengetahuan.get(kodeRule);

            if (gejalaRule.size() == dataFaktaGejala.size() && new HashSet<>(gejalaRule).containsAll(dataFaktaGejala)) {
                kodeAturanDitemukan.add(aturan.getKodePenyakit());
            }
        }

        // Output hasil
        if (kodeAturanDitemukan.isEmpty()) {
            return null;
        } else {
            return kodeAturanDitemukan.get(0);
        }
    }

}
