package com.tts.weather_app.controller;

import com.tts.weather_app.model.Request;
import com.tts.weather_app.model.Response;
import com.tts.weather_app.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

// we are linking this WeatherController (MVC Controller component of this project) directly linked to our templates
   // the templates are DYNAMIC. we will be utilizing ThymeLeaf as our Template Engine
   // ThymeLeaf - server-side template engine for web and standalone environments.
   // ThymeLeaf allows you to refer to your java code in HTML!!!!!
@Controller
public class WeatherController {

    @Autowired    // Autowired is 1 dependency injection type  -- this is setting WeatherController dependent on WeatherService
    private WeatherService weatherService;



    //    @GetMapping    // GetMapping - outputs to
    //    public String getIndex(Model model) {
    //      Response response = weatherService.getForecast("71106");
    //      model.addAttribute("data", response);            // attribute to be called upon later
    //      return "index.html";
    // }

    // new Mapping to handle the added Form on index
    @GetMapping
    public String getIndex(Model model) {
        model.addAttribute("request", new Request());
        model.addAttribute("history", weatherService.getLast10ZipCodes());
        return "index";
    }

    @PostMapping
    public String postIndex(Request request, Model model) {
        Response data = weatherService.getForecast(request.getZipCode());
        model.addAttribute("data", data);
        model.addAttribute("history", weatherService.getLast10ZipCodes());
        return "index";
    }
}
