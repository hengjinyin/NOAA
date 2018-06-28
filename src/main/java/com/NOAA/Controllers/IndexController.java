package com.NOAA.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//This file is useless.
@Controller
public class IndexController {
    @RequestMapping("/")
    public String showIndex(){
        return "index";
    }
}
