package com.example.administrator.datasport;

import java.io.Serializable;

public class SpotModel implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public SpotModel(){}
    // 今天日期
    public String data;
    // 买时点位
    public String buyPoint;
    // 昨收
    public String yesterdayData;
    // 今开
    public String todayData;
    // 最高
    public String highest;
    // 最低
    public String lowest;
    // 是涨跌
    public String upOrDown;
    // 操作次数
    public String operationCount;
    // 是否正确
    public String rightWrong;
    // 自猜
    public String yourself;
    // 年月
    public String yearMonth;
    // 备注
    public String remarks;

}
