package com.example.ApiService.messanger.http;

import com.example.ApiService.messanger.Messenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
@Profile({"default","dev"})
public class HttpMessenger implements Messenger {
    @Autowired
    private RestTemplate restTemplate;

    public void sendMessage(String message) {
        CompletableFuture<String> future = CompletableFuture.completedFuture(
                restTemplate.postForObject("http://lb:email-sender/", message, String.class)
        );
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println(String.format("Sent message=[%s]", (message)));
            } else {
                System.out.println("Unable to send message=[" +
                        message + "] due to : " + ex.getMessage());
            }
        });
    }
}
