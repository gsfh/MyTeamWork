package com.gsfh.myteamwork.vmovie.util;

import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 2016/6/30.
 */
public class TimeEXchange {
    /**
     *
     * 需要转换的毫秒数
     * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
     * @author fy.zhang
     */
    public static String englishtimeXYZ(long mss) {
        mss*=1000;
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        return days + " days " + hours + " hours " + minutes + " minutes "
                + seconds + " seconds ";
    }
    /**
     *
     * @param begin 时间段的开始
     * @param end   时间段的结束
     * @return  输入的两个Date类型数据之间的时间间格用* days * hours * minutes * seconds的格式展示
     * @author fy.zhang
     */
    public static String formatDuring(Date begin, Date end) {
        return timeXYZ(end.getTime() - begin.getTime());
    }

    /**
     * @ 董传亮
     * 为当前项目设计的方法
     * @param mss
     * @return
     */
    public static String timeXYZ(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);//zhengshu  17
        Calendar ca = Calendar.getInstance();
//        SimpleDateFormat bartDateFormat = new SimpleDateFormat(
//                "yyyy-MM-dd HH:mm");
        SimpleDateFormat bartDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        String nowtime = bartDateFormat.format(ca.getTime());

        int timeinterval =0+ (int) (days)-16989;
        Calendar ca2 = Calendar.getInstance();
        ca.add(ca.DATE, timeinterval);
        SimpleDateFormat bartDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        String latertime = bartDateFormat.format(ca.getTime());
        String week;

        if( ((int) (days%7))==1){week="星期五" ;return latertime+"  "+week;}
        if( ((int) (days%7))==2){ week="星期六" ; return latertime+"  "+week;}
            if( ((int) (days%7))==3){ week="星期日" ; return latertime+"  "+week;}
            if( ((int) (days%7))==4){ week="星期一" ; return latertime+"  "+week;}
            if( ((int) (days%7))==5){ week="星期二" ; return latertime+"  "+week;}
            if( ((int) (days%7))==6){ week="星期三";  return latertime+"  "+week; }
            if( ((int) (days%7))==0){ week="星期四" ; return latertime+"  "+week; }

return null;

    }

    /**
     * @ 董传亮 简单年月日
     * @return
     */
    public static String datamake(long mills){
        Date date=new Date(mills*1000);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM:dd");
        return  simpleDateFormat.format(date) ;
    }

    /**
     * @ 董传亮 简单年月日
     * @return
     */
    public static String clockmake(long mills){

        Date date=new Date(mills*1000);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
        return  simpleDateFormat.format(date) ;
    }



//    G 年代标志符
//    y 年
//    M 月
//    d 日
//    h 时 在上午或下午 (1~12)
//    H 时 在一天中 (0~23)
//    m 分
//    s 秒
//    S 毫秒
//    E 星期
//    D 一年中的第几天
//    F 一月中第几个星期几
//    w 一年中第几个星期
//    W 一月中第几个星期
//    a 上午 / 下午 标记符
//    k 时 在一天中 (1~24)
//    K 时 在上午或下午 (0~11)
//    z 时区

//    SimpleDateFormat myFmt=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
//    SimpleDateFormat myFmt1=new SimpleDateFormat("yy/MM/dd HH:mm");
//    SimpleDateFormat myFmt2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//等价于now.toLocaleString()
//    SimpleDateFormat myFmt3=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E ");
//    SimpleDateFormat myFmt4=new SimpleDateFormat(
//            "一年中的第 D 天 一年中第w个星期 一月中第W个星期 在一天中k时 z时区");


}
