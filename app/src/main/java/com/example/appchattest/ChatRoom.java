//str_Avatar la ten hinh avatar
//str_lastcontent noi dung cuoi cung cua chat room
//str_state trang thai co new message khong


package com.example.appchattest;

public class ChatRoom {
    private String str_RoomName;
    private String str_Avatar;
    private String str_LastContent;
    private String str_State;

    public ChatRoom() {

    }

    public ChatRoom(String name, String avatar, String lastcontent, String state) {
        this.str_RoomName = name;
        this.str_Avatar = avatar;
        this.str_LastContent = lastcontent;
        this.str_State = state;
    }

    public String GetRoomName() {
        return str_RoomName;
    }

    public String GetAvatar() {
        return str_Avatar;
    }

    public String GetLastContent() {
        return str_LastContent;
    }

    public String GetState() { return str_State; }

    public void SetRoomName(String name) {
        str_RoomName = name;
    }

    public void SetAvatar(String avatar) {
        str_Avatar = avatar;
    }

    public void SetLastContent(String lastcontent) {
        str_LastContent = lastcontent;
    }
}
