package com.example.appchattest.Model;

import java.util.HashMap;
import java.util.Map;

public class Chat {
    public String contents;
    public String time;
    public boolean sender;
    public Chat() {
    }

    public Chat(String contents, String time, boolean sender) {
        this.contents = contents;
        this.time = time;
        this.sender = sender;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isSender() {
        return sender;
    }

    public void setSender(boolean sender) {
        this.sender = sender;
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("contents", contents);
        result.put("time", time);
        result.put("sender", sender);
        return result;
    }
}
