package com.webapp.neo.model;

import javax.persistence.*;

@Entity
@Table(name = "chat_message")
public class ChatMessage {
    @Id
    @GeneratedValue
    private Long id;
    private MessageType type;
    private String content;

    public String getContent() {return content;}
    public void setContent(String content) {this.content = content;}
    public MessageType getType() {
        return type;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setType(MessageType type) {this.type = type;}
    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

}