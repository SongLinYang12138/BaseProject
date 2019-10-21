package com.bondex.ysl.databaselibrary.plan;

/**
 * date: 2019/10/10
 * Author: ysl
 * description:
 */
public class BattleBoardBean {

    private String battleDate; //打板日期

    private String pickupDate; //提货日期

    private String boardNum;//板号

    private String boardType;//打板类型

    private String mHwab;//主单号

    private String hawb;//分单号

    private String qty;//件数

    private String battleQty;//打板件数

    private String battleFee;//打板费用

    private String entiretyProtect = "";//整体保护

    private String singleProtect = "";//单件保护

    private String oldsmallPad = "";//老小垫

    private String newsmallPad = "";//新小垫

    private String oldBigPad = "";//老大垫

    private String newBigPad = "";//新大垫

    private String newEntiretyProect = "";//新品整体保护

    private String newSingleProect = "";//新品单体保护


    public String getBattleDate() {
        return battleDate == null ? "" : battleDate;
    }

    public void setBattleDate(String battleDate) {
        this.battleDate = battleDate == null ? "" : battleDate;
    }

    public String getPickupDate() {
        return pickupDate == null ? "" : pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate == null ? "" : pickupDate;
    }

    public String getBoardNum() {
        return boardNum == null ? "" : boardNum;
    }

    public void setBoardNum(String boardNum) {
        this.boardNum = boardNum == null ? "" : boardNum;
    }

    public String getBoardType() {
        return boardType == null ? "" : boardType;
    }

    public void setBoardType(String boardType) {
        this.boardType = boardType == null ? "" : boardType;
    }

    public String getmHwab() {
        return mHwab == null ? "" : mHwab;
    }

    public void setmHwab(String mHwab) {
        this.mHwab = mHwab == null ? "" : mHwab;
    }

    public String getHawb() {
        return hawb == null ? "" : hawb;
    }

    public void setHawb(String hawb) {
        this.hawb = hawb == null ? "" : hawb;
    }

    public void addHawb(String hawb) {

        this.hawb += ";" + hawb;

    }

    public String getQty() {
        return qty == null ? "" : qty;
    }

    public void setQty(String qty) {
        this.qty = qty == null ? "" : qty;
    }

    public String getBattleQty() {
        return battleQty == null ? "" : battleQty;
    }

    public void setBattleQty(String battleQty) {
        this.battleQty = battleQty == null ? "" : battleQty;
    }

    public String getBattleFee() {
        return battleFee == null ? "" : battleFee;
    }

    public void setBattleFee(String battleFee) {
        this.battleFee = battleFee == null ? "" : battleFee;
    }

    public String getEntiretyProtect() {
        return entiretyProtect == null ? "" : entiretyProtect;
    }

    public void setEntiretyProtect(String entiretyProtect) {
        this.entiretyProtect = entiretyProtect == null ? "" : entiretyProtect;
    }

    public String getSingleProtect() {
        return singleProtect == null ? "" : singleProtect;
    }

    public void setSingleProtect(String singleProtect) {
        this.singleProtect = singleProtect == null ? "" : singleProtect;
    }

    public String getOldsmallPad() {
        return oldsmallPad == null ? "" : oldsmallPad;
    }

    public void setOldsmallPad(String oldsmallPad) {
        this.oldsmallPad = oldsmallPad == null ? "" : oldsmallPad;
    }

    public String getNewsmallPad() {
        return newsmallPad == null ? "" : newsmallPad;
    }

    public void setNewsmallPad(String newsmallPad) {
        this.newsmallPad = newsmallPad == null ? "" : newsmallPad;
    }

    public String getOldBigPad() {
        return oldBigPad == null ? "" : oldBigPad;
    }

    public void setOldBigPad(String oldBigPad) {
        this.oldBigPad = oldBigPad == null ? "" : oldBigPad;
    }

    public String getNewBigPad() {
        return newBigPad == null ? "" : newBigPad;
    }

    public void setNewBigPad(String newBigPad) {
        this.newBigPad = newBigPad == null ? "" : newBigPad;
    }

    public String getNewEntiretyProect() {
        return newEntiretyProect == null ? "" : newEntiretyProect;
    }

    public void setNewEntiretyProect(String newEntiretyProect) {
        this.newEntiretyProect = newEntiretyProect == null ? "" : newEntiretyProect;
    }

    public String getNewSingleProect() {
        return newSingleProect == null ? "" : newSingleProect;
    }

    public void setNewSingleProect(String newSingleProect) {
        this.newSingleProect = newSingleProect == null ? "" : newSingleProect;
    }

    @Override
    public boolean equals(Object obj) {

        BattleBoardBean bean = (BattleBoardBean) obj;

        if (bean.getmHwab() == null) return false;

        return this.getmHwab().equals(bean.getmHwab());
    }

    @Override
    public int hashCode() {

        return this.getmHwab().hashCode();
    }
}
