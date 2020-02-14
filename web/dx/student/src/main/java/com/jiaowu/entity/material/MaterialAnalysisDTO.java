package com.jiaowu.entity.material;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Created by 李帅雷 on 2017/10/23.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class MaterialAnalysisDTO extends MaterialAnalysis implements Serializable{
    private static final long serialVersionUID = -532746465187322342L;
    //班型名称
    private String classTypeName;
    //班次名称
    private String className;
}
