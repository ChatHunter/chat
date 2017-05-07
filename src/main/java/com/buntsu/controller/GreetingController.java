package com.buntsu.controller;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import com.buntsu.domain.*;

@Controller
public class GreetingController {
    @MessageMapping("/hello") // エンドポイントの指定
    @SendTo("/topic/greetings") // メッセージの宛先を指定
    public Greeting greeting(Message message) {
        return new Greeting("Hello, " + message.getName() + "!");
    }
    @MessageExceptionHandler
    @SendToUser("/queue/errors") // 送信者のみを宛先にする
    public MessagingError handleException(Throwable exception) {
        MessagingError error = new MessagingError();
        error.setMessage(exception.getMessage());
        return error;
    }

}