package com.example.appchattest.Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Chat implements Comparable<Chat> {
    public String contents;
    public String time;
    public boolean image;
    public String contentsImage;
    public boolean sender;

    public Chat() {
    }

    public Chat(String contents, String time, boolean image, String contentsImage, boolean sender) {
        this.contents = contents;
        this.time = time;
        this.image = image;
        this.contentsImage = contentsImage;
        this.sender = sender;
    }

    public boolean isImage() {
        return image;
    }

    public void setImage(boolean image) {
        this.image = image;
    }

    public String getContentsImage() {
        return contentsImage;
    }

    public void setContentsImage(String contentsImage) {
        this.contentsImage = contentsImage;
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
        result.put( "contents", contents );
        result.put( "time", time );
        result.put( "sender", sender );
        result.put( "image",image );
        result.put( "contentsImage",contentsImage );
        return result;
    }


    @Override
    public int compareTo(Chat o) {
        DateFormat simpleDateFormat = new SimpleDateFormat( "dd-MM-yyyy HH:mm:s" );
        try {
            Date date1 = simpleDateFormat.parse( this.time );
            Date date2 = simpleDateFormat.parse( o.time );
            if (date1.after( date2 ))
                return 1;
            return -1;
        } catch (Exception e) {

        }

        return 0;
    }


}