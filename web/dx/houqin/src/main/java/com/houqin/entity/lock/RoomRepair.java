package com.houqin.entity.lock;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 房间维修状态
 *
 * @author YaoZhen
 * @date 05-14, 16:08, 2018.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoomRepair extends BaseEntity {

    /**
     * 房间ID
     */
    Long roomId;

    /**
     * 房间维修状态
     */
    Integer serviceStatus;
}
