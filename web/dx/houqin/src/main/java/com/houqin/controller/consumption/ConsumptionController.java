package com.houqin.controller.consumption;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.google.gson.Gson;
import com.houqin.common.CommonConstants;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消费统计
 *
 * @author YaoZhen
 * @create 10-21, 16:16, 2017.
 */
@Controller
public class ConsumptionController extends BaseController {
    /**
     * 日志类
     */
    private static final Logger logger = Logger.getLogger(ConsumptionController.class);
    /**
     * 一卡通地址
     */
    private static final String YI_KA_TONG = "/yikatong";
    /**
     * 访问路径
     */
    private static final String ADMIN_CONSUMPTION = "/admin/houqin/consumption";


    /**
     * 消费列表
     *
     * @param pagination 分页
     * @param searchOpt  员工编号
     * @param request    多线程请求
     * @return 消费数据
     */
    @RequestMapping(ADMIN_CONSUMPTION + "/list")
    public ModelAndView listConsumption(@ModelAttribute("pagination") Pagination pagination,
                                        String searchOpt,
                                        HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/consumption/list_consumption");
        try {
            // 拼装参数
            pagination.setRequest(request);
            // 解析考勤原始数据并取第一页
            Map<String, Object> map = getOnePageData(parseAttendanceData(getAttendanceData(searchOpt)), pagination);
            if(ObjectUtils.isNotNull(map)){
                // 第一页Json数据转list
                List<Map<String, Object>> resultList = (List<Map<String, Object>>) map.get("data");
                mv.addObject("resultList", resultList);
                // 环回分页
                pagination = (Pagination) map.get("pagination");
                mv.addObject("pagination", pagination);
            }
            // 环回搜索条件
            mv.addObject("searchOpt", searchOpt);
        } catch (Exception e) {
            logger.error("ConsumptionController.listConsumption", e);
        }
        return mv;
    }

    /**
     * 获取考勤原始数据
     *
     * @return
     */
    public String getAttendanceData(String searchPerId) {
        StringBuffer whereSql = new StringBuffer(" where 1=1");
        if (!StringUtils.isTrimEmpty(searchPerId)) {
            whereSql.append(" and Consume_Per_Date.Base_PerID = '" + searchPerId + "'");
        }
//        whereSql.append(" and  Consume_Per_Date.Cost_Date = '2017-00-00'");
//        whereSql.append(" and  Consume_Per_Date.Cost_Date ='2017-12-25'");

        HttpURLConnection connection = null;
        try {
            StringBuilder builder = new StringBuilder();
            builder.append(String.format("%1$s=%2$s&", URLEncoder.encode("whereSql", "UTF-8"), URLEncoder.encode(whereSql.toString(), "UTF-8")));
            System.out.println(builder.toString());

            connection = (HttpURLConnection) new URL(CommonConstants.cardPath + YI_KA_TONG + "/showConsumeListBySeach.json").openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
            out.write(builder.toString());
            out.flush();
            out.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            builder.setLength(0);
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            reader.close();
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * 解析考勤数据
     *
     * @param result
     * @return
     */
    public List<Map<String, Object>> parseAttendanceData(String result) {
        if (result == null || result.equals("")) {
            return null;
        }
        Gson gson = new Gson();
        Map<String, Object> all = gson.fromJson(result, Map.class);
        String code = all.get("code").toString();
        // 请求成功的状态码为0
        if (code == null || !code.equals(ErrorCode.SUCCESS)) {
            return null;
        }
        List<Map<String, Object>> data = (List<Map<String, Object>>) all.get("data");
        return data;
    }

    /**
     * 获取一页的数据
     *
     * @param all
     * @param pagination
     * @return
     */
    public Map<String, Object> getOnePageData(List<Map<String, Object>> all, Pagination pagination) {
        if (all == null || all.size() == 0) {
            return null;
        }
        pagination.setTotalCount(all.size());
        pagination.setTotalPages(all.size() % pagination.getPageSize() == 0 ? all.size() / pagination.getPageSize() : all.size() / pagination.getPageSize() + 1);
        int begin = (pagination.getCurrentPage() - 1) * pagination.getPageSize();
        int end = pagination.getCurrentPage() * pagination.getPageSize();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", all.subList(begin, end > all.size() ? all.size() : end));
        map.put("pagination", pagination);
        return map;
    }

}
