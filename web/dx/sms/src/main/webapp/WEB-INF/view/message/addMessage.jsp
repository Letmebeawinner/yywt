<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <style type="text/css">
        textarea {
            resize: none;
        }
    </style>
    <title>发送短信</title>
    <script type="text/javascript" src="/static/js/message.js"></script>
</head>
<body>
<div class="centercontent">
    <div class="pageheader" style="margin-left: 20px">
        <h1 class="pagetitle">发送短信</h1>
        <span>
                    <span style="color:red">说明</span><br>
                    1. 此页面用于发送短信；<br>
                    2. 自定义添加联系人  ：自定义添加联系人时需手动输入联系人电话号码，一次添加多个联系人时，电话号码之间以逗号隔开，一次最多添加200个联系人<br>
                    3. 选择学员添加联系人：选择学员添加联系人时，必须通过点击<span style="color:red">选择</span>学员，通过选择学员添加联系人，不能手动输入，一次添加多个联系人时，电话号码之间以逗号隔开，一次最多添加200个联系人<br>
                    4. 添加短信内容：短信内容不能超过<span style="color:red">67</span>个字<br>
                    5. 发送短信：联系人和短信内容添加完成后点击<span style="color:red">发送</span>按钮，发送短信<br>
                    6. 重置：点击<span style="color:red">重置</span>按钮，清空已添加的联系人，和短信内容<br>
		</span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="basicform" class="subcontent">
            <form class="stdform stdform2" method="post" onsubmit="return false;" id="addUser">
                <p>  <label><em style="color: red;">*</em>联系人添加方式</label>
                     <span class="field" style="width: 15%">
                        <select onchange="chooseType(this)" id="type">
                            <option value="0">自定义</option>
                            <option value="1">选择用户</option>
                        </select>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>添加联系人</label>
                    <span class="field" id="mobiles"><textarea cols="8" rows="8" class="mediuminput" id="tel" placeholder="自定义添加联系人，需手动输入联系人手机号" style="display: block"></textarea></span>
                    <span class="field" id="choose" style="display: none;">
                        <button class="stdbtn btn_red" onclick="chooseUser()">选择用户</button>
                    </span>
                </p>
                <p>
                    <label><em style="color: red;">*</em>发送内容</label>
                    <span class="field"><textarea cols="8" rows="8" class="mediuminput" id="context"></textarea></span>
                </p>
                <p class="stdformbutton">
                    <button class="submit radius2" onclick="send()">发送</button>
                    <input type="reset" class="reset radius2" value="重置"  onclick="resetForm()"/>
                </p>
                <input type="hidden" name="receiveUserId" id="receiveUserId">
            </form>
        </div>
    </div>
</div>
</body>
</html>