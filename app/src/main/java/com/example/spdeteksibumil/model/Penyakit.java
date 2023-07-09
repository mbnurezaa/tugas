package com.example.spdeteksibumil.model;

public class Penyakit {
    private String kode;
    private String nama;
    private String deskripsi;
    private String saran;

    public Penyakit(String kode, String nama) {
        this.kode = kode;
        this.nama = nama;
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

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public void setSaran(String saran) {
        this.saran = saran;
    }
}
