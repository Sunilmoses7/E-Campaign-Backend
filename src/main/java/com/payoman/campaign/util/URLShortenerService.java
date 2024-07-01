package com.payoman.campaign.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class URLShortenerService {

    public static String shortenerUrl(String url) {
        log.info("Shortening the URL " + url);
        WebClient client = WebClient.create();
        Map<String, String> bodyMap = new HashMap();
        bodyMap.put("cdn_prefix", "d3hzdaoi0rwmg.cloudfront.net");
        bodyMap.put("url_long", url);
        JsonNode jsonObject = null;
        try {
            String response = client
                    .post()
                    .uri(new URI("https://d3hzdaoi0rwmg.cloudfront.net/admin_shrink_url"))
                    .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                    .accept(org.springframework.http.MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(bodyMap))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            ObjectMapper mapper = new ObjectMapper();
            jsonObject = mapper.readTree(response);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonObject.get("url_short").asText();
    }
}
