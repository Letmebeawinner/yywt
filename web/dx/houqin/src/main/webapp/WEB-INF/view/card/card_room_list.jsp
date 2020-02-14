<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>客房开卡</title>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab">
        <h1 class="pagetitle">客房开卡</h1>
    </div><!--pageheader-->
    <div id="contentwrapper" class="contentwrapper">
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <div class="disIb mb10">
                    <font style="margin-left: 30px;">班级人数：<font style="color:red">50</font>人，空余房间<font style="color:red">14</font>间，已有10人办理</font>
                    <a href="${ctx}/admin/houqin/fenfang.json"  class="stdbtn btn_orange ml100">一键分房</a>
                </div>
            </div>
        </div>
        <div class="overviewhead clearfix mb10">
            <div class="fl mt5">
                <div class="disIb ml20 mb10">
                    <a href="javascript: void(0)"  class="stdbtn btn_orange">一号楼</a>
                    <a href="javascript: void(0)"  class="stdbtn ml30">二号楼</a>
                    <a href="javascript: void(0)"  class="stdbtn ml30">三号楼</a>
                    <a href="javascript: void(0)"  class="stdbtn ml30">五号楼</a>
                </div>
            </div>
        </div>
    <!-- 数据显示列表，开始 -->
    <div class="pr">
        <input type="hidden" value="${mess.id}" name="messId">
        <table cellpadding="0" cellspacing="0" border="0" class="stdtable">
            <thead>
            <tr>
                <th class="head0 center">ID</th>
                <th class="head0 center">楼号</th>
                <th class="head0 center">房间号</th>
                <th class="head0 center">状态</th>
                <th class="head0 center">入住人数</th>
                <th class="head0 center">天数</th>
                <th class="head0 center">入住时间</th>
                <th class="head0 center">结束时间</th>
                <th class="head0 center">
                    操作
                </th>
            </tr>
            </thead>
            <tbody>
                <tr>
                    <td>001</td>
                    <td>1号楼</td>
                    <td>Aj23344455</td>
                    <td>空闲</td>
                    <td>张三、李四</td>
                    <td>2</td>
                    <td>2017-11-7 9:00</td>
                    <td>2017-11-9 9:00</td>
                    <td class="center">
                        <a href="" class="stdbtn" title="添加">添加</a>
                        <a href="" class="stdbtn" title="调换">调换</a>
                    </td>
                </tr>
                <tr>
                    <td>002</td>
                    <td>1号楼</td>
                    <td>Aj23344455</td>
                    <td>空闲</td>
                    <td>张三、李四</td>
                    <td>2</td>
                    <td>2017-11-7 9:00</td>
                    <td>2017-11-9 9:00</td>
                    <td class="center">
                        <a href="" class="stdbtn" title="添加">添加</a>
                        <a href="" class="stdbtn" title="调换">调换</a>
                    </td>
                </tr>
                <tr>
                    <td>003</td>
                    <td>1号楼</td>
                    <td>Aj23344455</td>
                    <td>空闲</td>
                    <td>张三、李四</td>
                    <td>2</td>
                    <td>2017-11-7 9:00</td>
                    <td>2017-11-9 9:00</td>
                    <td class="center">
                        <a href="" class="stdbtn" title="添加">添加</a>
                        <a href="" class="stdbtn" title="调换">调换</a>
                    </td>
                </tr>
                <tr>
                    <td>004</td>
                    <td>1号楼</td>
                    <td>Aj23344455</td>
                    <td>空闲</td>
                    <td>张三、李四</td>
                    <td>2</td>
                    <td>2017-11-7 9:00</td>
                    <td>2017-11-9 9:00</td>
                    <td class="center">
                        <a href="" class="stdbtn" title="添加">添加</a>
                        <a href="" class="stdbtn" title="调换">调换</a>
                    </td>
                </tr>
                <tr>
                    <td>005</td>
                    <td>1号楼</td>
                    <td>Aj23344455</td>
                    <td>空闲</td>
                    <td>张三、李四</td>
                    <td>2</td>
                    <td>2017-11-7 9:00</td>
                    <td>2017-11-9 9:00</td>
                    <td class="center">
                        <a href="" class="stdbtn" title="添加">添加</a>
                        <a href="" class="stdbtn" title="调换">调换</a>
                    </td>
                </tr>
                <tr>
                    <td>006</td>
                    <td>1号楼</td>
                    <td>Aj23344455</td>
                    <td>空闲</td>
                    <td>张三、李四</td>
                    <td>2</td>
                    <td>2017-11-7 9:00</td>
                    <td>2017-11-9 9:00</td>
                    <td class="center">
                        <a href="" class="stdbtn" title="添加">添加</a>
                        <a href="" class="stdbtn" title="调换">调换</a>
                    </td>
                </tr>
                <tr>
                    <td>007</td>
                    <td>1号楼</td>
                    <td>Aj23344455</td>
                    <td>空闲</td>
                    <td>张三、李四</td>
                    <td>2</td>
                    <td>2017-11-7 9:00</td>
                    <td>2017-11-9 9:00</td>
                    <td class="center">
                        <a href="" class="stdbtn" title="添加">添加</a>
                        <a href="" class="stdbtn" title="调换">调换</a>
                    </td>
                </tr>
                <tr>
                    <td>008</td>
                    <td>1号楼</td>
                    <td>Aj23344455</td>
                    <td>空闲</td>
                    <td>张三、李四</td>
                    <td>2</td>
                    <td>2017-11-7 9:00</td>
                    <td>2017-11-9 9:00</td>
                    <td class="center">
                        <a href="" class="stdbtn" title="添加">添加</a>
                        <a href="" class="stdbtn" title="调换">调换</a>
                    </td>
                </tr>
                <tr>
                    <td>009</td>
                    <td>1号楼</td>
                    <td>Aj23344455</td>
                    <td>空闲</td>
                    <td>张三、李四</td>
                    <td>2</td>
                    <td>2017-11-7 9:00</td>
                    <td>2017-11-9 9:00</td>
                    <td class="center">
                        <a href="" class="stdbtn" title="添加">添加</a>
                        <a href="" class="stdbtn" title="调换">调换</a>
                    </td>
                </tr>

            </tbody>
        </table>
        <!-- 分页，开始 -->
        <div class="dataTables_info" id="dyntable_info">第 1 页，共 1 页，每页最多显示 10条记录</div>
        <div class="dataTables_paginate paging_full_numbers" id="dyntable_paginate">
            <span class="first paginate_button paginate_button_disabled" id="dyntable_first">首页</span>
            <span class="previous paginate_button paginate_button_disabled" id="dyntable_previous">上一页</span>
            <span>
                <span class="paginate_active">1</span>
                    </span>
            <span class="paginate_button" id="dyntable_next">下一页</span>
            <span class="last paginate_button" id="dyntable_last">尾页</span>
            <span class="disIb ml20 left_box">
               <label class="vam" style="padding: 4px 5px;">
                <input type="text" placeholder="" id="go_to_page_no" class="hasDatepicker" style="width: 40px;">
               </label>
               <span class="last paginate_button" onclick="goNum()" id="dyntable_last">确定</span>
           </span>
        </div>
    </div>
        <jsp:include page="/WEB-INF/view/common/adminPage.jsp"/>
        <!-- 分页，结束 -->
    </div>
    <!-- 数据显示列表，结束 -->
</div>
</body>
</html>