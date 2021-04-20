package org.miage.courses.requesters;

import net.minidev.json.JSONObject;
import org.miage.courses.controllers.user.UserService;
import org.miage.courses.entities.card.Card;
import org.miage.courses.entities.user.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UsersServiceRequesterBean implements UsersServiceRequester {

    private RestTemplate restTemplate;

    public UsersServiceRequesterBean( RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public Card[] getUserCards(User user) {
        return restTemplate.getForObject("lb://users-service/api/users/" + user.getId() + "/cards", Card[].class);
    }

    @Override
    public void patchCardAmount(Card card) {
        JSONObject json = new JSONObject();
        json.put("amount", card.getAmount());
        HttpHeaders header =  new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(json.toString(), header);
        restTemplate.patchForObject("lb://users-service/api/cards/" + card.getId(), request, String.class);
    }
}
