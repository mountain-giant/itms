package com.lister.itms.vo;

import java.io.Serializable;

/**
 * Describe :
 * Created by Lister<728661851@qq.com/> on 16/12/11 23:15.
 * Update reason :
 * Updated by Lister<728661851@qq.com/> on 16/12/11 23:15.
 * Remark : 修改代码请务必加上,修改日期,修改人,修改原因.每次修改都应该出现新的两行 'Updated reason'
 * 以及 'Updateed by XXX on XXXXXXXX XXXXXX',并且修改版本号
 * Version : 1.0
 */
public class UserFrequencyVo implements Serializable {
    private String userName;
    private Integer activityNum;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getActivityNum() {
        return activityNum;
    }

    public void setActivityNum(Integer activityNum) {
        this.activityNum = activityNum;
    }

    @Override
    public String toString() {
        return "UserFrequencyVO{" +
                "userName='" + userName + '\'' +
                ", activityNum='" + activityNum + '\'' +
                '}';
    }
}
