package com.example.biguncler.wp_launcher.view;

import android.animation.ValueAnimator;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Biguncler on 3/24/2019.
 */

public class GalleryCellView extends ImageView {
    private List<String> imageUrls;
    private Rect rect;

    public GalleryCellView(Context context) {
        super(context);
        init();
    }

    public GalleryCellView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GalleryCellView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setAlpha(0.5f);
        start();
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }


    public void start(){
        imageUrls=getSystemPhotoList(getContext());
        Observable.interval(0,20, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if(imageUrls.isEmpty()) return;
                        String url=imageUrls.get((int)(aLong%imageUrls.size()));
                        Glide.with(getContext().getApplicationContext())
                                .load(url)
                                .into(new SimpleTarget<GlideDrawable>() {
                                    @Override
                                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                       // 保存imageview的初始高度
                                        if(rect==null){
                                            rect=new Rect(getLeft(),getTop(),getRight(),getBottom());
                                       }
                                       // 重置topMargin
                                        layout(rect.left,rect.top,rect.right,rect.bottom);

                                        setImageDrawable(resource);
                                        Rect bounds=resource.getBounds();
                                        //用初始高度计算移动的margin
                                        float scale=bounds.height()*1f/bounds.width();
                                        int magin= (int) (scale*getWidth()-rect.height());
                                        startAnimator(-magin);
                                    }
                                });

                    }
                });
    }

    public static List<String> getSystemPhotoList(Context context)
    {
        List<String> result = new ArrayList<String>();
        /*Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor == null || cursor.getCount() <= 0) return null; // 没有图片
        while (cursor.moveToNext())
        {
            int index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String path = cursor.getString(index); // 文件地址
            File file = new File(path);
            if (file.exists())
            {
                result.add(path);
                if(result.size()>5) break;
            }
        }*/

        String dirPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/aibizhi/portrait";
        File dir=new File(dirPath);
        if(!dir.exists()){
            dir.mkdir();
        }
        File[] files=dir.listFiles();
        if(files!=null && files.length>0){
            for(File f:files){
                if(f.isFile() && isImageFile(f.getPath())){
                    result.add(f.getAbsolutePath());
                    if(result.size()>5) break;
                }
            }
        }
        return result ;
    }

    private void startAnimator(final int margin){
        ValueAnimator animator=ValueAnimator.ofInt(0,margin);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value= (int) valueAnimator.getAnimatedValue();

                layout(rect.left,rect.top+value,rect.right,rect.bottom);
                /*LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)getLayoutParams();
                params.topMargin=value;
                setLayoutParams(params);*/
            }
        });
        animator.setDuration(15000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setTarget(this);
        animator.start();
    }

    public static boolean isImageFile(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        if (options.outWidth == -1) {
            return false;
        }
        return true;
    }

}
