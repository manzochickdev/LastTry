package xyz.manzodev.lasttry;

import android.content.Context;
import android.content.res.AssetManager;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;

public class DataBinding {
    @BindingAdapter("setProfileImage")
    public static void setProfileImage(CircleImageView view, int id){
        if(id!=0){
            File file = new File(view.getContext().getFilesDir(),Integer.toString(id));
            try {
                Bitmap bm = BitmapFactory.decodeStream(new FileInputStream(file));
                view.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                view.setImageResource(R.drawable.image_holder);
                e.printStackTrace();
            }
        }
        else  view.setImageResource(R.drawable.image_holder);
    }

    @BindingAdapter("setFont")
    public static void setFont(TextView view, int type){
        AssetManager assetManager = view.getContext().getAssets();
        Typeface typeface;
        if (type==0){
            typeface = Typeface.createFromAsset(assetManager,"Fonts/RobotoRegular.ttf");
        }
        else typeface = Typeface.createFromAsset(assetManager,"Fonts/icon.ttf");
        view.setTypeface(typeface);
    }
}
