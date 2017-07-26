package com.viseo.companion.controller;

import com.viseo.companion.domain.Mean;
import com.viseo.companion.service.MeanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class MeanController {

    @Autowired
    private MeanService meanService ;

    @CrossOrigin
    @RequestMapping(value = "${endpoint.addMean}", method = POST)
    public Mean addMean(@RequestBody Mean mean) {
        return meanService.addMean(mean);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.getMeans}", method = GET)
    public List<Mean> getMeans() {
        return meanService.getMeans();
    }
}
