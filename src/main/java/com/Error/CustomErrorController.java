package com.Error;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletResponse response) {
        // Check the response status
        if (response.getStatus() == HttpServletResponse.SC_NOT_FOUND) {
        	return "error/404";
        }
        return "error/500";
    }
 
    public String getErrorPath() {
    	return null;
    }
}
