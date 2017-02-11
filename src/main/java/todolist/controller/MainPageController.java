package todolist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by jeonghoon-kim on 2/11/17.
 */
@Controller
@RequestMapping(value = "/")
public class MainPageController {

    @RequestMapping(method = RequestMethod.GET)
    public String main() {
        return "index";
    }

}
