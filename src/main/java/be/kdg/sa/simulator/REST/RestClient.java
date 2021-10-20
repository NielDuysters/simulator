package be.kdg.sa.simulator.REST;

import be.kdg.sa.simulator.domain.Flight;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class RestClient {
    private final RestTemplate restTemplate;

    private final String restUrl = "http://localhost:8080/api";

    @Autowired
    public RestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String createFlight() {
        System.out.println("[+] RUNNING TEST");

        try {
            Flight newFlight = new Flight();
            restTemplate.postForObject(restUrl + "/flights", "test", String.class);
            return "";
        } catch (RestClientException e) {
            // TODO exception handling
            throw e;
        }
    }

    /*
    public String createFlight(Flight flight) {
        String url = restUrl + "/flights";
        System.out.println("URL " + url);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        ObjectMapper oMapper = new ObjectMapper();
        Map<String, Object> map = oMapper.convertValue(flight, Map.class);

        map.forEach((key, value) -> System.out.println(key + ":" + value));
        ObjectMapper objectMapper = new ObjectMapper();

        String json = "";
        try {
            json = objectMapper.writeValueAsString(map);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpEntity<String> request =
                new HttpEntity<String>(json.toString(), headers);

        String response = this.restTemplate.postForObject(url, request, String.class);

        // check response status code
       /* if (response.getStatusCode() == HttpStatus.CREATED) {
            System.out.println("CREATED");
            return response.getBody();
        } else {
            return null;
        }*/
/*
        System.out.println("RESPONSE " + response);

        return response;
    }*/
}
