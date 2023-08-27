package com.fox.cradle;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SPAController
{

    @GetMapping({"/signup", "/dashboard", "/signin"})
    public String forwardToReactApp() {
        return "forward:/";
    }

}
