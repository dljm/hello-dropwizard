package com.example.helloworld.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Saying {
    private long id;
    private String content;
    private boolean active;

    public Saying() {
        // Jackson deserialization
    }

    public Saying(long id, String content, boolean active) {
        this.id = id;
        this.content = content;
        this.active = active;
    }

    @JsonProperty // Jackson serialization
    public long getId() {
        return id;
    }

    @JsonProperty
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @JsonProperty
    public boolean getActive() { return active; }
}
