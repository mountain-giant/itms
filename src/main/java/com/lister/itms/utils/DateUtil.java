package com.lister.itms.utils;

import com.lister.itms.exception.BizException;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Describe : 日期处理工具类
 * Created by Lister<728661851@qq.com/> on 16/12/11 22:14.
 * Update reason :
 * Updated by Lister<728661851@qq.com/> on 16/12/11 22:14.
 * Remark : 修改代码请务必加上,修改日期,修改人,修改原因.每次修改都应该出现新的两行 'Updated reason'
 * 以及 'Updateed by XXX on XXXXXXXX XXXXXX',并且修改版本号
 * Version : 1.0
 */
public class DateUtil {

    /**
     * 以空间换时间获取日期格式化对象
     */
    private static final ThreadLocal<DateFormat> tl = new ThreadLocal<DateFormat>(){
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
    /**
     * 以空间换时间获取日期格式化对象
     */
    private static final ThreadLocal<DateFormat> t2 = new ThreadLocal<DateFormat>(){
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    /**
     * 当前日期之前某一天
     * @param day 相减日期
     * @return
     */
    public static String daySubtraction(int day){
        Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。    
        cal.add(Calendar.DAY_OF_MONTH, -day);//取当前日期的前一天.    
        return tl.get().format(cal.getTime());
    }

    /**
     * 日期字符串转Date
     * @param date
     * @param format
     * @return
     * @throws BizException
     */
    public static Date strParseDate(String date, String format) throws BizException {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        try {
            return sf.parse(date);
        } catch (ParseException e) {
            throw new BizException("日期格式不正确");
        }
    }
    
    /**
     * Date转字符串
     * @param date
     * @return
     * @throws BizException
     */
    public static String dateParseStr(Date date) {
        return tl.get().format(date);
    }

    /**
     * Date转字符串
     * @param date
     * @return
     * @throws BizException
     */
    public static String dateParseStrTime(Date date) {
        return t2.get().format(date);
    }

    /**
     * 日期字符串转Date
     * @param date
     * @return
     * @throws BizException
     */
    public static Date strParseDate(String date) throws BizException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH24:mm:ss");
        try {
            return sf.parse(date);
        } catch (ParseException e) {
            throw new BizException("日期格式不正确");
        }
    }

    /**
     * 月份转换成季度
     * @param month
     * @return
     */
    public static String monthToQuarter(String month) throws BizException {
        month = month.trim();
        if (StringUtils.isEmpty(month)){
            throw new BizException("月份不能为空");
        }

        switch (month){
            case "1":
            case "2":
            case "3":
                return "1";
            case "4":
            case "5":
            case "6":
                return "2";
            case "7":
            case "8":
            case "9":
                return "3";
            case "10":
            case "11":
            case "12":
                return "4";
        }
        throw new BizException("月份不符合规范，请重新填写");
    }

    public static String longToString(Long time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(time));
    }
}
