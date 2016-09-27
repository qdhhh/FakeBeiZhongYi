package com.android.qdhhh.fakebeizhongyi.commom;


/**
 * Created by qdhhh on 2016/9/18.
 */
public class UrlAddress {

    /**
     *接口头地址
     */
    public static final String Common_URL = "http://115.28.148.167:8448/Api/";


    public static final String Url_Login = "Login";
    public static final String Url_GetHeadlineNews = "GetHeadlineNews";
    public static final String Url_GetGeneralNews = "GetGeneralNews";
    public static final String Url_CheckSaving = "CheckSaving";
    public static final String Url_FavouriteNews = "FavouriteNews";
    public static final String Url_AddNewsComment = "AddNewsComment";
    public static final String Url_GetPersonalInfo = "GetPersonalInfo";
    public static final String Url_FileUpload = "FileUpload";
    public static final String Url_PersonPicUpload = "PersonPicUpload";
    public static final String Url_PushStatus = "PushStatus";
    public static final String Url_ChatStatus = "ChatStatus";
    public static final String Url_DisturbStatus = "DisturbStatus";
    public static final String Url_GroupStatus = "GroupStatus";
    public static final String Url_GroupSetting  = "GroupSetting";
    public static final String Url_ChatSetting   = "ChatSetting";
    public static final String Url_DisturbSetting   = "DisturbSetting";
    public static final String Url_PushSetting  = "PushSetting";
    public static final String Url_ModifyUserName  = "ModifyUserName";
    public static final String Url_ModifyPasswd  = "ModifyPasswd";
    public static final String Url_About  = "About";




    public static String getURLAddress(String string) {

        StringBuffer sb = new StringBuffer();

        sb.append(Common_URL).append(string);

        return sb.toString();
    }


}
