//19125102
//Phan Đàm Tùng Lâm - 19APCS2

package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<User> listUsers ;
    MyAdapter myAdapter;
    ConstraintLayout rootLayout;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            this.getSupportActionBar().hide();
        }
        // catch block to handle NullPointerException
        catch (NullPointerException ignored) {
        }
        recyclerView = findViewById(R.id.news_rv);
        listUsers = new ArrayList<>();

        listUsers.add(new User("Phan Dam Tung Lam", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", R.drawable.image1, "https://www.facebook.com/profile.php?id=100012621634191", "0829976232"));
        listUsers.add(new User("Phan Dam Tung Lam", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", R.drawable.image2, "https://www.facebook.com/profile.php?id=100012621634191", "0829976232"));
        listUsers.add(new User("Phan Dam Tung Lam", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", R.drawable.image3, "https://www.facebook.com/profile.php?id=100012621634191", "0829976232"));
        listUsers.add(new User("Phan Dam Tung Lam", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", R.drawable.image4, "https://www.facebook.com/profile.php?id=100012621634191", "0829976232"));
        listUsers.add(new User("Phan Dam Tung Lam", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", R.drawable.image5, "https://www.facebook.com/profile.php?id=100012621634191", "0829976232"));
        listUsers.add(new User("Phan Dam Tung Lam", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", R.drawable.image6, "https://www.facebook.com/profile.php?id=100012621634191", "0829976232"));
        listUsers.add(new User("Phan Dam Tung Lam", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", R.drawable.image7, "https://www.facebook.com/profile.php?id=100012621634191", "0829976232"));
        listUsers.add(new User("Phan Dam Tung Lam", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", R.drawable.image8, "https://www.facebook.com/profile.php?id=100012621634191", "0829976232"));
        listUsers.add(new User("Phan Dam Tung Lam", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", R.drawable.image9, "https://www.facebook.com/profile.php?id=100012621634191", "0829976232"));
        listUsers.add(new User("Phan Dam Tung Lam", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", R.drawable.image10,"https://www.facebook.com/profile.php?id=100012621634191", "0829976232"));
        listUsers.add(new User("Phan Dam Tung Lam", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", R.drawable.image1, "https://www.facebook.com/profile.php?id=100012621634191", "0829976232"));
        listUsers.add(new User("Phan Dam Tung Lam", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", R.drawable.image2, "https://www.facebook.com/profile.php?id=100012621634191", "0829976232"));

        myAdapter = new MyAdapter(this, listUsers);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        rootLayout = findViewById(R.id.layout_outside);
        rootLayout.setBackgroundColor(R.color.white);
    }

}