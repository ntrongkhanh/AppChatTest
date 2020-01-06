package com.example.appchattest.Adapter;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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

import com.example.appchattest.ImageAvatarActivity;
import com.example.appchattest.LoginActivity;
import com.example.appchattest.MainNavigationActivity;
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

import java.io.ByteArrayOutputStream;
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
        // 1 tin nhắn văn bản của bạn gửi
        // 2 tin nhăn văn bản bạn nhận
        // 3 tin nhắn hình ảnh bạn gửi
        // 4 tin nhắn hình ảnh bạn nhận


        if (listChat.get( position ).isSender()==true)
        {
            if (listChat.get( position ).isImage()==true){
                return 2;
            }
             return 0;
        }
        else {
            if (listChat.get( position ).isImage()==true)
                return 3;
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }


    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        View view=convertView;

        int listViewItemType = getItemViewType(position);

        if (view == null) {
            holder=new ViewHolder();

            if (listViewItemType == 0) {

                view=layoutInflater.inflate( R.layout.item_chat_sender,null );
                holder.content=view.findViewById( R.id.textView_content_chat_sender );
                holder.time=view.findViewById( R.id.textView_time_chat_sender );
                holder.relativeLayout=view.findViewById( R.id.relativeLayout_chat_sender );
            } else if (listViewItemType == 1) {
                view=layoutInflater.inflate( R.layout.item_chat_receiver,null );

                holder.content=view.findViewById( R.id.textView_content_chat_receiver );
                holder.time=view.findViewById( R.id.textView_time_chat_receiver );
                holder.avatarReceiver=view.findViewById( R.id.imageView_avatar_chat_receiver );
                holder.relativeLayout=view.findViewById( R.id.relativeLayout_chat_receiver );

            }
            else if (listViewItemType==2)
            {
                view=layoutInflater.inflate( R.layout.item_chat_image_sender,null );

                holder.time=view.findViewById( R.id.textView_time_chat_image_sender );
                holder.relativeLayout=view.findViewById( R.id.relativeLayout_chat_image_sender );
                holder.image=view.findViewById( R.id.imageView_chat_sender );
            }
            else if (listViewItemType==3)
            {
                view=layoutInflater.inflate( R.layout.item_chat_image_receiver,null );
                holder.time=view.findViewById( R.id.textView_time_chat_image_receiver );
                holder.relativeLayout=view.findViewById( R.id.relativeLayout_chat_image_receiver );
                holder.image=view.findViewById( R.id.imageView_chat_receiver );
                holder.avatarReceiver=view.findViewById( R.id.imageView_avatar_chat_image_receiver );
            }
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        Chat chat = this.listChat.get(position);//lay phan tu trong mang

        holder.time.setText(chat.getTime());
        if (listViewItemType==0 || listViewItemType==1){
            holder.content.setText( chat.getContents() );
        }

        else {
            byte[] a= Base64.decode( chat.contentsImage,Base64.DEFAULT );
            Bitmap bitmap1= BitmapFactory.decodeByteArray( a,0,a.length );
            holder.image.setImageBitmap( bitmap1 );

        }




//            holder.image.setOnClickListener( new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
////                    Bitmap bitmap = ((BitmapDrawable) finalHolder.image.getDrawable()).getBitmap();
////                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
////                    bitmap.compress( Bitmap.CompressFormat.JPEG , 100, baos);
////                    byte[] data = baos.toByteArray();
//                    Intent intent=new Intent(context, LoginActivity.class);
//                   // intent.putExtra( "image",data );
//                    context.startActivity( intent );
//                }
//            } );



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
        ImageView image;
    }

}
