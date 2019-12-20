//package com.example.appchattest;
//
//import android.content.Context;
//import android.graphics.drawable.Drawable;
//import android.os.Build;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import androidx.core.content.ContextCompat;
//import com.example.appchattest.Model.User;
//
//import java.util.List;
//
//public class CustomListFriendAdapter extends BaseAdapter {
//    private List<User> listFriends;
//    private LayoutInflater layoutInflater;
//    private Context context;
//
//    public CustomListFriendAdapter(Context aContext, List<User> listFriends)
//    {
//        this.context = aContext;
//        this.listFriends = listFriends;
//        layoutInflater = LayoutInflater.from(aContext);
//    }
//
//    @Override
//    public int getCount()
//    {
//        return listFriends.size();
//    }
//
//    @Override
//    public Object getItem(int positon)
//    {
//        return listFriends.get(positon);
//    }
//
//    @Override
//    public long getItemId(int position)
//    {
//        return position;
//    }
//
//    public View getView(int position, View convertView, ViewGroup parent)
//    {
//        ViewHolder viewHolder;
//
//        if (convertView == null)
//        {
//            convertView = layoutInflater.inflate(R.layout.friend_item_layout, null);
//            viewHolder = new ViewHolder();
//            viewHolder.avatarFriendView = (ImageView)convertView.findViewById(R.id.imageView_avatar_friend);
//            viewHolder.friendNameView = (TextView)convertView.findViewById(R.id.textView_friendName);
//            viewHolder.stateView = (TextView)convertView.findViewById(R.id.textView_state_friend);
//            viewHolder.backgroundView = (RelativeLayout)convertView.findViewById(R.id.relativeLayout_listItem_friend);
//            convertView.setTag(viewHolder);
//        }
//        else
//        {
//            viewHolder = (ViewHolder)convertView.getTag();
//        }
//
//        User friend = this.listFriends.get(position);
//        viewHolder.friendNameView.setText(friend.getName());
//        //Xét status Online hay offline roi mới setText cho trường này
//        if (friend.getState() == null || friend.getState() == "")
//        {
//            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//                viewHolder.stateView.setTextColor(ContextCompat.getColor(context, R.color.color50white));
//            else
//                viewHolder.stateView.setTextColor(context.getResources().getColor(R.color.color50white));
//
//            viewHolder.stateView.setText("Updating...");
//            //viết hàm xét user có online không
//        }
//        else
//        {
//            if (friend.getState() == "Online")
//            {
//                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//                    viewHolder.stateView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryLight));
//                else
//                    viewHolder.stateView.setTextColor(context.getResources().getColor(R.color.colorPrimaryLight));
//            }
//            else
//            {
//                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//                    viewHolder.stateView.setTextColor(ContextCompat.getColor(context, R.color.color50white));
//                else
//                    viewHolder.stateView.setTextColor(context.getResources().getColor(R.color.color50white));
//            }
//            viewHolder.stateView.setText(friend.getState());
//        }
//
//        int imageID = this.getResIdByName(friend.getAvatar());
//        viewHolder.avatarFriendView.setImageResource(imageID);
//
//        return convertView;
//    }
//
//    static class ViewHolder
//    {
//        ImageView avatarFriendView;
//        TextView friendNameView;
//        TextView stateView;
//        RelativeLayout backgroundView;
//    }
//
//    public int getResIdByName(String resName)//can chinh sua de phu hop voi database
//    {
//        String pkgName = context.getPackageName();
//
//        //khong tim thay
//        int resID = context.getResources().getIdentifier(resName, "drawable", pkgName);
//        Log.i("Custom List View ", "Res Name:" + resName + "ResID = " + resID);
//        return resID;
//    }
//
//    public Drawable getResDrawable(String resName)
//    {
//        String pkgName = context.getPackageName();
//
//        final int resourceId = context.getResources().getIdentifier(resName, "drawable", pkgName);
//        return context.getResources().getDrawable(resourceId, null);
//    }
//}
