package com.android.qdhhh.fakebeizhongyi.me.bean;

/**
 * Created by qdhhh on 2016/9/27.
 */

public class GroupRemindBean {

    private String groupid;
    private String groupiconurl;
    private String groupiconurl_s;
    private String groupname;
    private int type;
    private int ifreceive;

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getGroupiconurl() {
        return groupiconurl;
    }

    public void setGroupiconurl(String groupiconurl) {
        this.groupiconurl = groupiconurl;
    }

    public String getGroupiconurl_s() {
        return groupiconurl_s;
    }

    public void setGroupiconurl_s(String groupiconurl_s) {
        this.groupiconurl_s = groupiconurl_s;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIfreceive() {
        return ifreceive;
    }

    public void setIfreceive(int ifreceive) {
        this.ifreceive = ifreceive;
    }

    @Override
    public String toString() {
        return "GroupRemindBean{" +
                "groupid='" + groupid + '\'' +
                ", groupiconurl='" + groupiconurl + '\'' +
                ", groupiconurl_s='" + groupiconurl_s + '\'' +
                ", groupname='" + groupname + '\'' +
                ", type=" + type +
                ", ifreceive=" + ifreceive +
                '}';
    }
}
