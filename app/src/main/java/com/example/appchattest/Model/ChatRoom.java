//str_Avatar la ten hinh avatar
//str_lastcontent noi dung cuoi cung cua chat room
//str_state trang thai co new message khong


package com.example.appchattest.Model;

public class ChatRoom {
    private String uid;
    private String str_RoomName;
    private String str_Avatar;
    private String str_LastContent;
    private String str_State;

    public ChatRoom() {

    }

    public ChatRoom(String uid, String str_RoomName, String str_Avatar, String str_LastContent, String str_State) {
        this.uid = uid;
        this.str_RoomName = str_RoomName;
        this.str_Avatar = str_Avatar;
        this.str_LastContent = str_LastContent;
        this.str_State = str_State;
    }

    public String getRoomName() {
        return str_RoomName;
    }

    public String getAvatar() {
        return str_Avatar;
    }

    public String getLastContent() {
        return str_LastContent;
    }

    public String getState() { return str_State; }

    public void setRoomName(String name) {
        str_RoomName = name;
    }

    public void setAvatar(String avatar) {
        str_Avatar = avatar;
    }

    public void setLastContent(String lastcontent) {
        str_LastContent = lastcontent;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStr_RoomName() {
        return str_RoomName;
    }

    public void setStr_RoomName(String str_RoomName) {
        this.str_RoomName = str_RoomName;
    }

    public String getStr_Avatar() {
        return str_Avatar;
    }

    public void setStr_Avatar(String str_Avatar) {
        this.str_Avatar = str_Avatar;
    }

    public String getStr_LastContent() {
        return str_LastContent;
    }

    public void setStr_LastContent(String str_LastContent) {
        this.str_LastContent = str_LastContent;
    }

    public String getStr_State() {
        return str_State;
    }

    public void setStr_State(String str_State) {
        this.str_State = str_State;
    }
}
