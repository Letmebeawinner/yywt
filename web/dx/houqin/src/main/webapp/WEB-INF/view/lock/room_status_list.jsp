<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>房间使用状态</title>
    <script type="text/javascript">
        jQuery(function () {
            jQuery("select[name='searchFloor']").val(jQuery("input[name='searchOpt']").val())
        })

        function searchForm() {
            jQuery("#searchForm").submit();
        }

        function useing() {
            jQuery(".repairing").css("display", "none");
            jQuery(".freeing").css("display", "none");
            jQuery(".useing").css("display", "block");
            jQuery(".nofulling").css("display", "none");
        }

        function freeing() {
            jQuery(".repairing").css("display", "none");
            jQuery(".useing").css("display", "none");
            jQuery(".freeing").css("display", "block");
            jQuery(".nofulling").css("display", "none");
        }

        function nofulling() {
            jQuery(".repairing").css("display", "none");
            jQuery(".useing").css("display", "none");
            jQuery(".freeing").css("display", "none");
            jQuery(".nofulling").css("display", "block");
        }

        function repairing() {
            jQuery(".useing").css("display", "none");
            jQuery(".freeing").css("display", "none");
            jQuery(".nofulling").css("display", "none");
            jQuery(".repairing").css("display", "block");
        }

        /**
         * 清空搜索条件
         */
        function emptyForm() {
            jQuery("input:text").val('');
            jQuery("select").val(0);
        }

        function goToOpen(roomId,name) {
            var oldId = '${id}';
            var oldRoomId = '${roomId}';
            if(oldId>1 && oldRoomId==roomId){
                alert("您当前房间就是该房间，不可更换");
                return;
            }
            if(oldId==1){
                window.location.href= '${ctx}/admin/houqin/tempOpenCardById/'+roomId+"/"+name+'.json';
            }else{
                jQuery.ajax({
                    url: "${ctx}/admin/houqin/updateTempOpenCard.json",
                    data: {"roomId": roomId,"name":name,"oldId":oldId,"oldRoomId":oldRoomId},
                    type: "post",
                    dataType: "json",
                    success: function (result) {
                        if (result.code==0) {
                            alert(result.message);
                            window.location.href = "/admin/houqin/tempOpenCardList.json";
                        } else {
                            alert(result.message);
                            return;
                        }
                    }
                });

            }
        }
    </script>

    <style type="text/css">
        .roomfreestatus {
            border:2px solid #57C6FE;
            width: 82px;
            height: 100px;
            background-color: #52d58e;
            float: left;
            margin-left: 30px;
            margin-top: 10px;
            margin-bottom: 30px;
            color: #ffffff;
            font-size: 12px;
        }

        .roomnofullstatus {
            border:2px solid #0099FF;
            width: 82px;
            height: 100px;
            background-color: #FFB951;
            float: left;
            margin-left: 30px;
            margin-top: 10px;
            margin-bottom: 30px;
            color: #ffffff;
            font-size: 12px;
        }

        .roombusystatus {
            width: 82px;
            height: 100px;
            background-color: #ff6764;
            float: left;
            margin-left: 30px;
            margin-top: 10px;
            margin-bottom: 30px;
            border: 2px solid #1d1d1d;
            color: #ffffff;
            font-size: 12px;
        }

        .roomname {
            margin: 5px;
            border-bottom:1px solid;
            text-align: center;
            font-weight: bold;
        }

        .status {
            text-align: center;
        }

        .qingkuang {
            text-align: center;
            font-weight: bold;
        }

        .kongxian {
            color: #FFF;
            font-weight: bold;
        }

        .useing {
            color: #FFF;
            font-weight: bold;
        }

        .repairing {
            color: #FFF;
            font-weight: bold;
        }

        .kongroom {
            background: #52d58e;
            width: 60px;
            height: 25px;
            line-height: 25px;
            text-align: center;
            color: #FFF;
        }
        .nofullroom {
            background: #FFB951;
            color: #FFF;
            width: 60px;
            height: 25px;
            line-height: 25px;
            text-align: center;
        }

        .fullroom {
            background: #ff6764;
            color: #FFF;
            width: 60px;
            height: 25px;
            line-height: 25px;
            text-align: center;
        }

        .repair {
            background: #61AEEF;
            color: #FFF;
            width: 60px;
            height: 25px;
            line-height: 25px;
            text-align: center;
        }

        .roomfreestatus .state-icon{
            display: block;
            width: 12px;
            height: 22px;
            margin: 0 auto;
            background: url("../../../static/css/img/open.png") no-repeat;
            background-size: cover;
            vertical-align: middle;
        }
        .roomnofullstatus .state-icon{
            display: block;
            width: 12px;
            height: 22px;
            margin: 0 auto;
            background: url("../../../static/css/img/open.png") no-repeat;
            background-size: cover;
            vertical-align: middle;
        }
        .roombusystatus .state-icon{
            display: block;
            width: 16px;
            height: 22px;
            margin: 0 auto;
            background: url("../../../static/css/img/close.png") no-repeat;
            background-size: cover;
            vertical-align: middle;
        }
    </style>


</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">房间使用状态</h1>
    </div>
    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <form class="disIb" id="searchForm" action="${ctx}/admin/houqin/queryRoomStatus.json" method="post">
                    <input type="hidden" value="${searchFloor}" name="searchOpt">
                    <div class="disIb ml20 mb10">
                        <span class="vam">地点 &nbsp;</span>
                        <label class="vam">
                            <select name="searchFloor" style="width: 100px;" onchange="searchForm()">
                                <option value="1">1号楼</option>
                                <option value="2">2号楼</option>
                                <option value="3">3号楼</option>
                                <option value="5">5号楼</option>
                            </select>
                        </label>
                    </div>
                    <div class="disIb ml20 mb10">
                        <a href="javascript: void(0)" onclick="useing()" class="stdbtn fullroom">已住满</a>
                        <a href="javascript: void(0)" onclick="nofulling()" class="stdbtn nofullroom">使用中</a>
                        <a href="javascript: void(0)" onclick="freeing()" class="stdbtn kongroom">空闲中</a>
                        <a href="javascript: void(0)" onclick="repairing()" class="stdbtn repair">维修中</a>
                    </div>
                    <div class="disIb ml20 mb10">
                        <a href="${ctx}/admin/houqin/toBatchTempOpenCard.json"  class="stdbtn ml10">批量开通</a>
                    </div>
                </form>
            </div>
        </div>

        <!-- 数据显示列表，开始 -->
        <div class="pr">

            <table cellpadding="0" cellspacing="0" border="0" class="stdtable" style="margin-bottom: 30px">
                <thead>
                <tr>
                    <th class="head0 center">入住人数</th>
                    <th class="head0 center">房间总数</th>
                    <th class="head0 center">空闲房间</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${userInfoDtoList.size()} </td>
                    <td>${roomList.size()}</td>
                    <td>${idle}</td>
                </tr>
                </tbody>
            </table>

            <c:forEach items="${roomList}" var="room" varStatus="index">
                <c:choose>
                    <c:when test="${repairList[room.id] != null}">
                        <a href="${ctx}/admin/houqin/tempOpenCardById/${room.id}/${room.name}.json">
                            <div class="roombusystatus repairing" style="background: #61AEEF">
                                <div class="roomname">${room.name}</div>
                                <div class="status">
                                    <tt class="state-icon"></tt>
                                    <div class="kongxian">维修</div>
                                </div>
                                <div class="qingkuang">
                                    不可安排入住
                                </div>
                            </div>
                        </a>
                    </c:when>
                    <c:when test="${room.type==0 and repairList[room.id] == null}">
                        <a href="javascript:void(0);" onclick="goToOpen('${room.id}','${room.name}')">
                            <div class="roomfreestatus freeing">
                                <div class="roomname">${room.name}</div>
                                <div class="status">
                                    <tt class="state-icon"></tt>
                                    <div class="kongxian">空闲</div>
                                </div>
                                <div class="qingkuang">
                                    可安排入住
                                </div>
                            </div>
                        </a>
                    </c:when>

                    <c:when test="${room.type==1 and repairList[room.id] == null}">
                        <a href="javascript:void(0);" onclick="goToOpen('${room.id}','${room.name}')">
                            <div class="roomnofullstatus nofulling">
                                <div class="roomname">${room.name}</div>
                                <div class="status">
                                    <tt class="state-icon"></tt>
                                    <div class="nofulling">未住满</div>
                                </div>
                                <div class="qingkuang">
                                    可安排入住
                                </div>
                            </div>
                        </a>
                    </c:when>

                    <c:when test="${room.type==2 and repairList[room.id] == null}">
                        <a href="javascript:void(0)">
                            <div class="roombusystatus useing">
                                <div class="roomname">${room.name}</div>
                                <div class="status">
                                    <tt class="state-icon"></tt>
                                    <div class="kongxian">已住满</div>
                                </div>
                                <div class="qingkuang">
                                    不可安排入住
                                </div>
                            </div>
                        </a>
                    </c:when>
                </c:choose>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>