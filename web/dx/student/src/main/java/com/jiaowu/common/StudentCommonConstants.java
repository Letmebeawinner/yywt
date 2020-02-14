package com.jiaowu.common;

import com.a_268.base.util.PropertyUtil;

/**
 * Created by 李帅雷 on 2017/10/17.
 */
public class StudentCommonConstants {
    //获取属性配置文件
    private static PropertyUtil propertyUtil = PropertyUtil.getInstance("config");
    //项目本身域名
    public final static String contextPath = propertyUtil.getProperty("context.path");
    //图片上传服务域名
    public final static String imageServicePath = propertyUtil.getProperty("image.service.path");
    // sms根域名
    public final static String smsPath = propertyUtil.getProperty("sms.root");

    public final static String BASE_PATH=propertyUtil.getProperty("base.root");

    public final static String YICARDTONG_PATH=propertyUtil.getProperty("yicardtong.root");
    //班主任角色ID
    public static final Long CLASS_LEADER_ROLE_ID=Long.parseLong(propertyUtil.getProperty("classleader.roleid"));
    //学员角色ID
    public static final Long STUDENT_ROLE_ID=Long.parseLong(propertyUtil.getProperty("student.roleid"));
    //学员处角色ID
    public static final Long XUEYUANCHU_ROLE_ID=Long.parseLong(propertyUtil.getProperty("xueyuanchu.roleid"));

    //大礼堂一楼座区图(478)
    public static final int[] ZUOQUTU478=new int[]{
            4, 16, 4,//1
            5, 17, 5,//2
            6, 17, 6,//3
            7, 17, 7,//4
            7, 17, 7,//5
            7, 17, 7,//6
            7, 17, 7,//7
            7, 17, 7,//8
            7, 17, 7,//9
            5, 17, 5,//10
            7, 17, 7,//11
            7, 17, 7,//12
            7, 17, 7,//13
            7, 17, 7,//14
            7, 17, 7,//15
            7, 15, 7//16
    };

    //100座座区图(100)
    public static final int[] ZUOQUTU100=new int[]{
            4, 6, 4,//1
            4, 6, 4,//2
            4, 6, 4,//3
            4, 6, 4,//4
            4, 6, 4,//5
            4, 6, 4,//6
            4, 6, 4,//7
            4, 6, 4//8
    };

    //大礼堂一二楼(736)
    public static final int[] ZUOQUTU736=new int[]{
            4, 16, 4,//1
            5, 17, 5,//2
            6, 17, 6,//3
            7, 17, 7,//4
            7, 17, 7,//5
            7, 17, 7,//6
            7, 17, 7,//7
            7, 17, 7,//8
            7, 17, 7,//9
            5, 17, 5,//10
            7, 17, 7,//11
            7, 17, 7,//12
            7, 17, 7,//13
            7, 17, 7,//14
            7, 17, 7,//15
            7, 15, 7,//16
            5, 8, 8, 5,//1
            5, 8, 8, 5,//2
            0, 8, 8, 0,//3
            5, 8, 8, 5,//4
            5, 8, 8, 5,//5
            5, 8, 8, 5,//6
            5, 8, 8, 5,//7
            5, 8, 8, 5,//8
            5, 8, 8, 5,//9
            5, 8, 8, 5//10
    };

    //座区图200座
    public static final int[] ZUOQUTU200=new int[]{
            4,6,4,//1
            4,6,4,//2
            4,6,4,//3
            4,6,4,//4
            4,6,4,//5
            4,6,4,//6
            4,6,4,//7
            4,6,4,//8
            4,6,4,//9
            4,6,4,//10
            4,6,4,//11
            18//12
    };

    //二楼会议室(158)
    public static final int[] ZUOQUTU158=new int[]{
            2,6,6,2,//1
            2,6,6,2,//2
            2,6,6,2,//3
            2,6,6,2,//4
            2,6,6,2,//5
            2,6,6,2,//6
            2,6,6,2,//7
            2,6,6,2,//8
            2,6,6,2,//9
            2,6,6//10
    };


    //新教学楼4楼环形教室(80)
    public static final int[] ZUOQUTU80=new int[]{
            4,8,4,//1
            4,10,4,//2
            3,12,3,//3
            2,12,2,//4
            6,6//5
    };

    //新大楼报告厅(312)
    public static final int[] ZUOQUTU312=new int[]{
            5,8,8,5,//1
            5,8,8,5,//2
            5,8,8,5,//3
            5,8,8,5,//4
            5,8,8,5,//5
            5,8,8,5,//6
            5,8,8,5,//7
            5,8,8,5,//8
            5,8,8,5,//9
            5,8,8,5,//10
            5,8,8,5,//11
            5,8,8,5//12
    };

    //综合楼一楼演播厅(207)
    public static final int[] ZUOQUTU207=new int[]{
            4,9,4,//1
            5,9,5,//2
            5,9,5,//3
            5,9,5,//4
            5,9,5,//5
            5,9,5,//6
            5,9,5,//7
            5,9,5,//8
            5,9,5,//9
            5,9,5,//10
            5,9,5//11
    };

    //综合楼4楼演播厅(100)
    public static final int[] ZUOQUTU100_2=new int[]{
            6,8,6,//1
            6,8,6,//2
            6,8,6,//3
            6,8,6,//4
            6,8,6//5
    };
}
