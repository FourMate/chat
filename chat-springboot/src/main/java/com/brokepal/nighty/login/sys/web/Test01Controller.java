package com.brokepal.nighty.login.sys.web;

import com.brokepal.nighty.login.core.dto.OperationResult;
import com.brokepal.nighty.login.core.exception.RequestParamException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by chenchao on 2018/5/9.
 */
@Controller
@RequestMapping(value="")
public class Test01Controller {
    @RequestMapping(value="/static/test1")
    @ResponseBody
    public OperationResult test1(HttpServletRequest req, HttpServletResponse resp) throws RequestParamException {
        return OperationResult.buildSuccessResult("hello");
    }
    @RequestMapping(value="/test2")
    @ResponseBody
    public OperationResult test2(HttpServletRequest req, HttpServletResponse resp) throws RequestParamException {
        return OperationResult.buildSuccessResult("hello world");
    }
}
