package com.example.spdeteksibumil.model;

public class Rule {
    private String idRule;
    private String kodePenyakit;
    private String kodeGejala;

    public Rule(String idRule, String kodePenyakit, String kodeGejala) {
        this.idRule = idRule;
        this.kodePenyakit = kodePenyakit;
        this.kodeGejala = kodeGejala;
    }

    public String getIdRule() {
        return idRule;
    }

    public String getKodePenyakit() {
        return kodePenyakit;
    }

    public String getKodeGejala() {
        return kodeGejala;
    }
}
