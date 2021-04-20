package org.miage.users.requesters;

import net.minidev.json.JSONObject;
import org.miage.users.entities.user.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CoursesServiceRequesterBean implements CoursesServiceRequester {

    private final RestTemplate restTemplate;

    public CoursesServiceRequesterBean(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void postUser(User user) {
            JSONObject json = new JSONObject();
            json.put("id", user.getId());
            HttpHeaders header =  new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<String>(json.toString(), header);
            restTemplate.postForEntity("lb://courses-service/api/users", request, String.class);
    }
}
