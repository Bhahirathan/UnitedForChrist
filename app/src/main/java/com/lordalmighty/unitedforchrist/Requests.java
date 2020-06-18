package com.lordalmighty.unitedforchrist;

public class Requests {
    private String name,desc;
    private long Pray_count;
    public   Requests(){

    }
    public Requests(String name,String desc,long pray_count){
        this.name=name;
        this.desc=desc;
        this.Pray_count=pray_count;
    }

    public long getPray_count() {
        return Pray_count;
    }

    public void setPray_count(long pray_count) {
        Pray_count = pray_count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
