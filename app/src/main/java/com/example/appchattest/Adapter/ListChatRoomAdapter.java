package com.example.appchattest.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.appchattest.ChatActivity;
import com.example.appchattest.Model.ChatRoom;
import com.example.appchattest.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListChatRoomAdapter extends BaseAdapter{
    private List<ChatRoom> listChatRooms;
    private LayoutInflater layoutInflater;
    private Context context;
    private Activity activity;
    private DatabaseReference databaseReference;

    public ListChatRoomAdapter(Context aContext,Activity activity, ArrayList<ChatRoom> chatRooms) {
        this.context = aContext;
        this.listChatRooms = chatRooms;
        this.activity=activity;
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
            holder.relativeLayout=convertView.findViewById( R.id.relativeLayout_listItem );
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        final ChatRoom chatRoom = this.listChatRooms.get(i);
        databaseReference.child( "chats" ).child( FirebaseAuth.getInstance().getUid() ).child( chatRoom.getUid() ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String x= (String) dataSnapshot.child("lastcontents").getValue();

                holder.textView_lastContent.setText( x );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
        holder.textView_roomName.setText(chatRoom.getStr_RoomName());
        final byte[] imgage= Base64.decode( chatRoom.getStr_Avatar(),Base64.DEFAULT );
        final Bitmap bitmap1= BitmapFactory.decodeByteArray( imgage,0,imgage.length );
        holder.imageView_avatar.setImageBitmap( bitmap1 );

        holder.relativeLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( context, ChatActivity.class );


                intent.putExtra( "nameFriend",chatRoom.getStr_RoomName() );
                intent.putExtra( "uidFriend",chatRoom.getUid() );
                context.startActivity( intent );
                activity.overridePendingTransition( R.anim.slide_in_from_right, R.anim.slide_out_to_left);

            }


        } );
        return convertView;
    }

    static class ViewHolder
    {
        ImageView imageView_avatar;
        TextView textView_roomName;
        TextView textView_lastContent;
        RelativeLayout relativeLayout;
    }
}
