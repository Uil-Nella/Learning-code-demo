package org.bugkilers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by liuxinyu on 15/4/21.
 */
@Controller
@RequestMapping("/test")
public class TestController {
    @RequestMapping("/test1")
    public void test1(){

    }

}
