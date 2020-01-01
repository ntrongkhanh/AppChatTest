package com.example.appchattest.Adapter;

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

public class FriendRequestAdapter extends BaseAdapter {
    private List<User> listRoom;
    private FirebaseUser user;
    private LayoutInflater layoutInflater;
    private Context context;
    private DatabaseReference databaseReference;
    private ArrayList<Contacts> listContacts=new ArrayList<>();
    private ArrayList<User> listUser=new ArrayList<>(  );
    public FriendRequestAdapter(Context aContext, List<User> listRoom)
    {
        this.context = aContext;
        this.listRoom = listRoom;
        layoutInflater  = LayoutInflater.from(aContext);//?
        user=FirebaseAuth.getInstance().getCurrentUser();
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
            convertView = layoutInflater.inflate( R.layout.item_loimoiketban, null);//lay layout tu xml sang convertView
            holder = new ViewHolder();
            holder.avatarView = (ImageView) convertView.findViewById(R.id.imageView_avatar_item_loimoiketban);
            holder.roomNameView = (TextView) convertView.findViewById(R.id.textView_Name_item_loimoiketban);
            holder.lastContentView = (TextView) convertView.findViewById(R.id.textView_phone_number_item_loimoiketban);
            holder.buttonChapNhap=convertView.findViewById( R.id.button_dongy_ketban_item_loimoiketban);
            holder.buttonTuchoi=convertView.findViewById( R.id.button_tuchoi_item_loimoiketban );


            holder.relativeLayout_bg = (RelativeLayout) convertView.findViewById(R.id.relativeLayout_list_item_loimoiketban);
            convertView.setTag(holder);// set convert view la holder
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.buttonTuchoi.setVisibility( View.VISIBLE );
        holder.buttonChapNhap.setVisibility( View.VISIBLE );
        final User chatRoom = this.listRoom.get(position);//lay phan tu trong mang
        holder.buttonChapNhap.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String key=databaseReference.child( "contacts" ).push().getKey();
//                Contacts contact=new Contacts( FirebaseAuth.getInstance().getUid().toString(),chatRoom.getUid( ));
//                Map<String, Object> postValues = contact.toMap();
//                Map<String, Object> childUpdates = new HashMap<>();
//                childUpdates.put("/contacts/" + key, postValues);
//                databaseReference.updateChildren( childUpdates );
                // holder.buttonKB.setEnabled( false );
                holder.buttonTuchoi.setVisibility( View.INVISIBLE );
                holder.buttonChapNhap.setVisibility( View.INVISIBLE );
//                    Chat chat=new Chat( editTextContent.getText().toString(),formattedDate,true );
//                    Map<String, Object> postValues = chat.toMap();
//
//                childUpdates.put("/chats/" + uidUser+"/"+uidFriend+"/"+key, postValues);
//                databaseReference.updateChildren( childUpdates );
//                    String key1=databaseReference.child( "chats" ).child( uidFriend ).child( uidUser ).push().getKey();
//                    Chat chat1=new Chat( editTextContent.getText().toString(),formattedDate,false );
//                    Map<String, Object> postValues1 = chat1.toMap();
//                    Map<String, Object> childUpdates1 = new HashMap<>();
//                childUpdates1.put("/chats/" + uidFriend+"/"+uidUser+"/"+key1, postValues1);
//                databaseReference.updateChildren( childUpdates1 );
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put( "/friends/"+user.getUid()+"/"+chatRoom.uid,chatRoom.name );
                databaseReference.updateChildren( childUpdates );

                Map<String, Object> childUpdates1 = new HashMap<>();
                childUpdates1.put( "/friends/"+chatRoom.getUid()+"/"+user.getUid(),user.getDisplayName() );
                databaseReference.updateChildren( childUpdates1 );

                databaseReference.child( "contacts" ).addValueEventListener( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> nodechild=dataSnapshot.getChildren();
                        for (DataSnapshot data:nodechild)
                        {
                            Contacts contact=data.getValue(Contacts.class);
                            String key=data.getKey();
                            if(contact.contactID.equals(user.getUid()) && contact.userID.equals( chatRoom.getUid() ))
                            {
                               // contact.setStatus( true );
                          //      databaseReference.child( "contacts" ).child( key ).setValue( contact );
                                databaseReference.child( "contacts" ).child( key ).removeValue();

                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                } );

            }
        } );
        holder.buttonTuchoi.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.buttonTuchoi.setVisibility( View.INVISIBLE );
                holder.buttonChapNhap.setVisibility( View.INVISIBLE );
                databaseReference.child( "contacts" ).addValueEventListener( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Iterable<DataSnapshot> nodechild=dataSnapshot.getChildren();
                        for (DataSnapshot data:nodechild)
                        {
                            Contacts contact=data.getValue(Contacts.class);
                            String key=data.getKey();
                            if(contact.contactID.equals(FirebaseAuth.getInstance().getUid()) && contact.userID.equals( chatRoom.getUid() ))
                            {
                                databaseReference.child( "contacts" ).child( key ).removeValue();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                } );
            }
        } );
        //databaseReference.child( "contacts" ).addValueEventListener(  this );
        holder.roomNameView.setText(chatRoom.getName());
        holder.lastContentView.setText( chatRoom.getPhone() );
//        StorageReference flieRef=FirebaseStorage.getInstance().getReference().child("avatar/"+chatRoom.uid+"/avatar.png");
//        long megabyte=1024*1024;
//        flieRef.getBytes( megabyte ).addOnSuccessListener( new OnSuccessListener<byte[]>() {
//            @Override
//            public void onSuccess(byte[] bytes) {
//                Bitmap bitmap= BitmapFactory.decodeByteArray( bytes,0,bytes.length );
//                holder.avatarView.setImageBitmap( bitmap );
//            }
//        } ).addOnFailureListener( new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        } );
        byte[] a= Base64.decode( chatRoom.avatar,Base64.DEFAULT );
        Bitmap bitmap1= BitmapFactory.decodeByteArray( a,0,a.length );
        holder.avatarView.setImageBitmap( bitmap1 );

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
        ImageView buttonTuchoi;
        Button buttonChapNhap;
        ImageView avatarView;
        TextView roomNameView;
        TextView lastContentView;
        RelativeLayout relativeLayout_bg;
    }

}
