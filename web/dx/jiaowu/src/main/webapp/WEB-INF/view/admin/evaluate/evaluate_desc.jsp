<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>评价心得</title>
    <script type="text/javascript">
        jQuery(function () {
            jQuery("textarea[name='evaluate.text']").val(
                jQuery("input[name='evalText']").val()
            )
        });


    </script>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">评价心得</h1>
        <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于评价心得<br/>
                    2.按要求填写相关信息,点击"提交"按钮,评价心得.<br/>
                    3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
				</span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <div id="validation" class="subcontent">
                <form id="form1" class="stdform">
                    <input type="hidden" value="${evaluate.text}" name="evalText">
                    <p>
                        <label><em style="color: red;">*</em>标题</label>
                        <span class="field">
                            <input type="text" disabled name="evaluate.title" value="${evaluate.title}"
                                   class="longinput">
                            </span>
                    </p>

                    <p>
                        <label><em style="color: red;">*</em>内容</label>
                        <span class="field"><textarea cols="80" rows="5" disabled name="evaluate.text"
                                                      class="mediuminput" id="content"></textarea></span>
                    </p>
                    <br/>


                </form>

                <p class="stdformbutton">
                    <input type="button" value="返回" onclick="history.go(-1)"
                           style="background-color: #d20009;border-color: #de4204;cursor: pointer;font-weight: bold;padding: 7px 10px;color: #fff;margin-left: 220px;"/>
                </p>
            </div>
            <!-- 主要内容结束 -->
            <div class="clear"></div>
        </div><!-- #updates -->
    </div>
</div>
</body>
</html>