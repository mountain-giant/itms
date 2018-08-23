package com.lister.itms.utils;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

/**
 * Describe :
 * Created by Lister<728661851@qq.com/> on 16/11/10 16:27.
 * Update reason :
 * Updated by Lister<728661851@qq.com/> on 16/11/10 16:27.
 * Remark : 修改代码请务必加上,修改日期,修改人,修改原因.每次修改都应该出现新的两行 'Updated reason'
 * 以及 'Updateed by XXX on XXXXXXXX XXXXXX',并且修改版本号
 * Version : 1.0
 */
@Slf4j
public class MD5Generator {
    public static String toMD5(String str) {
        try {
            //生成实现指定摘要算法的 MessageDigest 对象。
            MessageDigest md = MessageDigest.getInstance("MD5");
            //使用指定的字节数组更新摘要。
            md.update(str.getBytes());
            //通过执行诸如填充之类的最终操作完成哈希计算。
            byte b[] = md.digest();
            //生成具体的md5密码到buf数组
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        }
        catch (Exception e) {
           log.error("MD5加密失败");
        }
        return null;
    }
    
    public static void main(String[] args) {
        try {
            System.out.print(MD5Generator.toMD5("itms"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
