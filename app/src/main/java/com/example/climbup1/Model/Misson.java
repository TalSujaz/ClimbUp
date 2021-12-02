package com.example.climbup1.Model;

public class Misson {
    private String misson;
    private String missonid;

    public Misson(String misson, String missonid) {
        this.misson = misson;
        this.missonid = missonid;
    }

    public Misson() {
    }

    public String getMisson() {
        return misson;
    }

    public void setMisson(String misson) {
        this.misson = misson;
    }

    public String getMissonid() {
        return missonid;
    }

    public void setMissonid(String missonid) {
        this.missonid = missonid;
    }
}
