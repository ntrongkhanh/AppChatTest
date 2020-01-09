package com.example.appchattest.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.example.appchattest.ChatActivity;
import com.example.appchattest.FriendInfoActivity;
import com.example.appchattest.Model.Contacts;
import com.example.appchattest.Model.User;
import com.example.appchattest.R;
import com.google.firebase.database.*;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListFriendsAdapter extends BaseAdapter {
    private List<User> listUser;

    private LayoutInflater layoutInflater;
    private Context context;
    private DatabaseReference databaseReference;
    private ArrayList<Contacts> listContacts = new ArrayList<>();

    private String idUnFriend = "";

    public static final int MY_REQEST_CODE = 100;

    public ListFriendsAdapter(Context aContext, List<User> list)
    {

        this.context = aContext;
        this.listUser = list;

        layoutInflater  = LayoutInflater.from( context );
        databaseReference=FirebaseDatabase.getInstance().getReference();
    }


    @Override
    public int getCount() {
        return listUser.size();
    }

    @Override
    public Object getItem(int position) {
        return listUser.get( position );
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder; //su dung holder de giu lay view trong convertView
        if (convertView == null) //neu convertView = null thi tao moi view va view holder
        {
            convertView = layoutInflater.inflate( R.layout.item_friend_layout, null );//lay layout tu xml sang convertView
            holder = new ViewHolder();
            holder.avatarView = (ImageView) convertView.findViewById( R.id.imageView_avatar_friend );
            holder.roomNameView = (TextView) convertView.findViewById( R.id.textView_friendName );
            holder.relativeLayout_bg = (RelativeLayout) convertView.findViewById( R.id.relativeLayout_listItem_friend );
            holder.status = (TextView) convertView.findViewById(R.id.textView_state_friend);
            convertView.setTag( holder );// set convert view la holder
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final User friend = this.listUser.get( position );//lay phan tu trong mang

        holder.roomNameView.setText( friend.getName() );
//        StorageReference flieRef=FirebaseStorage.getInstance().getReference().child("avatar/"+friend.uid+"/avatar.png");
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
        final byte[] imgage = Base64.decode( friend.avatar, Base64.DEFAULT );
        final Bitmap bitmap1 = BitmapFactory.decodeByteArray( imgage, 0, imgage.length );
        holder.avatarView.setImageBitmap( bitmap1 );
        holder.relativeLayout_bg.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( context, FriendInfoActivity.class );

                intent.putExtra("nameFriend",friend.name );
                intent.putExtra("uidFriend",friend.uid );
                intent.putExtra("emailFriend", friend.email);
                intent.putExtra("sexFriend", friend.sex);
                intent.putExtra("phoneFriend", friend.phone);
                intent.putExtra("birthFriend", friend.birthday);
//                 Bundle bundle=new Bundle(  );
//
//                bundle.putString( "nameFriend",friend.name );
//              //  bundle.putString( "x",friend.avatar );
//
//                bundle.putString( "uidFriend",friend.uid );
                //intent.putExtras( bundle );
                context.startActivity(intent);
            }
        } );

        final DatabaseReference hasStatus = FirebaseDatabase.getInstance().getReference().child("users").child(friend.uid);
        hasStatus.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                holder.status.setText(dataSnapshot.child("status").getValue().toString());

                if (holder.status.getText().toString().equals("Offline"))
                {
                    holder.status.setTextColor(Color.parseColor("#7fffffff"));
                }
                else if (holder.status.getText().toString().equals("Online"))
                {
                    holder.status.setTextColor(Color.parseColor("#9eff3d"));
                }
                Log.d("DEBUG sTATUS", dataSnapshot.child("status").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return convertView;
    }
    //Tim ID cua anh avatar
    public int getResIdByName(String resName)//can chinh sua de phu hop voi database
    {
        String pkgName = context.getPackageName();

        //khong tim thay
        int resID = context.getResources().getIdentifier( resName, "drawable", pkgName );
        Log.i( "Custom List View ", "Res Name:" + resName + "ResID = " + resID );
        return resID;
    }

    public Drawable getResDrawable(String resName) {
        String pkgName = context.getPackageName();

        final int resourceId = context.getResources().getIdentifier( resName, "drawable", pkgName );
        return context.getResources().getDrawable( resourceId, null );
    }
    static class ViewHolder
    {

        ImageView avatarView;
        TextView roomNameView;
        RelativeLayout relativeLayout_bg;
        TextView status;
    }
}
