package com.example.appchattest.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.appchattest.Model.ChatRoom;
import com.example.appchattest.Model.User;
import com.example.appchattest.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class CustomListAdapter extends BaseAdapter{
    private List<User> listRoom;

    private LayoutInflater layoutInflater;
    private Context context;




    public CustomListAdapter(Context aContext, List<User> listRoom)
    {
        this.context = aContext;
        this.listRoom = listRoom;
        layoutInflater  = LayoutInflater.from(aContext);//?
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

    public View getView(int position, View convertView, ViewGroup parent)//ham nay ke duoc viet lai tu lop cha Adapter
    {
        final ViewHolder holder; //su dung holder de giu lay view trong convertView
        if (convertView == null) //neu convertView = null thi tao moi view va view holder
        {
            convertView = layoutInflater.inflate( R.layout.chatroom_item_layout, null);//lay layout tu xml sang convertView
            holder = new ViewHolder();
            holder.avatarView = (ImageView) convertView.findViewById(R.id.imageView_avatar_chatroom);
            holder.roomNameView = (TextView) convertView.findViewById(R.id.textView_RoomName);
            holder.lastContentView = (TextView) convertView.findViewById(R.id.textView_LastContent);
            holder.relativeLayout_bg = (RelativeLayout) convertView.findViewById(R.id.relativeLayout_listItem);
            convertView.setTag(holder);// set convert view la holder
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }



        User chatRoom = this.listRoom.get(position);//lay phan tu trong mang

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
        ImageView avatarView;
        TextView roomNameView;
        TextView lastContentView;
        RelativeLayout relativeLayout_bg;
    }

}
