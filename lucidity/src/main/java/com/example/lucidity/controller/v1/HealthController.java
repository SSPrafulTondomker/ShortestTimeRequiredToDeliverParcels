package com.example.lucidity.controller.v1;


import com.example.lucidity.response.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/health")
public class HealthController {

    @RequestMapping(value="/check", method= RequestMethod.GET)
    public
    @ResponseBody
    ApiResponse getHealth( @PathVariable(required = false) String id) {
        return new SuccessResponse("ok", "success", 200);
    }

}