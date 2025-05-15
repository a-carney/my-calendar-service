package com.calendar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(value = {"", "/", "/calendar", "/contacts", "/locations" })
    public String index() {
        return "forward:/index.html";
    }
}
