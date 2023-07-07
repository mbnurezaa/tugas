package com.example.spdeteksibumil.model;

public class Penyakit {
    private String kode;
    private String nama;
    private String deskripsi;
    private String saran;

    public Penyakit(String kode, String nama, String deskripsi, String saran) {
        this.kode = kode;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.saran = saran;
    }

    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getSaran() {
        return saran;
    }
}
