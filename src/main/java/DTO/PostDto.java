package DTO;

import java.time.Instant;

public class PostDto {
    private Long id;
    private String text;
    private Instant createdAt;
    private String username;


    public PostDto() {}
    public PostDto(Long id, String text, Instant createdAt, String username) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.username = username;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}

