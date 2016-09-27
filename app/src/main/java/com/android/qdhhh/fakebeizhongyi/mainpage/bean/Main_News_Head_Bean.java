package com.android.qdhhh.fakebeizhongyi.mainpage.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by qdhhh on 2016/9/23.
 */

public class Main_News_Head_Bean implements Serializable {



    private String newsid;
    private String newstitle;
    private String imgurl;
    private String hasthumbnailimg;
    private String issave;
    private String publishtime;
    private String browsecount;
    private String detailurl;


    public String getNewsid() {
        return newsid;
    }

    public void setNewsid(String newsid) {
        this.newsid = newsid;
    }

    public String getNewstitle() {
        return newstitle;
    }

    public void setNewstitle(String newstitle) {
        this.newstitle = newstitle;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getHasthumbnailimg() {
        return hasthumbnailimg;
    }

    public void setHasthumbnailimg(String hasthumbnailimg) {
        this.hasthumbnailimg = hasthumbnailimg;
    }

    public String getIssave() {
        return issave;
    }

    public void setIssave(String issave) {
        this.issave = issave;
    }

    public String getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(String publishtime) {
        this.publishtime = publishtime;
    }

    public String getBrowsecount() {
        return browsecount;
    }

    public void setBrowsecount(String browsecount) {
        this.browsecount = browsecount;
    }

    public String getDetailurl() {
        return detailurl;
    }

    public void setDetailurl(String detailurl) {
        this.detailurl = detailurl;
    }


}
