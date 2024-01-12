package com.example.lucidity.controller.v1;


import com.example.lucidity.response.*;
import com.example.lucidity.services.impl.IEstimationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/estimation-service/v1")
public class EstimationController {

    @Autowired
    IEstimationService estimationService;


    @RequestMapping(value="/shortest-time", method= RequestMethod.GET)
    public
    @ResponseBody
    ApiResponse getShortestTime() {
        return new SuccessResponse(estimationService.getShortestTime(), "success", 200);
    }

}