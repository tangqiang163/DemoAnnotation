package com.sixeco.order.base.constant;

/**
 * 返回值常量
 *
 * @author: Zhanghe
 * @date: 2019-03-28
 */
public class RtnConstant {

    /**
     * 返回值信息
     * @author: Zhanghe
     * @date: 2019/3/28
     */
    public static class Code {
        // 成功返回码
        public static final Integer SUCCESS_CODE = 0;

        // 参数缺失返回码
        public static final Integer PARAMS_LACK_CODE = 105;

        // 新增失败返回码
        public static final Integer INSERT_ERROR_CODE = 106;

        // 系统异常返回码
        public static final Integer SERVER_ERROR_CODE = 999;

        // 用户不存在返回码
        public static final Integer USER_NULL_CODE = 104;

        // 验证码错误返回码
        public static final Integer VALIDCODE_ERROR_CODE = 102;

        // 验证码失效返回码
        public static final Integer VALIDCODE_TIMEOUT_CODE = 103;

        // 密码错误返回码
        public static final Integer PASSWORD_ERROR_CODE = 101;

        // 参数错误
        public static final Integer PARAM_ERROR = 303;

        // 更新失败返回码
        public static final Integer UPDATE_ERROR_CODE = 500;

        public static final Integer UPDATE_PAPERNUM_FAIL = 201;

        // 重复提交
        public static final Integer REPEAT_SUBMIT = 108;

    }

    /**
     * 返回值信息
     * @author: Zhanghe
     * @date: 2019/3/28
     */
    public static class Msg {

        public static final String PARAM_ERROR = "参数错误";

        // 成功返回信息
        public static final String SUCCESS_MSG = "成功";

        // 参数缺失信息
        public static final String PARAMS_LACK_MSG = "参数缺失";

        // 新增失败信息
        public static final String INSERT_ERROR_MSG = "新增失败";

        // 系统异常返回信息
        public static final String SERVER_ERROR_MSG = "服务器异常";

        // 用户不存在返回信息
        public static final String USER_NULL_MSG = "手机号未注册";

        // 验证码错误返回信息
        public static final String VALIDCODE_ERROR_MSG = "验证码错误";

        // 验证码失效返回信息
        public static final String VALIDCODE_TIMEOUT_CODE = "验证码失效";

        // 密码错误返回信息
        public static final String PASSWORD_ERROR_MSG = "密码错误";

        // 修改失败信息
        public static final String UPDATE_ERROR_MSG = "修改失败";

        public static final String NOT_DELETED = "该数据无法删除";

        // 重复提交信息
        public static final String REPEAT_SUBMIT = "请求重复提交";

    }
}
