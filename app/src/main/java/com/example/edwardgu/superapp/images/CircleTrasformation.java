package com.example.edwardgu.superapp.images;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;

/**
 * Created by EdwardGu on 2016/10/16.
 */

public class CircleTrasformation implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        Bitmap ret ;
        int sw=source.getWidth();
        int sh=source.getHeight();

        int bw=Math.min(sw,sh);

        //1.找到原始图片的中心部分
        int scx=sw>>1;
        int hbw=bw>>1;
        //裁剪
        ret=Bitmap.createBitmap(source,scx-hbw,0,bw,bw);
        Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        BitmapShader shader=new BitmapShader(
                ret,
                Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP
        );
        paint.setShader(shader);


        //2.生成新的空的内容
        ret=Bitmap.createBitmap(bw,bw, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(ret);
        canvas.drawColor(Color.TRANSPARENT);

        int half = bw >> 1;
        canvas.drawCircle(half, half, half,paint);

        canvas=null;
        paint=null;
        //原始图像不用了，
        source.recycle();
        return ret;
    }

    /**
     * 名称，唯一标识，如果picasso传入两个一样的transformation就可以识别到
     * @return
     */
    @Override
    public String key() {
        return "CircleImagesTransformation";
    }
}
