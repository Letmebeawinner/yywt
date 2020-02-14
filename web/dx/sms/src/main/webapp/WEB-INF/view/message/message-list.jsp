<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>短信列表</title>
    <script type="text/javascript" src="/static/js/message.js"></script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader" style="margin-left: 20px">
        <h1 class="pagetitle">短信列表</h1>
        <span>
                    <span style="color:red">说明</span><br>
                    1.此页面为短信记录列表页面,显示已发送短信的相关信息<br>
                    2.按手机号搜索短信记录：在<span style="color:red">查询条件</span>处输入接收者手机号，点击<span style="color:red">搜索</span>进行查询<br>
                    3.按发送类型搜索短信记录：点击类型处的下拉框，选择发送类型，点击<span style="color:red">搜索</span>进行查询<br>
                    4.发送短信：点击<span style="color:red">发送短信</span>，跳转发送短信页面发送短信<br>
                    5.查看详情：点击<span style="color:red">查看详情</span>，跳转短信详情页面查看短信记录详细信息<br>
                    6.删除:点击<span style="color:red">删除</span>，删除对应短信记录,删除短信记录<span style="color:red">不可恢复</span>，请慎重删除<br>
        </span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <!-- 搜索条件，开始 -->
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form  class="disIb" id="searchForm" action="${ctx}/admin/sms/message/messageList.json" method="get">
                    <div class="disIb ml20 mb10">
                        <span class="vam">查询条件 &nbsp;</span>
                        <label class="vam">
                            <input style="width: auto;" type="text" class="hasDatepicker" name="queryMessage" placeholder="输入接收者手机号" value="${queryMessage}">
                        </label>
                    </div>

                    <div class="tableoptions disIb mb10">
                        <span class="vam">类型 &nbsp;</span>
                        <label class="vam">
                            <select name="type" class="vam" id="">
                                <option value="0">请选择</option>
                                <option value="1" <c:if test="${type==1}"> selected="selected"</c:if>>系统发送</option>
                                <option value="2" <c:if test="${type==2}"> selected="selected"</c:if>>管理员发送</option>
                                <option value="3" <c:if test="${type==3}"> selected="selected"</c:if>>用户对发</option>
                            </select>
                        </label>
                    </div>
                </form>
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)" onclick="searchForm()" class="stdbtn btn_orange">搜 索</a>
                    <a href="javascript: void(0)" onclick="emptyForm()" class="stdbtn ml10">重 置</a>
                    <a href="${ctx}/admin/sms/message/addMessage.json"  class="stdbtn ml10">发送短信</a>
                </div>
            </div>
        </div>
        <!-- 搜索条件，结束 -->

        <!-- 数据显示列表，开始 -->
        <div class="pr">
            <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
                <colgroup>
                    <col class="con0"/>
                    <col class="con1" />
                    <col class="con0"/>
                    <col class="con1" />
                    <col class="con0"/>
                    <col class="con1" />
                    <col class="con0"/>
                    <col class="con1" />
                </colgroup>
                <thead>
                <tr>
                    <th class="head0 center">id</th>
                    <th class="head1 center" width="20%">短信内容</th>
                    <th class="head0 center">发送类型</th>
                    <th class="head1 center" width="8%">发送者</th>
                    <th class="head0 center">接收者手机号</th>
                    <th class="head1 center">创建时间</th>
                    <th class="head0 center">最后更新时间</th>
                    <th class="head1 center">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${messageList}" var="smssendrecord" varStatus="index">
                    <tr>
                        <td>${index.count}</td>
                        <td>${smssendrecord.context}</td>
                        <td>
                            <c:if test="${smssendrecord.sendType==1}">
                                系统发送
                            </c:if>
                            <c:if test="${smssendrecord.sendType==2}">
                                管理员发送
                            </c:if>
                            <c:if test="${smssendrecord.sendType==3}">
                                用户对发
                            </c:if>
                        </td>
                        <td>${userNames.get(smssendrecord.userId.toString())==null?smssendrecord.userId:userNames.get(smssendrecord.userId.toString())}</td>
                        <td>${smssendrecord.mobiles}
                          <%-- ${smssendrecord.mobiles.length()<=30?smssendrecord.mobiles:smssendrecord.mobiles.substring(0,30)}
                            <c:if test="${smssendrecord.mobiles.length()>30}">
                                ...
                            </c:if>--%>
                        </td>
                        <td>
                            <fmt:formatDate value="${smssendrecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>
                            <fmt:formatDate value="${smssendrecord.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td class="center" width="13%">
                            <a href="${ctx}/admin/sms/message/getMsgRecodeInfo.json?id=${smssendrecord.id}" class="stdbtn" title="查看详情">查看详情</a>
                            <a href="javascript:void(0)" class="stdbtn" title="删除" onclick="deleteMsgRecode(${smssendrecord.id},this)">删除</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- 分页，开始 -->
            <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
            <!-- 分页，结束 -->
        </div>
        <!-- 数据显示列表，结束 -->
    </div>
</div>
</body>
</html>