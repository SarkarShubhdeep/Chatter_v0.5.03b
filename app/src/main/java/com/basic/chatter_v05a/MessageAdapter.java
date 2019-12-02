package com.basic.chatter_v05a;


import android.graphics.Color;

import android.os.Build;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.Gravity.END;

import static android.view.Gravity.LEFT;
import static android.view.Gravity.RIGHT;
import static android.view.Gravity.START;
import static android.view.Gravity.isHorizontal;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    //Message text Bubble situated in drawable

    private List<Messages> mMessageList;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mUserDatabse;
    private DatabaseReference mChatDatabase = FirebaseDatabase.getInstance().getReference();
    private String timeStamp;

    public MessageAdapter(){}


    public  MessageAdapter(List<Messages> mMessageList){

        this.mMessageList = mMessageList;

    }




    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_single_layout, parent, false);

        return new MessageViewHolder(v);

    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{

        public TextView messageText;
//        public CircleImageView profileImage;
//        public TextView displayName;
        public RelativeLayout relativeList;
        public ImageView messageImage;
        public TextView timeText;



        public MessageViewHolder(View view){

            super(view);

            messageText = (TextView) view.findViewById(R.id.message_text_layout);
//            profileImage = view.findViewById(R.id.message_profile_layout);
//            displayName = view.findViewById(R.id.name_text_layout);
            relativeList = view.findViewById(R.id.message_single_layout);
            messageImage = view.findViewById(R.id.message_image_layout);
            timeText = view.findViewById(R.id.time_text_layout);
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder holder, int position) {




        String current_user_id = mAuth.getCurrentUser().getUid();

        Log.d("cur user",current_user_id);


        final Messages c =  mMessageList.get(position);
//
//        Calendar t = Calendar.getInstance();
//
//        int hour = t.get(Calendar.HOUR_OF_DAY);
//        int minute = t.get(Calendar.MINUTE);
//        String minString = String.valueOf(minute);
//        if(minute <= 9){
//
//            minString = "0" + minute;
//
//        }
//
//        final String time = hour + ":" + minString;

        //final String time = String.valueOf(c.getTime());
        String from_user = c.getFrom();


        String message_type = c.getType();
        long message_time = c.getTime();

        mUserDatabse = FirebaseDatabase.getInstance().getReference().child("Users").child(from_user);
        mUserDatabse.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("name").getValue().toString();
                final String image = dataSnapshot.child("thumb_image").getValue().toString();

//                holder.displayName.setText(name);
//                holder.timeText.setText(time);
//                Picasso.with(holder.messageText.getContext()).load(image).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.avatar)
//                        .into(holder.messageText, new Callback() {
//                            @Override
//                            public void onSuccess() {
//
//                            }
//
//                            @Override
//                            public void onError() {
//                                Picasso.with(holder.messageText.getContext()).load(image)
//                                        .placeholder(R.drawable.avatar).into(holder.profileImage);
//                            }
//                        });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(message_type.equals("text")){
            holder.messageText.setVisibility(View.VISIBLE);
            holder.messageText.setText(c.getMessage());
            holder.messageImage.setVisibility(View.GONE);
            holder.messageImage.setEnabled(false);


        }else{
            holder.messageImage.setVisibility(View.VISIBLE);
            holder.messageText.setVisibility(View.GONE);
            Picasso.with(holder.messageText.getContext()).load(c.getMessage()).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.default_thumbnail)
                    .into(holder.messageImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            Picasso.with(holder.messageText.getContext()).load(c.getMessage()).placeholder(R.drawable.default_thumbnail)
                                    .into(holder.messageImage);
                        }
                    });
            holder.messageText.setEnabled(false);
            Messages time = new Messages();
            String current_time = String.valueOf(time.getTime());
            holder.timeText.setText(current_time);
            holder.messageImage.setMaxHeight(800);
            holder.messageImage.setMaxWidth(800);

        }

        if(from_user.equals(current_user_id)){

            holder.messageText.setBackgroundResource(R.drawable.message_text_sender_background);
            holder.messageText.setTextColor(Color.BLACK);
            holder.relativeList.setGravity(END);


//            holder.profileImage.setVisibility(View.GONE);

        }else{

            holder.messageText.setBackgroundResource(R.drawable.message_text_background);
            holder.messageText.setTextColor(Color.WHITE);
            holder.relativeList.setGravity(START);


//            holder.profileImage.setVisibility(View.VISIBLE);

        }
        holder.messageText.setText(c.getMessage());

        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        String timeString = formatter.format(new Date(message_time));
        holder.timeText.setText(timeString);



    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }


}
