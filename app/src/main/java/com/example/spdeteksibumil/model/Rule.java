package com.example.spdeteksibumil.model;

public class Rule {
    private String id;
    private String kodeRule;
    private String kodePenyakit;
    private String kodeGejala;

    public Rule(String id, String kodeRule, String kodePenyakit, String kodeGejala) {
        this.id = id;
        this.kodeRule = kodeRule;
        this.kodePenyakit = kodePenyakit;
        this.kodeGejala = kodeGejala;
    }

    public String getId() {
        return id;
    }

    public String getKodeRule() {
        return kodeRule;
    }

    public String getKodePenyakit() {
        return kodePenyakit;
    }

    public String getKodeGejala() {
        return kodeGejala;
    }
}
