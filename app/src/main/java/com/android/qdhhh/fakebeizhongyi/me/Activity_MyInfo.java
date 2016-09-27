package com.android.qdhhh.fakebeizhongyi.me;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.qdhhh.fakebeizhongyi.R;
import com.android.qdhhh.fakebeizhongyi.commom.LocalInfo;
import com.android.qdhhh.fakebeizhongyi.commom.UrlAddress;
import com.android.qdhhh.fakebeizhongyi.me.bean.PersonInfo;
import com.android.qdhhh.fakebeizhongyi.view.RoundImageView;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.squareup.picasso.Picasso;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static java.security.AccessController.getContext;

public class Activity_MyInfo extends AppCompatActivity {

    private RoundImageView my_info_mypic_civ_id;
    private TextView myinfo_name_tv_id;
    private TextView myinfo_gender_tv_id;
    private EditText myinfo_phone_et_id;
    private EditText myinfo_idcard_et_id;
    private TextView myinfo_location_tv_id;
    private TextView myinfo_education_tv_id;
    private EditText myinfo_email_et_id;
    private EditText myinfo_qq_et_id;
    private EditText myinfo_wx_et_id;
    private TextView myinfo_birthday_tv_id;
    private TextView myinfo_industry_tv_id;
    private TextView myinfo_word_tv_id;

    private EditText myinfo_company_et_id;
    private EditText myinfo_com_location_et_id;
    private EditText myinfo_position_et_id;
    private EditText myinfo_favor_et_id;

    private MyInfoOnClickListener myInfoOnClickListener;

    private WheelView add_province;
    private WheelView add_city;
    private AlertDialog addDialog;

    private HashMap<String, List<String>> mapData;

    private List<String> listProvince;
    private List<String> listCity;

    private AlertDialog dateDialog;

    private List<String> listIndustryMain;

    private HashMap<String, List<String>> mapIndustry;

    private AlertDialog industryMainDialog;

    private AlertDialog industryDialog;

    private WheelView industryMainWV;

    private WheelView industryWV;

    private AlertDialog picDialog;

    private TakePhoto takePhoto;

    private PictureOperationListener pictureOperationListener;

    private PersonInfo personInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        personInfo = (PersonInfo) getIntent().getSerializableExtra("personinfo");

        setStatus();

        pictureOperationListener = new PictureOperationListener();

        takePhoto = new TakePhotoImpl(this, pictureOperationListener);

        takePhoto.onCreate(savedInstanceState);

        initView();

        if(personInfo!=null){
            setView();
        }else{
            requestForPersonInfo();
        }

        initAddressDialog();

        initBirthdayDialog();

        initIndustryDialog();

        initPicDialog();

    }

    /**
     * 请求个人信息
     */
    private void requestForPersonInfo() {

            KJHttp kj = new KJHttp();

            HttpParams params = new HttpParams();

            params.put("otherid", LocalInfo.userid);
            params.put("token", LocalInfo.token);
            params.put("userid", LocalInfo.userid);

            kj.post(UrlAddress.getURLAddress(UrlAddress.Url_GetPersonalInfo), params, false, new HttpCallBack() {
                @Override
                public void onSuccess(String t) {

                    try {
                        String code = new JSONObject(t).optJSONObject("result").getString("error_code");

                        if ("200".equals(code)) {
                            personInfo = JSON.parseObject(new JSONObject(t).getString("personalinfo"), PersonInfo.class);
                            setView();
                        } else {
                            Toast.makeText(Activity_MyInfo.this, "信息获取失败", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    super.onSuccess(t);
                }

                @Override
                public void onFailure(int errorNo, String strMsg) {
                    Toast.makeText(Activity_MyInfo.this, "信息获取失败", Toast.LENGTH_SHORT).show();

                    super.onFailure(errorNo, strMsg);
                }
            });
        }

    /**
     * 设置控件的值
     */
    private void setView() {

        Picasso.with(this).load(personInfo.getIconimg()).into(my_info_mypic_civ_id);

        myinfo_name_tv_id.setText(personInfo.getUsername());
        myinfo_gender_tv_id.setText(personInfo.getSex() == 1 ? "男" : "女");
        myinfo_phone_et_id.setText(personInfo.getMobile());
        myinfo_idcard_et_id.setText(personInfo.getIdcard());
        myinfo_location_tv_id.setText(personInfo.getProvince() + personInfo.getArea());
        myinfo_email_et_id.setText(personInfo.getEmail());

        myinfo_qq_et_id.setText(personInfo.getQq());
        myinfo_wx_et_id.setText(personInfo.getWechat());
        myinfo_birthday_tv_id.setText(personInfo.getBirthday());
        myinfo_industry_tv_id.setText(personInfo.getWorktypename());

        myinfo_company_et_id.setText(personInfo.getCompany());
        myinfo_com_location_et_id.setText(personInfo.getOfficetel());
        myinfo_position_et_id.setText(personInfo.getPosition());
        myinfo_favor_et_id.setText(personInfo.getHobbit());
    }

    /**
     * 选择头像时调用
     *
     * @param outState
     * @param outPersistentState
     */
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        takePhoto.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    /**
     * 选择头像时调用
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        takePhoto.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 初始化头像选择的提示框
     */
    private void initPicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = View.inflate(this, R.layout.dialog_me_myinfo_pic, null);

        view.findViewById(R.id.me_myinfo_pic_tv_camera).setOnClickListener(myInfoOnClickListener);
        view.findViewById(R.id.me_myinfo_pic_tv_pic).setOnClickListener(myInfoOnClickListener);
        view.findViewById(R.id.me_myinfo_pic_tv_cancle).setOnClickListener(myInfoOnClickListener);

        picDialog = builder.setView(view).create();

        picDialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    /**
     * 初始化行业的选择框（数据源暂时以地址代替）
     */
    private void initIndustryDialog() {

        getIndustryData();

        industryMainWV = new WheelView(this);

        industryMainWV.setSkin(WheelView.Skin.Holo);

        industryMainWV.setWheelAdapter(new ArrayWheelAdapter(this));

        industryMainWV.setWheelData(listIndustryMain);

        industryWV = new WheelView(this);

        industryWV.setSkin(WheelView.Skin.Holo);

        industryWV.setWheelAdapter(new ArrayWheelAdapter(this));

        industryWV.setWheelData(mapIndustry.get(listIndustryMain.get(0)));

        industryMainWV.join(industryWV);

        industryMainWV.joinDatas(mapIndustry);

        AlertDialog.Builder builderMain = new AlertDialog.Builder(this);

        builderMain.setView(industryMainWV).setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                industryMainDialog.dismiss();
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                industryMainDialog.dismiss();
                industryDialog.show();
            }
        });

        industryMainDialog = builderMain.create();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(industryWV).setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                industryDialog.dismiss();
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myinfo_industry_tv_id.setText(industryWV.getSelectionItem().toString());
                industryDialog.dismiss();
            }
        });

        industryDialog = builder.create();

    }

    /**
     * 需要请求来获取行业的数据源
     */
    private void getIndustryData() {
        listIndustryMain = new LinkedList<>();

        listIndustryMain = listProvince;

        mapIndustry = new HashMap<String, List<String>>();

        mapIndustry = mapData;

        /**
         * TODO
         */

    }

    ;

    /**
     * 初始化生日选择器的选择框
     */
    private void initBirthdayDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final DatePicker dataPaker = new DatePicker(this);

        builder.setView(dataPaker).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dateDialog.dismiss();
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myinfo_birthday_tv_id.setText(new StringBuffer().append(dataPaker.getYear())
                        .append(dataPaker.getMonth() + 1).append(dataPaker.getDayOfMonth()));
                dateDialog.dismiss();
            }
        });

        dateDialog = builder.create();

    }

    /**
     * 初始化地址选择框
     */
    private void initAddressDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View addView = View.inflate(this, R.layout.myinfo_address_dialog, null);

        add_province = (WheelView) addView.findViewById(R.id.add_province);

        add_city = (WheelView) addView.findViewById(R.id.add_city);

        setWheelView();

        builder.setView(addView);

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addDialog.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                StringBuilder sb = new StringBuilder();

                sb.append(add_province.getSelectionItem().toString());
                sb.append(add_city.getSelectionItem().toString());

                myinfo_location_tv_id.setText(sb.toString());

                addDialog.dismiss();
            }
        });

        addDialog = builder.create();

    }

    /**
     * 设置地址选择框里的wheelview控件
     */
    private void setWheelView() {

        mapData = new HashMap<>();

        getDataAddress();

        add_province.setWheelAdapter(new ArrayWheelAdapter(this));

        add_province.setSkin(WheelView.Skin.Holo);

        add_city.setWheelAdapter(new ArrayWheelAdapter(this));

        add_city.setSkin(WheelView.Skin.Holo);

        add_city.setWheelData(mapData.get(listProvince.get(0)));

        add_province.setWheelData(listProvince);

        add_province.join(add_city);

        add_province.joinDatas((HashMap<String, List<String>>) mapData);

    }


    /**
     * 获取地址选择器的数据源
     */
    private void getDataAddress() {

        listProvince = new LinkedList<>();

        String data = openAssetsText();

        Log.i("haha", data);

        try {
            JSONArray jsonArray = new JSONObject(data).getJSONArray("citylist");

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject obj = jsonArray.getJSONObject(i);

                String provinceName = obj.getString("p");

                listProvince.add(provinceName);

                JSONArray array = new JSONArray(obj.getString("c"));

                listCity = new LinkedList<>();

                for (int j = 0; j < array.length(); j++) {

                    listCity.add(array.getJSONObject(j).getString("n"));

                }
                mapData.put(provinceName, listCity);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    /**
     * 从资源文件中获取地址的数据
     *
     * @return 地址的Json数据
     */
    public String openAssetsText() {
        Resources res = this.getResources();
        InputStream inputStream = null;
        String str = null;
        try {
            inputStream = res.getAssets().open("city.json");
            byte[] buffer = new byte[inputStream.available()];
            while (inputStream.read(buffer) != -1) {
            }
            str = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return str;
    }


    /**
     * 初始化界面上的控件
     */
    private void initView() {

        myInfoOnClickListener = new MyInfoOnClickListener();

        findViewById(R.id.myinfo_mypic_rl_id).setOnClickListener(myInfoOnClickListener);
        findViewById(R.id.myinfo_location_rl_id).setOnClickListener(myInfoOnClickListener);
        findViewById(R.id.myinfo_education_rl_id).setOnClickListener(myInfoOnClickListener);
        findViewById(R.id.myinfo_industry_rl_id).setOnClickListener(myInfoOnClickListener);
        findViewById(R.id.myinfo_birthday_rl_id).setOnClickListener(myInfoOnClickListener);
        findViewById(R.id.myinfo_word_rl_id).setOnClickListener(myInfoOnClickListener);
        findViewById(R.id.myinfo_mypic_rl_id).setOnClickListener(myInfoOnClickListener);
        findViewById(R.id.me_myinfo_backicon_iv_id).setOnClickListener(myInfoOnClickListener);

        my_info_mypic_civ_id = (RoundImageView) findViewById(R.id.my_info_mypic_civ_id);

        myinfo_name_tv_id = (TextView) findViewById(R.id.myinfo_name_tv_id);
        myinfo_gender_tv_id = (TextView) findViewById(R.id.myinfo_gender_tv_id);
        myinfo_location_tv_id = (TextView) findViewById(R.id.myinfo_location_tv_id);
        myinfo_education_tv_id = (TextView) findViewById(R.id.myinfo_education_tv_id);
        myinfo_birthday_tv_id = (TextView) findViewById(R.id.myinfo_birthday_tv_id);
        myinfo_industry_tv_id = (TextView) findViewById(R.id.myinfo_industry_tv_id);
        myinfo_word_tv_id = (TextView) findViewById(R.id.myinfo_word_tv_id);

        myinfo_phone_et_id = (EditText) findViewById(R.id.myinfo_phone_et_id);
        myinfo_idcard_et_id = (EditText) findViewById(R.id.myinfo_idcard_et_id);
        myinfo_email_et_id = (EditText) findViewById(R.id.myinfo_email_et_id);
        myinfo_qq_et_id = (EditText) findViewById(R.id.myinfo_qq_et_id);
        myinfo_wx_et_id = (EditText) findViewById(R.id.myinfo_wx_et_id);

        myinfo_company_et_id = (EditText) findViewById(R.id.myinfo_company_et_id);
        myinfo_com_location_et_id = (EditText) findViewById(R.id.myinfo_com_location_et_id);
        myinfo_position_et_id = (EditText) findViewById(R.id.myinfo_position_et_id);
        myinfo_favor_et_id = (EditText) findViewById(R.id.myinfo_favor_et_id);

    }

    private void setStatus() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorDefault));
        }
    }

    /**
     * 界面上控件的点击监听事件
     */
    private final class MyInfoOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.myinfo_location_rl_id: {

                    addDialog.show();

                    break;
                }
                case R.id.myinfo_education_rl_id: {
                    break;
                }
                case R.id.myinfo_birthday_rl_id: {

                    dateDialog.show();

                    break;
                }
                case R.id.myinfo_industry_rl_id: {

                    industryMainDialog.show();

                    break;
                }
                case R.id.myinfo_word_rl_id: {
                    break;
                }
                case R.id.myinfo_mypic_rl_id: {

                    picDialog.show();

                    break;
                }
                case R.id.me_myinfo_backicon_iv_id: {

                    onBackPressed();

                    break;
                }
                case R.id.me_myinfo_pic_tv_pic: {

                    getPicFromPic();

                    picDialog.dismiss();
                    break;
                }
                case R.id.me_myinfo_pic_tv_camera: {

                    getPicFromCamera();

                    picDialog.dismiss();
                    break;
                }
                case R.id.me_myinfo_pic_tv_cancle: {
                    picDialog.dismiss();
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }


    /**
     * 从相册选取图片的方法
     */
    private void getPicFromPic() {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);

        CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).create();

        CropOptions cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(false).create();

        takePhoto.onEnableCompress(compressConfig, true).onPickFromGalleryWithCrop(imageUri, cropOptions);
    }

    /**
     * 从相机拍摄选取图片的方法
     */
    private void getPicFromCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);

        CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).create();

        CropOptions cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(false).create();

        takePhoto.onEnableCompress(compressConfig, true).onPickFromCaptureWithCrop(imageUri, cropOptions);
    }


    /**
     * 返回图片时的调用
     */
    private final class PictureOperationListener implements TakePhoto.TakeResultListener {
        @Override
        public void takeSuccess(String imagePath) {
            KJBitmap kj = new KJBitmap();

            kj.display(my_info_mypic_civ_id, imagePath);

            upLoadPic(imagePath);

        }

        @Override
        public void takeFail(String msg) {
        }

        @Override
        public void takeCancel() {

        }
    }

    /**
     * 上传图片
     */
    private void upLoadPic(final String str) {

        File file = new File(str);

        KJHttp kj = new KJHttp();

        HttpParams params = new HttpParams();

        params.put("apptype", 1);
        params.put("filetype", 1);
        params.put("file", file);

        kj.post(UrlAddress.getURLAddress(UrlAddress.Url_FileUpload), params, false, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {

                Log.i("------", t);

                try {
                    String strCode = new JSONObject(t).optJSONObject("result").getString("error_code");
                    if ("200".equals(strCode)) {
                        Toast.makeText(Activity_MyInfo.this, "上传成功", Toast.LENGTH_SHORT).show();

                        String url = new JSONObject(t).optJSONArray("fileurls").getString(0);

                        updataPic(url);
                    }
                    Toast.makeText(Activity_MyInfo.this, "上传成功", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onSuccess(t);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
            }
        });
    }

    private void updataPic(String url) {

        KJHttp kj = new KJHttp();

        HttpParams params = new HttpParams();

        params.put("iconurl_s", "");
        params.put("token", LocalInfo.token);
        params.put("iconurl", url);
        params.put("userid", LocalInfo.userid);

        kj.post(UrlAddress.getURLAddress(UrlAddress.Url_PersonPicUpload), params, false, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {

                try {
                    String code = new JSONObject(t).optJSONObject("result").getString("error_code");

                    if ("200".equals(code)) {
                        Toast.makeText(Activity_MyInfo.this, "头像更新成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Activity_MyInfo.this, "头像更新失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onSuccess(t);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                Toast.makeText(Activity_MyInfo.this, "头像更新请求失败", Toast.LENGTH_SHORT).show();
                super.onFailure(errorNo, strMsg);
            }
        });
    }
}
