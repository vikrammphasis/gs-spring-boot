package com.example.springboot;
   
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@EnableBinding(Source.class)
@RestController
public class KafkaSource {
  @Autowired
  private Source source;

  @GetMapping("/send")
  public String sendForm() {
    return "<html><body>" +
      "<form action=\"/messages\" method=\"post\">" +
      "<input type=\"text\" name=\"text\">" +
      "<input type=\"submit\">" +
      "</form></body><html>";
    }

  @PostMapping("/messages")
  public String sendMessage(@RequestBody String message) {
    this.source.output().send(new GenericMessage<>(message));
    return message;
  }
}
