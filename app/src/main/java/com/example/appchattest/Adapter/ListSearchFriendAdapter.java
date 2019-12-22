package com.example.appchattest.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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

public class ListSearchFriendAdapter extends BaseAdapter{
    private List<User> listRoom;

    private LayoutInflater layoutInflater;
    private Context context;
    private DatabaseReference databaseReference;
    private List<Contacts> listContacts;
    public ListSearchFriendAdapter(Context aContext, List<User> listRoom,List<Contacts> listContacts)
    {

        this.context = aContext;
        this.listRoom = listRoom;
        this.listContacts=listContacts;
        layoutInflater  = LayoutInflater.from(aContext);//?
        databaseReference=FirebaseDatabase.getInstance().getReference();
    }


    @Override
    public int getCount()
    {
        return listRoom.size();
    }

    @Override
    public Object getItem(int position)
    {
        return listRoom.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder; //su dung holder de giu lay view trong convertView
        if (convertView == null) //neu convertView = null thi tao moi view va view holder
        {
            convertView = layoutInflater.inflate( R.layout.item_find_friends, null);//lay layout tu xml sang convertView
            holder = new ViewHolder();
            holder.avatarView = (ImageView) convertView.findViewById(R.id.imageView_avatar_item_friend);
            holder.roomNameView = (TextView) convertView.findViewById(R.id.textView_Name_item_friend);
            holder.lastContentView = (TextView) convertView.findViewById(R.id.textView_phone_number_item_friend);
            holder.buttonKB=convertView.findViewById( R.id.button_ketban_item_friend );
            holder.buttonKB.setEnabled( true );
            holder.buttonKB.setText( "Kết bạn" );

            holder.relativeLayout_bg = (RelativeLayout) convertView.findViewById(R.id.relativeLayout_list_item_friend);
            convertView.setTag(holder);// set convert view la holder
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }



        final User chatRoom = this.listRoom.get(position);//lay phan tu trong mang

        holder.buttonKB.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key=databaseReference.child( "contacts" ).push().getKey();
                Contacts contact=new Contacts( FirebaseAuth.getInstance().getUid().toString(),chatRoom.getUid( ));
                Map<String, Object> postValues = contact.toMap();
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/contacts/" + key, postValues);
                databaseReference.updateChildren( childUpdates );
                holder.buttonKB.setEnabled( false );
            }
        } );

        holder.roomNameView.setText(chatRoom.getName());
        holder.lastContentView.setText( chatRoom.getPhone() );
        StorageReference flieRef=FirebaseStorage.getInstance().getReference().child("avatar/"+chatRoom.uid+"/avatar.png");
        long megabyte=1024*1024;
        flieRef.getBytes( megabyte ).addOnSuccessListener( new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap= BitmapFactory.decodeByteArray( bytes,0,bytes.length );
                holder.avatarView.setImageBitmap( bitmap );
            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        } );

        for (Contacts c:listContacts)
        {

            if((c.userID.equals( chatRoom.getUid())&& c.contactID.equals( FirebaseAuth.getInstance().getUid() ))||(c.contactID.equals( chatRoom.getUid())&& c.userID.equals( FirebaseAuth.getInstance().getUid()))) {
                holder.buttonKB.setEnabled( false );
                if (c.status==false)
                    holder.buttonKB.setText( "Đã gửi lời mời kết bạn" );
                else holder.buttonKB.setText( "Bạn bè" );

            }
        }
        return convertView;
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
        Button buttonKB;
        ImageView avatarView;
        TextView roomNameView;
        TextView lastContentView;
        RelativeLayout relativeLayout_bg;
    }

}
