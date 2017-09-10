package com.dailmer.microservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class RateController {

    @Value("${rate}")
    String rate;

    @Value("${lanecount}")
    String lanecount;

    @Value("${tollstart}")
    String tollStart;

    @Value("${environment}")
    String env;



//    @RequestMapping(value = "/rate", method = RequestMethod.GET)
    @GetMapping("/rate")
    public String getRate(){
        return String.format("rate is %s, lanecount is %s, tollStart is %s", rate, lanecount, tollStart);
    }


}
