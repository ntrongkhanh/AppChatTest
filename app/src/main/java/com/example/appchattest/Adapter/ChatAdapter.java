package com.example.appchattest.Adapter;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.fragment.app.FragmentActivity;
import com.example.appchattest.Model.Chat;
import com.example.appchattest.Model.ChatRoom;
import com.example.appchattest.Model.Contacts;
import com.example.appchattest.Model.User;
import com.example.appchattest.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatAdapter extends BaseAdapter {
    private Bitmap avatarFriend;
    private String uidFriend;
    private Context context;
    private List<Chat> listChat;

    private DatabaseReference databaseReference;
    private LayoutInflater layoutInflater;
    private boolean flagSender;
    public ChatAdapter(Context aContext,List<Chat> listChat,String uidFriend,Bitmap avatarFriend)
    {

        this.avatarFriend=avatarFriend;
        this.context=aContext;
        this.listChat=listChat;
        this.uidFriend=uidFriend;
        layoutInflater  = LayoutInflater.from(aContext);

//        databaseReference=FirebaseDatabase.getInstance().getReference().child( "chats" ).child( FirebaseAuth.getInstance().getUid() ).child( uidFriend );
    }

    public ChatAdapter(Context aContext,ArrayList<Chat> listChat,String uidFriend )
    {

        this.context=aContext;
        this.listChat=listChat;
        this.uidFriend=uidFriend;
        layoutInflater  = LayoutInflater.from(aContext);
//        databaseReference=FirebaseDatabase.getInstance().getReference().child( "chats" ).child( FirebaseAuth.getInstance().getUid() ).child( uidFriend );

    }

    @Override
    public int getCount()
    {
        return listChat.size();
    }

    @Override
    public Object getItem(int position)
    {
        return listChat.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (listChat.get( position ).isSender()==true)
            return 1;
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    private ViewHolder setSender(View convertView)
    {
        ViewHolder holder=new ViewHolder();
        convertView=layoutInflater.inflate( R.layout.item_chat_sender,null );

        holder.content=convertView.findViewById( R.id.textView_content_chat_sender );
        holder.time=convertView.findViewById( R.id.textView_time_chat_sender );
        holder.relativeLayout=convertView.findViewById( R.id.relativeLayout_chat_sender );
        return holder;

    }
    private ViewHolder setReceiver(View convertView)
    {
        convertView=layoutInflater.inflate( R.layout.item_chat_receiver,null );
        ViewHolder holder=new ViewHolder();
        holder.content=convertView.findViewById( R.id.textView_content_chat_receiver );
        holder.time=convertView.findViewById( R.id.textView_time_chat_receiver );
        holder.avatarReceiver=convertView.findViewById( R.id.imageView_avatar_chat_receiver );
        holder.relativeLayout=convertView.findViewById( R.id.relativeLayout_chat_receiver );
        return holder;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        View view=convertView;

        int listViewItemType = getItemViewType(position);

        if (view == null) {
            holder=new ViewHolder();

            if (listViewItemType == 1) {

                view=layoutInflater.inflate( R.layout.item_chat_sender,null );
                holder.content=view.findViewById( R.id.textView_content_chat_sender );
                holder.time=view.findViewById( R.id.textView_time_chat_sender );
                holder.relativeLayout=view.findViewById( R.id.relativeLayout_chat_sender );
            } else if (listViewItemType == 0) {
                view=layoutInflater.inflate( R.layout.item_chat_receiver,null );

                holder.content=view.findViewById( R.id.textView_content_chat_receiver );
                holder.time=view.findViewById( R.id.textView_time_chat_receiver );
                holder.avatarReceiver=view.findViewById( R.id.imageView_avatar_chat_receiver );
                holder.relativeLayout=view.findViewById( R.id.relativeLayout_chat_receiver );

            }
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        Chat chat = this.listChat.get(position);//lay phan tu trong mang

        holder.time.setText(chat.getTime());
        holder.content.setText( chat.getContents() );

        if (chat.isSender()==false)
        {
            holder.avatarReceiver.setImageBitmap( avatarFriend );
        }
        return view;
    }

    //Tim ID cua anh avatar
    public int getResIdByName(String resName)//can chinh sua de phu hop voi database
    {
        String pkgName = context.getPackageName();

        //khong tim thay
        int resID = context.getResources().getIdentifier(resName, "drawable", pkgName);
        Log.i("Custom List View ", "Res Name:" + resName + "ResID = " + resID);
        return resID;
    }

    public Drawable getResDrawable(String resName)
    {
        String pkgName = context.getPackageName();

        final int resourceId = context.getResources().getIdentifier(resName, "drawable", pkgName);
        return context.getResources().getDrawable(resourceId, null);
    }
    static class ViewHolder
    {
        RelativeLayout relativeLayout;
        ImageView avatarReceiver;
        TextView time;
        TextView content;
    }

}
