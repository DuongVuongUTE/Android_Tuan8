package com.example.sqlite;

public class BaiHoc {
    private int id;
    private String tenBai;

    public BaiHoc(int id, String tenBai) {
        this.id = id;
        this.tenBai = tenBai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenBai() {
        return tenBai;
    }

    public void setTenBai(String tenBai) {
        this.tenBai = tenBai;
    }

}
