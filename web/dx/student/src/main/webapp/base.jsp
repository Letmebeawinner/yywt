<%@ page import="com.jiaowu.common.StudentCommonConstants"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%--
<c:set var="smsPath" value="<%=CommonConstants.smsPath%>" />
--%>
<c:set var="ctx" value="<%=StudentCommonConstants.contextPath%>"/>
<c:set var="imgPath" value="<%=StudentCommonConstants.imageServicePath%>"/>
<c:set var="basePath" value="<%=StudentCommonConstants.BASE_PATH%>"/>
<c:set var="smsPath" value="<%=StudentCommonConstants.smsPath%>" />
<script type="text/javascript">
    var imagePath = "<%=StudentCommonConstants.imageServicePath%>";
</script>
<script type="text/javascript" src="${basePath}/static/js/plugins/jquery-1.7.min.js"></script>

<script type="text/javascript">
    /*jQuery(function(){
        jQuery.ajax({
            url:'${ctx}/admin/jiaowu/queryUserDepartment.json',
            data:"",
            type:'post',
            dataType:'json',
            success:function (result){
                if(result.code=="0"){
                    if(result.data=="80"){
                        jQuery("#backSystem").hide();
                    }
                }else{
                    jAlert(result.message,'提示',function() {});
                }
            } ,
        });
    })*/
</script>