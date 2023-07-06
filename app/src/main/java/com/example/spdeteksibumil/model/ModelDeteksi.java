package com.example.spdeteksibumil.model;

import java.io.Serializable;

public class ModelDeteksi implements Serializable {
    String strGejala = null;
    boolean selected = false;

    public String getStGejala() {
        return strGejala;
    }
    public void setStGejala(String strGejala) {
        this.strGejala = strGejala;
    }
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}