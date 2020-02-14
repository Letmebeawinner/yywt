package com.oa.controller.activiti;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.oa.biz.common.BaseHessianBiz;
import com.oa.entity.sysuser.SysUser;
import com.oa.utils.WorkflowUtils;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * activiti控制层
 *
 * @author lzh
 * @create 2017-02-06-16:26
 */
@Controller
@RequestMapping("/admin/oa")
public class ModelController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ModelController.class);

    private static final String modelAdd = "/workflow/model/model_add";
    private static final String modelList = "/workflow/model/model_list";
    private static final String modelerHtml = "/modeler.html";
    private static final String userList = "/user/user_list";

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private BaseHessianBiz baseHessianBiz;

    /**
     * 查询生日列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/activiti")
    @ResponseBody
    public Object birthdayList(HttpServletRequest req) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "activiti");
        return map;
    }


    /**
     * @Description:跳转到设计流程图的页面
     * @author: lzh
     * @Param: [name, key, description, request, response]
     * @Return: void
     * @Date: 17:28
     */
    @RequestMapping("/addModel")
    public void create(@RequestParam("name") String name,
                       @RequestParam("key") String key,
                       @RequestParam(value = "description", required = false) String description,
                       HttpServletRequest request, HttpServletResponse response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);
            Model modelData = repositoryService.newModel();

            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            description = StringUtils.defaultString(description);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName(name);
            modelData.setKey(StringUtils.defaultString(key));

            repositoryService.saveModel(modelData);
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));

            response.sendRedirect(request.getContextPath() + modelerHtml + "?modelId=" + modelData.getId());
        } catch (Exception e) {
            logger.error("ModelController.create", e);
        }
    }

    /**
     * @Description: 跳转到增加模型页面
     * @author: lzh
     * @Param: []
     * @Return: java.lang.String
     * @Date: 16:37
     */
    @RequestMapping("/model/toAdd")
    public String toModelAdd() {
        return modelAdd;
    }

    /**
     * @Description: 查看模型列表
     * @author: lzh
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 10:47
     */
    @RequestMapping("/model/list")
    public String modelList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            pagination.setRequest(request);
            int totalSize = repositoryService.createModelQuery().list().size();
            int offset = (pagination.getCurrentPage() - 1) * pagination.getPageSize();
            List<Model> models = repositoryService.createModelQuery().listPage(offset, pagination.getPageSize());
            //配置分页参数
            pagination.init(totalSize, pagination.getPageSize(), pagination.getCurrentPage());
            request.setAttribute("models", models);
            request.setAttribute("pagination", pagination);
        } catch(Exception e) {
            logger.error("ModelController.modelList", e);
            return this.setErrorPath(request, e);
        }

        return modelList;
    }

    /**
     * @Description: 根据模型部署流程
     * @author: lzh
     * @Param: [request, modelId]
     * @Return: java.lang.String
     * @Date: 11:12
     */
    @RequestMapping("/model/deploy")
    @ResponseBody
    public Map<String, Object> modelDeploy(@RequestParam("modelId") String modelId) {
        Map<String, Object> json = null;
        try {
            Model model = repositoryService.getModel(modelId);
            ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(model.getId()));
            byte [] bpmnBytes = null;
            BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);
            String processName = model.getName() + ".bpmn20.xml";
            Deployment deployment = repositoryService.createDeployment().name(model.getName()).addString(processName, new String(bpmnBytes)).deploy();
            json = this.resultJson(ErrorCode.SUCCESS, "根据模型部署流程成功，部署id = " + deployment.getId(), null);
        } catch(Exception e) {
            logger.error("根据模型部署流程失败，modelId = {}", modelId, e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, e.getMessage(), null);
        }
        return json;
    }

    /**
     * @Description: 删除模型
     * @author: lzh
     * @Param: [modelId]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 13:43
     */
    @RequestMapping("/model/delete")
    @ResponseBody
    public Map<String, Object> delModel(@RequestParam("modelId") String modelId) {
        Map<String, Object> resultMap = null;
            try {
                repositoryService.deleteModel(modelId);
                resultMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
            } catch (Exception e) {
                logger.error("ModelController.delModel", e);
                resultMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
            }
            return resultMap;
    }

    /**
     * @Description: 查询所有用户
     * @author: lzh
     * @Param: [request]
     * @Date: 17:16
     */
    @RequestMapping("/ajax/model/sys/user/list")
    public String getSysUserList(HttpServletRequest request) {
        try {
            SysUser sysUser = new SysUser();
            List<SysUser> sysUsers = baseHessianBiz.getSysUserList(sysUser);
            request.setAttribute("sysUsers", sysUsers);
        } catch (Exception e) {
            logger.error("ModelController.getSysUserList", e);
            return this.setErrorPath(request, e);
        }
        return userList;
    }

    /**
     * @Description: 导出模型
     * @author: lzh
     * @Param: [modelId, response]
     * @Return: void
     * @Date: 16:41
     */
    @RequestMapping("/model/export")
    public void export(@RequestParam("modelId") String modelId,
                       HttpServletResponse response) {
        try {
            Model modelData = repositoryService.getModel(modelId);
            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
            byte[] modelEditorSource = repositoryService.getModelEditorSource(modelData.getId());
            JsonNode editorNode = new ObjectMapper().readTree(modelEditorSource);
            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);

            // 处理异常
            if (bpmnModel.getMainProcess() == null) {
                response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
                response.getOutputStream().println("no main process, can't export");
                response.flushBuffer();
                return;
            }
            String filename = "";
            byte[] exportBytes = null;
            String mainProcessId = bpmnModel.getMainProcess().getId();
            BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
            exportBytes = xmlConverter.convertToXML(bpmnModel);
            filename = modelData.getName() + mainProcessId + ".bpmn20.xml";
            ByteArrayInputStream in = new ByteArrayInputStream(exportBytes);
            IOUtils.copy(in, response.getOutputStream());
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            response.flushBuffer();
        } catch (Exception e) {
            logger.error("导出model的xml文件失败：modelId={}", modelId, e);
        }
    }

    /**
     * @Description: 获取用户列表
     * @author: lzh
     * @Param: []
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 18:07
     */
    @RequestMapping("/model/user/info")
    @ResponseBody
    public Map<String, Object> getUserJson() {
        Map<String, Object> json = null;
        try {
            SysUser user = new SysUser();
            List<SysUser> users = baseHessianBiz.getSysUserList(user);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, users);
        } catch(Exception e) {
            logger.error("ModelController.getUserJson", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description: 发布流程，以压缩包的形式，发布外部表单
     * @author: lzh
     * @Param: [file]
     * @Return: string
     * @Date: 13:59
     */
    @RequestMapping("/model/form/deploy")
    public String deploy(@RequestParam(value = "file", required = false) MultipartFile file,
                         HttpServletRequest request,
                         RedirectAttributes attr) {

        String fileName = file.getOriginalFilename();

        try {
            InputStream fileInputStream = file.getInputStream();
            Deployment deployment = null;
            //以压缩包的形式部署流程
            String extension = FilenameUtils.getExtension(fileName);
            if (extension.equals("zip") || extension.equals("bar")) {
                ZipInputStream zip = new ZipInputStream(fileInputStream);
                deployment = repositoryService.createDeployment().addZipInputStream(zip).deploy();
            } else {
                deployment = repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
            }
            attr.addFlashAttribute("message", "发布成功, 发布id = " + deployment.getId());
            List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();
        } catch (Exception e) {
            logger.error("error on deploy process, because of file input stream", e);
            return this.setErrorPath(request, e);
        }
        return "redirect:/admin/oa/process/list.json";
    }

}
