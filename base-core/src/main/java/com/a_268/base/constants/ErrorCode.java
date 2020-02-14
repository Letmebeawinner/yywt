package com.a_268.base.constants;

/**
 * 错误码常量接口
 * @author s.li
 */
public interface ErrorCode {
	/**成功*/
	public static final String SUCCESS="0";
	/**系统错误*/
	public static final String ERROR_SYSTEM="10000";
	/**参数错误*/
	public static final String ERROR_PARAMETER="10001";
	/**参数验证错误*/
	public static final String ERROR_PARAMETER_VERIFY="10002";
	/**数据不存在*/
	public static final String ERROR_DATA_NULL="10003";
	/**数据错误*/
	public static final String ERROR_DATA="10004";
	/**参数值为空*/
	public static final String ERROR_PARAMETER_NULL="10005";
	/**邮箱号错误*/
	public static final String ERROR_EMAIL="10006";
	/**手机号错误*/
	public static final String ERROR_MOBILE="10007";

	///==========================================================
	/**系统错误默认提示信息*/
	public static final String SYS_ERROR_MSG="系统错误，请稍后再操作";
	/**操作成功默认提示信息*/
	public static final String SUCCESS_MSG="操作成功";
}
