package com.android.qdhhh.fakebeizhongyi.im;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.android.qdhhh.fakebeizhongyi.commom.Constants;
import com.yonyou.sns.im.config.YYIMPreferenceConfig;
import com.yonyou.sns.im.core.YYIMCallBack;
import com.yonyou.sns.im.core.YYIMChatManager;
import com.yonyou.sns.im.core.YYIMSettings;
import com.yonyou.sns.im.entity.YYToken;
import com.yonyou.sns.im.exception.YYIMErrorConsts;
import com.yonyou.sns.im.provider.ITokenProvider;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by qdhhh on 2016/10/8.
 */

public class CusTokenProvider extends ITokenProvider {

    @Override
    public void getToken(final YYIMCallBack yyimCallBack) {
        // 获取token
        System.out.println();
        new Thread() {
            @Override
            public void run() {
                // TODO Auto-generated method stub

                YYIMSettings settings = YYIMChatManager.getInstance().getYmSettings();
                String account = YYIMPreferenceConfig.getInstance().getString(Constants.FRONT_LAST_LOGIN_ACCOUNT, "");
                String password = YYIMPreferenceConfig.getInstance().getString(Constants.FRONT_LAST_LOGIN_PASSWORD, "");
                String userID = YYIMPreferenceConfig.getInstance().getString(Constants.FRONT_LAST_LOGIN_USERID, "");
                String nickName = YYIMPreferenceConfig.getInstance().getString(Constants.FRONT_LAST_LOGIN_NICKNAME, "");
                // url
                String url = Constants.getIMToken + Constants.etpid + "/" + Constants.appid + "/" + "token";

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.addHeader("Content-Type", "application/json");
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("clientId", Constants.ClientID);
                    obj.put("clientSecret", Constants.ClientSecret);
                    obj.put("userid", userID);
                    obj.put("nickname", nickName);
                    httpPost.setEntity(new StringEntity(obj.toString(), HTTP.UTF_8));
                    HttpResponse response = httpClient.execute(httpPost);
                    int code = response.getStatusLine().getStatusCode();
                    if (code == 200) {
                        String rev = EntityUtils.toString(response.getEntity());
                        try {
                            YYToken yyToken = JSON.parseObject(rev, YYToken.class);
                            yyimCallBack.onSuccess(yyToken);
                        } catch (Exception e) {
                            yyimCallBack.onError(YYIMErrorConsts.ERROR_GET_TOKEN_EXCEPTION, rev);
                        }
                    }
                    System.out.println("");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            };
        }.start();
    }
}
