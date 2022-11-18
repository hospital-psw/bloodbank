package com.example.dummybank.demo;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/send")
public class MessageSenderController {
    @CrossOrigin
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String SendMessage(@RequestBody News news) throws IOException, TimeoutException {
        // parse from image input string it's at the start "data:image/EXTENSION;base64,IMAGE_BASE_64"
        String[] image_parts = news.reqImage.split(",");
        String reqImage = image_parts[1];
        String extension = image_parts[0].split("/")[1].split(";")[0];

        var factory = new ConnectionFactory();
        factory.setHost("localhost");

        var connection = factory.newConnection();
        var channel = connection.createChannel();

        channel.queueDeclare("hello", false, false, false, null);

        String delimiter = '\n' + "-".repeat(20);
        String upperDelimiter = extension + delimiter;

        String message = upperDelimiter + reqImage + delimiter + news.reqTitle + delimiter + news.reqBody;

        byte[] body = message.getBytes(StandardCharsets.UTF_8);

        channel.basicPublish("", "hello", null, body);

        return "Message sent";
    }
}
