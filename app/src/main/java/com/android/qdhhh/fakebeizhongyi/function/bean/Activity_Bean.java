package com.android.qdhhh.fakebeizhongyi.function.bean;

import org.json.JSONObject;

/**
 * Created by qdhhh on 2016/10/8.
 */

public class Activity_Bean {

    private String UserIcon;
    private String UserName;
    private int ApplyCount;
    private JSONObject AtInfoList;


    public String getUserIcon() {
        return UserIcon;
    }

    public void setUserIcon(String userIcon) {
        UserIcon = userIcon;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getApplyCount() {
        return ApplyCount;
    }

    public void setApplyCount(int applyCount) {
        ApplyCount = applyCount;
    }

    public JSONObject getAtInfoList() {
        return AtInfoList;
    }

    public void setAtInfoList(JSONObject atInfoList) {
        AtInfoList = atInfoList;
    }

    @Override
    public String toString() {
        return "Activity_Bean{" +
                "UserIcon='" + UserIcon + '\'' +
                ", UserName='" + UserName + '\'' +
                ", ApplyCount=" + ApplyCount +
                ", AtInfoList=" + AtInfoList +
                '}';
    }
}

