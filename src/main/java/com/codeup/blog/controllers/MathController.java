package com.codeup.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MathController {


//  ADD:
    @GetMapping("/add/{num1}/and/{num2}")
    @ResponseBody
    public int add(@PathVariable int num1, @PathVariable int num2) {
        return num1 + num2;
    }

// Example of using the String return type:
//    ADD:
//    @GetMapping("/add/{num1}/and/{num2}")
//    @ResponseBody
//    public String add(@PathVariable int num1, @PathVariable int num2) {
//        int sum = num1 + num2;
//        return String.valueOf(sum);
//    }


//  SUBTRACT:
    @GetMapping("/subtract/{num1}/from/{num2}")
    @ResponseBody
    public int subtract(@PathVariable int num1, @PathVariable int num2) {
        return num2 - num1;
    }


//  MULTIPLY:
    @GetMapping("/multiply/{num1}/and/{num2}")
    @ResponseBody
    public int multiply(@PathVariable int num1, @PathVariable int num2) {
        return num1 * num2;
    }


//  DIVIDE:
    @GetMapping("/divide/{num1}/by/{num2}")
    @ResponseBody
    public double divide(@PathVariable double num1, @PathVariable double num2) {
        return num1 / num2;
    }



//  closes the class:
}
