package com.keduit.helloworld.error;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/error/*")
@Log4j2
public class ErrorPageController implements ErrorController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void error400() {
        log.info("ErrorPageController error400");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void error404() {
        log.info("ErrorPageController error404");
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void error500() {
        log.info("ErrorPageController error500");
    }
}

