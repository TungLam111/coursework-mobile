package com.example.google_map;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    List<CoffeeHouse> myList ;
    static int zoom = 13;
    private BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior;
    ConstraintLayout bottomSheet ;
    ImageView imageView3, imageView, imageView2;
    TextView textView4, textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        bottomSheet = findViewById(R.id.bottomSheet);

        imageView = bottomSheet.findViewById(R.id.imageView);
        imageView2 = bottomSheet.findViewById(R.id.imageView2);
        imageView3 = bottomSheet.findViewById(R.id.imageView3);
        textView4 = bottomSheet.findViewById(R.id.textView4);
        textView2 = bottomSheet.findViewById(R.id.textView2);
        setDefaultImage();
        initCoffeeHouse();
        initBottomSheet();
        clickShowBottom();
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                String term = textView4.getText().toString();
                intent.putExtra(SearchManager.QUERY, term);
                startActivity(intent);

            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = textView2.getText().toString();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });
    }

    private void setDefaultImage() {
        Bitmap bitmap = resizeMapIcons("suga", 100, 100);
        imageView.setImageBitmap(bitmap);
        bitmap = resizeMapIcons("link", 100, 100);
        imageView2.setImageBitmap(bitmap);
        bitmap = resizeMapIcons("search", 100, 100);
        imageView3.setImageBitmap(bitmap);
    }

    Button buttonBottomSheetPersistent;

    private void initBottomSheet (){
        buttonBottomSheetPersistent = (Button) findViewById(R.id.buttonBottomSheetPersistent);
        //#2 Initializing the BottomSheetBehavior
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        BottomSheetBehavior.BottomSheetCallback haha = null;
        haha = new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                String temp;
                if  (newState == BottomSheetBehavior.STATE_EXPANDED ) {
                    temp = "Close Persistent Bottom Sheet";
                }
                else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    temp = "Open Persistent Bottom Sheet";
                }
                else temp = "Persistent Bottom Sheet";

                buttonBottomSheetPersistent.setText(temp);
                }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        };
        bottomSheetBehavior.addBottomSheetCallback(haha);
    }

    private void clickShowBottom(){
        buttonBottomSheetPersistent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                whatHappen(0);
            }
        });
    }

    private void whatHappen(int check){
        int state ;
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            if (check == 1) state = BottomSheetBehavior.STATE_EXPANDED;
            else state = BottomSheetBehavior.STATE_COLLAPSED;
        }
        else
            state = BottomSheetBehavior.STATE_EXPANDED;
        bottomSheetBehavior.setState(state);
    }

    private void initCoffeeHouse(){
        myList = new ArrayList<CoffeeHouse>();
        myList.add(new CoffeeHouse("The Coffee House Nguyễn Thị Thập", 10.739751,106.7031814, "https://www.youtube.com/watch?v=52nfjRzIaj8", "the coffee house nguyen thi thap", "anime6"  ));
        myList.add(new CoffeeHouse("The Coffee House Phạm Ngũ Lão", 10.7690291,106.6923028, "https://www.thecoffeehouse.com/", "pham ngu lao the coffee house", "anime1"  ));
        myList.add(new CoffeeHouse("The Coffee House Ba Tháng Hai", 10.7661595,106.6618167, "https://www.thecoffeehouse.com/", "Ba thang Hai the coffee house", "anime2" ));
        myList.add(new CoffeeHouse("The Coffee House Cao Thắng Quận 3", 10.7603755,106.6516232, "https://www.thecoffeehouse.com/", "Cao thang quan 3 the coffee house", "anime3"  ));
        myList.add(new CoffeeHouse("The Coffee House Nguyễn Thái Bình", 10.7672425,106.6860327, "https://www.thecoffeehouse.com/", "nguyen thai binh the coffee house", "anime4"  ));
        myList.add(new CoffeeHouse("The Coffee House Ngô Thời Nhiệm", 10.7713582,106.670742, "https://www.thecoffeehouse.com/", "Ngo Thoi Nhiem The coffee house", "anime5" ));
        myList.add(new CoffeeHouse("Coffee House Factory", 10.7784724,106.6847646, "https://www.thecoffeehouse.com/", "Coffee house factory", "anime2"  ));
        myList.add(new CoffeeHouse("The Coffee House Ba Đình", 10.7346449,106.713558, "https://www.thecoffeehouse.com/", "Ba Dinh The coffee house", "anime7"  ));
        myList.add(new CoffeeHouse("The Coffee House Thanh Xuân", 10.7332799,106.7100034, "https://www.thecoffeehouse.com/", "Thanh Xuan The coffee house", "anime8"  ));
        myList.add(new CoffeeHouse("The Coffee House Lâm Văn Bền", 10.7446167,106.7162375, "https://www.thecoffeehouse.com/", "Lam Van Ben the coffee house", "anime1"  ));

    }

    LatLngBounds.Builder builder = new LatLngBounds.Builder();
    private void createMarker() {
        double sumLat = 0;
        double sumLng = 0;
        for (int i = 0 ;i< myList.size(); i++){
            addMarker(myList.get(i), i);
            sumLat += myList.get(i).getLat();
            sumLng += myList.get(i).getLng();
        }


        LatLngBounds bounds = builder.build();

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);

        mMap.animateCamera(cu);
    }

    private HashMap<Marker, Integer> mHashMap = new HashMap<Marker, Integer>();

    private void addMarker(CoffeeHouse coffeeHouse, int i) {
        LatLng location = new LatLng(coffeeHouse.getLat(), coffeeHouse.getLng());
        Bitmap resizeIcon = resizeMapIcons("shooky", 100, 100);
        MarkerOptions newMarker = new MarkerOptions().position(location).title(coffeeHouse.getName())
                .icon(BitmapDescriptorFactory.fromBitmap(resizeIcon));

        Marker marker = mMap.addMarker(newMarker);
        mHashMap.put(marker, i);
        builder.include(newMarker.getPosition());

    }

    private Bitmap resizeMapIcons(String iconName,int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        createMarker();
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                int index = mHashMap.get(marker);
                CoffeeHouse coffeeHouse = myList.get(index);
                String name = coffeeHouse.getName();
                String url = coffeeHouse.getUrl();
                String avatar = coffeeHouse.getAvatar();
                String detail = coffeeHouse.getDetail();

                TextView titleView = (TextView) bottomSheet.findViewById(R.id.textView);
                TextView urlView = (TextView) bottomSheet.findViewById(R.id.textView2);
                TextView detailView = (TextView) bottomSheet.findViewById(R.id.textView4);
                ImageView avatarView = (ImageView) bottomSheet.findViewById(R.id.imageView);

                titleView.setText(name);
                urlView.setText(url);
                detailView.setText(detail);
                avatarView.setImageBitmap(resizeMapIcons(avatar, 100,100));

                whatHappen(1);

                return false;

            }
        });

    }
}