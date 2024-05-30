// package com.nighthawk.spring_portfolio.mvc.message;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;
// import java.util.Optional;

// @RestController
// public class ChannelApiController {

//     @Autowired
//     ChannelJpaRepository channelJpaRepository;

//     @GetMapping("/channel")
//     public List<Channel> getAllChannel() {
//         return channelJpaRepository.findAll();
//     }

//     @GetMapping("/channel/{id}")
//     public Channel getChannel(@PathVariable String id) {
//         Long channelID = Long.parseLong(id);
//         Optional<Channel> channel = channelJpaRepository.findById(channelID);
//         return channel.get();
//     }

//     @PostMapping("/channel/{id}")
//     public Channel updateChannel(@PathVariable String id, @RequestBody Channel newChannel) {
//         Long channelID = Long.parseLong(id);
//         Optional<Channel> channel = channelJpaRepository.findById(channelID);
//         channel.get().setName(newChannel.getName());
//         channel.get().setPlace(newChannel.getPlace());
//         channelJpaRepository.save(channel.get());
//         return channel.get();
//     }

//     @PostMapping("/channel")
//     public Channel createChannel(@RequestBody Channel channel) {
//         // Get the title and content from the request body
//         String name = channel.getName();
//         Place place = channel.getPlace();
//         // Create a new Post object
//         Channel newChannel = new Channel();
//         newChannel.setName(name);
//         newChannel.setPlace(place);
//         // Set other fields as needed

//         // Save the new Post
//         channelJpaRepository.save(newChannel);

//         return newChannel;
//     }

//     @DeleteMapping("/channel/{id}")
//     public String deleteChannel(@PathVariable String id) {
//         Long channelID = Long.parseLong(id);
//         channelJpaRepository.deleteById(channelID);
//         return "Delete Success!";
//     }
// }
