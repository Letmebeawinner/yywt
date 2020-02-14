<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>新建调研报告</title>
    <script type="text/javascript" src="${ctx}/static/js/plugins/jquery.alerts.js"></script>
    <link type="text/css" rel="stylesheet" href='${ctx }/static/uploadify/uploadify.css'/>
    <script type="text/javascript" src="${ctx }/static/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="${ctx }/static/uploadify/upload-file.js"></script>
    <script type="text/javascript">
        jQuery(function () {
            jQuery("#type").change(function () {
                var selectedType = jQuery(this).children('option:selected').val();
                if (selectedType == "teacher") {
                    jQuery("#teacherp").show();
                    jQuery("#studentp").hide();
                    jQuery("#peopleId").val("");
                    jQuery("#teacherspan").html("");
                    jQuery("#studentspan").html("");
                } else {
                    jQuery("#teacherp").hide();
                    jQuery("#studentp").show();
                    jQuery("#peopleId").val("");
                    jQuery("#teacherspan").html("");
                    jQuery("#studentspan").html("");
                }
            });

            // 绑定上传控件
            uploadFile("uploadFile", true, "myFile", imagePath, callbackFile);

            jQuery("select[name='researchReport.classType']").change()
        });


        /**
         * 文件上传方法
         * @param id 控件ID
         * @param auto 是否自动上传,true自动，false非自动
         * @param dir 目录
         * @param resServicePath 资源服务全域名
         * @param callback 回调方法
         */
        function uploadFile(id, auto, dir, resServicePath, callback) {
            jQuery("#" + id).uploadify({
                uploader: resServicePath + '/upload/res/file/uploadFile',
                swf: '/static/uploadify/uploadify.swf',
                formData: {'dir': dir},
                fileObjName: 'files',
                auto: true,
                method: 'psot',
                multi: false,
                height: 30,
                width: 120,
                buttonText: '上传文件',
                buttonClass: '',
                onSelect: function (file) {//每添加一个文件至上传队列时触发该事件。

                },
                onUploadComplete: function (file) {//每一个文件上传完成都会触发该事件，不管是上传成功还是上传失败。

                },
                onQueueComplete: function (queueData) {//队列中的所有文件被处理完成时触发该事件。
                    //queueData.uploadsSuccessful
                },
                onUploadSuccess: function (file, data, response) {//每一个文件上传成功时触发该事件。
                    callback(data);
                },
                onUploadError: function (file, errorCode, errorMsg, errorString) {
                    alert("文件上传失败");
                }
            });
        }

        function callbackFile(data) {
            data = data.substr(2);
            data = data.substr(0, data.length - 2);
            jQuery("#fileUrl").val(data);
            jQuery("#file").html('已上传：' + jQuery(".fileName").html());
        }


        function addResearchReport() {
            var $researchId = jQuery("select[name='researchReport.researchId']");
            var researchIdName = $researchId.find("option:selected").text()

            if (!$researchId.val()) {
                jAlert('请选择调研报告类型', '提示', function () {
                });
                return false
            } else {
                jQuery("#researchName").val(researchIdName)
            }

            if (jQuery("#zdls").val().length < 1) {
                jAlert('请输入指导老师', '提示', function () {
                });
                return false
            }

            if (jQuery("#dyzbr").val().length < 1) {
                jAlert('请输入第一执笔人', '提示', function () {
                });
                return false
            }

            if (jQuery("#ktcy").val().length < 1) {
                jAlert('请输入课题参与人员', '提示', function () {
                });
                return false
            }

            if (jQuery("#lxfs").val().length < 1) {
                jAlert('请输入联系方式', '提示', function () {
                });
                return false
            }

            if (jQuery("#content").val().length < 1) {
                jAlert('请输入内容', '提示', function () {
                });
                return false
            }

            if (jQuery("#fileUrl").val().length < 1) {
                jAlert('请上传文件', '提示', function () {
                });
                return false
            }

            var params = jQuery("#form1").serialize();
            jQuery.ajax({
                url: '${ctx}/admin/jiaowu/researchReport/createResearchReport.json',
                data: params,
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    jAlert(result.message, '提示', function () {
                        if (result.code === "0") {
                            window.location.href =
                                "/admin/jiaowu/researchReport/researchReportList.json" +
                                "?approvalDepartment=" + result.data;
                        }
                    });
                },
                error: function () {
                    jAlert('添加失败', '提示', function () {
                    });
                }
            });
        }

        /**
         * 二级联动
         * @param obj 下拉列表
         */
        function findClass(obj) {
            var param = jQuery(obj).find("option:selected").attr("class")
            jQuery.getJSON(
                "${ctx}/admin/jiaowu/researchReport/findClassByType.json",
                {"typeId": param},
                function (result) {
                    var $classStr = jQuery("select[name='researchReport.classStr']");
                    $classStr.empty()
                    if (result.data.length > 0) {
                        jQuery.each(result.data, function () {
                            $classStr.append("<option value='" + this.name + "'>" + this.name + "</option>")
                        })
                    } else {
                        $classStr.append("<option value=''>暂无班次</option>")
                    }
                })
        }

        function selectUser() {
            window.open('${ctx}/jiaowu/user/userListForSelect.json', 'newwindow', 'toolbar=no,scrollbars=yes,location=no,resizable=no,top=100,left=300,width=800,height=600');
        }
        function addUser(userIdAndName) {
            jQuery("#studentId").val(userIdAndName[0]);
            jQuery("#userspan").html(userIdAndName[1]);
        }

    </script>

</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">新建${resultTypeName}调研报告</h1>
        <span>
                    <span style="color:red">说明</span><br>
                    1.本页面用于新建${resultTypeName}调研报告<br/>
                    2.按要求填写相关信息,点击"提交"按钮,新建调研报告.<br/>
					3.带有红色<span style="color: red">*</span>标记的内容为必填部分。
				</span>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div id="updates" class="subcontent">
            <!-- 主要内容开始 -->
            <div id="validation" class="subcontent">

                <form id="form1" class="stdform stdform2" method="post" action="">

                    <p>
                        <input type="hidden" name="researchReport.peopleId" id="studentId"/>
                        <label><em style="color: red;">*</em>学员</label>
                        <span class="field">
                                <span id="userspan"></span>
                                <a href="javascript:selectUser()" class="stdbtn btn_orange">选择学员</a>
                            </span>
                    </p>

                    <p>
                        <label><em style="color: red;">*</em>调研报告类型</label>
                        <span class="field">
                            <select name="researchReport.researchId">
                                <option value="">请选择</option>
                                <c:forEach items="${researchList}" var="rl">
                                    <option value="${rl.id}">${rl.title}</option>
                                </c:forEach>
                            </select>

                        </span>
                    </p>
                    <p>
                        <label><em style="color: red;">*</em>班型</label>
                        <span class="field">
                            <select name="researchReport.classType" onchange="findClass(this)">
                                <c:forEach items="${classTypes}" var="ct">
                                    <option value="${ct.name}" class="${ct.id}">${ct.name}</option>
                                </c:forEach>
                            </select>
                        </span>
                    </p>
                    <p>
                        <label><em style="color: red;">*</em>班次</label>
                        <span class="field">
                            <select name="researchReport.classStr" id="bj">
                                <option value="">请选择</option>
                            </select>
                        </span>
                    </p>
                    <p>
                        <label><em style="color: red;">*</em>调研组</label>
                        <span class="field">
                            <select name="researchReport.agroup" id="dyz">
                                <c:forEach begin="1" end="20" var="i">
                                    <option value="${i}">${i}</option>
                                </c:forEach>
                            </select>
                        </span>
                    </p>
                    <p>
                        <label><em style="color: red;">*</em>指导老师</label>
                        <span class="field"><input type="text" name="researchReport.teacher"
                                                   class="mediuminput" id="zdls" maxlength="225"></span>
                    </p>
                    <p>
                        <label><em style="color: red;">*</em>第一执笔人</label>
                        <span class="field"><input type="text" class="mediuminput" name="researchReport.zbr" id="dyzbr"
                                                   maxlength="225"></span>
                    </p>

                    <p>
                        <label><em style="color: red;">*</em>课题参与人员</label>
                        <span class="field"><input type="text" class="mediuminput" name="researchReport.participant"
                                                   id="ktcy" maxlength="225"></span>
                    </p>
                    <p>
                        <label><em style="color: red;">*</em>联系方式</label>
                        <span class="field"><input type="text" class="mediuminput" name="researchReport.contact"
                                                   id="lxfs" maxlength="225"></span>
                    </p>
                    <p>
                        <label><em style="color: red;">*</em>内容</label>
                        <span class="field"><textarea cols="80" rows="5" name="researchReport.content"
                                                      class="mediuminput" id="content"></textarea></span>
                    </p>
                    <p>
                        <label><em style="color: red;">*</em>文件地址:</label>
                        <span class="field">
						 <input type="hidden" name="researchReport.fileUrl" id="fileUrl"/>
						 <input type="button" id="uploadFile" value="上传文件"/>
						 <center><h4 id="file"></h4></center>
					    </span>
                    </p>
                    <p>
                        <label>备注</label>
                        <span class="field"><textarea cols="80" rows="5" name="researchReport.note" class="mediuminput"
                                                      id="note"></textarea></span>
                    </p>
                    <%--隐藏域--%>
                    <input type="hidden" name="researchReport.researchName" id="researchName"/>
                    <%--URL传参--%>
                    <input type="hidden" name="researchReport.approvalDepartment" value="${approvalDepartment}"/>
                    <p class="stdformbutton" style="text-align: center">
                        <button class="radius2" onclick="addResearchReport()" id="submitButton">
                            提交
                        </button>
                    </p>
                </form>

            </div>
            <!-- 主要内容结束 -->
            <div class="clear"></div>
        </div><!-- #updates -->
    </div>
</div>
</body>
</html>