package com.example.biguncler.wp_launcher.util;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Biguncler on 12/5/16.
 */

public class BitmapUtil {
    /**
     * 获取bitmap的平均亮度
     * @param bitmap
     */
    public static int  getBitmapBrightness(Bitmap bitmap){
        // 此处为网上copy的原本
       /* Drawable localDrawable = wpm.getDrawable();

        Bitmap bitmap = Bitmap

                .createBitmap(

                        localDrawable.getIntrinsicWidth(),

                        localDrawable.getIntrinsicHeight(),

                        localDrawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888

                                : Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);

        localDrawable.setBounds(0, 0, localDrawable.getIntrinsicWidth(),

                localDrawable.getIntrinsicHeight());

        localDrawable.draw(canvas);



        int localWidth = this.getWindowManager().getDefaultDisplay().getWidth();

        int y[] = { 0, 4, 9, 13, 18, 23, 28, 33, 38, 43, 48 };

        int x[] = { 0, localWidth / 8, localWidth * 2 / 8, localWidth * 3 / 8,

                localWidth * 4 / 8, localWidth * 5 / 8, localWidth * 6 / 8,

                localWidth * 7 / 8, localWidth };



        int r;

        int g;

        int b;

        int number = 0;

        double bright = 0;

        Integer localTemp;

        for (int i = 0; i < x.length; i++) {

            for (int j = 0; j < y.length; j++) {

                number++;

                localTemp = (Integer) bitmap.getPixel(x[i], y[j]);

                r = (localTemp | 0xff00ffff) >> 16 & 0x00ff;

                g = (localTemp | 0xffff00ff) >> 8 & 0x0000ff;

                b = (localTemp | 0xffffff00) & 0x0000ff;



                bright = bright + 0.299 * r + 0.587 * g + 0.114 * b;

                Log.i("xiao", "bright = " + bright);

            }

        }

        localDrawable = null;

        bitmap = null;

        bright =(int)(bright / number);
*/

        if(bitmap==null) return -1;
        int width=bitmap.getWidth()-2;
        int height=bitmap.getHeight()-2;
        // 设置bitmap上的取样点
        List<Integer> listx=new ArrayList<>();
        List<Integer> listy=new ArrayList<>();
        double scale=Arith.div(width,height,4);
        int minPoint=10;
        int maxPoint=minPoint;
        if(scale>1) {
            if(scale>5) scale=5;
            maxPoint = (int) (Arith.mul(minPoint,scale));
            for (int i = 0; i <= minPoint; i++) {
                listy.add((int) (Arith.mul(i, Arith.div(height, minPoint))));
            }
            for (int j = 0; j <= maxPoint; j++) {
                listx.add((int) (Arith.mul(j, Arith.div(width, maxPoint,4))));
            }
        }else{
            if(scale<0.2) scale=0.2;
            maxPoint = (int) (Arith.div(minPoint,scale,4));
            for (int i = 0; i <= minPoint; i++) {
                listx.add((int) (Arith.mul(i, Arith.div(width, minPoint))));
            }
            for (int j = 0; j <= maxPoint; j++) {
                listy.add((int) (Arith.mul(j, Arith.div(height, maxPoint,4))));
            }
        }
        //遍历所有的点计算总亮度
        double totalBrightness=0;
        for(Integer x:listx){
            for(Integer y:listy){

                int localTemp = (Integer) bitmap.getPixel(x, y);

                int r = (localTemp | 0xff00ffff) >> 16 & 0x00ff;

                int g = (localTemp | 0xffff00ff) >> 8 & 0x0000ff;

                int b = (localTemp | 0xffffff00) & 0x0000ff;
                // 计算某点的亮度公式
                totalBrightness = totalBrightness + 0.299 * r + 0.587 * g + 0.114 * b;

            }
        }

        //计算平均亮度,亮度值范围[0,255], 大于128属于高亮,小于128属于低亮
        int averageBrightness=(int)Arith.div(totalBrightness,(listx.size()*listy.size()),4);

        return averageBrightness;


    }


    /**
     * 获取图片这色调
     * @param bitmap
     * @return
     */
    public static int[]  getBitmapTintColor(Bitmap bitmap){
        if(bitmap==null) return new int[3];
        int width=bitmap.getWidth()-2;
        int height=bitmap.getHeight()-2;
        // 设置bitmap上的取样点
        List<Integer> listx=new ArrayList<>();
        List<Integer> listy=new ArrayList<>();
        double scale=Arith.div(width,height,4);
        int minPoint=10;
        int maxPoint=minPoint;
        if(scale>1) {
            if(scale>5) scale=5;
            maxPoint = (int) (Arith.mul(minPoint,scale));
            for (int i = 0; i <= minPoint; i++) {
                listy.add((int) (Arith.mul(i, Arith.div(height, minPoint))));
            }
            for (int j = 0; j <= maxPoint; j++) {
                listx.add((int) (Arith.mul(j, Arith.div(width, maxPoint,4))));
            }
        }else{
            if(scale<0.2) scale=0.2;
            maxPoint = (int) (Arith.div(minPoint,scale,4));
            for (int i = 0; i <= minPoint; i++) {
                listx.add((int) (Arith.mul(i, Arith.div(width, minPoint))));
            }
            for (int j = 0; j <= maxPoint; j++) {
                listy.add((int) (Arith.mul(j, Arith.div(height, maxPoint,4))));
            }
        }
        //遍历所有的点计算总亮度
        double totalBrightness=0;
        double red=0;
        double green=0;
        double blue=0;
        for(Integer x:listx){
            for(Integer y:listy){

                int localTemp = (Integer) bitmap.getPixel(x, y);

                int r = (localTemp | 0xff00ffff) >> 16 & 0x00ff;

                int g = (localTemp | 0xffff00ff) >> 8 & 0x0000ff;

                int b = (localTemp | 0xffffff00) & 0x0000ff;
                // 计算某点的亮度公式
                //otalBrightness = totalBrightness + 0.299 * r + 0.587 * g + 0.114 * b;
                red=red+r;
                green=green+g;
                blue=blue+b;

            }
        }

        //计算平均亮度,亮度值范围[0,255], 大于128属于高亮,小于128属于低亮

        red=Arith.div(red,(listx.size()*listy.size()),4);
        green=Arith.div(green,(listx.size()*listy.size()),4);
        blue=Arith.div(blue,(listx.size()*listy.size()),4);

        return new int[]{(int)red,(int)green,(int)blue};


    }




    /**
     * 从一张大图片中裁剪出一张小图片
     * @param bitmap
     * @param startX
     * @param startY

     * @return
     */
    public  static Bitmap cropBitmap(Bitmap bitmap,int startX,int startY,int width,int height){
        if(bitmap==null){
            return null;
        }
        int bitmapWidth=bitmap.getWidth();
        int bitmapHeight=bitmap.getHeight();
        // 超出bitmap大小
        if(width>bitmapWidth||height>bitmapHeight){
            return null;
        }
        return Bitmap.createBitmap(bitmap,startX,startY,width,height);
    }








}
