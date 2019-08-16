package com.bondex.ysl.battledore.plan;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.bondex.ysl.databaselibrary.base.bean.PlateType;
import com.bondex.ysl.databaselibrary.base.bean.ProtectType;
import com.bondex.ysl.databaselibrary.base.bean.SubPlateType;
import com.bondex.ysl.databaselibrary.hawb.HAWBBean;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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
    private int QtyTotal;
    private String WeightTotal;
    private String VolumeTotal;
    private String Flight;
    private String flghtDate;
    private String Destination;
    private String boardNum;//板号
    private String lockNum;//板锁号
    private ArrayList<PlateType> plateType;

    private ArrayList<ProtectType> protectType;
    private ArrayList<SubPlateType> subPlateTypel;
    private ArrayList<HAWBBean> hawbs;
    private String search;//用于在binearySearch中进行搜索的字段

    public PlanBean() {
    }

    protected PlanBean(Parcel in) {
        id = in.readString();
        mBillTotal = in.readString();
        hawbTotal = in.readString();
        QtyTotal = in.readInt();
        WeightTotal = in.readString();
        VolumeTotal = in.readString();
        Flight = in.readString();
        flghtDate = in.readString();
        Destination = in.readString();
        boardNum = in.readString();
        lockNum = in.readString();
        plateType = in.createTypedArrayList(PlateType.CREATOR);
        protectType = in.createTypedArrayList(ProtectType.CREATOR);
        subPlateTypel = in.createTypedArrayList(SubPlateType.CREATOR);
        hawbs = in.createTypedArrayList(HAWBBean.CREATOR);
        search = in.readString();
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
        return QtyTotal;
    }

    public void setQtyTotal(int qtyTotal) {
        QtyTotal = qtyTotal;
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

    public ArrayList<PlateType> getPlateType() {
        return plateType;
    }

    public void setPlateType(ArrayList<PlateType> plateType) {
        this.plateType = plateType;
    }

    public ArrayList<ProtectType> getProtectType() {
        return protectType;
    }

    public void setProtectType(ArrayList<ProtectType> protectType) {
        this.protectType = protectType;
    }

    public ArrayList<SubPlateType> getSubPlateTypel() {
        return subPlateTypel;
    }

    public void setSubPlateTypel(ArrayList<SubPlateType> subPlateTypel) {
        this.subPlateTypel = subPlateTypel;
    }

    public ArrayList<HAWBBean> getHawbs() {
        return hawbs;
    }

    public void setHawbs(ArrayList<HAWBBean> hawbs) {
        this.hawbs = hawbs;
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
        dest.writeInt(QtyTotal);
        dest.writeString(WeightTotal);
        dest.writeString(VolumeTotal);
        dest.writeString(Flight);
        dest.writeString(flghtDate);
        dest.writeString(Destination);
        dest.writeString(boardNum);
        dest.writeString(lockNum);
        dest.writeTypedList(plateType);
        dest.writeTypedList(protectType);
        dest.writeTypedList(subPlateTypel);
        dest.writeTypedList(hawbs);
        dest.writeString(search);
    }
}
