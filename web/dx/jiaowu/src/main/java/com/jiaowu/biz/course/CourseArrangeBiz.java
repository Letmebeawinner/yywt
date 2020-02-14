package com.jiaowu.biz.course;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.common.FileExportImportUtil;
import com.jiaowu.dao.course.CourseArrangeDao;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.course.Course;
import com.jiaowu.entity.course.CourseArrange;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CourseArrangeBiz extends BaseBiz<CourseArrange, CourseArrangeDao> {

    @Autowired
    private ClassesBiz classesBiz;
    @Autowired
    private CourseBiz courseBiz;

    /**
     * 导出排课表
     *
     * @param request
     * @param classId
     * @return
     */
    public File courseArrangeExcel(HttpServletRequest request, Long classId, String exportStartTime, String exportEndTime, String contactTeacher, String contactNumber) throws FileNotFoundException, IOException {
        Classes classes = classesBiz.findById(classId);
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        String whereSql = " classId=" + classId;
        if (!StringUtils.isTrimEmpty(exportStartTime)) {
            whereSql += " and startTime >= '" + exportStartTime + "'";
        }
        if (!StringUtils.isTrimEmpty(exportEndTime)) {
            whereSql += " and endTime <= '" + exportEndTime + "'";
        }
        List<CourseArrange> courseArrangeList = this.find(null, whereSql + " order by startTime asc");
        File file = new File(request.getSession().getServletContext().getRealPath("/static/common/courseArrange2.xls"));
        FileInputStream inputStream = new FileInputStream(file);
        // datalist拼装List<String[]> datalist,
        HSSFWorkbook wookbook = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = wookbook.getSheetAt(0);
        HSSFCellStyle titleStyle = getHssfCellStyle(wookbook);
        HSSFCell title = sheet.getRow((short) 0).getCell((short) 0);
        title.setCellStyle(titleStyle);
        title.setEncoding(HSSFCell.ENCODING_UTF_16);
        title.setCellValue(classes.getName());
//        int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
        if (courseArrangeList != null && courseArrangeList.size() > 0) {
            for (int i = 0; i < courseArrangeList.size(); i++) {
                HSSFCellStyle cellStyle = wookbook.createCellStyle();
                cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
                cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);

//                if((i+1)<(rows+1)){
                HSSFRow row = sheet.createRow((short) (i + 2));
//                    HSSFRow row = sheet.getRow(i+1);
                CourseArrange courseArrange = courseArrangeList.get(i);
                row.createCell((short) 0);
//                System.out.println(sdf3.format(courseArrange.getStartTime())+"----------------"+sdf3.format(courseArrange.getEndTime()));

                row.getCell((short) 0).setCellStyle(cellStyle);
                row.getCell((short) 0).setEncoding(HSSFCell.ENCODING_UTF_16);

                row.getCell((short) 0).setCellValue(sdf.format(courseArrange.getStartTime())+"("+getWeekday(courseArrange.getStartTime())+")");
                c.setTime(courseArrange.getStartTime());
                int hour = c.get(Calendar.HOUR_OF_DAY);
                row.createCell((short) 1);
                row.getCell((short) 1).setCellStyle(cellStyle);
                row.getCell((short) 1).setEncoding(HSSFCell.ENCODING_UTF_16);
                if (hour < 12) {
                    row.getCell((short) 1).setCellValue("上午");
                } else if ((hour > 12) && (hour < 18)) {
                    row.getCell((short) 1).setCellValue("下午");
                } else {
                    row.getCell((short) 1).setCellValue("晚上");
                }
                row.createCell((short) 2);
                row.getCell((short) 2).setCellStyle(cellStyle);
                row.getCell((short) 2).setEncoding(HSSFCell.ENCODING_UTF_16);
                row.getCell((short) 2).setCellValue(sdf2.format(courseArrange.getStartTime()) + "-" + sdf2.format(courseArrange.getEndTime()));
                Course course = courseBiz.findById(courseArrange.getTeachingProgramCourseId());
                if(ObjectUtils.isNull(course)){
                    continue;
                }
                row.createCell((short) 3);
                row.getCell((short) 3).setCellStyle(cellStyle);
                row.getCell((short) 3).setEncoding(HSSFCell.ENCODING_UTF_16);
                row.getCell((short) 3).setCellValue(course.getName());
                row.createCell((short) 4);
                row.getCell((short) 4).setCellStyle(cellStyle);
                row.getCell((short) 4).setEncoding(HSSFCell.ENCODING_UTF_16);
                row.getCell((short) 4).setCellValue(courseArrange.getTeacherName().substring(0,courseArrange.getTeacherName().length()-1));
                /*row.createCell((short) 5);
                row.getCell((short) 5).setCellStyle(cellStyle);
                row.getCell((short) 5).setEncoding(HSSFCell.ENCODING_UTF_16);
                row.getCell((short) 5).setCellValue(courseArrange.getClassroomName());*/

               /* }else{
                    break;
                }*/
            }
            // 页尾
            /*HSSFRow footer = sheet.createRow((short) (courseArrangeList.size() + 3));
            HSSFCellStyle style = getHssfCellStyleNoBorder(wookbook);
            HSSFCell footerCell = footer.createCell((short) 0);
            footerCell.setCellStyle(style);
            footerCell.setEncoding(HSSFCell.ENCODING_UTF_16);
            footerCell.setCellValue("注: 课程如有变动, 以通知为准");
            HSSFRow footer1 = sheet.createRow((short) (courseArrangeList.size() + 4));
            HSSFCell footerCell1 = footer1.createCell((short) 0);
            footerCell1.setCellStyle(style);
            footerCell1.setEncoding(HSSFCell.ENCODING_UTF_16);
            footerCell1.setCellValue("市委党校联系老师: " + contactTeacher + "      联系电话: " + contactNumber);
            HSSFRow footer2 = sheet.createRow((short) (courseArrangeList.size() + 5));
            HSSFCell footerCell2 = footer2.createCell((short) 0);
            footerCell2.setCellStyle(style);
            footerCell2.setEncoding(HSSFCell.ENCODING_UTF_16);
            if(StringUtils.isTrimEmpty(classes.getClassName())){
                footerCell2.setCellValue("上课地点: ");
            }else{
                footerCell2.setCellValue("上课地点: "+classes.getClassName());
            }*/

        }

        /*for (int i = 1; i <rows+1; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null) {
//                System.out.println("-------第"+i+"行"+"-----------0:"+getCellValue(row.getCell((short) 0))+"-----------1:"+getCellValue(row.getCell((short) 1))+"-------------2:"+getCellValue(row.getCell((short) 2))+"--------------3:"+getCellValue(row.getCell((short) 3))+"---------4:"+getCellValue(row.getCell((short) 4))+"--------------5:"+getCellValue(row.getCell((short) 5)));


            }
        }*/
        File newFile = new File(request.getSession().getServletContext().getRealPath("/excelfile/courseArrange") + "/" + "课程表_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".xls");//生成excel文件
        FileOutputStream fos = new FileOutputStream(newFile);
        wookbook.write(fos);
        fos.close();
        return newFile;
    }

    /**
     * 判断当前日期是星期几
     * @param date
     * @return
     */
    public String getWeekday(Date date) {
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0){
            w = 0;
        }
        return weekDays[w];
    }

    private HSSFCellStyle getHssfCellStyle(HSSFWorkbook wookbook) {
        HSSFCellStyle titleStyle = wookbook.createCellStyle(); // 单元格样式
        HSSFFont fontStyle = wookbook.createFont(); // 字体样式
        fontStyle.setBoldweight((short) 6); // 加粗
        fontStyle.setFontName("黑体"); // 字体
        fontStyle.setFontHeightInPoints((short) 14); // 大小
        // 将字体样式添加到单元格样式中
        titleStyle.setFont(fontStyle);
        // 边框，居中
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        return titleStyle;
    }

    private HSSFCellStyle getHssfCellStyleNoBorder(HSSFWorkbook wookbook) {
        HSSFCellStyle cellStyle = wookbook.createCellStyle();
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_NONE);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_NONE);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_NONE);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_NONE);

        HSSFFont fontStyle = wookbook.createFont(); // 字体样式
        fontStyle.setBoldweight((short) 6); // 加粗
        fontStyle.setFontName("黑体"); // 字体
        fontStyle.setFontHeightInPoints((short) 11); // 大小
        // 将字体样式添加到单元格样式中
        cellStyle.setFont(fontStyle);

        return cellStyle;
    }

    /**
     * @param cell
     * @return
     * @Description 获得Hsscell内容
     */
    public String getCellValue(HSSFCell cell) {
        String value = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_FORMULA:
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC:
                    DecimalFormat df = new DecimalFormat("0");
                    value = df.format(cell.getNumericCellValue());
                    break;
                case HSSFCell.CELL_TYPE_STRING:
                    value = cell.getStringCellValue().trim();
                    break;
                default:
                    value = "";
                    break;
            }
        }
        return value.trim();
    }

    /**
     * 导出班次数量
     *
     * @param request
     * @param dir
     * @param headName
     * @param expName
     * @param courseArrangeNumList
     * @return
     * @throws Exception
     */
    public List<File> statisticCourseLessonExcel(HttpServletRequest request, String dir, String[] headName, String expName, List<CourseArrange> courseArrangeNumList) throws Exception {
        List<File> srcfile = new ArrayList<File>();// 生成的excel的文件的list
        List<List<String>> all = new LinkedList<List<String>>();
        for (CourseArrange courseArrange : courseArrangeNumList) {
            List<String> list = new LinkedList<String>();
            list.add(courseArrange.getTeacherName());
            list.add(courseArrange.getSum() + "");
            all.add(list);
        }
        File file = FileExportImportUtil.createExcel(headName, all, expName, dir);
        srcfile.add(file);
        return srcfile;
    }

    /**
     * 教师课时详情导出
     *
     * @param request
     * @param dir
     * @param headName
     * @param expName
     * @param courseArrangeNumList
     * @return
     * @throws Exception
     */
    public List<File> queryCourseInfoExcel(HttpServletRequest request, String dir, String[] headName, String expName, List<CourseArrange> courseArrangeNumList) throws Exception {
        List<File> srcfile = new ArrayList<File>();// 生成的excel的文件的list
        List<List<String>> all = new LinkedList<List<String>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (CourseArrange courseArrange : courseArrangeNumList) {
            List<String> list = new LinkedList<String>();
            list.add(courseArrange.getTeacherName());
            list.add(courseArrange.getClassName());
            list.add(courseArrange.getCourseName());
            list.add(courseArrange.getClassroomName());
            list.add(sdf.format(courseArrange.getStartTime()));
            all.add(list);
        }
        File file = FileExportImportUtil.createExcel(headName, all, expName, dir);
        srcfile.add(file);
        return srcfile;
    }

    /**
     * 教师课时详情导出
     *
     * @param request
     * @param dir
     * @param headName
     * @param expName
     * @param list
     * @return
     * @throws Exception
     */
    public List<File> averageNumOfOneClassExcel(HttpServletRequest request, String dir, String[] headName, String expName, List<List<String>> list) throws Exception {
        List<File> srcfile = new ArrayList<File>();// 生成的excel的文件的list
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        File file = FileExportImportUtil.createExcel(headName, list, expName, dir);
        srcfile.add(file);
        return srcfile;
    }

    /**
     * 教师课时详情导出
     *
     * @param request
     * @param dir
     * @param headName
     * @param expName
     * @param list
     * @return
     * @throws Exception
     */
    public List<File> courseArrangePercentOfClassExcel(HttpServletRequest request, String dir, String[] headName, String expName, List<Map<String, Object>> list) throws Exception {
        List<File> srcfile = new ArrayList<File>();// 生成的excel的文件的list
        List<List<String>> all = new LinkedList<List<String>>();
        if (list != null && list.size() > 0) {
            for (Map<String, Object> map : list) {
                List<String> smallList = new LinkedList<String>();
                smallList.add(map.get("className").toString());
                smallList.add(map.get("num").toString());
                all.add(smallList);
            }
        }
        File file = FileExportImportUtil.createExcel(headName, all, expName, dir);
        srcfile.add(file);
        return srcfile;
    }
}
