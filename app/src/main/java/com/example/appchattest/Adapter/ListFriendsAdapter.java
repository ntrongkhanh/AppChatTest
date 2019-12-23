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
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.appchattest.Model.Contacts;
import com.example.appchattest.Model.User;
import com.example.appchattest.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ListFriendsAdapter extends BaseAdapter {
    private List<User> listUser;

    private LayoutInflater layoutInflater;
    private Context context;
    private DatabaseReference databaseReference;
    private ArrayList<Contacts> listContacts=new ArrayList<>();

    public ListFriendsAdapter(Context aContext, List<User> list)
    {

        this.context = aContext;
        this.listUser = list;



        layoutInflater  = LayoutInflater.from( context );
        databaseReference=FirebaseDatabase.getInstance().getReference();
    }


    @Override
    public int getCount()
    {
        return listUser.size();
    }

    @Override
    public Object getItem(int position)
    {
        return listUser.get(position);
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
            convertView = layoutInflater.inflate( R.layout.friend_item_layout, null);//lay layout tu xml sang convertView
            holder = new ViewHolder();
            holder.avatarView = (ImageView) convertView.findViewById(R.id.imageView_avatar_friend);
            holder.friendName = (TextView) convertView.findViewById(R.id.textView_friendName);
            holder.status = (TextView) convertView.findViewById(R.id.textView_state_friend);
            convertView.setTag(holder);// set convert view la holder
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        final User friend = this.listUser.get(position);//lay phan tu trong mang

        holder.friendName.setText(friend.getName());
        StorageReference flieRef=FirebaseStorage.getInstance().getReference().child("avatar/"+friend.uid+"/avatar.png");
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
        TextView friendName;
        TextView status;
    }
}
