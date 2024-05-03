package com.nighthawk.spring_portfolio.mvc.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MessageApiController {

    @Autowired
    MessageJpaRepository MessageJpaRepository;

    @GetMapping("/message")
    public List<Message> getAllMessage() {
        return MessageJpaRepository.findAll();
    }

    @GetMapping("/message/{id}")
    public Message getMessage(@PathVariable String id) {
        Long messageID = Long.parseLong(id);
        Optional<Message> message = MessageJpaRepository.findById(messageID);
        return message.get();
    }

    @PostMapping("/message/update/{id}")
    public Message updateMessage(@PathVariable String id, @RequestBody Message newMessage) {
        Long messageID = Long.parseLong(id);
        Optional<Message> message = MessageJpaRepository.findById(messageID);
        message.get().setText(newMessage.getText());
        message.get().setWriter(newMessage.getWriter());
        MessageJpaRepository.save(message.get());
        return message.get();
    }

    @PostMapping("/message")
    public Message createMessage(@RequestBody Message message) {
        // Get the title and content from the request body
        String text = message.getText();
        String writer = message.getWriter();
        String time = message.getTime();
        String groupname = message.getGroupName();
        // Create a new Post object
        Message newMessage = new Message();
        newMessage.setText(text);
        newMessage.setTime(time); // Set the title
        newMessage.setWriter(writer);
        newMessage.setGroupName(groupname);
        // Set other fields as needed

        // Save the new Post
        MessageJpaRepository.save(newMessage);

        return newMessage;
    }

    @DeleteMapping("/message/{id}")
    public String deleteMessage(@PathVariable String id) {
        Long messageID = Long.parseLong(id);
        MessageJpaRepository.deleteById(messageID);
        return "Delete Success!";
    }
}
