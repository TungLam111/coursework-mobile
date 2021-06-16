package com.example.homework_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // try block to hide Action bar
        try {
            this.getSupportActionBar().hide();
        }
        // catch block to handle NullPointerException
        catch (NullPointerException ignored) {
        }
        this.customListViewViaBaseAdapter();
    }

    // This method shows how to use BaseAdapter to customize ListView widget.
    private void customListViewViaBaseAdapter()
    {
        final String[] titleArr = { "AndroidFP", "Deitel", "Google", "iPhoneFP",
                "JavaFP", "JavaHTP", "Windows7", "Max OS X"};
        final String edit_title = "Edit";

        GradientDrawable gradientdrawable;
        GradientDrawable rest;
        int colors[] = {
                Color.WHITE,
                Color.LTGRAY,
                 };
        gradientdrawable = new GradientDrawable();
        gradientdrawable.setShape(GradientDrawable.RECTANGLE);
        gradientdrawable.setColors(colors);

        rest = new GradientDrawable();
        rest.setShape(GradientDrawable.RECTANGLE);
        rest.setColors(colors);

        ArrayList<Map<String,Object>> itemDataList = new ArrayList<Map<String,Object>>();;

        int titleLen = titleArr.length;
        for(int i =0; i < titleLen; i++) {
            Map<String,Object> listItemMap = new HashMap<String,Object>();
            listItemMap.put("title", titleArr[i]);
            itemDataList.add(listItemMap);
        }

        // Create a BaseAdapter instance.
        BaseAdapter customBaseAdapter = new BaseAdapter() {
            // Return list view item count.
            @Override
            public int getCount() {
                return titleArr.length;
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int itemIndex, View itemView, ViewGroup viewGroup) {

                if(itemView == null)
                {   // First inflate object.
                    itemView = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_item, null);
                }

                // Find related view object inside the itemView.
                TextView titleView = (TextView)itemView.findViewById(R.id.baseUserTitle);

                final String title = titleArr[itemIndex];
                titleView.setText(title);

                // Find the button in list view row.
                TextView itemButton = (TextView)itemView.findViewById(R.id.baseUserButton);
                itemButton.setBackgroundColor(Color.LTGRAY);
                itemButton.setText(edit_title);

               // titleView.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.button_shadow));
                titleView.setBackgroundColor(Color.LTGRAY);

               // buttonLayout.setBackground(ContextCompat.getDrawable(getBaseContext(), android.R.drawable.dialog_holo_light_frame));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    itemButton.setElevation(5);
                    titleView.setElevation(5);
                }

                titleView.setBackground(gradientdrawable);
                itemButton.setBackground(gradientdrawable);

                itemButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "You click " + title , Toast.LENGTH_SHORT).show();
                    }
                });

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TextView itemTitle = v.findViewById(R.id.baseUserTitle);

                        Toast.makeText(MainActivity.this, "You select item is  " + itemTitle.getText() + " , " , Toast.LENGTH_SHORT).show();
                    }
                });
                return itemView;
            }
        };

        // Get the ListView object.
        ListView listView = (ListView)findViewById(R.id.listViewExample);
        // Set the custom base adapter to it.
        listView.setAdapter(customBaseAdapter);


//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
//                Object clickItemObj = adapterView.getAdapter().getItem(index);
//                HashMap clickItemMap = (HashMap)clickItemObj;
//                String itemTitle = (String)clickItemMap.get("title");
//
//                Toast.makeText(MainActivity.this, "You select item is  " + itemTitle, Toast.LENGTH_SHORT).show();
//            }
//        });

        int color_button = Color.LTGRAY;
        TextView btn = findViewById(R.id.button2);
        btn.setBackgroundColor(color_button);

        TextView btn_clear_tag = findViewById(R.id.clear_tag_button);
        btn_clear_tag.setBackgroundColor(color_button);
        btn_clear_tag.setTextColor(Color.BLACK);

        btn.setTextColor(Color.BLACK);
        btn_clear_tag.setBackground(rest);
        btn.setBackground(rest);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btn.setElevation(5);
            btn_clear_tag.setElevation(5);
        }

        //set Click handler on Save Buttons
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSave(v);
            }
        });
        //set Clear Tags handler on Clear Tags Button
        btn_clear_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickClearTags(v);
            }
        });
    }

    public void onClickSave(View view) {
        Toast.makeText(MainActivity.this, "On want save", Toast.LENGTH_SHORT).show();
    }

    public void onClickClearTags(View view) {
        Toast.makeText(MainActivity.this, "On want clear tags", Toast.LENGTH_SHORT).show();
    }
}

