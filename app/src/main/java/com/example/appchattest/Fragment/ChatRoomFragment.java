package com.example.appchattest.Fragment;

import android.os.Bundle;
import android.view.*;
import androidx.fragment.app.Fragment;

import com.example.appchattest.Model.User;
import com.example.appchattest.R;

import java.util.ArrayList;


public class ChatRoomFragment extends Fragment {

    public ChatRoomFragment(ArrayList<User> listFriends) {

    }

    public ChatRoomFragment() {

    }
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_chatroom, container, false );
    }
}
