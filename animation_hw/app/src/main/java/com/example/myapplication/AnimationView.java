package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.myapplication.CharacterType.*;

enum CharacterType {ATTACK, IDLE, WALK, DEAD}
public class AnimationView extends View {

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

    public AnimationView(Context context) {
        super(context);
        prepareContent();

    }

    private void prepareContent() {
        sprites = new ArrayList<>();
        CreateIsland(100, 100, R.drawable.island1);
        CreateIsland(500, 100, R.drawable.island2);
        CreateIsland(900, 100, R.drawable.island3);
        CreateIsland(100, 500, R.drawable.island4);
        CreateIsland(500, 500, R.drawable.island5);
        CreateIsland(900, 500, R.drawable.island6);
        CreateIsland(100, 900, R.drawable.island7);
        CreateIsland(500, 900, R.drawable.island8);
        CreateIsland(900, 900, R.drawable.island9);
    //    CreateBuilding(50, 150, R.drawable.kindergarten01);
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
                CreateAngel(100 + i * 200, 100 + j * 200, realType);
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

    private void CreateAngel(int left, int top, CharacterType type) {
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
                            bitmaps[i], 300, 300, false);
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
                            bitmaps[i], 300, 300, false);
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
                            bitmaps[i], 300, 300, false);
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
                            bitmaps[i], 300, 300, false);
                    bitmaps[i] = resizedBitmap;
                }
                break;
            }
        }

        Zoombie newSprite = new Zoombie(bitmaps, left, top, 0, 0);
        sprites.add(newSprite);
    }

    private void CreateBuilding(int left, int top, int resID) {
        createSpriteWithASingleImage(left, top, resID);

    }

    private void createSpriteWithASingleImage(int left, int top, int resID) {
        Bitmap[] bitmaps = new Bitmap[1];
        bitmaps[0] = BitmapFactory.decodeResource(getResources(), resID);
        Zoombie newSprite = new Zoombie(bitmaps, left, top, 0, 0);
        sprites.add(newSprite);
    }

    private void CreateIsland(int left, int top, int resID) {
        createSpriteWithASingleImage(left, top, resID);
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
//                tempIdx = getSelectedSpriteIndex(x, y);
//                if (tempIdx!=-1) {
//                    selectSprite(tempIdx);
//                    invalidate();
//                }
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

    private void endDrag(float x, float y) {
        processDrag(x, y);
        selectedIndex = -1;
    }

    private void processDrag(float x, float y) {
        if (selectedIndex!=-1)
        {
            float dx = x - oldx;
            float dy = y - oldy;
            oldx = x; oldy = y;
            sprites.get(selectedIndex).left+=dx;
            sprites.get(selectedIndex).top+=dy;
            invalidate();
        }
    }

    int selectedIndex = -1;

    private void beginDrag(float x, float y) {
        int tempIdx = getSelectedSpriteIndex(x, y);
        if (tempIdx!=-1) {
            selectedIndex = tempIdx;
            oldx = x;
            oldy = y;

            selectSprite(selectedIndex);
            if (onSpriteClickListener!=null)
            {
                onSpriteClickListener.OnSpriteClick(this, x, y, selectedIndex);
            }
            invalidate();
        }
        else selectedIndex = -1;


    }

    private void selectSprite(int newIndex) {
        for (int i=0; i<sprites.size(); i++)
            if (i == newIndex)
                sprites.get(i).State = 1;
            else
                sprites.get(i).State = 0;

    }

    private int getSelectedSpriteIndex(float x, float y) {
        for (int i=sprites.size()-1;i>=0; i--)
            if (sprites.get(i).isSelected(x, y))
                return i;

        return -1;
    }
}
