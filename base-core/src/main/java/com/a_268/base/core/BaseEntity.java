package com.a_268.base.core;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Entity实体抽象基类
 * @author jingxue.chen
 */
public abstract class BaseEntity implements IEntity {

	private static final long serialVersionUID = 7777676040873093991L;

	/**
     * 主键ID
     */
    private Long id;

    /**
     * 数据状态
     */
    private Integer status;

    /**
     * 创建时间
     */

    private Timestamp createTime;

    /**
     * 更新时间
     */
    private Timestamp updateTime;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getCreateTime() {
        if(this.createTime==null){
            return new Timestamp(new Date().getTime());
        }
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        if(this.updateTime==null){
            return new Timestamp(new Date().getTime());
        }
        return this.updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BaseEntity other = (BaseEntity) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
