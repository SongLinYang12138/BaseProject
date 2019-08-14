package com.bondex.ysl.databaselibrary.contact;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import java.io.Serializable;

/**
 * date: 2019/1/11
 * Author: ysl
 * description:
 */
@Entity
public class ContactBean   implements Serializable {


    /**
     * code : 0002930
     * name : 杨慧慧
     * position : 操作
     * department : 潍坊分公司●操作部
     * company : 海程邦达潍坊
     * phone : 15763008215
     * tel : 0536-8087107
     * fax :
     * email : yanghh@bondex.com.cn
     * QQ :
     * MSN : yanghui08086@live.com
     * Tags : null
     * companyCode : 010308
     * departmentCode : 013503
     * positionCode : JF1007070371
     */

    @NonNull
    @PrimaryKey()
    private String code;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "position")
    private String position;
    @ColumnInfo(name = "department")
    private String department;
    @ColumnInfo(name = "company")
    private String company;
    @ColumnInfo(name = "phone")
    private String phone;
    @ColumnInfo(name = "tel")
    private String tel;
    @ColumnInfo(name = "fax")
    private String fax;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "qq")
    private String QQ;
    @ColumnInfo(name = "msn")
    private String MSN;
    @ColumnInfo(name = "tags")
    private String Tags;
    @ColumnInfo(name = "companycode")
    private String companyCode;
    @ColumnInfo(name = "departmentcode")
    private String departmentCode;
    @ColumnInfo(name = "positioncode")
    private String positionCode;







    public String getCode() {
        return code == null ? "" : code;
    }

    public void setCode(String code) {
        this.code = code == null ? "" : code;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name == null ? "" : name;
    }

    public String getPosition() {
        return position == null ? "" : position;
    }

    public void setPosition(String position) {
        this.position = position == null ? "" : position;
    }

    public String getDepartment() {
        return department == null ? "" : department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? "" : department;
    }

    public String getCompany() {
        return company == null ? "" : company;
    }

    public void setCompany(String company) {
        this.company = company == null ? "" : company;


    }

    public String getPhone() {
        return phone == null ? "" : phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? "" : phone;
    }

    public String getTel() {
        return tel == null ? "" : tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? "" : tel;
    }

    public String getFax() {
        return fax == null ? "" : fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? "" : fax;
    }

    public String getEmail() {
        return email == null ? "" : email;
    }

    public void setEmail(String email) {
        this.email = email == null ? "" : email;
    }

    public String getQQ() {
        return QQ == null ? "" : QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ == null ? "" : QQ;
    }

    public String getMSN() {
        return MSN == null ? "" : MSN;
    }

    public void setMSN(String MSN) {
        this.MSN = MSN == null ? "" : MSN;
    }

    public String getTags() {
        return Tags == null ? "" : Tags;
    }

    public void setTags(String tags) {
        Tags = tags == null ? "" : tags;
    }

    public String getCompanyCode() {
        return companyCode == null ? "" : companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode == null ? "" : companyCode;
    }

    public String getDepartmentCode() {
        return departmentCode == null ? "" : departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode == null ? "" : departmentCode;
    }

    public String getPositionCode() {
        return positionCode == null ? "" : positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode == null ? "" : positionCode;
    }

}
