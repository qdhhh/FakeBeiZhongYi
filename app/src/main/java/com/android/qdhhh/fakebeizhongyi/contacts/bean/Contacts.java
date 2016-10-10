package com.android.qdhhh.fakebeizhongyi.contacts.bean;

/**
 * Created by qdhhh on 2016/10/9.
 */

public class Contacts {


    private String userid;
    private String username;
    private String iconimg;
    private String departmentname;
    private String classname;
    private int sex;
    private int type;
    private String sign;
    private String mobile;
    private String officetel;
    private String EducationName;
    private String Year;
    private String worktypename;
    private String birthday;
    private String area;
    private String company;
    private String position;
    private String hobbit;
    private String imid;
    private String isvip;

    private String pinyin = "##";
    private String firstLetter = "#";

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIconimg() {
        return iconimg;
    }

    public void setIconimg(String iconimg) {
        this.iconimg = iconimg;
    }

    public String getDepartmentname() {
        return departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOfficetel() {
        return officetel;
    }

    public void setOfficetel(String officetel) {
        this.officetel = officetel;
    }

    public String getEducationName() {
        return EducationName;
    }

    public void setEducationName(String educationName) {
        EducationName = educationName;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getWorktypename() {
        return worktypename;
    }

    public void setWorktypename(String worktypename) {
        this.worktypename = worktypename;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getHobbit() {
        return hobbit;
    }

    public void setHobbit(String hobbit) {
        this.hobbit = hobbit;
    }

    public String getImid() {
        return imid;
    }

    public void setImid(String imid) {
        this.imid = imid;
    }

    public String getIsvip() {
        return isvip;
    }

    public void setIsvip(String isvip) {
        this.isvip = isvip;
    }

    @Override
    public String toString() {
        return "Contacts{" +
                "userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                ", iconimg='" + iconimg + '\'' +
                ", departmentname='" + departmentname + '\'' +
                ", classname='" + classname + '\'' +
                ", sex=" + sex +
                ", type=" + type +
                ", sign='" + sign + '\'' +
                ", mobile='" + mobile + '\'' +
                ", officetel='" + officetel + '\'' +
                ", EducationName='" + EducationName + '\'' +
                ", Year='" + Year + '\'' +
                ", worktypename='" + worktypename + '\'' +
                ", birthday='" + birthday + '\'' +
                ", area='" + area + '\'' +
                ", company='" + company + '\'' +
                ", position='" + position + '\'' +
                ", hobbit='" + hobbit + '\'' +
                ", imid='" + imid + '\'' +
                ", isvip='" + isvip + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", firstLetter='" + firstLetter + '\'' +
                '}';
    }
}
