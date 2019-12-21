package com.example.appchattest.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchattest.Model.User;
import com.example.appchattest.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class AdapterRecyclerDanhSachBan extends RecyclerView.Adapter<AdapterRecyclerDanhSachBan.ViewHolder> {



    public class ViewHolder extends RecyclerView.ViewHolder {

        ArrayList<User> listUser;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            textView=itemView.findViewById( R.id.textView_RoomName );

        }
    }
    ArrayList <User> listUser;
    int resource;

    public AdapterRecyclerDanhSachBan(ArrayList<User> listUser, int resource) {
        this.listUser = listUser;
        this.resource = resource;
    }

    @NonNull
    @Override
    public AdapterRecyclerDanhSachBan.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from( parent.getContext() ).inflate( resource,parent,false );
        ViewHolder viewHolder=new ViewHolder(  view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerDanhSachBan.ViewHolder holder, int position) {
        User user=listUser.get( position );

        holder.textView.setText( user.getName() );


    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }


}
