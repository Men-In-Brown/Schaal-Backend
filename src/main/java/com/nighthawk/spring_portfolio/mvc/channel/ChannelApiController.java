package com.nighthawk.spring_portfolio.mvc.channel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ChannelApiController {

    @Autowired
    ChannelJpaRepository ChannelJpaRepository;

    @GetMapping("/channel")
    public List<Channel> getAllChannel() {
        return ChannelJpaRepository.findAll();
    }

    @GetMapping("/channel/{id}")
    public Channel getChannel(@PathVariable String id) {
        Long channelID = Long.parseLong(id);
        Optional<Channel> channel = ChannelJpaRepository.findById(channelID);
        return channel.get();
    }

    @PostMapping("/channel/{id}")
    public Channel updateChannel(@PathVariable String id, @RequestBody Channel newChannel) {
        Long channelID = Long.parseLong(id);
        Optional<Channel> channel = ChannelJpaRepository.findById(channelID);
        channel.get().setName(newChannel.getName());
        channel.get().setRecipient(newChannel.getRecipient());
        ChannelJpaRepository.save(channel.get());
        return channel.get();
    }

    @PostMapping("/channel")
    public Channel createChannel(@RequestBody Channel channel) {
        // Get the title and content from the request body
        String recipient = channel.getRecipient();
        String name = channel.getName();
        String creator = channel.getCreator();
        // Create a new Post object
        Channel newChannel = new Channel();
        newChannel.setRecipient(recipient);
        newChannel.setName(name); // Set the title
        newChannel.setCreator(creator);
        // Set other fields as needed

        // Save the new Post
        ChannelJpaRepository.save(newChannel);

        return newChannel;
    }

    @DeleteMapping("/channel/{id}")
    public String deleteMessage(@PathVariable String id) {
        Long channelID = Long.parseLong(id);
        ChannelJpaRepository.deleteById(channelID);
        return "Delete Success!";
    }
}
