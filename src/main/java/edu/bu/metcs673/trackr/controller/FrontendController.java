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
     * Credit: Regex is from StackOverflow. There is also an alternative solution using filters which I may explore later.
     * Link: https://stackoverflow.com/questions/47689971/how-to-work-with-react-routers-and-spring-boot-controller
     *
     * @return String template name.
     */
    @RequestMapping(value = { "/", "/{x:[\\w\\-]+}", "/{x:^(?!api$).*$}/**/{y:[\\w\\-]+}" })
    public String index() {
        return "index";
    }
}
