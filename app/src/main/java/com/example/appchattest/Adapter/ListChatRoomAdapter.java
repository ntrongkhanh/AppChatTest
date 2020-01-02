package com.example.appchattest.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.appchattest.Model.ChatRoom;
import com.example.appchattest.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListChatRoomAdapter extends BaseAdapter{
    private List<ChatRoom> listChatRooms;
    private LayoutInflater layoutInflater;
    private Context context;
    private DatabaseReference databaseReference;

    public ListChatRoomAdapter(Context aContext, ArrayList<ChatRoom> chatRooms) {
        this.context = aContext;
        this.listChatRooms = chatRooms;

        layoutInflater = LayoutInflater.from(context);
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }


    @Override
    public int getCount() {
        return listChatRooms.size();
    }

    @Override
    public Object getItem(int i) {
        return listChatRooms.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        if(convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.chatroom_item_layout, null);
            holder = new ViewHolder();
            holder.imageView_avatar = (ImageView) convertView.findViewById(R.id.imageView_avatar_chatroom);
            holder.textView_roomName = (TextView) convertView.findViewById(R.id.textView_roomName);
            holder.textView_lastContent = (TextView) convertView.findViewById(R.id.textView_LastContent);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        final ChatRoom chatRoom = this.listChatRooms.get(i);
        holder.textView_roomName.setText(chatRoom.getRoomName());
        holder.textView_lastContent.setText(chatRoom.getLastContent());
        final byte[] imgage= Base64.decode( chatRoom.getAvatar(),Base64.DEFAULT );
        final Bitmap bitmap1= BitmapFactory.decodeByteArray( imgage,0,imgage.length );
        holder.imageView_avatar.setImageBitmap( bitmap1 );

        return convertView;
    }

    static class ViewHolder
    {
        ImageView imageView_avatar;
        TextView textView_roomName;
        TextView textView_lastContent;
    }
}
