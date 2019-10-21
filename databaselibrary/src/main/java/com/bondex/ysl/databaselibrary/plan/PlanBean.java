package com.bondex.ysl.databaselibrary.plan;

import android.os.Parcel;
import android.os.Parcelable;

import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import com.bondex.ysl.databaselibrary.hawb.HAWBBean;

import java.util.ArrayList;

/**
 * date: 2019/5/30
 * Author: ysl
 * description:
 */
/*{success:true，//Boolean
        msg:”获取成功”,//String
        Data:[
        {
        mBillTotal:主单号总数,int
        hawbTotal:分单号总数,int
        QtyTotal:总件数,int
        WeightTotal:总重量,String
        VolumeTotal:总体积,String
        Flight:航班号,String
        flghtDate:航班日期, yyyy-mm-dd
        Destination:目的港,String
        plateType:{ //板型{板型从后台获取，所以传对象}
        plateName:板型名称String
        plateId:板型ID int
        }，
        protectType:[ 保护类型 （同上）
        {
        protectName: 保护类型名称,String
        protectID:保护类型id,String
        protectNum:保护类型数量, int
        }],
        subPlateType:[ //垫板类型
        {
        SubplateName:垫板名称,String
        SubplateId:垫板id String
        SubplateNum:垫板数量 int
        }],
        Hawbs:[//打板计划中的分单信息
        {
        mBillCode:主单号，String
        hawbCode:分单号，String
        allQty:分单总件数,String
        allWeight:分单总重量,String
        allVoulme:分单总体积,String
        hawbFlight:分单航班号,String
        hwabDestination:分单目的港,String
        }
        ]
        }
        ]}}
        ]*/
public class PlanBean implements Parcelable {


    private String id;
    private String mBillTotal;
    private String hawbTotal;
    private int qtyTotal;
    private String WeightTotal;
    private String VolumeTotal;
    private String Flight;
    private String flghtDate;
    private String battleDate;//打板时间
    private String Destination;
    private String boardNum;//板号
    private String lockNum;//板锁号
    private long battleMilles;//打板时间，毫秒值
    private WorkBentchChoiceBean plateType = new WorkBentchChoiceBean();
    private ArrayList<WorkBentchChoiceBean> protectType = new ArrayList<>();
    private ArrayList<WorkBentchChoiceBean> subPlateTypel = new ArrayList<>();
    private ArrayList<HAWBBean> hawbs = new ArrayList<>();
    private String search;//用于在binearySearch中进行搜索的字段
    private int status;//判断本地板信息，是否提交，或提交失败 0,提交失败 1 提交成功 2 未提交 3未完成的单子


    public void toPlanBean(BattleBoardBean bean) {

//            "battleDate": "9/2/19",
//                "battleFee": "440",
//                "battleQty": "整体保护*1     \n垫板*3",
//                "boardNum": "PMC64759CX",
//                "boardType": "LD",
//                "entiretyProtect": "1",
//                "hawb": "890110036118  2/2;890110036123  2/2;890110036124  1/1;890110036125  1/1;890110036131  1/1;890110036132  2/2;890110036133  1/1;890110036134  1/1",
//                "mHwab": "160-13392470",
//                "newBigPad": "",
//                "newEntiretyProect": "",
//                "newSingleProect": "",
//                "newsmallPad": "",
//                "oldBigPad": "",
//                "oldsmallPad": "3",
//                "pickupDate": "9/2/19",
//                "qty": "11",
//                "singleProtect": ""

        this.mBillTotal = "1";
        this.setWeightTotal("20.0");
        this.setVolumeTotal("30.0");
        this.flghtDate = bean.getPickupDate();
        this.setFlight("CH7824");
        battleDate = bean.getBattleDate();
        boardNum = bean.getBoardNum();
        this.setDestination("LDU");
        setQtyTotal(parseInt(bean.getQty()));

        WorkBentchChoiceBean platType = new WorkBentchChoiceBean();
        platType.setId(0);
        platType.setCount(0);
        platType.setName(bean.getBoardType());
        this.plateType = platType;
        battleMilles = System.currentTimeMillis();
        setStatus(5);

//        WorkBentchChoiceBean("大", 0, 0),
//                WorkBentchChoiceBean("中", 1, 0),
//                WorkBentchChoiceBean("小", 2, 0)
        if (!TextUtils.isEmpty(bean.getEntiretyProtect())) {
            WorkBentchChoiceBean proptect = new WorkBentchChoiceBean("整体保护", 0, parseInt(bean.getEntiretyProtect()));
            protectType.add(proptect);
        }

        if (!TextUtils.isEmpty(bean.getSingleProtect())) {
            WorkBentchChoiceBean proptect = new WorkBentchChoiceBean("单体保护", 1, parseInt(bean.getSingleProtect()));
            protectType.add(proptect);
        }
        if (!TextUtils.isEmpty(bean.getNewEntiretyProect())) {
            WorkBentchChoiceBean proptect = new WorkBentchChoiceBean("新品整体保护", 2, parseInt(bean.getNewEntiretyProect()));
            protectType.add(proptect);
        }

        if (!TextUtils.isEmpty(bean.getNewSingleProect())) {
            WorkBentchChoiceBean proptect = new WorkBentchChoiceBean("新品单体保护", 3, parseInt(bean.getNewSingleProect()));
            protectType.add(proptect);
        }

        if (!TextUtils.isEmpty(bean.getNewBigPad())) {
            WorkBentchChoiceBean pad = new WorkBentchChoiceBean("新大垫", 0, parseInt(bean.getNewBigPad()));
            subPlateTypel.add(pad);
        }

        if (!TextUtils.isEmpty(bean.getNewsmallPad())) {
            WorkBentchChoiceBean pad = new WorkBentchChoiceBean("新小垫", 1, parseInt(bean.getNewsmallPad()));
            subPlateTypel.add(pad);
        }


        if (!TextUtils.isEmpty(bean.getOldBigPad())) {
            WorkBentchChoiceBean pad = new WorkBentchChoiceBean("老大垫", 2, parseInt(bean.getOldBigPad()));
            subPlateTypel.add(pad);
        }
        if (!TextUtils.isEmpty(bean.getOldsmallPad())) {
            WorkBentchChoiceBean pad = new WorkBentchChoiceBean("新小垫", 3, parseInt(bean.getOldsmallPad()));
            subPlateTypel.add(pad);
        }


        String[] hawbs = bean.getHawb().split(";");

        this.hawbTotal = hawbs.length + "";


        for (int i = 0; i < hawbs.length; ++i) {

            String hawb = hawbs[i];

            HAWBBean hawbBean = new HAWBBean();
            hawbBean.setmBillCode(bean.getmHwab());
            hawbBean.setHawb(hawb);
            hawbBean.setId(i + "" + hawb);
            hawbBean.setBoardId(i + "" + hawb);
            hawbBean.setDate(bean.getPickupDate());
            hawbBean.setDetination("ldu");
            hawbBean.setQty(parseInt(bean.getBattleQty()));
            this.hawbs.add(hawbBean);
        }


    }


    private int parseInt(String str) {

        int i = 0;
        try {
            i = Integer.parseInt(str);
        } catch (Exception e) {
            i = 0;
        }

        return i;
    }

    protected PlanBean(Parcel in) {
        id = in.readString();
        mBillTotal = in.readString();
        hawbTotal = in.readString();
        qtyTotal = in.readInt();
        WeightTotal = in.readString();
        VolumeTotal = in.readString();
        Flight = in.readString();
        flghtDate = in.readString();
        battleDate = in.readString();
        Destination = in.readString();
        boardNum = in.readString();
        lockNum = in.readString();
        battleMilles = in.readLong();
        plateType = in.readParcelable(WorkBentchChoiceBean.class.getClassLoader());
        protectType = in.createTypedArrayList(WorkBentchChoiceBean.CREATOR);
        subPlateTypel = in.createTypedArrayList(WorkBentchChoiceBean.CREATOR);
        hawbs = in.createTypedArrayList(HAWBBean.CREATOR);
        search = in.readString();
        status = in.readInt();
    }

    public static final Creator<PlanBean> CREATOR = new Creator<PlanBean>() {
        @Override
        public PlanBean createFromParcel(Parcel in) {
            return new PlanBean(in);
        }

        @Override
        public PlanBean[] newArray(int size) {
            return new PlanBean[size];
        }
    };

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public PlanBean() {
    }

    public long getBattleMilles() {
        return battleMilles;
    }

    public void setBattleMilles(long battleMilles) {
        this.battleMilles = battleMilles;
    }

    public WorkBentchChoiceBean getPlateType() {
        return plateType;
    }

    public void setPlateType(WorkBentchChoiceBean plateType) {
        this.plateType = plateType;
    }


    public ArrayList<WorkBentchChoiceBean> getProtectType() {
        return protectType;
    }

    public void setProtectType(ArrayList<WorkBentchChoiceBean> protectType) {
        this.protectType = protectType;
    }

    public ArrayList<WorkBentchChoiceBean> getSubPlateTypel() {
        return subPlateTypel;
    }

    public void setSubPlateTypel(ArrayList<WorkBentchChoiceBean> subPlateTypel) {
        this.subPlateTypel = subPlateTypel;
    }

    public ArrayList<HAWBBean> getHawbs() {
        return hawbs;
    }

    public String getBattleDate() {
        return battleDate == null ? "" : battleDate;
    }

    public void setBattleDate(String battleDate) {
        this.battleDate = battleDate == null ? "" : battleDate;
    }


    public String getBoardNum() {
        return boardNum == null ? "" : boardNum;
    }

    public void setBoardNum(String boardNum) {
        this.boardNum = boardNum == null ? "" : boardNum;
    }

    public String getLockNum() {
        return lockNum == null ? "" : lockNum;
    }

    public void setLockNum(String lockNum) {
        this.lockNum = lockNum == null ? "" : lockNum;
    }


    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id == null ? "" : id;
    }

    public String getSearch() {
        return search == null ? "" : search;
    }

    public void setSearch(String search) {
        this.search = search == null ? "" : search;
    }

    public String getmBillTotal() {
        return mBillTotal == null ? "" : mBillTotal;
    }

    public void setmBillTotal(String mBillTotal) {
        this.mBillTotal = mBillTotal == null ? "" : mBillTotal;
    }

    public String getHawbTotal() {
        return hawbTotal == null ? "" : hawbTotal;
    }

    public void setHawbTotal(String hawbTotal) {
        this.hawbTotal = hawbTotal == null ? "" : hawbTotal;
    }

    public int getQtyTotal() {
        return qtyTotal;
    }

    public void setQtyTotal(int qtyTotal) {
        this.qtyTotal = qtyTotal;
    }

    public String getWeightTotal() {
        return WeightTotal == null ? "" : WeightTotal;
    }

    public void setWeightTotal(String weightTotal) {
        WeightTotal = weightTotal == null ? "" : weightTotal;
    }

    public String getVolumeTotal() {
        return VolumeTotal == null ? "" : VolumeTotal;
    }

    public void setVolumeTotal(String volumeTotal) {
        VolumeTotal = volumeTotal == null ? "" : volumeTotal;
    }

    public String getFlight() {
        return Flight == null ? "" : Flight;
    }

    public void setFlight(String flight) {
        Flight = flight == null ? "" : flight;
    }

    public String getFlghtDate() {
        return flghtDate == null ? "" : flghtDate;
    }

    public void setFlghtDate(String flghtDate) {
        this.flghtDate = flghtDate == null ? "" : flghtDate;
    }

    public String getDestination() {
        return Destination == null ? "" : Destination;
    }

    public void setDestination(String destination) {
        Destination = destination == null ? "" : destination;
    }


    public void setHawbs(ArrayList<HAWBBean> hawbs) {
        this.hawbs = hawbs;
    }


    @Override
    public boolean equals(Object obj) {

        PlanBean bean = (PlanBean) obj;

        if (bean != null && bean.id != null) return this.id.equals(bean.getId());
        return false;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(mBillTotal);
        dest.writeString(hawbTotal);
        dest.writeInt(qtyTotal);
        dest.writeString(WeightTotal);
        dest.writeString(VolumeTotal);
        dest.writeString(Flight);
        dest.writeString(flghtDate);
        dest.writeString(battleDate);
        dest.writeString(Destination);
        dest.writeString(boardNum);
        dest.writeString(lockNum);
        dest.writeLong(battleMilles);
        dest.writeParcelable(plateType, flags);
        dest.writeTypedList(protectType);
        dest.writeTypedList(subPlateTypel);
        dest.writeTypedList(hawbs);
        dest.writeString(search);
        dest.writeInt(status);
    }
}
