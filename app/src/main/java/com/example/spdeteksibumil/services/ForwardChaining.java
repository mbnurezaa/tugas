package com.example.spdeteksibumil.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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

    public Map<String, Object> determinePenyakit() {
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
        List<String> kodeRules = new ArrayList<>();
        List<String> kodeAturanDitemukan = new ArrayList<>();

        for (Rule aturan : dataAturan) {
            String kodeRule = aturan.getKodeRule();
            List<String> gejalaRule = pengetahuan.get(kodeRule);

            if (gejalaRule.size() == dataFaktaGejala.size() && new HashSet<>(gejalaRule).containsAll(dataFaktaGejala)) {
                kodeAturanDitemukan.add(aturan.getKodePenyakit());
                kodeRules.add(aturan.getKodeRule());
            }
        }

        // Output hasil
        Map<String, Object> gejala = new HashMap<>();

        if (kodeAturanDitemukan.isEmpty()) {
            gejala.put("hasil", null);
        } else {
            String hasil = kodeAturanDitemukan.get(0);
            ArrayList<String> listGejala = databaseHelper.getListGejalaByKode(Objects.requireNonNull(pengetahuan.get(kodeRules.get(0))));

            gejala.put("hasil", hasil);
            gejala.put("gejala_list", (ArrayList<String>) listGejala);
        }
        return gejala;
    }

}
