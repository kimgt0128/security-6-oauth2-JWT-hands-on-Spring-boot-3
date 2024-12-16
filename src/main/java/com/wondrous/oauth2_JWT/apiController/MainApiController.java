package com.wondrous.oauth2_JWT.apiController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainApiController {

    @GetMapping("/")
    @ResponseBody
    public String mainApi() {
        return "main route";
    }
}
