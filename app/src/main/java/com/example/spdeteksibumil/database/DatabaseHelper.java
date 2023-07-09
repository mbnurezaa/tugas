package com.example.spdeteksibumil.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.spdeteksibumil.model.Gejala;
import com.example.spdeteksibumil.model.Penyakit;
import com.example.spdeteksibumil.model.Rule;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "bumil.sqlite";
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
    public ArrayList<Penyakit> getListPenyakit() {
        ArrayList<Penyakit> draftOffline = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT kode, nama FROM penyakit ORDER BY kode";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Penyakit modelDraftOffline = new Penyakit(
                        cursor.getString(0),
                        cursor.getString(1)
                );
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
        String selectQuery = "SELECT kode, nama FROM gejala ORDER BY kode";
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

    //    @Nullable
    public Penyakit getPenyakitBykode(String kode_penyakit) {
        Penyakit penyakit = null;
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT kode, nama, deskripsi, saran FROM penyakit where kode = '" + kode_penyakit + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                penyakit = new Penyakit(
                        cursor.getString(0),
                        cursor.getString(1)
                );
                penyakit.setDeskripsi(cursor.getString(2));
                penyakit.setSaran(cursor.getString(3));
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return penyakit;
    }

    public ArrayList<Rule> getAturanPenyakit() {
        ArrayList<Rule> draftOffline = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT id, kode_aturan, kode_penyakit, kode_gejala FROM aturan_penyakit ORDER BY id";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Rule modelDraftOffline = new Rule(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                );
                draftOffline.add(modelDraftOffline);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return draftOffline;
    }
}
