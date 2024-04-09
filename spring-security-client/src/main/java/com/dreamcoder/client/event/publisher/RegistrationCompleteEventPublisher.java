package com.dreamcoder.client.event.publisher;


import com.dreamcoder.client.entity.User;
import com.dreamcoder.client.event.RegistrationCompleteEvent;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class RegistrationCompleteEventPublisher {

    @Autowired
    private ApplicationEventPublisher publisher;

    public void publishEvent(User user, HttpServletRequest request) {

        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));

    }


    private String applicationUrl(HttpServletRequest request) {

        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }
}
