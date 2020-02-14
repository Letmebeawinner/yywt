package com.oa.controller.archive;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.SysUserUtils;
import com.oa.biz.archivetype.ArchiveBiz;
import com.oa.biz.archivetype.ArchiveSearchBiz;
import com.oa.biz.archivetype.ArchiveTypeBiz;
import com.oa.biz.archivetype.UserArchiveBiz;
import com.oa.biz.common.BaseHessianBiz;
import com.oa.common.FileExportImportUtil;
import com.oa.entity.archivetype.Archive;
import com.oa.entity.archivetype.ArchiveSearch;
import com.oa.entity.archivetype.ArchiveType;
import com.oa.entity.archivetype.UserArchive;
import com.oa.entity.department.DepartMent;
import com.oa.utils.GenerateSqlUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/oa")
public class ArchiveController extends BaseController {


    @Autowired
    private ArchiveBiz archiveBiz;

    @Autowired
    private ArchiveTypeBiz archiveTypeBiz;

    @Autowired
    private BaseHessianBiz baseHessianBiz;

    @Autowired
    private ArchiveSearchBiz archiveSearchBiz;
    @Autowired
    private UserArchiveBiz userArchiveBiz;

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ArchiveTypeController.class);

    @InitBinder("archive")
    public void initBinderArchive(HttpServletRequest request, ServletRequestDataBinder binder) {
        binder.setFieldDefaultPrefix("archive.");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @InitBinder("archiveSearch")
    public void initBinderArchiveSearch(HttpServletRequest request, ServletRequestDataBinder binder) {
        binder.setFieldDefaultPrefix("archiveSearch.");
    }

    /**
     * 查询档案
     *
     * @param archive
     * @param pagination
     * @return
     */
    @RequestMapping("/queryArchiveList")
    public ModelAndView queryArchiveList(@ModelAttribute("archive") Archive archive,
                                         @ModelAttribute("pagination") Pagination pagination,
                                         @RequestParam(value = "flag", required = false) int flag) {
        ModelAndView modelAndView = new ModelAndView("/archive/archive_list");
        try {
            List<ArchiveType> archiveTypeList = archiveTypeBiz.find(null, " 1=1");
            modelAndView.addObject("archiveTypeList", archiveTypeList);
            modelAndView.addObject("flag", flag);
            modelAndView.addObject("archive", archive);

            String whereSql = GenerateSqlUtil.getSql(archive);
            List<Archive> archiveList = archiveBiz.find(pagination, whereSql);


            if (archiveList != null && archiveList.size() > 0) {
                for (Archive archive1 : archiveList) {
                    ArchiveType archiveType = archiveTypeBiz.findById(archive1.getTypeId());
                    archive1.setTypeName(archiveType.getName());

                    DepartMent departMent = baseHessianBiz.queryDepartemntById(archive1.getDepartId());
                    if (departMent != null) {
                        archive1.setDepartmentName(departMent.getDepartmentName());
                    } else {
                        archive1.setDepartmentName("");
                    }
                }
            }


            modelAndView.addObject("archiveList", archiveList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @RequestMapping("/ajax/getArchiveListByIds")
    @ResponseBody
    public Map<String, Object> getArchiveListByIds(@RequestParam("ids") String ids) {
        Map<String, Object> json = null;
        String sql = "id in (" + ids + ")";
        try {
            List<Archive> archives = archiveBiz.find(null, sql);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, archives);
        } catch(Exception e) {
            logger.error("ArchiveController.getArchiveListByIds", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }
    /**
     * 查询档案(已经入库的档案)
     *
     * @param archive
     * @param pagination
     * @return
     */
    @RequestMapping("/ajax/queryArchiveList")
    public ModelAndView ajaxQueryArchiveList(@ModelAttribute("archive") Archive archive,
                                         @ModelAttribute("pagination") Pagination pagination,
                                         @RequestParam(value = "flag", required = false) int flag) {
        ModelAndView modelAndView = new ModelAndView("/archive/ajax_archive_list");
        try {
            List<ArchiveType> archiveTypeList = archiveTypeBiz.find(null, " 1=1");
            modelAndView.addObject("archiveTypeList", archiveTypeList);
            modelAndView.addObject("flag", flag);
            modelAndView.addObject("archive", archive);
            archive.setStockFlag(1);
            String whereSql = GenerateSqlUtil.getSql(archive);
            List<Archive> archiveList = archiveBiz.find(pagination, whereSql);
            if (archiveList != null && archiveList.size() > 0) {
                for (Archive archive1 : archiveList) {
                    ArchiveType archiveType = archiveTypeBiz.findById(archive1.getTypeId());
                    archive1.setTypeName(archiveType.getName());

                    DepartMent departMent = baseHessianBiz.queryDepartemntById(archive1.getDepartId());
                    if (departMent != null) {
                        archive1.setDepartmentName(departMent.getDepartmentName());
                    } else {
                        archive1.setDepartmentName("");
                    }
                }
            }

            modelAndView.addObject("archiveList", archiveList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    /**
     * 去添加档案
     *
     * @return
     */
    @RequestMapping("/toAddArchive")
    public String toAddArchive(HttpServletRequest request) {
        List<ArchiveType> archiveTypeList = archiveTypeBiz.find(null, " 1=1");
        request.setAttribute("archiveTypeList", archiveTypeList);
        return "/archive/archive_add";
    }

    /**
     * 添加档案
     *
     * @param archive
     * @return
     */
    @RequestMapping("/addArchive")
    @ResponseBody
    public Map<String, Object> addArchive(HttpServletRequest request, @ModelAttribute("archive") Archive archive) {
        Map<String, Object> json = null;
        try {

            Map<String, String> department = baseHessianBiz.queryDepartmentBySysUserId(SysUserUtils.getLoginSysUserId(request));
            String departmentId = department.get("id");
            Long departId = Optional.ofNullable(departmentId)
                    .map((id) -> Long.parseLong(id))
                    .orElse(null);
            archive.setDepartId(departId);
            System.out.println("++++" + department);
            archive.setStockFlag(0);
            archiveBiz.save(archive);
            json = this.resultJson(ErrorCode.SUCCESS, "添加成功", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 去修改
     *
     * @return
     */
    @RequestMapping("/toUpdateArchive")
    public ModelAndView toUpdateArchive(@RequestParam("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/archive/archive_update");
        try {
            List<ArchiveType> archiveTypeList = archiveTypeBiz.find(null, " 1=1");
            modelAndView.addObject("archiveTypeList", archiveTypeList);
            Archive archive = archiveBiz.findById(id);
            modelAndView.addObject("archive", archive);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    /**
     * 修改
     *
     * @param archive
     * @return
     */
    @RequestMapping("/updateArchive")
    @ResponseBody
    public Map<String, Object> updateArchive(@ModelAttribute("archive") Archive archive) {
        Map<String, Object> json = null;
        try {
            archiveBiz.update(archive);
            json = this.resultJson(ErrorCode.SUCCESS, "修改成功", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 删除档案
     *
     * @param id
     * @return
     */
    @RequestMapping("/deleteArchive")
    @ResponseBody
    public Map<String, Object> deleteArchive(@RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            archiveBiz.deleteById(id);
            json = this.resultJson(ErrorCode.SUCCESS, "删除成功", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


    /**
     * @param request
     * @param response
     * @Description 导出Excel
     * @author CCl
     */
    @RequestMapping("/exportArchiveList")
    public void exportArchiveList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("archive") Archive archive) {
        try {

            String whereSql = GenerateSqlUtil.getSql(archive);
            String dir = request.getSession().getServletContext().getRealPath("/excelfile/archiveList");
            String expName = "档案列表" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String[] headName = {"党号", "件号", "责任者", "文号", "题名", "日期", "页数", "机构或问题", "备注"};
            List<File> srcfile = archiveBiz.getExcelArchiveList(request, dir, headName, expName, whereSql);
            FileExportImportUtil.createRar(response, dir, srcfile, expName);// 生成的多excel的压缩包
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 申请查询
     *
     * @param request
     * @return
     */
    @RequestMapping("/applySearcharchive")
    public ModelAndView applySearchArchive(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/archive/archiveSearch_add");
        return modelAndView;

    }

    /**
     * 添加申请
     *
     * @param request
     * @return
     */
    @RequestMapping("/addSearchArchive")
    @ResponseBody
    public Map<String, Object> addSearchArchive(HttpServletRequest request, @ModelAttribute("archiveSearch") ArchiveSearch archiveSearch) {
        Map<String, Object> json = null;
        try {
            archiveSearch.setUserId(SysUserUtils.getLoginSysUserId(request));
            archiveSearch.setApplyDate(new Date());
            archiveSearchBiz.save(archiveSearch);
            json = this.resultJson(ErrorCode.SUCCESS, "申请成功", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 查询申请记录
     *
     * @param request
     * @param archiveSearch
     * @return
     */
    @RequestMapping("/searchArchiveList")
    public ModelAndView searchArchiveList(HttpServletRequest request, @ModelAttribute("archiveSearch") ArchiveSearch archiveSearch, @ModelAttribute("pagination") Pagination pagination) {
        ModelAndView modelAndView = new ModelAndView("/archive/archiveSearch_list");
        try {
            String whereSql = GenerateSqlUtil.getSql(archiveSearch);
            List<ArchiveSearch> archiveSearchList = archiveSearchBiz.find(pagination, whereSql);
            if (ObjectUtils.isNotNull(archiveSearchList)) {

                for (ArchiveSearch archiveSearch1 : archiveSearchList) {

                    Map<String, String> department = baseHessianBiz.queryDepartmentBySysUserId(SysUserUtils.getLoginSysUserId(request));
                    String departmentId = department.get("departmentId").toString();

                    DepartMent departMent = baseHessianBiz.queryDepartemntById(Long.parseLong(departmentId));
                    if (departMent != null) {
                        archiveSearch1.setDepartmentName(departMent.getDepartmentName());
                    } else {
                        archiveSearch1.setDepartmentName("");
                    }

                }
            }
            modelAndView.addObject("archiveSearchList", archiveSearchList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    /**
     * 入库（是否入库）
     * @param stockFlag
     * @param id
     * @return
     */
    @RequestMapping("/updateStockFlag")
    @ResponseBody
    public Map<String, Object> updateStockFlag(@RequestParam("stockFlag") int stockFlag, @RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            Archive archive = new Archive();
            archive.setStockFlag(stockFlag);
            archive.setId(id);
            archiveBiz.update(archive);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("ArchiveController.updateStockFlag", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.ERROR_SYSTEM, null);
        }
        return json;
    }

    /**
     * 推送给我的档案
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping("/archive/send/to/me")
    public String archiveSendToMe(HttpServletRequest request,@ModelAttribute("pagination") Pagination pagination
                                  ) {
        Long userId = SysUserUtils.getLoginSysUserId(request);
        String sql = " userId = " + userId;
        List<Archive> archives = null;
        try {
            List<UserArchive> userArchives = userArchiveBiz.find(null, sql);
            if (userArchives != null && userArchives.size() > 0) {
                String archiveIds = userArchives.stream()
                        .map(userArchive -> {return userArchive.getArchiveId() + "";})
                        .collect(Collectors.joining(",", "(", ")"));
                String archiveSql = " id in " + archiveIds;
                archives = archiveBiz.find(pagination, archiveSql);
            }
            request.setAttribute("archiveList", archives);
        } catch(Exception e) {
            logger.error("ArchiveController.archiveSendToMe", e);
            return this.setErrorPath(request, e);
        }
        return "/archive/archive_send_to_me_list";
    }

    /**
     * 下载文件，自定义名字
     * @param response
     * @param request
     * @param fileUrl
     * @param fileName
     */
    @RequestMapping("/file/load")
    public void downloadNet(HttpServletResponse response,
                            HttpServletRequest request,
                            @RequestParam("fileUrl") String fileUrl,
                            @RequestParam("fileName") String fileName) {

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            //处理中文乱码
            request.setCharacterEncoding("UTF-8");
            //处理浏览器兼容
            response.setContentType("application/msexcel;charset=utf-8");//定义输出类型
            Enumeration enumeration = request.getHeaders("User-Agent");
            String browserName = (String) enumeration.nextElement();
            boolean isMSIE = browserName.contains("MSIE");
            if (isMSIE) {
                response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
            } else {
                response.addHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
            }
            //url地址如果存在空格，会导致报错！  解决方法为：用+或者%20代替url参数中的空格。
            fileUrl = fileUrl.replace(" ", "%20");
            //图片下载
            URL url = new URL(fileUrl);
            URLConnection conn = url.openConnection();
            outputStream = response.getOutputStream();
            inputStream = conn.getInputStream();
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            System.err.println(e);
        }finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }

    }

}
