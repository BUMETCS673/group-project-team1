package edu.bu.metcs673.trackr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Main controller to launch the UI which is a React single page application.
 *
 * @author Jean Dorancy
 */
@Controller
public class FrontendController {

    /**
     * Index page returning the name of the index template.
     *
     * @return String template name.
     */
    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }
}
