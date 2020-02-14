package com.jiaowu.biz.course;

import com.a_268.base.core.BaseBiz;
import com.jiaowu.biz.teach.TeachingProgramCourseBiz;
import com.jiaowu.dao.course.CourseArrangeDao;
import com.jiaowu.entity.course.CourseArrange;
import com.jiaowu.entity.ruiqu.RqEntity;
import com.jiaowu.entity.teach.TeachingProgramCourse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CourseArrangeBiz extends BaseBiz<CourseArrange, CourseArrangeDao>{
    @Autowired
    private TeachingProgramCourseBiz teachingProgramCourseBiz;
    /**
     * 导出排课表
     * @param request
     * @param classId
     * @return
     */
    public File courseArrangeExcel(HttpServletRequest request, Long classId) throws FileNotFoundException,IOException{
        SimpleDateFormat sdf=new SimpleDateFormat("MM月dd日");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
        Calendar c= Calendar.getInstance();
        List<CourseArrange> courseArrangeList=this.find(null," classId="+classId+" order by startTime desc");
        File file=new File(request.getSession().getServletContext().getRealPath("/static/common/courseArrange.xls"));
        FileInputStream inputStream=new FileInputStream(file);
        // datalist拼装List<String[]> datalist,
        HSSFWorkbook wookbook = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = wookbook.getSheetAt(0);

        int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
        if(courseArrangeList!=null&&courseArrangeList.size()>0){
            for(int i=0;i<courseArrangeList.size();i++){
                if((i+1)<(rows+1)){
                    HSSFRow row = sheet.getRow(i+1);
                    CourseArrange courseArrange=courseArrangeList.get(i);
                    row.getCell((short) 0).setCellValue(sdf.format(courseArrange.getStartTime()).replace("0",""));
                    c.setTime(courseArrange.getStartTime());
                    int hour=c.get(Calendar.HOUR_OF_DAY);
                    if(hour<12){
                        row.getCell((short) 1).setCellValue("上午");
                    }else if((hour>12)&&(hour<18)){
                        row.getCell((short) 1).setCellValue("下午");
                    }else{
                        row.getCell((short) 1).setCellValue("晚上");
                    }
                    row.getCell((short) 2).setCellValue(sdf2.format(courseArrange.getStartTime())+"-"+sdf2.format(courseArrange.getEndTime()));
                    TeachingProgramCourse teachingProgramCourse=teachingProgramCourseBiz.findById(courseArrange.getTeachingProgramCourseId());
                    row.getCell((short) 3).setCellValue(teachingProgramCourse.getCourseName());
                    row.getCell((short) 4).setCellValue(courseArrange.getTeacherName());
                    row.getCell((short) 5).setCellValue(courseArrange.getClassroomName());

                }else{
                    break;
                }
            }
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
     * @Description 获得Hsscell内容
     * @param cell
     * @return
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

    public List<RqEntity> findRqEntityList() {
        List<CourseArrange> courseArranges = super.findAll();
        return convert(courseArranges);
    }

    private List<RqEntity> convert(List<CourseArrange> courseArranges) {

        List<RqEntity> rqEntities = new ArrayList<>();
        for (CourseArrange c : courseArranges) {
            RqEntity r = new RqEntity();
            rqEntities.add(r);
            BeanUtils.copyProperties(c, r);

            TeachingProgramCourse t =
                    teachingProgramCourseBiz.findById(c.getTeachingProgramCourseId());
            if (t != null) {
                r.setCourseName(t.getCourseName());
            }else {
                r.setCourseName("");
            }

        }
        return rqEntities;
    }
}
