<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>历年成果统计</title>
    <script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
    <script src="http://code.highcharts.com/highcharts.js"></script>
    <style>
        .stdtable {
            width: 100%;
        }

        .stdtable .con0 {
            background: #fff;
        }

        .stdtable .con1 {
            background: #fcfcfc;
        }

        .stdtable th, .stdtable td {
            line-height: 21px;
            vertical-align: middle;
            color: #333;
        }

        .stdtable thead th, .stdtable thead td {
            padding: 7px 10px;
            border: 1px solid #ddd;
            border-left: 0;
            text-align: left;
        }

        .stdtable tfoot th, .stdtable tfoot td {
            font-weight: normal;
            font-size: 14px;
            padding: 7px 10px;
            border-right: 1px solid #ddd;
            border-bottom: 1px solid #ddd;
            text-align: left;
        }

        .stdtable thead th:first-child, .stdtable tfoot th:first-child,
        .stdtable thead td:first-child, .stdtable tfoot td:first-child {
            border-left: 1px solid #ddd;
        }

        .stdtable thead th.head0, .stdtable tfoot th.head0, .stdtable thead td.head0, .stdtable tfoot td.head0 {
            font-size: 14px;
            background-color: #fcfcfc;
        }

        /*.stdtable thead td { font-weight: bold;}*/
        .stdtable tbody tr td {
            font-size: 12px;
            padding: 8px 10px;
            border-right: 1px solid #eee;
            border-bottom: 1px solid #eee;
            color: #666;
        }

        .stdtable tbody tr:last-child td {
            border-bottom: 1px solid #ddd;
        }

        .stdtable tbody tr td:first-child {
            border-left: 1px solid #ddd;
        }

        .stdtable tbody tr td:last-child {
            border-right: 1px solid #ddd;
        }

        .stdtable tbody tr.togglerow td {
            background: #fff;
            padding: 15px;
        }

        .stdtable tbody tr.togglerow:hover td {
            background: #fff;
        }

        .stdtable .actions a {
            display: inline-block;
            margin-left: 5px;
            border-left: 1px solid #ccc;
            padding-left: 5px;
        }

        .stdtable .actions a:first-child {
            border-left: 0;
            margin-left: 0;
        }

        .stdtable .actions a:hover {
            color: #D20009;
        }
    </style>
</head>
<body>
<div class="centercontent tables">
    <div class="pageheader notab" style="margin-left: 30px">
        <h1 class="pagetitle">历年成果统计</h1>
        <span>
                    <span style="color:red">说明</span><br/>
					1.本页面展示调研报告列表.<br/>
                </span>
    </div><!--pageheader-->
        <div style=" width: 100%; height: 500px;">
            <iframe src="${ctx}/hessian/admin/ky/resultEveryYearStatistics.json?type=${type}" width="100%"
                    height="100%"></iframe>
        </div>
</div>
</body>
</html>