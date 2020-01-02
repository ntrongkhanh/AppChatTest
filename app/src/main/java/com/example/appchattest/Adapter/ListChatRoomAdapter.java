package com.example.appchattest.Adapter;

import android.app.Activity;
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

import com.example.appchattest.ChatActivity;
import com.example.appchattest.Model.Chat;
import com.example.appchattest.Model.ChatRoom;
import com.example.appchattest.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ListChatRoomAdapter extends BaseAdapter {
    private List<ChatRoom> listChatRooms;

    private LayoutInflater layoutInflater;
    private Context context;
    private Activity activity;
    private DatabaseReference databaseReference;



    public ListChatRoomAdapter(Context aContext, Activity activity, ArrayList<ChatRoom> chatRooms) {
        this.context = aContext;
        this.listChatRooms = chatRooms;
        this.activity = activity;
        layoutInflater = LayoutInflater.from( context );
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }


    @Override
    public int getCount() {
        return listChatRooms.size();
    }

    @Override
    public Object getItem(int i) {
        return listChatRooms.get( i );
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate( R.layout.chatroom_item_layout, null );
            holder = new ViewHolder();
            holder.imageView_avatar = (ImageView) convertView.findViewById( R.id.imageView_avatar_chatroom );
            holder.textView_roomName = (TextView) convertView.findViewById( R.id.textView_roomName );
            holder.textView_lastContent = (TextView) convertView.findViewById( R.id.textView_LastContent );
            holder.relativeLayout = convertView.findViewById( R.id.relativeLayout_listItem );
            holder.textView_name = convertView.findViewById( R.id.textView_name );
            holder.textView_time = convertView.findViewById( R.id.textView_time_chat );
            convertView.setTag( holder );
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ChatRoom chatRoom = this.listChatRooms.get( i );


        Query lastQuery = databaseReference.child( "chats" ).child( FirebaseAuth.getInstance().getUid() ).child( chatRoom.getUid() ).orderByKey().limitToLast( 1 );
        lastQuery.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //String x= (String) dataSnapshot.child("lastcontents").getValue();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    try {
                        Chat chat = dataSnapshot1.getValue( Chat.class );
                        holder.textView_lastContent.setText( chat.getContents() );
                        if (chat.isSender()) {
                            holder.textView_name.setText( "Bạn:" );
                        } else holder.textView_name.setText( chatRoom.getStr_RoomName() + ":" );



                        Date c = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat( "dd-MM-yyyy HH:mm:s" );
                        String formattedDate = df.format( c );
                        holder.textView_time.setText( sosanhDate( chat.getTime(), formattedDate ) );


                    } catch (Exception e) {

                    }
                }


                //  holder.textView_lastContent.setText( x );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );


        holder.textView_roomName.setText( chatRoom.getStr_RoomName() );
        final byte[] imgage = Base64.decode( chatRoom.getStr_Avatar(), Base64.DEFAULT );
        final Bitmap bitmap1 = BitmapFactory.decodeByteArray( imgage, 0, imgage.length );
        holder.imageView_avatar.setImageBitmap( bitmap1 );

        holder.relativeLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( context, ChatActivity.class );


                intent.putExtra( "nameFriend", chatRoom.getStr_RoomName() );
                intent.putExtra( "uidFriend", chatRoom.getUid() );
                context.startActivity( intent );
                activity.overridePendingTransition( R.anim.slide_in_from_right, R.anim.slide_out_to_left );

            }


        } );
        return convertView;
    }

    static class ViewHolder {
        ImageView imageView_avatar;
        TextView textView_roomName;
        TextView textView_lastContent;
        RelativeLayout relativeLayout;
        TextView textView_name;
        TextView textView_time;
    }

    private String sosanhDate(String dateChat, String currDate) throws ParseException {
        DateFormat simpleDateFormat = new SimpleDateFormat( "dd-MM-yyyy HH:mm:s" );

        Date date1 = simpleDateFormat.parse( dateChat );
        Date date2 = simpleDateFormat.parse( currDate );

        long getDiff = date2.getTime() - date1.getTime();

        long getDaysDiff = TimeUnit.MILLISECONDS.toDays( getDiff );
        if (getDaysDiff == 0) {
            getDaysDiff = TimeUnit.MILLISECONDS.toHours( getDiff );
            if (getDaysDiff == 0) {
                getDaysDiff = TimeUnit.MILLISECONDS.toMinutes( getDiff );
                if (getDaysDiff == 0) {
                    getDaysDiff = TimeUnit.MILLISECONDS.toSeconds( getDiff );
                    return getDaysDiff + " giây";
                }
                return getDaysDiff + " phút";
            }
            return getDaysDiff + " giờ";
        }

        return getDaysDiff + " ngày";
    }

}
