package com.tongji.entity.annual;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AnnualBudget extends BaseEntity {

    private static final long serialVersionUID = -9156960985515471006L;

    /* 年度 */
    private String year;

    /* 年度总收入 */
    private Double earning;

    /* 年度总支出 */
    private Double expense;

    /* 年度宿舍收入 */
    private Double dorm;

    /* 年度能源支出 */
    private Double energy;

    /* 年度薪资支出 */
    private Double salary;

    /* 年度结余 */
    private Double balance;

    /* 年度结余情况 1.年度结余≥0；2.年度结余＜0 */
    private Integer balanceType;

    /* 年度重新审计结余 */
    private Double redoneBalance;

    /* 年度重新审计结余情况 1.年度结余≥0；2.年度结余＜0 */
    private Integer redoneBalanceType;

    /* 填充 earning、expense等依赖其他字段的字段 */
    public void fill() {
        earning = getEarning();
        expense = getExpense();
        balance = earning - expense;
        balanceType = (balance < 0) ? 2 : 1;
        balance = Math.abs(balance); /* 数据库存绝对值 */
    }

    public Double getEarning() {
        return dorm;
    }

    public Double getExpense() {
        return energy + salary;
    }
}
