package com.wondrous.oauth2_JWT.apiController;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyApiController {

    @GetMapping("/my")
    @ResponseBody
    public String myApi() {
        return "my route";
    }
}
