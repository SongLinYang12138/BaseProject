package com.bondex.ysl.battledore.plan;

import com.bondex.ysl.databaselibrary.base.bean.PlateType;
import com.bondex.ysl.databaselibrary.base.bean.ProtectType;
import com.bondex.ysl.databaselibrary.base.bean.SubPlateType;
import com.bondex.ysl.databaselibrary.hawb.HAWBBean;
import org.jetbrains.annotations.NotNull;

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
public class PlanBean{

    private String mBillTotal;
    private String hawbTotal;
    private int QtyTotal;
    private String WeightTotal;
    private String VolumeTotal;
    private String Flight;
    private String flghtDate;
    private String Destination;
    private List<PlateType> plateType;

    private List<ProtectType> protectType;
    private List<SubPlateType> subPlateTypel;
    private List<HAWBBean> Hawbs;


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

    public List<PlateType> getPlateType() {
        return plateType;
    }

    public void setPlateType(List<PlateType> plateType) {
        this.plateType = plateType;
    }

    public List<ProtectType> getProtectType() {
        return protectType;
    }

    public void setProtectType(List<ProtectType> protectType) {
        this.protectType = protectType;
    }

    public List<SubPlateType> getSubPlateTypel() {
        return subPlateTypel;
    }

    public void setSubPlateTypel(List<SubPlateType> subPlateTypel) {
        this.subPlateTypel = subPlateTypel;
    }

    public List<HAWBBean> getHawbs() {
        return Hawbs;
    }

    public void setHawbs(List<HAWBBean> hawbs) {
        Hawbs = hawbs;
    }


}
