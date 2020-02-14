<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/base.jsp" %>
<!DOCTYPE html>

<html><head>
    <title>iSignature HTML5签章示例DEMO(客户端-Windows)</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    
    <link rel="stylesheet" href="${ctx}/kinggrid/dialog/artDialog/ui-dialog.css">
    <link rel="stylesheet" href="${ctx}/kinggrid/core/kinggrid.plus.css">
    <link rel="stylesheet" href="${ctx}/kinggrid/css/search.css">


    <script type="text/javascript" src="${ctx}/kinggrid/core/kinggrid.min.js"></script>
    <script type="text/javascript" src="${ctx}/kinggrid/core/kinggrid.plus.min.js"></script>
    <script type="text/javascript" src="${ctx}/kinggrid/dialog/artDialog/dialog-min.js"></script>
    <script type="text/javascript" src="${ctx}/kinggrid/signature.min.js"></script><!-- html签章核心JS -->
    <script type="text/javascript" src="${ctx}/kinggrid/signature.pc.min.js"></script><!-- PC端附加功能 -->
    <script type="text/javascript" src="${ctx}/kinggrid/password.min.js"></script>
    <script type="text/javascript" src="${ctx}/kinggrid/signature_pad.min.js"></script>
    <script type="text/javascript" src="${ctx}/kinggrid/jquery.timer.dev.js"></script>
    <script type="text/javascript" src="${ctx}/kinggrid/jquery.qrcode.min.js"></script>
    <script type="text/javascript" src="${ctx}/kinggrid/qrcode.min.js"></script>
    <script type="text/javascript" src="${ctx}/kinggrid/jsQR.js"></script>
    <script type="text/javascript" src="${ctx}/kinggrid/search.js" charset="utf-8"></script>
    <style type="text/css">
        @media print{
            #in{ display:none}
        }

        table{margin: 0 auto;}
    </style>



    <script type="text/javascript">
        if(!window['console']){//IE8，没有改对象，创建一个对象
            window['console'] = {log: function(){}}
        }

        function delCB(signatureid, signData){
            console.log("delCB");
            if(Signature.list != null && Signature.list[signatureid] != null){
                var signatureCreator = Signature.create();
                signatureCreator.removeSignature(signData.documentid, signatureid);
            }
            return true;
        }

        Signature.init({//初始化属性
            clientConfig:{//初始化客户端参数
                'SOFTTYPE':'0'//0为：标准版， 1：网络版
            },
            delCallBack: delCB,
            imgtag: 0, //签章类型：0：无; 1:公章; 2:私章; 3:法人章; 4:法人签名; 5:手写签名
            valid : false,    //签章和证书有效期判断， 缺省不做判断
            icon_move : true, //移动签章按钮隐藏显示，缺省显示
            icon_remove : true, //撤销签章按钮隐藏显示，缺省显示
            icon_sign : true, //数字签名按钮隐藏显示，缺省显示
            icon_signverify : true, //签名验证按钮隐藏显示，缺省显示
            icon_sealinfo : true, //签章验证按钮隐藏显示，缺省显示
            certType : 'client',//设置证书在签章服务器
            sealType : 'client',//设置印章从签章服务器取
            serverUrl : 'http://127.0.0.1:80/iSignatureHTML5',//
            documentid:'KG2016093001',//设置文档ID
            documentname:'测试文档KG2016093001',//设置文档名称
            pw_timeout:'s1800' //s：秒；h:小时；d:天
        })

        window.onload = function(){
            var signs =[
                {
                    extra:{
                        icon_move: function(){
                            alert('禁止移动');
                            return false;
                        }
                    },
                    signatureid:'147683973060447728',
                    signatureData:'eyJ0aW1lc3RhbXAiOnsidGltZSI6MTQ3NjgzOTczMDM1Mn0sImFwcG5hbWUiOiJNb3ppbGxhLzUuMCAoV2luZG93cyBOVCA2LjE7IFdPVzY0KSBBcHBsZVdlYktpdC81MzcuMzYgKEtIVE1MLCBsaWtlIEdlY2tvKSBDaHJvbWUvNTMuMC4yNzg1LjE0MyBTYWZhcmkvNTM3LjM2IiwiZG9jdW1lbnRpZCI6IktHMjAxNjA5MzAwMSIsImRvY3VtZW50bmFtZSI6Iua1i+ivleaWh+aho0tHMjAxNjA5MzAwMSIsIm1vdmVhYmxlIjp0cnVlLCJrZXlzbiI6IjNEMkI1NTMwMDMyMjA1MTQiLCJvcmduYW1lIjoi6YeR5qC856eR5oqA5b6Q5qC56Iux5rWL6K+VMDEiLCJ1c2VybmFtZSI6InhiIiwic2VhbCI6eyJoZWlnaHQiOiIyLjk5IiwiaW1nZGF0YSI6ImdUbnhvYStDSlozRU41ZC84ZTBzUTlIV1Z5cVBiQkxraEFJdTE9ZndPdFNSTVlHNm1wREtjelVsRlh2ajI0cmk3ZWM9K2R4PUE2Z1RwZ2ROZ2dnZ2dnL2lpaW1nZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnbkNYVGdvZ2dnb2dFZ2dnZ2duMmdDb2dnZ3NyTk5BWlNqY0Z2MmM9cmFjT3VmZXR3Zkkza3FLUzZ3Z01LbUNQY3dJR2p2aDUyTW5oYmRieHhKaUp0Mz1POXhTa2NUT0swU2xEZnhyUkJHNm5iUjRob1JWeUV0TVlFQjRYa1FqUFUveG1qZVU2SHJibEdwanZRcmlpMFdBWGhKZVdIV3ZhSWVZeUp3ZTBoRlNlb2NINUxWSUUxWlo9UXBVOHV0SU5mdEdiTyswVzN2TElPdjE5d3Q9Tz0zdFJ0VExHUlhkcEJQTHZSVDBBdWpyckdXK2tCPUZ5UjJXbjZIUE5NMmRteU42VHB6U2ptNUxSRFNsMGM0enVsOUNOWUN4MVlMb0x1R3hPa0VsZlBidkZEK2lSYllZR21CUlhsZEtWdmlsSWp1V3l0SWlvNndzaWdnVjJUcTNMNkhLbmRuMG89anhJPXdoZWRpMUFxM1VJcERScmN4ZWdlQ1dtRnVRekZUeURBNTl1Wm9JZENBUXFWTUl0WEVEOVpmNHk5K2Z0dzFVME53TmRVMT09VU5iPWRFaThycWJRUVBIOCt3MlVaME9RV0srT1FiZFhCVFpLUzE1ckh2TFR4cVNvM3NnR1cyMj05cVl6RXkzUVYzNitMekIzK1ljd3FlKzBUODNsaj1YYk9MdkhsV3U5RUIycG91YStuem01UjQrQUYySklQV3BzTnV5NmhmYlNyWDBTam1LM0pBUTZDdUFhNCtMTXdaQjBBOCt2TVlzYUw9b3F5d2xXcVFLcz1RcHdCT2xGMkErTWhnMmV3eWxqUnJySHh3K3dVZHpqT1lJZHBvYml1PUZucitBaXdxd1JRZ3ZjSkhzR0p2cEVhbXByR2lxdmZhckVJSHZVYj1RYjVaZkNUNHJMUldvSVg0K0VLV3I0THdVVWNhd3J5PWdmNlBsZ2w5YWdUV3VraE5mYzV0PUZ2OFdXeGwyRDgvaGhkaUo9PXRCNEJSMXVWZ2o9eVZIQUFxQ0JlR1RmZ3d6SjhGaHFqdC9BTEVCYW1VYWllOWFWSjFnMXVBSDBQK016eWZOTkVFUEpVbFNMcEg5QTlEaTRVNVU4eFR5T1dVbURKOUl1STFsRGF0MHhOY0V0PWxkeFQwMTFBeitydkoybEhpamwrY2FRMFl1Qjh5c3lBKzMvUXR2WnR3U0doczFBM0RrQUllKzBQbGZGVkpFdDU4aHNmbE5mWUgra1hTc1h3SmV2alJFME9seGZxWnZzdW1UWnhEOXZrMT11d3NiU1VNOWl3MVp5K3RIZHVHME5mbm5kRDFJT09TdHgzdTlBYUxTPWhQMVFZSUpZcmJhM3l2ZEtTT25SUzJZcFNJeUQ9ZFZIU29ZYzVmZkViVGtyK01rSU8wTFZHTHFrVm1hcVNXaW9LQk9ST2N5b3BHSGF6aFJGRlJQbVUweU93TlJMVVZMRnZyWGFQT1c4dEpBR0lEdlNZR1lsRHVYY09TaktCPVM2SjVBcjI0ZGoySmpKakRIRjFCa3NrVjlCUzAwSXZ1UDI9bElHZFJZNjlPeGhCQ252K2E0UENuT0JiMjlHMGVHRXZFZ3Q0clZ4Y2JOVFBsUVM2cHBSM1lmaGYxWk5GTUdSM3dxS0pUL3NXSlJKUz05eXRTb2pGcENtdVFBbEdXeDVjRDNSVXZRNHlLTk9TaWRCREUwaU5MYlAzNG9lM1V6bWNTPWZxbHM5WmpNVzAzYWhsRE9VL2ZlV1ZSUDU1anpCTkNZdVgyR2hZV0VJcm5yenNURXRXQjBnUEtPb3Qrd3MwQWlRcDg5TEVCcEhwdTlsTXdTPWNqeWloQXJVTklmWnhjWTN0QTBaSnpIWHlJYnNUdWZWcENWUjlXZk5Ra1VwRHFJR0dlREh1d01McnJUL2FwWVlmMVdJa1dlTXl2cllMVmZraUlsSXYyM3RFUnNrRWdiNjZDam8yaHZjUjJ5eDY0Ry8vTkcwWGFENG1mRjRqZWVzUmtsVThIdS9iRzZoL2NYRDJoMFd1anBudlFHR2t1RzhZNmlyPStDMy9LMlA2NDI2MDZqdjBVWmlpaWhQQzhUS29nb2dkbTc3IiwiaW1nZXh0IjoiLmdpZiIsInNpZ25uYW1lIjoi5b6Q6I2j57uP55CG562+5ZCNIiwic2lnbnNuIjoiZ1RueG9hK0NKWjNFTjVkLzhlMHNROUhXVnlxUGJCTGtoQUl1MT1md090U1JNWUc2bXBES2N6VWxGWHZqMjRyaTciLCJ1c2VybmFtZSI6InhiIiwid2lkdGgiOiI0Ljk3In0sInByb3RlY3RlZERhdGEiOlt7ImZpZWxkIjoiaXRlbTEiLCJkZXNjIjoi5L+d5oqk6aG5MSIsInZhbHVlIjoi5ZCM5Yib6L2v5Lu25YWs5Y+4In0seyJmaWVsZCI6Iml0ZW0yIiwiZGVzYyI6IuS/neaKpOmhuTIiLCJ2YWx1ZSI6IumHkeagvOenkeaKgOaciemZkOWFrOWPuCJ9LHsiZmllbGQiOiJpdGVtMyIsImRlc2MiOiLkv53miqTpobkzIiwidmFsdWUiOiLmoLnmja7jgIrlkIjlkIzms5XjgIvlj4rmnInlhbPop4TlrprvvIznu4/lj4zmlrnljY/llYbvvIzovr7miJDkuIDoh7TvvIznrb7orqLmnKzlkIjlkIzjgIJcclxu5LiA44CB55Sy5pa55aeU5omY5LmZ5pa55Yi25L2c5peg57q45YyW5Yqe5YWs57O75YiX5Lqn5ZOB44CCXHJcbuS6jOOAgeWFt+S9k+inhOagvOWei+WPt+OAgeaVsOmHj+OAgeWNleS7t+WPiuS7t+agvOivpuingemZhOWQjua4heWNleOAglxyXG7kuInjgIHkuqTotKfml7bpl7TvvJo2MOW3peS9nOaXpVxyXG7lm5vjgIHpqozmlLbmnJ/pmZDvvJoyMOW3peS9nOaXpeOAglxyXG7kupTjgIHlj4zmlrnlnKjlkIjlkIzkuIrnlLXlrZDnrb7lrZfnm5bnq6DlkI7nq4vljbPnlJ/mlYjjgIIifV0sInNlYWxUeXBlIjoiY2xpZW50IiwicG9zaXRpb24iOnsicG9zMCI6eyJtYXJnaW5MZWZ0IjoiMzEycHgiLCJtYXJnaW5Ub3AiOiIwLjgxMjVweCJ9fSwidGltZWlkIjoxNDc2ODM5NzMwNjA0LCJ2ZXIiOnsibmFtZSI6IjEuMC4yMCIsImNvZGUiOjEwMH19'
                }
            ];
            Signature.loadSignatures(signs);
            Signature.bind({
                remove:function(fn){//签章数据撤销时，将回调此方法，需要实现签章数据持久化（保存数据到后台数据库）,
                    console.log('获取删除的签章ID：'+this.getSignatureid());
                    fn(true);//保存成功后必须回调fn(true/false)传入true/false分别表示保存成功和失败

                },
                update:function(fn){//签章数据有变动时，将回调此方法，需要实现签章数据持久化（保存数据到后台数据库）,执行后必须回调fn(true/false)，传入true/false分别表示保存成功和失败
                    console.log('获取更新的签章ID：'+this.getSignatureid());
                    console.log('获取更新的签章数据：'+this.getSignatureData());
                    fn(true);
                }
            });

            ////////////////////////////////////
            var signatureCreator = Signature.create();
            signatureCreator.getSaveSignatures("KG2016093001", function(signs){
                var signdata = new Array();
                var jsonList = eval("("+signs+")");
                for(var i=0;i<jsonList.length;i++){
                    var map = {};
                    map.signatureid = jsonList[i]["signatureId"];
                    map.signatureData = jsonList[i]["signature"];
                    signdata.push(map);
                }

                Signature.loadSignatures(signdata);
            });
            ////////////////////////////////////

            window.onbeforeunload = onbeforeunload_handler;
            window.onunload = onunload_handler;
            function onbeforeunload_handler() {

            }
            function onunload_handler() {
                Signature.clearRPW();
            }

            /////////绑定搜索/////////////
            bindDOM("ipt", "ser_box", "bot_box", "oul", function(){
                var signatureCreator = Signature.create();
                signatureCreator.loadSeals(function(data){
                    setSeals(data);
                });
            });

            /////////////////////////////////
        }
    </script>
</head>

<body>
<p>fsdfsdfsdfsdfffffffffffffffffffffffffffffffffffffffffffffffffffffff</p>
<table width=100% height=100% border="0" cellspacing="0" cellpadding="0" bgcolor="ffffff">
    <tr>
        <td id="in" colspan=2 bgcolor="#ffffff" height=32 align="center">
            <!-- <input type="button" onclick="modifyPwd()" value="密码修改"> -->
            <input type="button" pos="pos0" class="test" value="电子签章">
            <input type="button" pos="pos0" class="testSign" value="签章(数字签名)">
            <input type="button" pos="pos0" class="testHW" value="手写签名">
            <input type="button" pos="pos0" class="testBC" value="二维码">
            <input type="button" pos="pos1" class="testSave" value="保存签章">
            <!-- <input type="button" pos="pos0" class="testScanBC" value="扫码签章">
         <!-- 	<input type="button" pos="pos1" class="test" value="盖章位置B"> -->
            <input type="button" onclick="verifySignature()" value="验证签章">
            <input type="button" onclick="showSignature()" value="显示签章">
            <input type="button" onclick="hideSignature()" value="隐藏签章">
            <input type="button" onclick="takeCare();" id="yfdiv" value="注意事项">
            <!-- <br>本页为普通页面，用户可根据自己的需求更改。注意：签章密码为<font color="red">000000</font>。正式版iSignature电子签章HTML版的签章保存在用户智能印章钥匙盘上，支持身份认证和数字证书，符合《电子签名法》。 -->
        </td>
    </tr>

    <tr>
        <td id=Page>
            <table width="758" border="0" cellspacing="0" cellpadding="0" align="center" bgcolor="ffffff" style="align:center">
                <tr><td height="50" align="center" valign="bottom"><font color="red" size="6"><b>订购合同<b></font></td></tr>
                <tr><td height="50" align="center"><div>合同编号:KG2016093001</div>
                    <div id="ser_box">
                        <input type="search" id="ipt" />
                        <span><input id="sousuo" pos="pos0" value="搜索签章" class="s_btn" type="submit"></span>
                    </div>

                    <div id="bot_box">
                        <ul id="oul"></ul>
                    </div>
                </td></tr>
                <tr>
                    <td>
                        <table width="600" border="0" cellspacing="0" cellpadding="0" align="center" >
                            <tr>
                                <td style="border-bottom:1px solid; border-color:#ff0000" nowrap>
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr height="30">
                                            <td width=96 align=left><font color="red">甲方：</font></td>
                                            <td ><input type="text" id="item1" kg-desc="保护项1" value="同创软件公司">  </td>

                                            <td width=96  align=right><font color="red">乙方：</font></td>

                                            <td ><input type="text" id="item2" kg-desc="保护项2" value="金格科技有限公司"></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td style="border-bottom:1px solid; border-color:#ff0000" height="260" nowrap >
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" height="100%">
                                        <tr>
                                            <td valign="top"><font color="red" >合作内容：</font>&nbsp;
                                        </tr>
                                        <tr>
                                            <td height="100%">
		<textarea rows="" cols="" id="item3" kg-desc="保护项3" style="overflow:auto;border: 0;width:85%;height:90%;font-size: 14px; padding-left:80px; line-height: 2.5">根据《合同法》及有关规定，经双方协商，达成一致，签订本合同。
一、甲方委托乙方制作无纸化办公系列产品。
二、具体规格型号、数量、单价及价格详见附后清单。
三、交货时间：60工作日
四、验收期限：20工作日。
五、双方在合同上电子签字盖章后立即生效。</textarea>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            <tr>

                            <tr>

                                <td  height="250">

                                    <table width="100%" height="250" border="0" cellspacing="0" cellpadding="0" align="center"style=" border-bottom:1px solid; border-color:#ff0000;position:relative;">
                                        <tr height="250" style="position:absolute">
                                            <td width=100 valign="top" ><font color="red" >电子签章：</font> </td>
                                            <td width=200 height="250" style="border-bottom:1px solid; border-color:#ff0000;cursor:hand" valign="top">
                                                <div style="width: 200px; height: 250px; border: 0px black solid;margin:0 auto;position:absolute">
                                                    <div id="pos0"></div>
                                                </div>
                                            </td>
                                        </tr>
                                    </table>

                                </td>
                            </tr>

                            <tr>
                                <td style="border-bottom:1px solid; border-color:#ff0000" height="40" nowrap >
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" height="100%">
                                        <tr>
                                            <td width="100"  ><font color="red">&nbsp;产品名称：</font></td>
                                            <td><input type="text" id="CPMC" kg-desc="保护项1" value="iSignature电子签章系统">
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td style="border-bottom:1px solid; border-color:#ff0000" height="40" nowrap  >
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" height="100%" >
                                        <tr>
                                            <td width="100" ><font color="red">&nbsp;订购数量：</font></td>
                                            <td><input type="text" id="DGSL" value="53"> </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td style="border-bottom:1px solid; border-color:#ff0000" height="40" nowrap >
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" height="100%">
                                        <tr>
                                            <td width="100" ><font color="red">&nbsp;订购日期：</font></td>
                                            <td><input type="text" id="DGRQ" value="2016-10-18"> </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>


                            <tr>
                                <td style="border-bottom:1px solid; border-color:#ff0000" height="1" nowrap >
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr><td height="60">&nbsp;</td></tr>
            </table>

        </td>
        <td width=2></td>
    </tr>
    <tr>
        <td height=2></td>
        <td></td>
    </tr>

</table>


<script type="text/javascript">


    function verifySignature() {

        var invalidSignatureArray = Signature.verify();//返回无效签章
        if (invalidSignatureArray.length > 0) {
            for (var i = 0; i < invalidSignatureArray.length; i++) {
                var signature = invalidSignatureArray[i];
                //console.log(signature.modifiedItems);//获取修改的保护项
            }

        }else{
            Signature.alert("当前文档所有签章有效");
        }
    }
    function findUpdateSignature(){
        var updateList = Signature.updateList;
        jQuery('#pos2').html('<div>获取有更新的签章数据:</div>');
        for (var i = 0; i < updateList.length; i++) {
            var signature = updateList[i];
            jQuery('#pos2').append('<div>'+signature.getSignatureid()+":"+signature.getSignatureData()+'</div>');
        }
    }

    function findRemoveSignature(){
        var removeList = Signature.removeList;
        jQuery('#pos1').html('<div>获取删除的签章ID：</div>');
        for (var i = 0; i < removeList.length; i++) {
            var signatureid = removeList[i];
            jQuery('#pos1').append('<div>'+signatureid+'</div>');
        }
        console.log(jQuery('#pos1').html());
    }

    function showSignature() {
        Signature.show();
    }


    function hideSignature() {
        Signature.hide();
    }


    jQuery('.test').click(
        function() {
            var that = this;
            var posid = jQuery(that).attr('pos')
            //Signature.AXI('SetParamByName', 'AUTHORIZECODE','9C0B134E2727402E8F5AAE69A6EF0874', function(data){
            //alert(data);
            //	if(data){
            var signatureCreator = Signature.create();
            signatureCreator.run({
                protectedItems:[ 'item1', 'item2', 'item3'],//设置定位页面DOM的id，自动查找ID，自动获取保护DOM的kg-desc属性作为保护项描述，value属性为保护数据。不设置，表示不保护数据，签章永远有效。
                position: posid,//设置盖章定位dom的ID，必须设置
                okCall: function(fn, image) {//点击确定后的回调方法，this为签章对象 ,签章数据撤销时，将回调此方法，需要实现签章数据持久化（保存数据到后台数据库）,保存成功后必须回调fn(true/false)渲染签章到页面上
                    console.log("盖章ID："+this.getSignatureid());
                    console.log("盖章数据："+this.getSignatureData());

                    /* 	alert(image.name);
                        alert(image.imgdata);
                        alert(image.height);
                        alert(image.width); */

                    fn(true);
                },
                cancelCall : function() {//点击取消后的回调方法
                    console.log("取消！")
                }
            });
            //	}
            //});

        });

//    jQuery('.testSign').click(
//        function() {
//            var signatureCreator = Signature.create();
//            var that = this;
//            signatureCreator.run({
//                protectedItems:[ 'item1', 'item2', 'item3'],//设置定位页面DOM的id，自动查找ID，自动获取保护DOM的kg-desc属性作为保护项描述，value属性为保护数据。不设置，表示不保护数据，签章永远有效。
//                position: jQuery(that).attr('pos'),//设置盖章定位dom的ID，必须设置
//                autoCert:true,
//                okCall: function(fn) {//点击确定后的回调方法，this为签章对象 ,签章数据撤销时，将回调此方法，需要实现签章数据持久化（保存数据到后台数据库）,保存成功后必须回调fn(true/false)渲染签章到页面上
//                    console.log("盖章ID："+this.getSignatureid());
//                    console.log("盖章数据："+this.getSignatureData());
//                    fn(true);
//
//                },
//                cancelCall : function() {//点击取消后的回调方法
//                    console.log("取消！")
//                }
//            });
//        });
//
//    jQuery('.testHW').click(
//        function(){
//            var signatureCreator = Signature.create();
//            var that = this;
//
//            signatureCreator.handWriteDlg({
//                image_height: "6.7",
//                image_width: "5",
//                onBegin: function() {
//                    console.log('onbegin');
//                },
//                onEnd: function() {
//                    console.log('onEnd');
//                }
//            }, function(param){
//                //alert(param.imageData);
//                signatureCreator.runHW(param, {
//                    protectedItems:[ 'item1', 'item2', 'item3'],//设置定位页面DOM的id，自动查找ID，自动获取保护DOM的kg-desc属性作为保护项描述，value属性为保护数据。不设置，表示不保护数据，签章永远有效。
//                    position: jQuery(that).attr('pos'),//设置盖章定位dom的ID，必须设置
//                    okCall: function(fn) {//点击确定后的回调方法，this为签章对象 ,签章数据撤销时，将回调此方法，需要实现签章数据持久化（保存数据到后台数据库）,保存成功后必须回调fn(true/false)渲染签章到页面上
//                        console.log("盖章ID："+this.getSignatureid());
//                        console.log("盖章数据："+this.getSignatureData());
//                        fn(true);
//                    },
//                    cancelCall : function() {//点击取消后的回调方法
//                        console.log("取消！")
//                    }
//                });
//            });
//        }
//    );
//
//    jQuery('.testBC').click(
//        function(){
//            var signatureCreator = Signature.create();
//            var that = this;
//
//            signatureCreator.barCodeDlg({
//                image_height: "6.7",
//                image_width: "5",
//                content:"123"
//            }, function(param){
//                alert(param.imageData);
//                signatureCreator.runHW(param, {
//                    protectedItems:[ 'item1', 'item2', 'item3'],//设置定位页面DOM的id，自动查找ID，自动获取保护DOM的kg-desc属性作为保护项描述，value属性为保护数据。不设置，表示不保护数据，签章永远有效。
//                    position: jQuery(that).attr('pos'),//设置盖章定位dom的ID，必须设置
//                    okCall: function(fn) {//点击确定后的回调方法，this为签章对象 ,签章数据撤销时，将回调此方法，需要实现签章数据持久化（保存数据到后台数据库）,保存成功后必须回调fn(true/false)渲染签章到页面上
//                        console.log("盖章ID："+this.getSignatureid( ));
//                        console.log("盖章数据："+this.getSignatureData());
//                        fn(true);
//                    },
//                    cancelCall : function() {//点击取消后的回调方法
//                        console.log("取消！")
//                    }
//                });
//            });
//        }
//    );
//
//    jQuery('.testScanBC').click(
//        function(){
//            var signatureCreator = Signature.create();
//            var that = this;
//
//            signatureCreator.scanBCDlg({
//                image_height: "5",
//                image_width: "5",
//            }, function(param){
//                //alert(param.imageData);
//                signatureCreator.runHW(param, {
//                    protectedItems:[ 'item1', 'item2', 'item3'],//设置定位页面DOM的id，自动查找ID，自动获取保护DOM的kg-desc属性作为保护项描述，value属性为保护数据。不设置，表示不保护数据，签章永远有效。
//                    position: jQuery(that).attr('pos'),//设置盖章定位dom的ID，必须设置
//                    okCall: function(fn) {//点击确定后的回调方法，this为签章对象 ,签章数据撤销时，将回调此方法，需要实现签章数据持久化（保存数据到后台数据库）,保存成功后必须回调fn(true/false)渲染签章到页面上
//                        console.log("盖章ID："+this.getSignatureid( ));
//                        console.log("盖章数据："+this.getSignatureData());
//                        fn(true);
//                    },
//                    cancelCall : function() {//点击取消后的回调方法
//                        console.log("取消！")
//                    }
//                });
//            });
//        }
//    );
//
//    jQuery('.testSave').click(
//        function(){
//            var signatureCreator = Signature.create();
//            var that = this;
//            var list = Signature.list;
//            for ( var key in list) {
//                var tt = list[key];
//                console.log(key);
//                signatureCreator.saveSignature("KG2016093001", key, list[key].getSignatureData());
//            }
//            alert("签章保存成功！");
//        }
//
//    );
//
//    jQuery('.s_btn').click(
//        function(){
//            var signatureCreator = Signature.create();
//            var that = this;
//            signatureCreator.runSS({
//                protectedItems:[ 'item1', 'item2', 'item3'],//设置定位页面DOM的id，自动查找ID，自动获取保护DOM的kg-desc属性作为保护项描述，value属性为保护数据。不设置，表示不保护数据，签章永远有效。
//                position: jQuery(that).attr('pos'),//设置盖章定位dom的ID，必须设置
//                autoCert : false,
//                okCall: function(fn) {//点击确定后的回调方法，this为签章对象 ,签章数据撤销时，将回调此方法，需要实现签章数据持久化（保存数据到后台数据库）,保存成功后必须回调fn(true/false)渲染签章到页面上
//                    console.log("盖章ID："+this.getSignatureid());
//                    console.log("盖章数据："+this.getSignatureData());
//                    fn(true);
//                },
//                cancelCall : function() {//点击取消后的回调方法
//                    console.log("取消！")
//                },
//                beginCall: function(){
//                    console.log("begin");
//                },
//                endCall: function(){
//                    console.log("end");
//                }
//            }, getSeal());
//        }
//    );
    function takeCare(){
        Signature.alert('注意： 由于IE9及以下浏览器受限，请使用其他浏览器访问本页面，<br>如果需要IE访问，复制本页面目录和kinggrid文件夹至服务器端访问。');
    }


</script>
</body>
</html>

