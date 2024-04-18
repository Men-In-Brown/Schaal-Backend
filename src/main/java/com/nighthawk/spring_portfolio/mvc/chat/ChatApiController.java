package com.nighthawk.spring_portfolio.mvc.chat;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/chat")
public class ChatApiController {
    @Autowired
    private ChatJpaRepository chatJpaRepository; 
    
    @PostMapping("create")
    public String sendMessage(@RequestParam String email, @RequestParam String message, @RequestParam String fromEmail, @RequestParam long personId, @RequestParam long fromPersonId){
        Chat chat = new Chat(email, message, fromEmail, personId, fromPersonId);
        Chat savedChat = chatJpaRepository.save(chat);

        // store this message somewhere and deliver to user
        return "Message Sent Successfully. ID: " + savedChat.getId();
    }
    
    @GetMapping("read")
    public ResponseEntity<List<Chat>> readUnreadMessages(@RequestParam String email, @RequestParam String fromEmail) throws JsonProcessingException{
        List<Chat> unreadMessages = chatJpaRepository.findByEmailAndFromEmailAndReadFlagOrderByDateSent(email, fromEmail, false);
        List<Chat> unreadMessagesNew = new ArrayList<Chat>();
        for (Chat c : unreadMessages) {
        	c.setReadFlag(true);
        	unreadMessagesNew.add(chatJpaRepository.save(c));
        }

        return new ResponseEntity<List<Chat>>(unreadMessagesNew, HttpStatus.OK);
    }
    
    @GetMapping("history")
    public ResponseEntity<List<Chat>> readAllMessages(@RequestParam String email, @RequestParam String fromEmail) throws JsonProcessingException{
    	System.out.println("Email: " + email);
    	System.out.println("From Email: " + fromEmail);
        List<Chat> histMessages = chatJpaRepository.findByEmailAndFromEmailOrFromEmailAndEmailOrderByDateSent(email, fromEmail);

        return new ResponseEntity<List<Chat>>(histMessages, HttpStatus.OK);
    }
    
    @DeleteMapping("history/clear")
    public void deleteMessages(@RequestParam String email, @RequestParam String fromEmail) throws JsonProcessingException{
    	System.out.println("Email: " + email);
    	System.out.println("From Email: " + fromEmail);
    	if (fromEmail.equals("All")) {
    		chatJpaRepository.deleteAllByEmail(email);
    	} else {
    		chatJpaRepository.deleteAllByEmailAndFromEmail(email, fromEmail);
    	}
    }
}
