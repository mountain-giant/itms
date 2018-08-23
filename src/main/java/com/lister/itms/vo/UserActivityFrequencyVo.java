package com.lister.itms.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Describe : 用户活动频率 VO
 * Created by Lister<728661851@qq.com/> on 16/12/11 22:05.
 * Update reason :
 * Updated by Lister<728661851@qq.com/> on 16/12/11 22:05.
 * Remark : 修改代码请务必加上,修改日期,修改人,修改原因.每次修改都应该出现新的两行 'Updated reason'
 * 以及 'Updateed by XXX on XXXXXXXX XXXXXX',并且修改版本号
 * Version : 1.0
 */
public class UserActivityFrequencyVo implements Serializable {
    
    /**
     * 当前时间前五天的集合
     */
    private List<String> dates = new ArrayList<String>();

    /**
     * 对应的数据                    
     */
    private Map<String,List<Integer>> datas = new HashMap<String, List<Integer>>();

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public Map<String, List<Integer>> getDatas() {
        return datas;
    }

    public void setDatas(Map<String, List<Integer>> datas) {
        this.datas = datas;
    }

    @Override
    public String toString() {
        return "UserActivityFrequencyVo{" +
                "dates=" + dates +
                ", datas=" + datas +
                '}';
    }
}
