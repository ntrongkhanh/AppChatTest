package com.example.appchattest.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appchattest.FindFriendsActivity;
import com.example.appchattest.MainNavigationActivity;
import com.example.appchattest.Model.User;
import com.example.appchattest.R;


public class FriendFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private ImageView imageViewFindFriend;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_friend, container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        addControls(view);
        imageViewFindFriend.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent( getActivity(), FindFriendsActivity.class );
               startActivity( intent );

            }
        } );

    }

    private void addControls(View view) {
        imageViewFindFriend=view.findViewById( R.id.imageView_addfriend );
    }
}
