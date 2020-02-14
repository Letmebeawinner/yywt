if(!window['console']){//IE8，没有改对象，创建一个对象
    window['console'] = {log: function(){}}
}

function delCB(signatureid, signData){
    console.log("delCB");
    if(Signature.list != null && Signature.list[signatureid] != null){
        // var signatureCreator = Signature.create();
        // signatureCreator.removeSignature(signData.documentid, signatureid);
        deleteSignature(signatureid, signData);
    }
    return true;
}


window.onload = function(){
    // var signs =[
    //     {
    //         extra:{
    //             icon_move: function(){
    //                 return false;
    //             }
    //         },
    //         signatureid:'151427127312770646',
    //         signatureData: 'eyJ0aW1lc3RhbXAiOnsidGltZSI6MTUxNDI3MTI3MjkzNH0sImFwcG5hbWUiOiJNb3ppbGxhLzUuMCAoV2luZG93cyBOVCA2LjE7IFdPVzY0OyBydjo0OS4wKSBHZWNrby8yMDEwMDEwMSBGaXJlZm94LzQ5LjAiLCJkb2N1bWVudGlkIjoiMjEiLCJkb2N1bWVudG5hbWUiOiLpg6jpl6jpooblr7zlrqHmibkiLCJtb3ZlYWJsZSI6dHJ1ZSwia2V5c24iOiIiLCJvcmduYW1lIjoi6YeR5qC856eR5oqA5LqR562+56ug5bmz5Y+w5ryU56S65Y2V5L2NIiwidXNlcm5hbWUiOiLph5HmoLzmvJTnpLrnlKjmiLciLCJzZWFsIjp7ImVuZGF0YSI6IiIsImhlaWdodCI6IjQuMDAiLCJpbWdkYXRhIjoiaDBSQ05kPUJ0Ynl3QW1vZnZTWmFQSWdZL0grcWMzdU1lejE4V2xVS1hHMnNWazZFeDRuaUxKOWpyT1Q1cDdGUURTTGw9b0Nsemx4Ullob0FoaGZwQ2hRcGF0RXAxaGZwNEFmN3ZQZjdJM1E3aklNN3hjZkZ2V2ZGY3NFRjJXTUZrc01Rb2lFUTE5RVFGUWVoaGhSQk8waE5oaGhyaHdoaGhoaFJZaGJjaGhoYUZMQWxiMjVMclQ1TGd0VWhYems5bktnMTJzVW5zdVpoZWlqU2tubjAxNktpRVFUUUNyTi9WZGV1QkJZQ2JxZHQvdjJtTFpXdm5LbXpWMktOdFBoPWVIPWxBQ0tMQ3pySDlpUC9BOGM4b3M0b2R0N3pydDB1dWFDQlB5eGtKUz1sT3oxRzVTaFN5eWxMN1J4U2R6L3VQZGU0RW1lbWpmaFZoUnZybThSazZJSXVJbGg5U21VMWh3djlFS3YxR2hDT2NZOC9Ocyszc1JDYzBVNG9VeVdnTWZ4MWptY1I1YWVpdEFPWFlSeD0xPUtwaGJHalA9dHlUZFR2Smhjd3dmNUFKaEZiUWRoMGpCMVdDaHhyYkpqT1JMbWcvQTVLOGZzamVuenRBZk5qck91Q2hvelpTUlZiaXBvcFJ0aCtTZDhkVm5tUkhDdHM1bUdaOEFjV1JlaGZGQ1I9RlZPTXpJK1hoQ2F2SlR0STRoWGIxWVowT2h1S3YvaEo3PUlOTEFuTW9oVk56MGJDZTRtUkVSdE5tMDl6RmxtaGVoUExBbHBDSmk9VXpUdG5LPXZlTnZBZmVlckEwaGFIQVl2QlJ2WmNKd0pmL0dtTm5HVFhITDRSRi9qY0wvZDY9aHAxcFdGVmV5UmVMeTJhM0dkWHpHcWw2PXZyVnBXWnhzaS95RXZ5QU10bVJjWG9DZ3o3OEFONDhob2I0Q1o0MWZ1Tkp5ZDFVR2hCWDlLazFZWkY9aEErdWdSVkNiM1l2bWF2YXNNVm9BdEd1MjQzVFV4MG5odGRVNjhVaEZMNEdiSmlIPXp0YzAvSU5SQmZ0bXJTcUxSWDRydHlwZk9pWXpYckt0aGpxZGN6d1poaEFVVj1vajJUSGF5VzdVbCt4UkxuR2s3Um8yY1k3Z29DRjBrM0t2cVlkZTB0UjdJZ3FjTU5kekxiWGdUWXpKUytTeVJtZXEzM2xjWk5oS1VkZVBDTktZPS95UEN5QUgzUGl5QmFuMG5LSmJhL0MxTml6ZUpydT01cVhQODEwMnUrPVpXSjdKVjYwQlRQTD1lSEYwYW9XMEk5aGhIOUE9YXcwR3RjMzJ6Q1BTVC92aHlBZCtseD1pZExvQndTZGRUNFhPdG5tYXN4ZHh0eElsZEkvZ0ZOTGhiTk5XWmhnWmVVNzFBPVowVkNHemhBOD1ncnJLU0hnWGFVMFcwMWN1K1NhVEhhSmdsSVpRQlcwZW44L2JOMlBVaUtDR3ZFVWlaaHFoVzN5QXR2OTNxSWJiWkwrdVNLMWZiWW1YdDQyL2tQaHJoWHFiV2NQOGVPYmNoeFBWcWRUS0hIMldxc2RBZ2hoNHlHbmhPcWJXdmswbXVXR1o2RUZWQlhoU3M0Z08yZT12NGo4Z0hUaUxhaGtmTzlPaFVVeVQ0Q2V6elhJb1VTMm1xQjdsNlRlN3hKNWg4VFlLbHNWUjg2ZEcvdDcwbWFhTks0SDkrVmNnQ2picjk2dGkxODV3ZTluSTFSbXRmZlhjQXkwTFhnTzRkeWt4c0F4M0dKNkxUTGZLSWlkeDI5ZWNvemhJQTNrWDdQMHM5VDRCcnZmUVliZVk0VTVvTmZSPVpDOHdyWVI9PVp5L28wU04wYW1nZXh6OFVCNENXMjBpYWp2bDhiUGROa2VQcG41U1J0eXpOUnFPZzFVYWNvMjVTYitJWUN3TFU5YzZUaTB4L3prYmtXUHJtZnlMdD1WUmpteVNxZlNwcVpTNXRNaGJWTHR1elV5N3d0ZEwwQ2h1aD1McUo5OFN6STVSZWQxTmJjMnhLV2MvbC9rUTNxbU5CZHJ6Z3dGeUplS209PWkzR1BKTHhWbGVoWld5eDlQQ250TGFmWTNTRnVMZlNmSz12MTlzWklxYytYa2F2b2gxQ1lIK09OYlVPY01qPVl1aXFoWWJrVUMvUmJ4WlMyenNJcjBibGhpek1oTlBUbXpGMzViRUl1cG5nb2x0ZU5XL0E4enpXVmMxa3k0Q0EyNkgrN3p2clc1cHpDPWxJVm05azZFMHBIQUNkeG5LSFBha29UYTQ0a2h6OHloR3ZBb2taYmYxVGtNTkYySlIvZlZPWmFQTExTZjFXdHBacENsaGVxNGU4MVp2c1RyeU1oUlgxNk1zcXFuMVM4TjU5ckhFaGhubmZBZzhiQ1ZSMXVIMVRqTklzWVRDK040YndvL2RpSFNvTE9HWG4xaVV4aG1tZlJ3VS9hVXU0Qk92d0dnVkdQZmVYVlVWbjhIb2k1UnZxZlZIcmUreExCRi8yL2E9cTNId2FrZVJ4dnFwV2MwdFp4ZU5hME5ZeHN1cHp6QUU9WGdRMm1oYnhUdmVDb2dHcmJtV05xcGNzUkkrWmFsPXc1U1d4NFZrd2NpVlBoSXZqQmN0WVROMDQxdGFoOE1XTEJMUTJNPVJ6U2xXODVhNGhUTUFDaDlTaHlhdUt6Z29SdFo0WHlYbmViNkh0ZDBhbzBoYk1TVjhiWno0Q3YxTlNjdUh5R2RnPVowVXhoWk5hUHB5eDdkMlpOZk9oTmRQPS9lbE9CUHR6aE5YeEFma2VoYXZjU1JOT0toNDh5dWNlbDVaeFZxUlVLdGZBOENVMDZySG1YTHpQTEUwQnRtT25rU0JXMTFXZzlTVmduQ0lBY0E4bytDUDF5czA2Q0F2bmN3QUdHY0tVcFR1U21Zb3NIMGxQNFBybFdWNUFVcU1DK2k9UDJaeVA1U1pVakZwR1BodjAxPU1sOGg5ZXh4bmVRck91VmxGTUJsbFM0aEV3SVZYR1hZVHRHMUtHTkVJdDlDbWt1VjROMlVtYk9yQ3RsclFyU2F2eEVhWlRxaGgwbXRQbXJHd0NidDhzWGVkYi92MmtnOXRyVGMzVVV1VGFOb3YyV0tSRkdONzAvMFdOZTgxbjBBZDloNmZJTWtiVWFGZWNJY1JYTjFlR0JBZHF2YTFlN05XT0dkYz0vd0NieXdLNWJQME9TK2FpUUtUM1pTMHVrPWlONmFmWTdoSm1menJQU0hLWkdaRjd5YW43U1p2YXdYZT09MzBlL1RRVk51WWJDdGpIK3pQK0tXWFpWVFRBSGphd0JIUjFTdEdPTklPVEN2dG1kMGpXTG5GYXN2Z089WWxtcys3cytKNGNjbXBYWXFqa0hnc2w4Wloxaz1nT2VMaXZubGg0bFBiUUNGTjNhMC82Y3dMdEo2QmJtVXZoWHJJOE1RU1JUY0VoK3l3ZWVkMjRaSmI5dTFaSk9SaEFOVVZMa1dtZnl6WTNHMDQ4ejJUaEdtRVAxZ3Brc2pnZlBMT0x6aXp0ZjlkR2NRNWhTZGhlKzU2RWs9bEpIVFVobTUzNzJSQzQxSWdlcUZ2OFRqOWxwPTZUeXNyWjRWemRqNS9CYVV0T1lKTnBuPWZTRTEwUE05ZXJXL1YzMXlsSkx3V3hBd2I1YkUvNzh4WjR1TEZBU2hMYkY9MzBrMzdnMnpIQy85VlBWT0kydlY0YmV5eEI5VUpQd3dZaUNySjR0V2daNGprWmh4cFV5cjBNL25MTE1iY0ZBenVGMUVMOGV5R25zMjJ2T0M2d25pT3ZkZEJRdEtKYzhZSDg1Rkc5R3djMUh6d0F4Y2gzZUVvdVR3clIvaCtPQz1lK2R4bXpFRlJFMjE3ekc3NHZDZUhHYVlQd2VmUmlSbXFHdE00MVVxWCs9MWdXTGJ5bWlXQUFlMXVzYi9obHpBYnNodDZuc2FSYW49NVh2ZkZ2M3RhQj12cldzeWduSFNhZ3ZGdCs2aHIwWktBYkJJNkpMOXYwQm8yNHFwRzNJVXk9K2FCazBkZytkK2VJZGFxT1JuYkVJZHBvMUN5bDMwUmwzRkxOYlp4VUhaRUp5dUE4MUxOVWh4UFdqQm5kbVAvVXhhbUd0YmdNaU8wMmtveFlNUHJkZG9mWlBjQUtuOUFsZDJLS3VWZy8xa1JONkZnbEI4Yi8xQjQ9cVNWdFE3TXlIWVZ0dVUxWklJZjk4MUltZz1lOExNdWhBWklreXlFQlNDb2VpTXFZUmJXb0xtVm9jaXl6TFI0SDcxTFVhRjNhQVN4clg5aXduMz01YVduZmRVZVVuditGRUZDTjhCNk1abm5BeWtuOUFLdHpmTnkrd04xTWVkZGFBSDZWZGhheFFNeUI2UzhoeTczaVBBclBvbWh2KytXOVFzd1c3aU8yc3docHJBWVp4K0p4bS9JSzRmQmg2UzlhN3RlMDNwYkZwaWEzMlBhcFFjUElzT2VPcjhqSC8yQ3JKaU01L04rdXZwbT1pNkZsdzVkVzd3aEtoUGNZQ2VpWGlhSDRJUEpjcEYyUndKWTh1ellsZzdBMDNXdlpNQW4vOHRqYkppaHZXPTNFeFgzZVdlcmltUUlPdXJRUmcvSz1RZm13NUFBNVYyeVFIcUhFQmwranZnblc4T2s1QTVXWUc1OHhWa2VTPUlzYjVlblN3WDZ2KzFTM3RpeS9CRTR4UWUzPWJTTlBBMHY2cjFMckNMR01KTzgvQ0VxS2NpZE16ZktPZklUY2NudHI2UlNodjBRUThGS0MybFhjTVphYk40PVlRS3dRQ0hJanFyWEluZGVkdUlPanNXU052NG1QT2VwMHlLSlNQOHJ4SWhkWENQbVpaWXZnNzF2TEJIUHo2Z05SY1MyaHZWTlNsS2wwcFBuUmphSVlSTTNDWmZTOWM2SDE2d2NockFjQnRjdFhkUnRST3Vkcmg1ZWFNTldyYlV2dkE9TGlFcmxKSTlPeE5WSVl0bCtkTFpVMHR1TE4vc29COUd4cjBYdDBvYlgwYkJVZE5JMHZVeHhTZ2NrZGRaV0tGM1c9bFFtWGRJbz14TFdZQk1lZW5rYm5pWW1kMFFyakg4UlIxSTRSZWJ1UnBGbmRvZ0xqbEU2aEVjU0J1cVZhWVo3bHpTbHIza2RyMzhtck1LYnQ4OGVDWTR6OHZoZS96UHgvMWNlZEZvVTBvTFBZd3IvdFphRmhlcVB4RUtjL0FHWGhDRm4yQUMxZkFJQ0ZOSjNmdlhJNFJ0VmR0eFo5aElNdGVkVWxoaElLQWQ0aC84aFcwWjNYSFJxVklGM3cxaGY5aFVrc2NkK3NoaDBNaFYwZ2hoNGloMFJsaGgvbmhoNVI9dzJGUGhSZWhac0xoYmtiPT1DRnRBVUJlUkZkL2hWZ2VoY0UzTUdoZXlYZGhSWlpoPVVXM3o9YmJTc1V0dHdMTmhqWGVoNDQxb0hFZEl1bU5hbkNlPWppSD01Z3pXS2dldDRHZTVwRmVFbmplZWlHZTVpWi89QlpoWGdzaHpFQmUwSFZ5dlZOMUFMaVJ2bmUxbWs1MUFpdi84eDIvemlYUm13RWhXZ2VoUzRUTi9xZXlvMEFVQUNZUndiQ1BuNHRkWU9GMWJscXliOWl2VXF2aFppcnRhMENXaDBxUlp3cj1BSnkxZi9lUmZxUlJabUN0VUFXS0ZodnFoOFJoNGdreWU4d3BoSHlCU3RsMkphUEhHUjBWMVdjRU5NMGY0MGRlbmI9d1hXTTdyUnoybGxIVXIwTUZTMUFPeGU9dUdOOTZHMVczbE4zay96dUtQY3djej1Vakd1TW1uPTVzSVdZV095QjlIbE5DZTN0UUpsT1ZqPWhYUC9wQnJ2SEI5V2RseGNicWlsSmQ0QktSTzA2eFVaL3lPQ1JVZ0NPS2JSVEVlVXdFeHFzT3RlSGNIVWdBclUzc1pVL2NoVVN4UlVzaWgvOWlPeldjOS9ybEhneVRITitZR01jSThSNTZsVThVMFU2SHhVTzBZUlhxT2xQLzRsUXdVMzBpelVRZ2lVSEt2MEZlTFJmQz1LQU1qQ1BjZTBLQWVSeUtnPWN0T3pzNGIwZ2RoMDZ3SGhnSGgwU2JhS2ZQV0taWDkwSStoS2xVZUt1NkhCM2hIVEhPT2hoZnlaSXJPZWJpTDl2dDNyZC84cGJwbFd0Y1NoaGg1IiwiaW1nZXh0IjoiLmdpZiIsImltZ3RhZyI6IjAiLCJzaWdubmFtZSI6Iua1i+ivleeroCIsInNpZ25zbiI6ImgwUkNOZD1CdGJ5d0Ftb2Z2U1phUElnWS9IK3FjM3VNZXoxOFdsVUtYRzJzVms2RXg0bmlMSjlqck9UNXA3RlFEIiwic3RhcnRkYXRhIjoiIiwidXNlcm5hbWUiOiLph5HmoLzmvJTnpLrnlKjmiLciLCJ3aWR0aCI6IjQuMDAifSwicHJvdGVjdGVkRGF0YSI6W10sInNlYWxUeXBlIjoiY2xpZW50IiwidmVyIjp7Im5hbWUiOiIxLjAuMjAiLCJjb2RlIjoxMDB9LCJwb3NpdGlvbiI6eyJwb3MwIjp7fX0sInRpbWVpZCI6MTUxNDI3MTI3MzEyN30='
    //     }
    // ];
    // Signature.loadSignatures(signs);

    initSignature(id, "领导审批");
    jQuery.getJSON(contextPath + "/admin/oa/ajax/signature/get.json", {documentId: id,documentType: type}, function(result) {
        var signs = result.data;
        var signdata = new Array();
        if (signs.length == 0) {
            return;
        }
        for(var i=0;i<signs.length;i++){
            var map = {};
            map.signatureid = signs[i]["signatureId"];
            map.signatureData = signs[i]["signatureData"];
            map.extra = {
                icon_move: function() {
                    return false;
                }
            };
            signdata.push(map);
        }

        Signature.loadSignatures(signdata);

        Signature.bind({
            remove:function(fn){//签章数据撤销时，将回调此方法，需要实现签章数据持久化（保存数据到后台数据库）,
                console.log('获取删除的签章ID：'+this.getSignatureid());
                deleteSignature(this.getSignatureid());
                fn(true);//保存成功后必须回调fn(true/false)传入true/false分别表示保存成功和失败
            },
            update:function(fn){//签章数据有变动时，将回调此方法，需要实现签章数据持久化（保存数据到后台数据库）,执行后必须回调fn(true/false)，传入true/false分别表示保存成功和失败
                console.log('获取更新的签章ID：'+ this.getSignatureid());
                console.log('获取更新的签章数据：'+ this.getSignatureData());
                updateSignature(this.getSignatureid(), this.getSignatureData());
                fn(true);
            }
        });

    });

    window.onbeforeunload = onbeforeunload_handler;
    window.onunload = onunload_handler;
    function onbeforeunload_handler() {

    }
    function onunload_handler() {
        Signature.clearRPW();
    }
}

function initSignature(documentId, documentName) {
    Signature.init({//初始化属性
        clientConfig:{//初始化客户端参数
            'SOFTTYPE':'0'//0为：标准版， 1：网络版
        },
        delCallBack: delCB,
        imgtag: 0, //签章类型：0：无; 1:公章; 2:私章; 3:法人章; 4:法人签名; 5:手写签名
        valid : false,    //签章和证书有效期判断， 缺省不做判断
        icon_move : true, //移动签章按钮隐藏显示，缺省显示
        icon_remove : true, //撤销签章按钮隐藏显示，缺省显示
        icon_sign : false, //数字签名按钮隐藏显示，缺省显示
        icon_signverify : false, //签名验证按钮隐藏显示，缺省显示
        icon_sealinfo : false, //签章验证按钮隐藏显示，缺省显示
        certType : 'client',//设置证书在签章服务器
        sealType : 'client',//设置印章从签章服务器取
        // serverUrl : 'http://192.168.0.130:8080/iSignatureHTML5',
        documentid: documentId,//设置文档ID
        documentname: documentName,//设置文档名称
        pw_timeout:'s1800' //s：秒；h:小时；d:天
    })
}

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


function clickSignature(that, posId) {
    var signatureCreator = Signature.create();
    signatureCreator.run({
        position: posId,//设置盖章定位dom的ID，必须设置
        okCall: function (fn, image) {//点击确定后的回调方法，this为签章对象 ,签章数据撤销时，将回调此方法，需要实现签章数据持久化（保存数据到后台数据库）,保存成功后必须回调fn(true/false)渲染签章到页面上
            console.log("盖章ID：" + this.getSignatureid());
            console.log("盖章数据：" + this.getSignatureData());
            jQuery("#isSignature").val("ok");
            // saveSignature(this.getSignatureid(), this.getSignatureData());
            fn(true);
        },
        cancelCall: function () {//点击取消后的回调方法
            console.log("取消！");
            jQuery("#isSignature").val("notOk");
        }
    });

}

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

function saveSignature(signatureId, signatureData) {
    jQuery.ajax({
        url: contextPath + "/admin/oa/signature/save.json",
        data: {
            "signature.documentId": id,
            "signature.signatureId": signatureId,
            "signature.signatureData": signatureData,
            "signature.documentType": type
        },
        type: "post",
        datType: "json",
        cache: false,
        success: function() {

        }
    });
}

function updateSignature(signatureId, signatureData) {
    jQuery.ajax({
        url: contextPath + "/admin/oa/signature/update.json",
        data: {
            "signature.documentId": id,
            "signature.signatureId": signatureId,
            "signature.signatureData": signatureData,
            "signature.documentType": type
        },
        type: "post",
        datType: "json",
        cache: false,
        success: function() {

        }
    });
}

function deleteSignature(signatureId) {
    jQuery.ajax({
        url: contextPath + "/admin/oa/signature/delete.json",
        data: {
            "signature.documentId": id,
            "signature.signatureId": signatureId,
            "signature.status": 1,
            "signature.documentType": type
        },
        type: "post",
        datType: "json",
        cache: false,
        success: function() {

        }
    });
}

function printDocument() {
    jQuery(".printHide").hide();
    jQuery(".header").hide();
    jQuery(".iconmenu").hide();
    jQuery(".centercontent").css("marginLeft","0");
    jQuery("textarea").css("min-height","102px");
    //jQuery("td").css("border","1px solid #999");


    // return;
    window.print();
    jQuery(".printHide").show();
    jQuery(".header").show();
    jQuery(".iconmenu").show();
    jQuery(".centercontent").css("marginLeft","181px");
    //jQuery("td").css("border","1px solid #ddd");
    jQuery("textarea").css("height", "100%");

}
