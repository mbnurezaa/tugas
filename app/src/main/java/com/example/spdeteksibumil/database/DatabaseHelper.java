package com.example.spdeteksibumil.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.spdeteksibumil.model.Gejala;
import com.example.spdeteksibumil.model.ModelDaftarPenyakit;
import com.example.spdeteksibumil.model.ModelDeteksi;
import com.example.spdeteksibumil.model.Penyakit;
import com.example.spdeteksibumil.model.Rule;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "bumil.db";
    private static String DB_PATH = "";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase sqLiteDatabase;
    private final Context ctx;
    private boolean needUpdate = false;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        DB_PATH = "/data/data/" + context.getPackageName() + "/databases/"; //path database
        this.ctx = context;

        copyDatabase();

        this.getReadableDatabase();

    }

    // fungsi untuk update database, jika diperlukan
    public void updateDatabase() throws IOException {
        if (needUpdate) {
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists())
                dbFile.delete();

            copyDatabase();

            needUpdate = false;
        }
    }

    //fungsi untuk membuka koneksi database
    public boolean openDatabase() throws SQLException {
        sqLiteDatabase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return sqLiteDatabase != null;
    }

    //fungsi untuk close koneksi database
    @Override
    public synchronized void close() {
        if (sqLiteDatabase != null)
            sqLiteDatabase.close();
        super.close();
    }

    //fungsi untuk cek apakah file database sudah ada atau tidak
    private boolean checkDatabase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    //fungsi untuk copy database yang sudah dibuat sebelumnya difolder asset kedalam aplikasi
    private void copyDatabase() {
        if (!checkDatabase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException e) {
                throw new Error("ErrorCopyingDatabase");
            }
        }
    }

    //fungsi untuk copy database dari folder asset
    private void copyDBFile() throws IOException {
        InputStream inputStream = ctx.getAssets().open(DB_NAME);
        OutputStream outputStream = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] bBuffer = new byte[1024];
        int iLength;
        while ((iLength = inputStream.read(bBuffer)) > 0)
            outputStream.write(bBuffer, 0, iLength);
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    //jika versi database lebih baru maka perlu di update
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            needUpdate = true;
    }

    //get list daftar penyakit
    public ArrayList<ModelDaftarPenyakit> getDaftarPenyakit() {
        ArrayList<ModelDaftarPenyakit> draftOffline = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT kode_penyakit, nama_penyakit FROM penyakit ORDER BY kode_penyakit";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ModelDaftarPenyakit modelDraftOffline = new ModelDaftarPenyakit();
                modelDraftOffline.setStrKode(cursor.getString(0));
                modelDraftOffline.setStrDaftarPenyakit(cursor.getString(1));
                draftOffline.add(modelDraftOffline);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return draftOffline;
    }

    public ArrayList<Gejala> getListGejala() {
        ArrayList<Gejala> draftOffline = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT kode_gejala, nama_gejala FROM gejala ORDER BY kode_gejala";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Gejala gejala = new Gejala(cursor.getString(0), cursor.getString(1));
                draftOffline.add(gejala);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return draftOffline;
    }

    //get list gejala
    public ArrayList<ModelDeteksi> getDaftarGejala() {
        ArrayList<ModelDeteksi> draftOffline = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT nama_gejala FROM gejala ORDER BY kode_gejala";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ModelDeteksi modelDraftOffline = new ModelDeteksi();
                modelDraftOffline.setStGejala(cursor.getString(0));
                draftOffline.add(modelDraftOffline);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return draftOffline;
    }

    @Nullable
    public Penyakit getPenyakitById(String id) {
        Penyakit penyakit = null;
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT kode_penyakit, nama_penyakit, deskripsi, saran FROM penyakit where kode_penyakit = " + id ;
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                penyakit = new Penyakit(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                );
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return penyakit;
    }

    public ArrayList<Rule> getRules() {
        ArrayList<Rule> draftOffline = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT id_rule, kode_penyakit, kode_gejala FROM rule ORDER BY id_rule";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Rule modelDraftOffline = new Rule(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2)
                );
                draftOffline.add(modelDraftOffline);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return draftOffline;
    }
}
