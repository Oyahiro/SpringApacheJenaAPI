package com.ontology.project.controllers;

import com.ontology.project.services.JenaService;
import com.ontology.project.models.StandardRequest;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class MainController {

    private final JenaService jenaService;

    @Autowired
    public MainController(JenaService jenaService) {
        this.jenaService = jenaService;
    }

    @RequestMapping(value = "/execute",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<JSONObject> findAny(@RequestBody StandardRequest standardRequest) {
        return jenaService.generalExecute(standardRequest);
    }
}
