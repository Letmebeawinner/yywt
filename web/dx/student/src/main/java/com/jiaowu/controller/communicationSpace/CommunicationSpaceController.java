package com.jiaowu.controller.communicationSpace;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.jiaowu.biz.common.CommonBiz;
import com.jiaowu.biz.common.HrHessianService;
import com.jiaowu.biz.praise.PraiseBiz;
import com.jiaowu.biz.reply.ReplyBiz;
import com.jiaowu.biz.topic.TopicBiz;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.entity.praise.Praise;
import com.jiaowu.entity.reply.Reply;
import com.jiaowu.entity.topic.Topic;
import com.jiaowu.entity.user.User;

/**
 * 交流空间Controller
 * 
 * @author 李帅雷
 *
 */
@Controller
public class CommunicationSpaceController extends BaseController {
	private static Logger logger = LoggerFactory
			.getLogger(CommunicationSpaceController.class);
	@Autowired
	private TopicBiz topicBiz;
	@Autowired
	private ReplyBiz replyBiz;
	@Autowired
	private PraiseBiz praiseBiz;
	@Autowired
	private HrHessianService hrHessianService;
	@Autowired
	private UserBiz userBiz;
	@Autowired
	private CommonBiz commonBiz;

	private static final String ADMIN_PREFIX = "/admin/jiaowu";
	private static final String WITHOUT_ADMIN_PREFIX = "/jiaowu";

	@InitBinder({ "topic" })
	public void initTopic(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("topic.");
	}

	@InitBinder({ "reply" })
	public void initReply(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("reply.");
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	/**
	 * @Description 跳转到创建话题的页面
	 * @author 李帅雷
	 * @param request
	 * @return java.lang.String
	 * 
	 */
	@RequestMapping(ADMIN_PREFIX + "/topic/toCreateTopic")
	public String toCreateTopic(HttpServletRequest request) {
		return "/admin/communicationSpace/create_topic";
	}

	/**
	 * @Description 创建话题
	 * @param request
	 * @param topic
	 * @return java.util.Map
	 */
	@RequestMapping(ADMIN_PREFIX + "/topic/createTopic")
	@ResponseBody
	public Map<String, Object> createTopic(HttpServletRequest request,
			@ModelAttribute("topic") Topic topic) {
		Map<String, Object> json = null;
		try {
			json = validateTopic(request, topic);
			if (json != null) {
				return json;
			}
			topicBiz.save(topic);
			json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
					null);
		} catch (Exception e) {
			e.printStackTrace();
			json = this.resultJson(ErrorCode.ERROR_SYSTEM,
					ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}

	/**
	 * @Description 话题列表
	 * @author 李帅雷
	 * @param request
	 * @param pagination
	 * @return java.lang.String
	 * 
	 */
	@RequestMapping(ADMIN_PREFIX + "/topic/topicList")
	public String topicList(HttpServletRequest request,
			@ModelAttribute("pagination") Pagination pagination) {
		try {
			String whereSql = " status=1";
			Topic topic = new Topic();
			whereSql += topicBiz.addCondition(request, topic);
			pagination.setRequest(request);
			List<Topic> topicList = topicBiz.find(pagination, whereSql);
			request.setAttribute("topicList", topicList);
			request.setAttribute("pagination", pagination);
			request.setAttribute("topic", topic);
		} catch (Exception e) {
			e.printStackTrace();
			return this.setErrorPath(request, e);
		}
		return "/admin/communicationSpace/topic_list";
	}

	/**
	 * @Description 供选择的话题列表
	 * @author 李帅雷
	 * @param request
	 * @param pagination
	 * @return java.lang.String
	 * 
	 */
	@RequestMapping(WITHOUT_ADMIN_PREFIX + "/topic/topicListForSelect")
	public String topicListForSelect(HttpServletRequest request,
			@ModelAttribute("pagination") Pagination pagination) {
		try {
			StringBuffer sb = request.getRequestURL().append(
					"?pagination.currentPage=" + pagination.getCurrentPage());
			String whereSql = " status=1";
			Topic topic = new Topic();
			whereSql += topicBiz.addCondition(request, topic);
			pagination.setCurrentUrl(sb.toString());
			List<Topic> topicList = topicBiz.find(pagination, whereSql);
			request.setAttribute("topicList", topicList);
			request.setAttribute("pagination", pagination);
			request.setAttribute("topic", topic);
		} catch (Exception e) {
			e.printStackTrace();
			return this.setErrorPath(request, e);
		}
		return "/admin/communicationSpace/topic_list_forSelect";
	}

	/**
	 * @Description 删除话题
	 * @param id
	 * @return java.util.Map
	 */
	@RequestMapping(ADMIN_PREFIX + "/topic/deleteTopic")
	@ResponseBody
	public Map<String, Object> deleteTopic(@RequestParam("id") Long id) {
		Map<String, Object> json = null;
		try {
			Topic topic = new Topic();
			topic.setId(id);
			topic.setStatus(0);
			topicBiz.update(topic);
			json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
					null);
		} catch (Exception e) {
			e.printStackTrace();
			json = this.resultJson(ErrorCode.ERROR_SYSTEM,
					ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}

	/**
	 * @Description 跳转到话题详情页面
	 * @author 李帅雷
	 * @param request
	 * @param id
	 * @return java.lang.String
	 * 
	 */
	@RequestMapping(ADMIN_PREFIX + "/topic/detailOfOneTopic")
	public String detailOfOneTopic(HttpServletRequest request,
			@RequestParam("id") Long id) {
		try {
			Topic topic = topicBiz.findById(id);
			request.setAttribute("topic", topic);
			List<Reply> replyList = replyBiz.find(null,
					" status=1 and replyId=0 and topicId=" + id);
			request.setAttribute("replyList", replyList);
			topicBiz.addTopicViewNum(topic);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin/communicationSpace/one_topic";
	}

	/**
	 * @Description 新增话题的回复
	 * @param request
	 * @param topicId
	 * @param content
	 * @return java.util.Map
	 */
	@RequestMapping(ADMIN_PREFIX + "/reply/addReplyToTopic")
	@ResponseBody
	public Map<String, Object> addReplyToTopic(HttpServletRequest request,
			@RequestParam("topicId") Long topicId,
			@RequestParam("content") String content) {
		Map<String, Object> json = null;
		try {
			Reply reply = new Reply();
			json = validateReply(request, reply, content);
			if (json != null) {
				return json;
			}
			reply.setTopicId(topicId);
			reply.setContent(content);
//			reply.setPraiseNum(0L);
//			reply.setReplyNum(0L);
			replyBiz.save(reply);
			topicBiz.addTopicReplyNum(topicId);
			json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
					reply);
		} catch (Exception e) {
			e.printStackTrace();
			json = this.resultJson(ErrorCode.ERROR_SYSTEM,
					ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}

	/**
	 * @Description 点赞
	 * @param request
	 * @param replyId
	 * @return java.util.Map
	 */
	@RequestMapping(ADMIN_PREFIX + "/reply/addPraise")
	@ResponseBody
	public Map<String, Object> addPraise(HttpServletRequest request,
			@RequestParam("replyId") Long replyId) {
		Map<String, Object> json = null;
		try {
			Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
			if (userMap == null) {
				json = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY,
						"请先登录", null);
				return json;
			}
			Integer userType = Integer.parseInt(userMap.get("userType"));
			Long userId = Long.parseLong(userMap.get("linkId"));
			json = checkUserHasPraiseOneReply(userType, userId, replyId);
			if (json != null) {
				return json;
			}
			praiseBiz.save(userType, userId, replyId,1);
			Long praiseNum = replyBiz.addReplyPraiseNum(replyId);
			json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
					praiseNum);
		} catch (Exception e) {
			e.printStackTrace();
			json = this.resultJson(ErrorCode.ERROR_SYSTEM,
					ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}

	

	/**
	 * @Description 获取某条回复的子回复
	 * @param replyId
	 * @return java.util.Map
	 */
	@RequestMapping(ADMIN_PREFIX + "/reply/getChildReply")
	@ResponseBody
	public Map<String, Object> getChildReply(
			@RequestParam("replyId") Long replyId) {
		Map<String, Object> json = null;
		try {
			List<Reply> replyList = replyBiz.find(null,
					" status=1 and replyId=" + replyId);
			json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
					replyList);
		} catch (Exception e) {
			e.printStackTrace();
			json = this.resultJson(ErrorCode.ERROR_SYSTEM,
					ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}

	/**
	 * @Description 增加某条回复的子回复
	 * @param request
	 * @param topicId
	 * @param replyId
	 * @param content
	 * @return java.util.Map
	 */
	@RequestMapping(ADMIN_PREFIX + "/reply/addChildReply")
	@ResponseBody
	public Map<String, Object> addChildReply(HttpServletRequest request,
			@RequestParam("topicId") Long topicId,
			@RequestParam("replyId") Long replyId,
			@RequestParam("content") String content) {
		Map<String, Object> json = null;
		try {
			Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
			if (userMap == null) {
				json = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY,
						"请先登录", null);
				return json;
			}
			json=validateReply(content);
			if(json!=null){
				return json;
			}
			Reply reply = new Reply();
			reply.setTopicId(topicId);
			reply.setUserId(Long.parseLong(userMap.get("linkId")));
			reply.setUserName(commonBiz.getCurrentUserName(request));
			reply.setType(Integer.parseInt(userMap.get("userType")));
			reply.setReplyId(replyId);
			reply.setContent(content);
			replyBiz.save(reply);
			Long replyNum=replyBiz.addReplyChildReplyNum(replyId);
			// 只是在页面中展示,无其它作用.因为子回复没有回复.
			reply.setReplyNum(replyNum);
			topicBiz.addTopicReplyNum(topicId);
			json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
					reply);
		} catch (Exception e) {
			e.printStackTrace();
			json = this.resultJson(ErrorCode.ERROR_SYSTEM,
					ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}
	
	

	/**
	 * @Description 回复列表
	 * @author 李帅雷
	 * @param request
	 * @param pagination
	 * @return java.lang.String
	 * 
	 */
	@RequestMapping(ADMIN_PREFIX + "/reply/replyList")
	public String replyList(HttpServletRequest request,
			@ModelAttribute("pagination") Pagination pagination) {
		try {
			String whereSql = " status=1";
			Reply reply = new Reply();
			whereSql+=replyBiz.addCondition(request, reply);
			pagination.setRequest(request);
			List<Reply> replyList = replyBiz.find(pagination, whereSql);
			topicBiz.setTopicTitle(replyList);
			request.setAttribute("replyList", replyList);
			request.setAttribute("pagination", pagination);
			request.setAttribute("reply", reply);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin/communicationSpace/reply_list";
	}

	/**
	 * @Description 删除回复
	 * @param id
	 * @return java.util.Map
	 */
	@RequestMapping(ADMIN_PREFIX + "/reply/deleteReply")
	@ResponseBody
	public Map<String, Object> deleteReply(@RequestParam("id") Long id) {
		Map<String, Object> json = null;
		try {
			Reply reply = new Reply();
			reply.setId(id);
			reply.setStatus(0);
			replyBiz.update(reply);
			json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
					null);
		} catch (Exception e) {
			e.printStackTrace();
			json = this.resultJson(ErrorCode.ERROR_SYSTEM,
					ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}

	/**
	 * 验证话题
	 * 
	 * @param request
	 * @param topic
	 * @return
	 */
	public Map<String, Object> validateTopic(HttpServletRequest request,
			Topic topic) {
		Map<String, Object> error = null;
		Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
		if (userMap != null) {
			topic.setCreateUserId(Long.parseLong(userMap.get("linkId")));
			topic.setType(Integer.parseInt(userMap.get("userType")));
			topic.setCreateUserName(commonBiz.getCurrentUserName(request));
		} else {
			error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "请先登录",
					null);
			return error;
		}
		if (StringUtils.isTrimEmpty(topic.getTitle())) {
			error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "名称不能为空",
					null);
			return error;
		}
		if (StringUtils.isTrimEmpty(topic.getContent())) {
			error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "内容不能为空",
					null);
			return error;
		}
		if (topic.getTitle().length() > 50) {
			error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "名称过长",
					null);
			return error;
		}
		if (topic.getContent().length() > 10000) {
			error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "内容过长",
					null);
			return error;
		}
		return error;
	}

	/**
	 * 验证回复
	 * 
	 * @param request
	 * @param reply
	 * @param content
	 * @return
	 */
	public Map<String, Object> validateReply(HttpServletRequest request,
			Reply reply, String content) {
		Map<String, Object> error = null;
		Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
		if (userMap != null) {
			reply.setUserId(Long.parseLong(userMap.get("linkId")));
			reply.setUserName(commonBiz.getCurrentUserName(request));
			reply.setType(Integer.parseInt(userMap.get("userType")));
		} else {
			error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "请先登录",
					null);
			return error;
		}
		if (StringUtils.isTrimEmpty(content)) {
			error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "请填写内容",
					null);
			return error;
		}
		if (content.length() > 1000) {
			error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "内容过长",
					null);
			return error;
		}
		return error;
	}

	/**
	 * 查询用户是否已对某条回复点赞
	 * @param userType
	 * @param userId
	 * @param replyId
	 * @return
	 */
	public Map<String, Object> checkUserHasPraiseOneReply(Integer userType,
			Long userId, Long replyId) {
		List<Praise> praiseList = praiseBiz.find(null, " status=1 and replyId="
				+ replyId + " and userId=" + userId + " and type=" + userType+" and belong=1");
		if (praiseList != null && praiseList.size() > 0) {
			return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "您已赞过",
					null);
		}
		return null;
	}
	
	/**
	 * 验证回复
	 * @param content
	 * @return
	 */
	public Map<String,Object> validateReply(String content){
		Map<String,Object> error=null;
		if (StringUtils.isTrimEmpty(content)) {
			error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY,
					"请填写内容", null);
			return error;
		}
		if (content.length() > 1000) {
			error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY,
					"内容过长", null);
			return error;
		}
		return null;
	}
}
