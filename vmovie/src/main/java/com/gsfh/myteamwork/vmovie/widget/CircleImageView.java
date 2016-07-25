package com.gsfh.myteamwork.vmovie.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by admin on 2016/7/21.
 */
public class CircleImageView extends BaseImageView{
    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public static Bitmap getBitmap(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        canvas.drawOval(new RectF(0.0f, 0.0f, width, height), paint);

        return bitmap;
    }

    @Override
    public Bitmap getBitmap() {
        return getBitmap(getWidth(), getHeight());
    }





//    private Bitmap bitmap;
//
//    private Rect bitmapRect=new Rect();
//
//    private PaintFlagsDrawFilter pdf=new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
//
//    private Paint paint = new Paint();
//
//    {
//
//        paint.setStyle(Paint.Style.STROKE);
//
//        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
//
//        paint.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
//
//    }
//
//    private Path mPath=new Path();
//
//
//    public void setImageBitmap(Bitmap bitmap)
//
//    {
//
//        this.bitmap=bitmap;
//
//    }
//
//    @Override
//
//    protected void onDraw(Canvas canvas) {
//
//
//        if(null==bitmap)
//
//        {
//
//            return;
//
//        }
//
//        bitmapRect.set(0, 0, getWidth(), getHeight());
//
//        canvas.save();
//
//        canvas.setDrawFilter(pdf);
//
//        mPath.reset();
//
//        canvas.clipPath(mPath); // makes the clip empty
//
//        mPath.addCircle(getWidth()/2, getWidth()/2, getHeight()/2, Path.Direction.CCW);
//
//        canvas.clipPath(mPath, Region.Op.REPLACE);
//
//        canvas.drawBitmap(bitmap, null, bitmapRect, paint);
//
//        canvas.restore();
//
//    }



}
