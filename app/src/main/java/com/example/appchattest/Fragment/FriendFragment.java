package com.example.appchattest.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.appchattest.Adapter.ListFriendsAdapter;
import com.example.appchattest.FindFriendsActivity;
import com.example.appchattest.FriendRequestActivity;
import com.example.appchattest.Model.Contacts;
import com.example.appchattest.Model.User;
import com.example.appchattest.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.util.ArrayList;


public class FriendFragment extends Fragment implements ValueEventListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ListFriendsAdapter listFriendsAdapter;

    private ListView listView;
    private ImageView imageViewFindFriend;
    private TextView textView;
    private EditText editTextSearch;
    private ImageView imageViewSearch;
    private String textSearch = "";

    private DatabaseReference databaseReference;
    private ArrayList<User> listFriends = new ArrayList<>();
    private ProgressBar progressBar;

    private ArrayList<String> listUidFriend = new ArrayList<>();
    private FirebaseUser user;


    public FriendFragment(ArrayList<String> listuidFriends) {
        this.listUidFriend = listuidFriends;
    }

    public FriendFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_friend, container, false);
        addControls(rootView);

        imageViewFindFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FindFriendsActivity.class);
                startActivity(intent);
            }
        });
        user = FirebaseAuth.getInstance().getCurrentUser();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FriendRequestActivity.class);
                startActivity(intent);
            }
        });

        imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchListFriend();
            }
        });

        editTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    SearchListFriend();
                    return true;
                }
                return false;
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference();
        // Vấn đề nan giải
        databaseReference.addValueEventListener(this);

        listFriendsAdapter = new ListFriendsAdapter(getActivity(), listFriends);

        listView.setAdapter(listFriendsAdapter);

        return rootView;
    }

    private void SearchListFriend() {


        textSearch = editTextSearch.getText().toString();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listFriends.clear();
                listUidFriend.clear();
                Iterable<DataSnapshot> nodechild = dataSnapshot.child("friends").child(user.getUid()).getChildren();
                for (DataSnapshot data : nodechild) {
                    String uid = data.getKey();
                    listUidFriend.add(uid);
                }
                Iterable<DataSnapshot> nodechild1 = dataSnapshot.child("users").getChildren();
                for (DataSnapshot data : nodechild1) {
                    for (String c : listUidFriend) {
                        User user1 = data.getValue(User.class);
                        Log.d("DSDSSSSSSSSS", c + " " + user1.getUid());
                        if (c.equals(user1.getUid())) {
                            if ((user1.name.toUpperCase()).contains(textSearch.toUpperCase())) {
                                listFriends.add(user1);
                            }
                        }
                    }
                }
                listFriendsAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addControls(View view) {
        imageViewFindFriend = view.findViewById(R.id.imageView_addfriend);
        textView = view.findViewById(R.id.button_item_Loimoiketban);
        listView = view.findViewById(R.id.listView_friend);
        progressBar = view.findViewById(R.id.progressBar_fragment_friend);
        editTextSearch = view.findViewById(R.id.editText_search_friend);
        imageViewSearch = view.findViewById(R.id.image_icon_search_friend);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        int x = 0;
        listFriends.clear();
        listUidFriend.clear();
        Iterable<DataSnapshot> nodechild = dataSnapshot.child("friends").child(user.getUid()).getChildren();
        for (DataSnapshot data : nodechild) {
            String uid = data.getKey();
            listUidFriend.add(uid);
        }
        Iterable<DataSnapshot> nodechild1 = dataSnapshot.child("users").getChildren();
        for (DataSnapshot data : nodechild1) {
            User user = data.getValue(User.class);
            for (String c : listUidFriend) {
                if (c.equals(user.getUid())) {
                    listFriends.add(user);
                }
            }

        }
        progressBar.setVisibility(View.INVISIBLE);
        listFriendsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
