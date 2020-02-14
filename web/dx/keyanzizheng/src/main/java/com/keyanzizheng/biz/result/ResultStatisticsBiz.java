package com.keyanzizheng.biz.result;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.DateUtils;
import com.a_268.base.util.StringUtils;
import com.keyanzizheng.dao.result.ResultStatisticsDao;
import com.keyanzizheng.entity.result.Result;
import com.keyanzizheng.entity.result.ResultStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 成果统计Biz
 *
 * @author 268
 */
@Service
public class ResultStatisticsBiz extends BaseBiz<ResultStatistics, ResultStatisticsDao> {
    @Autowired
    private ResultBiz resultBiz;

    /**
     * 成果统计
     *
     */
    public void resultStatistics(){
        List<ResultStatistics> resultStatisticsList = this.findAll();
        if(!CollectionUtils.isEmpty(resultStatisticsList)){
            for(ResultStatistics resultStatistics:resultStatisticsList){
                this.delete(resultStatistics);
            }
        }
        for(int a=1;a<=2;a++){
            String sqlString=" status!=2 and resultType="+a;
            sqlString+=" order by createTime desc";
            List<Result> resultList = resultBiz.find(null,sqlString);
            if(!CollectionUtils.isEmpty(resultList)){
                for(int i=0;i<resultList.size();i++){
                    String date="";
                    if(i>0){
                        String date1=DateUtils.format(resultList.get(i).getCreateTime(),"yyyy-MM");
                        String date2=DateUtils.format(resultList.get(i-1).getCreateTime(),"yyyy-MM");
                        if(date1.equals(date2)){
                            continue;
                        }else{
                            date=date1;
                        }
                    }else{
                        date=DateUtils.format(resultList.get(i).getCreateTime(),"yyyy-MM");
                    }
                    for(int j=1;j<=3;j++){
                        ResultStatistics resultStatistics=new ResultStatistics();
                        resultStatistics.setDate(date);
                        Integer declareCount= resultBiz.count(" status!=2 and resultForm="+j+" and left(createTime,7) like '"+date+"' and resultType="+a);
                        Integer approvalCount=resultBiz.count(" status!=2 and intoStorage=2 and resultForm="+j+" and left(createTime,7) like '"+date+"' and resultType="+a);
                        Integer endCount=resultBiz.count(" status!=2 and passStatus=4 and resultForm="+j+" and left(createTime,7) like '"+date+"' and resultType="+a);
                        resultStatistics.setResultForm(j);
                        resultStatistics.setResultType(a);
                        resultStatistics.setDeclareCount(declareCount);
                        resultStatistics.setApprovalCount(approvalCount);
                        resultStatistics.setDisApprovalCount(declareCount-approvalCount);
                        resultStatistics.setEndCount(endCount);
                        resultStatistics.setDisEndCount(declareCount-endCount);
                        this.save(resultStatistics);
                    }
                }
            }
        }
    }

    /**
     * 成果分页统计
     *
     */
    public List<ResultStatistics> getResultStatistics(Integer type,Pagination pagination ,String year,String month){
        String sqlString=" 1=1 ";
        if(type!=null && type>0){
            sqlString+=" and resultType="+type;
        }
        if(!StringUtils.isTrimEmpty(year)){
            if(!StringUtils.isTrimEmpty(month)){
                sqlString+=" and date like '"+year+"-"+month+"'";
            }else{
                sqlString+=" and left(date,4) like '"+year+"'";
            }
        }
        return this.find(pagination,sqlString);
    }

    /**
     * 成果按年统计
     *
     */
    public ResultStatistics getResultStatisticsByYear(Integer type,String year,Integer resultForm){
        ResultStatistics resultStatistics=new ResultStatistics();
        resultStatistics.setResultForm(resultForm);
        resultStatistics.setDate(year);
        resultStatistics.setEndCount(0);
        resultStatistics.setDisEndCount(0);
        resultStatistics.setDeclareCount(0);
        resultStatistics.setApprovalCount(0);
        resultStatistics.setDisApprovalCount(0);
        String sqlString=" 1=1 and resultType="+type;
        sqlString+=" and left(date,4) like '"+year+"' and resultForm="+resultForm;
        List<ResultStatistics> resultStatisticsList = this.find(null,sqlString);
        if(!CollectionUtils.isEmpty(resultStatisticsList)){
            for(ResultStatistics resultStatistics1:resultStatisticsList){
                resultStatistics.setEndCount(resultStatistics.getEndCount()+resultStatistics1.getEndCount());
                resultStatistics.setDisEndCount(resultStatistics.getDisEndCount()+resultStatistics1.getDisEndCount());
                resultStatistics.setDeclareCount(resultStatistics.getDeclareCount()+resultStatistics1.getDeclareCount());
                resultStatistics.setApprovalCount(resultStatistics.getApprovalCount()+resultStatistics1.getApprovalCount());
                resultStatistics.setDisApprovalCount(resultStatistics.getDisApprovalCount()+resultStatistics1.getDisApprovalCount());
            }
        }
        return resultStatistics;
    }
}
