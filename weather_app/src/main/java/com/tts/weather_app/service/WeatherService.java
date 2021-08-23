package com.tts.weather_app.service;



import com.tts.weather_app.model.Response;
import com.tts.weather_app.model.ZipCode;
import com.tts.weather_app.repository.ZipCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// Service class handles all business logic (arithmetic, anything we resolve to a specific value) is handled by service
    // ex: if we need to add 2 numbers the arithmetic would reside in a service class
@Service
public class WeatherService {
    /* this is the method to get the weather information from the site */

    // this Annotation pulls a value from our application.properties (in this case the api_key)
    @Value("${api_key}")
    private String api_key;

    //@Autowired   // prototypical way to utilize packet injection in Java
    //ZipCodeRepostitory zipCodeRepostitory;

    // another style of dependency injection using a constructor to inject the dependency into Java
    ZipCodeRepository zipCodeRepository;
    public WeatherService(ZipCodeRepository zipCodeRepository) {
            this.zipCodeRepository = zipCodeRepository;
    }

    // setting up a method to handle a 'rested template'
    public Response getForecast(String zipCode) {
        String url = "http://api.openweathermap.org/data/2.5/weather?zip=" + zipCode + "&units=imperial&appid=" + api_key;

        //  RestTemplate  (it is the representation of your client)
        RestTemplate restTemplate = new RestTemplate();

        //set Try / Catch block for Error Handling
        try {
            zipCodeRepository.save(new ZipCode(zipCode));
            return restTemplate.getForObject(url, Response.class);
        } catch (HttpClientErrorException ex) {
            Response response = new Response();
            response.setName("error");
            return response;
        }

    }
    // Method to return last 10 zipcode searches
    // database query and data return
    public List<ZipCode> getLast10ZipCodes() {
        List<ZipCode> zipCodeList = (List<ZipCode>) zipCodeRepository.findAll();
        Collections.reverse(zipCodeList);
        return zipCodeList.stream().limit(10).collect(Collectors.toList());
    }

}

