package com.android.qdhhh.fakebeizhongyi.me.bean;

/**
 * Created by qdhhh on 2016/9/27.
 */

public class SimpleRemainBean {
    private String groupid;
    private int ifreceive;

    public SimpleRemainBean(String groupid, int ifreceive) {

        this.groupid = groupid;
        this.ifreceive = ifreceive;

    }

    public SimpleRemainBean() {

    }


    public int getIfreceive() {
        return ifreceive;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public void setIfreceive(int ifreceive) {
        this.ifreceive = ifreceive;
    }

    @Override
    public String toString() {
        return "SimpleRemainBean{" +
                "groupid='" + groupid + '\'' +
                ", ifreceive=" + ifreceive +
                '}';
    }
}
