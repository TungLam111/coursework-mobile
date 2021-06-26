package com.example.myapplication;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

enum TYPE {OBJECT, DIALOG}
public class Zoombie {
    public String Id;
    public int nBMPs;
    public int iBMP;
    public Bitmap[] BMPs;
    public int left;
    public int top;
    public int width;
    public int height;
    public int State;
    public TYPE type;
    public boolean IsVisible;
    public String content;

    public Zoombie(Bitmap[] bmps, int left, int top, int width, int height, String id)
    {
        this.Id = id;
        BMPs = bmps;
        nBMPs = bmps.length;
        iBMP = 0;
        this.left = left;
        this.top = top;
        if (width == 0 && height==0)
        {
            width = bmps[0].getWidth();
            height = bmps[0].getHeight();
        }
        this.width = width;
        this.height = height;
        this.type = TYPE.OBJECT;
        this.IsVisible = true;
        this.State = 0;
    }

    public Zoombie(Bitmap[] bmps, int left, int top, int width, int height, TYPE type, String id, String content)
    {
        this.Id = id;
        BMPs = bmps;
        nBMPs = bmps.length;
        iBMP = 0;
        this.left = left;
        this.top = top;
        if (width == 0 && height==0)
        {
            width = bmps[0].getWidth();
            height = bmps[0].getHeight();
        }
        this.width = width;
        this.height = height;
        this.type = TYPE.OBJECT;
        this.type = type;
        if (type == TYPE.DIALOG) {
            this.IsVisible = false;
        }
        this.State = 0;
        this.content = content;
    }

    int d1  = 0 , d2 = 1;
    public void update()
    {
        iBMP = (iBMP+1) % nBMPs;
        if (State==1) // selected
        {
            d1+=d2;
            if (Math.abs(d1)>10)
                d2 *= -1;
        }
    }

    @SuppressLint("ResourceAsColor")
    public void draw(Canvas canvas) {
        if (IsVisible == true) {
            if (State == 0) // unselected
            {
                canvas.drawBitmap(BMPs[iBMP], left, top, null);
            }


            else // selected
            {
                Rect sourceRect = new Rect(0, 0, width - 1, height - 1);
                Rect destRect = new Rect(left - d1, top - d1, left + width - 1 + d1, top + height - 1 + d1);

                canvas.drawBitmap(BMPs[iBMP], sourceRect, destRect, null);

               if (type == TYPE.DIALOG){
                   Canvas newCanvas = new Canvas(BMPs[iBMP]);

                   Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
                   paintText.setColor(R.color.light_blue);
                   paintText.setTextSize(50f);
                   //paintText.setStyle(Paint.Style.FILL);
                   paintText.setShadowLayer(1f, 150f, 150f, Color.BLACK);

                   Rect rectText = new Rect();
                   paintText.getTextBounds(content, 0, content.length(), rectText);
                   paintText.setTextAlign(Paint.Align.CENTER);

                   newCanvas.drawText(
                           content,
                           0f, Float.intBitsToFloat(rectText.height()), paintText
                   );
               }


            }
        }
    }
    public boolean isSelected(float x, float y) {
        return (x>=left && x < left+width && y>=top && y <top+height);
    }
}
