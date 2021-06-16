package com.example.recyclerview;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder> {

    Context mContext;
    List<User> users ;

    public MyAdapter(Context mContext, List<User> mData) {
        this.mContext = mContext;
        this.users = mData;
    }

    @NonNull
    @Override
    public MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout;
        layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new MyAdapterViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterViewHolder holder, int position) {
        holder.tv_phonenumber.setText(users.get(position).getPhoneNumber());
        holder.tv_link.setText(users.get(position).getLink());
        holder.tv_content.setText(users.get(position).getContent());
        holder.tv_title.setText(users.get(position).getTitle());
        holder.img_user.setImageResource(users.get(position).getUserPhoto());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MyAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title, tv_content, tv_link, tv_phonenumber ;
        ImageView img_user;

        public MyAdapterViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tv_title = (TextView)itemView.findViewById(R.id.tv_title); // name
            tv_content = (TextView)itemView.findViewById(R.id.tv_content); // description of that person
            tv_phonenumber = (TextView)itemView.findViewById(R.id.tv_phonenumber); // phone number
            tv_link = (TextView)itemView.findViewById(R.id.tv_link); //
            img_user = (ImageView)itemView.findViewById(R.id.imageView3);

            //tv_title.setOnClickListener();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext.getApplicationContext(), tv_title.getText(), Toast.LENGTH_SHORT).show();

                }
            });

            tv_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext.getApplicationContext(), tv_link.getText(), Toast.LENGTH_SHORT).show();
                    fbClick(v);
                }
            });
            tv_phonenumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext.getApplicationContext(), tv_phonenumber.getText(), Toast.LENGTH_SHORT).show();
                    callClick(v);
                }
            });


        }

        public void fbClick(View view) {
            mContext.startActivity(getOpenFacebookIntent(view));
        }
        public void callClick(View view) {
            mContext.startActivity(getCallIntent(view));
        }
        public Intent getOpenFacebookIntent(View view) {
            try {
                TextView link = view.findViewById(R.id.tv_link);
                Toast.makeText(mContext.getApplicationContext(), link.getText(), Toast.LENGTH_SHORT).show();
                return new Intent(Intent.ACTION_VIEW, Uri.parse(link.getText().toString()));
            } catch (Exception e) {
                return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=pk7ESz6vtyA&list=RDGMEM6ijAnFTG9nX1G-kbWBUCJAVMpk7ESz6vtyA&start_radio=1"));
            }
        }
        public Intent getCallIntent(View view ){
            try {
                TextView phoneNumberView = view.findViewById(R.id.tv_phonenumber);
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + phoneNumberView.getText()));
                if (ActivityCompat.checkSelfPermission(mContext.getApplicationContext(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                }
                return callIntent;
            } catch (Exception e){
                return new Intent(Intent.ACTION_DIAL, Uri.parse("tel:0829976232")); // my phone <333
            }
        }
    }
}
