package com.jiaowu.biz.holiday;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.common.StudentCommonConstants;
import com.jiaowu.dao.holiday.HolidayDao;
import com.jiaowu.entity.holiday.Holiday;
import com.jiaowu.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by 李帅雷 on 2017/8/28.
 */
@Service
public class HolidayBiz extends BaseBiz<Holiday,HolidayDao> {
    private static final SimpleDateFormat SDF=new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private UserBiz userBiz;
    /**
     * 添加请假记录
     * @return
     */
    public String addWorkLeaveInfo(Holiday holiday,User user){

		/*if(!StringUtils.isTrimEmpty(userCondition.getClassTypeId())){

		}*/
        HttpURLConnection connection = null;
        try {
            StringBuilder builder = new StringBuilder();
            builder.append(String.format("%1$s=%2$s&", URLEncoder.encode("workLeaveInfoEntity.leaDate", "UTF-8"),  URLEncoder.encode(SDF.format(new Date()), "UTF-8")));
            builder.append(String.format("%1$s=%2$s&", URLEncoder.encode("workLeaveInfoEntity.leaName", "UTF-8"),  URLEncoder.encode(holiday.getReason(), "UTF-8")));
            builder.append(String.format("%1$s=%2$s&", URLEncoder.encode("workLeaveInfoEntity.leaType", "UTF-8"),  URLEncoder.encode(holiday.getLeaType(), "UTF-8")));
            builder.append(String.format("%1$s=%2$s&", URLEncoder.encode("workLeaveInfoEntity.modifyUser", "UTF-8"),  URLEncoder.encode(user.getName(), "UTF-8")));
            if(user.getPerId()!=null&&!user.getPerId().equals("")) {
                builder.append(String.format("%1$s=%2$s&", URLEncoder.encode("workLeaveInfoEntity.basePerId", "UTF-8"), URLEncoder.encode(user.getPerId(), "UTF-8")));
            }
            builder.append(String.format("%1$s=%2$s&", URLEncoder.encode("workLeaveInfoEntity.begDate", "UTF-8"),  URLEncoder.encode(SDF.format(holiday.getBeginTime()), "UTF-8")));
            builder.append(String.format("%1$s=%2$s&", URLEncoder.encode("workLeaveInfoEntity.endDate", "UTF-8"),  URLEncoder.encode(SDF.format(holiday.getEndTime()), "UTF-8")));
            System.out.println(builder.toString());
			/*for (String key : params.keySet()) {
				String val="xmlValues".equalsIgnoreCase(key)? URLEncoder.encode(params.get(key), "UTF-8"):params.get(key);
				builder.append(String.format("%1$s=%2$s&", key,  URLEncoder.encode(val, "UTF-8")));
			}*/
            connection = (HttpURLConnection) new URL(StudentCommonConstants.YICARDTONG_PATH+"/yikatong/addWorkLeaveInfo.json").openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
            out.write(builder.toString());
            out.flush();
            out.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            builder.setLength(0);
            String line = "";
            while ((line = reader.readLine()) != null)
                builder.append(line);
            reader.close();
//			System.out.println("回应数据 :" + builder);
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null)
                connection.disconnect();
        }
    }
}
