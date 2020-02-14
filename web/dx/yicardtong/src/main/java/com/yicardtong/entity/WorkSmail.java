package com.yicardtong.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Created by caichenglong on 2017/10/29.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WorkSmail implements Serializable{

    private String Base_OperCode;

    private String base_Mail;

    private String base_Password;

    private String base_MP;


}
