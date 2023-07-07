package com.example.spdeteksibumil.model;

import java.io.Serializable;

public class Gejala implements Serializable {
    String kodeGejala;
    String namaGejala;

    Boolean selected = false;

    public Gejala(String kodeGejala, String namaGejala) {
        this.kodeGejala = kodeGejala;
        this.namaGejala = namaGejala;
    }

    public String getKodeGejala() {
        return kodeGejala;
    }

    public void setKodeGejala(String kodeGejala) {
        this.kodeGejala = kodeGejala;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getNamaGejala() {
        return namaGejala;
    }

    public void setNamaGejala(String namaGejala) {
        this.namaGejala = namaGejala;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
