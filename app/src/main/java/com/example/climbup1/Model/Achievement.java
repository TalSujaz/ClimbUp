package com.example.climbup1.Model;

public class Achievement {
    private String missonid;
    private String userid;

    public Achievement(String missonid, String userid) {
        this.missonid = missonid;
        this.userid = userid;
    }

    public Achievement() {
    }

    public String getMissonid() {
        return missonid;
    }

    public void setMissonid(String missonid) {
        this.missonid = missonid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
