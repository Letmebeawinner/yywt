package com.a_268.base.enums;

/**
 * 状态枚举类
 * @author s.li
 *
 */
public enum StateEnum {
	/**不可用*/
	NOT_AVAILABLE(0),
	/**可用*/
	AVAILABLE(1),
	/**删除*/
	DELETE(2);
	
	private int state;
	StateEnum(int state){
		this.state = state;
	}
	
	public int getState(){
		return this.state;
	}
	
	public void setState(int state){
		this.state = state;
	}
}
