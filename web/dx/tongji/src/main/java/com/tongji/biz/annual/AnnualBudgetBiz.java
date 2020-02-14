package com.tongji.biz.annual;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.tongji.biz.common.HqHessianService;
import com.tongji.biz.common.HrHessianService;
import com.tongji.dao.annual.AnnualBudgetDao;
import com.tongji.entity.annual.AnnualBudget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * 年度收支统计Biz
 *
 * @author sk
 * @since 2017-03-03
 */
@Service
public class AnnualBudgetBiz extends BaseBiz<AnnualBudget, AnnualBudgetDao> {

    @Autowired
    private HqHessianService hqHessianService;
    @Autowired
    private HrHessianService hrHessianService;

    /**
     * 查询年度收支情况
     *
     * @param pagination 分页
     * @return 年度收支情况
     * @since 2017-03-03
     */
    public List<AnnualBudget> listAnnualBudget(Pagination pagination) {
        generateLastYear();
        String where = " 1=1 ORDER BY createTime DESC";
        List<AnnualBudget> budgetList = this.find(pagination, where);
        if (ObjectUtils.isNotNull(budgetList)) {
            return budgetList;
        }
        return new LinkedList<>();
    }

    /**
     * 重新审计年度结余情况
     *
     * @param year 年份
     * @return 重新审计后的年度结余(绝对值)
     * @since 2017-03-06
     */
    public void redoneBalance(String year) {
        String where = " year='" + year + "'";
        List<AnnualBudget> list = this.find(null, where);
        if (ObjectUtils.isNotNull(list)) {
            AnnualBudget budget = list.get(0);
            /* 重新审计年度各项收入、支出 */
            computeAnnualBudget(budget, year);
            /* 重新审计年度总收入、总支出 */
            Double earning = budget.getEarning();
            Double expense = budget.getExpense();
            budget.setEarning(earning);
            budget.setExpense(expense);
            Double redoneBalance = earning - expense;
            budget.setRedoneBalanceType(redoneBalance < 0 ? 2 : 1);
            budget.setRedoneBalance(Math.abs(redoneBalance)); /* 数据库存绝对值 */
            this.update(budget);
        }
    }

    /**
     * 结算年度各项收入、支出
     *
     * @param year 年份
     * @return 年度收支
     * @since 2017-03-06
     */
    private void computeAnnualBudget(AnnualBudget budget, String year) {
        budget.setDorm(hqHessianService.getAnnualDormEarning(year));
        budget.setEnergy(hqHessianService.getAnnualEnergyExpense(year));
        budget.setSalary(hrHessianService.getAnnualSalaryExpense(year));
    }

    /**
     * 年初(1月份)生成去年的收支情况
     *
     * @since 2017-03-06
     */
    private void generateLastYear() {
        LocalDate localDate = LocalDate.now();
        if (1 == localDate.getMonthValue()) {
            String year = String.valueOf(localDate.getYear() - 1);
            String where = " year='" + year + "'";
            List<AnnualBudget> list = this.find(null, where);
            if (ObjectUtils.isNull(list)) {
                AnnualBudget budget = new AnnualBudget();
                computeAnnualBudget(budget, year);
                budget.fill();
                budget.setYear(year);
                this.save(budget);
            }
        }
    }
}
