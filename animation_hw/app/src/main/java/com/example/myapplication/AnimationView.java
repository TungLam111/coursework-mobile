package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.myapplication.CharacterType.*;

enum CharacterType {ATTACK, IDLE, WALK, DEAD}
class Constant {
    static final int DISTANCE_VERTICAL = 400;
    static final int DISTANCE_VERTICAL_2 = 350;
    static final int DISTANCE_HORIZONTAL = 600;

    public int getHeight(){
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
    public int getDistanceVertical() {
        return DISTANCE_VERTICAL;
    };

    public int getDistanceHorizontal() {
        return DISTANCE_HORIZONTAL;
    }

    public int getDistanceVertical2(){
        return DISTANCE_VERTICAL_2;
    }

    public int getWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
}
public class AnimationView extends View {

    private int getNavigationBarHeight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            WindowManager w = ((Activity) getContext()).getWindowManager();
            DisplayMetrics metrics = new DisplayMetrics();
            w.getDefaultDisplay()
                    .getMetrics(metrics);
            int usableHeight = metrics.heightPixels;
            w.getDefaultDisplay().getRealMetrics(metrics);
            int realHeight = metrics.heightPixels;
            if (realHeight > usableHeight)
                return realHeight - usableHeight;
            else
                return 0;
        }
        return 0;
    }

    public interface OnSpriteClickListener
    {
        void OnSpriteClick(View view, float x, float y, int selectedIndex);
    }

    private OnSpriteClickListener onSpriteClickListener = null;
    public void setOnSpriteClickListener(OnSpriteClickListener listener)
    {
        onSpriteClickListener = listener;
    }

    private ArrayList<Zoombie> sprites;
    private Timer timer;
    private TimerTask timerTask;
    private float oldx;
    private float oldy;
    private Map<String, String> island_talk;
    private Constant constant;
    private void initMap(){
        island_talk = new HashMap<String, String>();
        for (int i = 0 ;i< 9; i++){
            island_talk.put( "island_" + Integer.toString(i+1) , "talk_" + Integer.toString(i+1));
        }
    }

    public AnimationView(Context context) {
        super(context);
        prepareContent();

    }

    private void prepareContent() {
        initMap();
        constant = new Constant();
        sprites = new ArrayList<>();

        CreateIsland(100, 100, R.drawable.island1, "island_1");
        CreateIsland(100, 100 + constant.getDistanceVertical(), R.drawable.island2, "island_2");
        CreateIsland(100, 100 + 2 * constant.getDistanceVertical(), R.drawable.island3, "island_3");
        CreateIsland(100, 100 + 3 * constant.getDistanceVertical(), R.drawable.island4, "island_4");


        CreateIsland(constant.getDistanceHorizontal(), 50, R.drawable.island5, "island_5");
        CreateIsland(constant.getDistanceHorizontal(), 50 + constant.getDistanceVertical2(), R.drawable.island6, "island_6");
        CreateIsland(constant.getDistanceHorizontal(), 50 + 2 * constant.getDistanceVertical2(), R.drawable.island7, "island_7");
        CreateIsland(constant.getDistanceHorizontal(), 50 + 3 * constant.getDistanceVertical2(), R.drawable.island8, "island_8");
        CreateIsland(constant.getDistanceHorizontal(), 50 + 4 * constant.getDistanceVertical2(), R.drawable.island9, "island_9");

        CreateTalk(200, 50, R.drawable.talk_1, "talk_1", "Xin chào");
        CreateTalk(200, 50 + constant.getDistanceVertical(), R.drawable.talk_2, "talk_2", "Konnichiwa");
        CreateTalk(200, 50 + 2 * constant.getDistanceVertical(), R.drawable.talk_3, "talk_3", "Namaskar");
        CreateTalk(200, 50 + 3 * constant.getDistanceVertical(), R.drawable.talk_4, "talk_4", "Nǐ hǎo");
        CreateTalk(constant.getDistanceHorizontal() + 100, 0, R.drawable.talk_5, "talk_5", "Bonjour");
        CreateTalk(constant.getDistanceHorizontal() + 100, constant.getDistanceVertical2(), R.drawable.talk_6, "talk_6", "Ahn nyong ha se yo");
        CreateTalk(constant.getDistanceHorizontal() + 100, 2*constant.getDistanceVertical2(), R.drawable.talk_7, "talk_7", "Zdravstvuyte");
        CreateTalk(constant.getDistanceHorizontal() + 100, 3*constant.getDistanceVertical2(), R.drawable.talk_8, "talk_8", "Hujambo");
        CreateTalk(constant.getDistanceHorizontal()+ 100, 4*constant.getDistanceVertical2(), R.drawable.talk_9, "talk_9", "Sawasdee");

        int type = 0;
        for (int i = 0; i < 4; i++) {
            type = i % 4;
            CharacterType realType = null;
            for (int j = 0; j < 4; j++){
                switch (type) {
                    case 0: {
                        realType = ATTACK;
                        break;
                    }
                    case 1: {
                        realType = DEAD;
                        break;
                    }
                    case 2: {
                        realType = IDLE;
                        break;
                    }
                    default: {
                        realType = WALK;
                        break;
                    }

                }
              if (i == 0) CreateAngel(50 + j * 300, 0, realType, "");
              else if ( i== 2 ) CreateAngel(50 + j * 300, constant.getHeight() - 120, realType, "");
              else if ( i == 1) CreateAngel(constant.getWidth() - 120, 300 + j * 400, realType, "");
              else CreateAngel(0, 300 + j * 400, realType, "");
            }

        }

        timerTask = new TimerTask() {
            @Override
            public void run() {
                for (int i=0; i<sprites.size(); i++)
                    sprites.get(i).update();
                postInvalidate();
            }
        };
//
        timer = new Timer();
        timer.schedule(timerTask, 1000, 100); // 25 fps



    }

    private void CreateAngel(int left, int top, CharacterType type, String id) {

        CharacterType typeHere = type;
        Bitmap[] bitmaps = null ;
        switch (typeHere){
            case ATTACK:{
                bitmaps = new Bitmap[8];
                bitmaps[0] = BitmapFactory.decodeResource(getResources(), R.drawable.attack_1);
                bitmaps[1] = BitmapFactory.decodeResource(getResources(), R.drawable.attack_2);
                bitmaps[2] = BitmapFactory.decodeResource(getResources(), R.drawable.attack_3);
                bitmaps[3] = BitmapFactory.decodeResource(getResources(), R.drawable.attack_4);
                bitmaps[4] = BitmapFactory.decodeResource(getResources(), R.drawable.attack_5);
                bitmaps[5] = BitmapFactory.decodeResource(getResources(), R.drawable.attack_6);
                bitmaps[6] = BitmapFactory.decodeResource(getResources(), R.drawable.attack_7);
                bitmaps[7] = BitmapFactory.decodeResource(getResources(), R.drawable.attack_8);
                for (int i = 0; i< 8; i++){
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(
                            bitmaps[i], 120, 120, false);
                    bitmaps[i] = resizedBitmap;
                }
                break;
            }
            case IDLE:{
                bitmaps = new Bitmap[15];
                bitmaps[0] = BitmapFactory.decodeResource(getResources(), R.drawable.idle_1);
                bitmaps[1] = BitmapFactory.decodeResource(getResources(), R.drawable.idle_2);
                bitmaps[2] = BitmapFactory.decodeResource(getResources(), R.drawable.idle_3);
                bitmaps[3] = BitmapFactory.decodeResource(getResources(), R.drawable.idle_4);
                bitmaps[4] = BitmapFactory.decodeResource(getResources(), R.drawable.idle_5);
                bitmaps[5] = BitmapFactory.decodeResource(getResources(), R.drawable.idle_6);
                bitmaps[6] = BitmapFactory.decodeResource(getResources(), R.drawable.idle_7);
                bitmaps[7] = BitmapFactory.decodeResource(getResources(), R.drawable.idle_8);
                bitmaps[8] = BitmapFactory.decodeResource(getResources(), R.drawable.idle_9);
                bitmaps[9] = BitmapFactory.decodeResource(getResources(), R.drawable.idle_10);
                bitmaps[10] = BitmapFactory.decodeResource(getResources(), R.drawable.idle_11);
                bitmaps[11] = BitmapFactory.decodeResource(getResources(), R.drawable.idle_12);
                bitmaps[12] = BitmapFactory.decodeResource(getResources(), R.drawable.idle_13);
                bitmaps[13] = BitmapFactory.decodeResource(getResources(), R.drawable.idle_14);
                bitmaps[14] = BitmapFactory.decodeResource(getResources(), R.drawable.idle_15);

                for (int i = 0; i< 15; i++){
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(
                            bitmaps[i], 120, 120, false);
                    bitmaps[i] = resizedBitmap;
                }
                break;
            }
            case WALK:{
                bitmaps = new Bitmap[9];
                bitmaps[0] = BitmapFactory.decodeResource(getResources(), R.drawable.walk_1);
                bitmaps[1] = BitmapFactory.decodeResource(getResources(), R.drawable.walk_2);
                bitmaps[2] = BitmapFactory.decodeResource(getResources(), R.drawable.walk_3);
                bitmaps[3] = BitmapFactory.decodeResource(getResources(), R.drawable.walk_4);
                bitmaps[4] = BitmapFactory.decodeResource(getResources(), R.drawable.walk_5);
                bitmaps[5] = BitmapFactory.decodeResource(getResources(), R.drawable.walk_6);
                bitmaps[6] = BitmapFactory.decodeResource(getResources(), R.drawable.walk_7);
                bitmaps[7] = BitmapFactory.decodeResource(getResources(), R.drawable.walk_8);
                bitmaps[8] = BitmapFactory.decodeResource(getResources(), R.drawable.walk_9);

                for (int i = 0; i< 9; i++){
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(
                            bitmaps[i], 120, 120, false);
                    bitmaps[i] = resizedBitmap;
                }
                break;
            }
            default: {
                bitmaps = new Bitmap[12];
                bitmaps[0] = BitmapFactory.decodeResource(getResources(), R.drawable.dead_1);
                bitmaps[1] = BitmapFactory.decodeResource(getResources(), R.drawable.dead_2);
                bitmaps[2] = BitmapFactory.decodeResource(getResources(), R.drawable.dead_3);
                bitmaps[3] = BitmapFactory.decodeResource(getResources(), R.drawable.dead_4);
                bitmaps[4] = BitmapFactory.decodeResource(getResources(), R.drawable.dead_5);
                bitmaps[5] = BitmapFactory.decodeResource(getResources(), R.drawable.dead_6);
                bitmaps[6] = BitmapFactory.decodeResource(getResources(), R.drawable.dead_7);
                bitmaps[7] = BitmapFactory.decodeResource(getResources(), R.drawable.dead_8);
                bitmaps[8] = BitmapFactory.decodeResource(getResources(), R.drawable.dead_9);
                bitmaps[9] = BitmapFactory.decodeResource(getResources(), R.drawable.dead_10);
                bitmaps[10] = BitmapFactory.decodeResource(getResources(), R.drawable.dead_11);
                bitmaps[11] = BitmapFactory.decodeResource(getResources(), R.drawable.dead_12);
                for (int i = 0; i< 12; i++){
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(
                            bitmaps[i], 120, 120, false);
                    bitmaps[i] = resizedBitmap;
                }
                break;
            }
        }

        Zoombie newSprite = new Zoombie(bitmaps, left, top, 0, 0, "dead");
        sprites.add(newSprite);
    }

    private void CreateTalk(int left, int top, int resID, String id, String content) {
        createSpriteWithASingleImageTalk(left, top, resID, TYPE.DIALOG, id, content);
    }

    private void createSpriteWithASingleImageTalk(int left, int top, int resID, TYPE type, String id, String content) {
        Bitmap[] bitmaps = new Bitmap[1];
        bitmaps[0] = BitmapFactory.decodeResource(getResources(), resID);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(
                bitmaps[0], 300, 300, false);
        bitmaps[0] = resizedBitmap;
        Zoombie newSprite = new Zoombie(bitmaps, left, top, 0, 0, type, id, content);
        sprites.add(newSprite);
    }

    private void createSpriteWithASingleImage(int left, int top, int resID, String ID) {
        Bitmap[] bitmaps = new Bitmap[1];
        bitmaps[0] = BitmapFactory.decodeResource(getResources(), resID);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(
                bitmaps[0], 300, 300, false);
        bitmaps[0] = resizedBitmap;
        Zoombie newSprite = new Zoombie(bitmaps, left, top, 0, 0, ID);
        sprites.add(newSprite);
    }

    private void CreateIsland(int left, int top, int resID, String id) {
        createSpriteWithASingleImage(left, top, resID, id);
    }

    public AnimationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        prepareContent();

    }

    public AnimationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        prepareContent();

    }

    @SuppressLint("NewApi")
    public AnimationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        prepareContent();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i=0; i<sprites.size(); i++)
            sprites.get(i).draw(canvas);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

//        return super.onTouchEvent(event);

        int maskedAction = event.getActionMasked();

        float x = event.getX();
        float y = event.getY();
        int tempIdx;
        switch (maskedAction) {

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {
                // TODO use data
                beginDrag(x, y);

                break;
            }
            case MotionEvent.ACTION_MOVE: { // a pointer was moved
                // TODO use data
                processDrag(x, y);

                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP: {
                endDrag(x, y);

                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                // TODO use data
                break;
            }
        }

        return true;

    }

    int selectedIndex = -1;
    int selectedTalk = -1;
    private void endDrag(float x, float y) {
        processDrag(x, y);
        selectedIndex = -1;
        selectedTalk = -1;
    }

    private void processDrag(float x, float y) {
        if (selectedIndex!=-1)
        {
            float dx = x - oldx;
            float dy = y - oldy;
            oldx = x; oldy = y;
            sprites.get(selectedIndex).left+=dx;
            sprites.get(selectedIndex).top+=dy;
            if (selectedTalk != -1) {
                if (sprites.get(selectedTalk).type == TYPE.DIALOG){
                    sprites.get(selectedTalk).left+= dx;
                    sprites.get(selectedTalk).top+= dy;

                }
            }
            invalidate();
        }
    }

    private void beginDrag(float x, float y) {
        // index of sprite on pressed
        int tempIdx = getSelectedSpriteIndex(x, y);

        if (tempIdx != -1 ) {
            selectedIndex = tempIdx;
            selectedTalk = getSelectedSpriteTalk(tempIdx);

             oldx = x;
             oldy = y;

             selectSprite(selectedIndex);
             if (onSpriteClickListener!=null)
             {
                 onSpriteClickListener.OnSpriteClick(this, x, y, selectedIndex);
             }
             invalidate();

        }
        else {
            selectedIndex = -1;
            selectedTalk = -1;
        }
    }

    private void selectSprite(int newIndex) {
        for (int i = 0; i < sprites.size(); i++)
        {
        if (sprites.get(i).type == TYPE.OBJECT)    {
            if (i == newIndex) {
                //  sprites.get(i).State = 1;
                if (sprites.get(i).State == 0) {
                    sprites.get(i).State = 1;
                    for (int j = 0; j < sprites.size(); j++) {
                        if (i != j && sprites.get(j).Id.equals(island_talk.get(sprites.get(i).Id))) {
                            sprites.get(j).IsVisible = true;
                            sprites.get(j).State = 1;
                        }
                    }
                } else {
                    sprites.get(i).State = 0;
                    for (int j = 0; j < sprites.size(); j++) {
                        if (i != j && sprites.get(j).Id.equals(island_talk.get(sprites.get(i).Id))) {
                            sprites.get(j).IsVisible = false;
                            sprites.get(j).State = 0;
                        }
                    }

                }
            } else {
                sprites.get(i).IsVisible = true;
                sprites.get(i).State = 0;
                for (int j = 0; j < sprites.size(); j++) {
                    if (i != j && sprites.get(j).Id.equals(island_talk.get(sprites.get(i).Id))) {
                        sprites.get(j).IsVisible = false;
                        sprites.get(j).State = 0;

                    }
                }
            }
        }
    }

    }

    private int getSelectedSpriteIndex(float x, float y) {
        for (int i = sprites.size() - 1 ; i >= 0; i--)
        {
            if (sprites.get(i).isSelected(x, y) && sprites.get(i).type == TYPE.OBJECT)
            {
                return i;
            }
        }

        return -1;
    }

    private int getSelectedSpriteTalk(int tempIndex){
        for (int i = sprites.size() - 1; i >= 0; i--){
            if (sprites.get(i).Id.equals(island_talk.get(sprites.get(tempIndex).Id))){
                return i;
            }
        }
        return -1;
    }
}
