package com.tracknme.challenge.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tracknme.challenge.domain.ViaCepDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ViaCepClient {


    private static final String URL = "viacep.com.br/ws/%s/json/";

    public ViaCepDTO findCep(String cep) {

        try{
        WebClient client = WebClient.create();

        WebClient.ResponseSpec responseSpec = client
                .get()
                .uri(String.format(URL, cep))
                .retrieve();

        String responseBody = responseSpec.bodyToMono(String.class).block();
            return new ObjectMapper().readValue(responseBody,ViaCepDTO.class);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
