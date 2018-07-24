package com.example.realgodjj.rxjavademo.ui;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.realgodjj.rxjavademo.R;

public class NewActivity extends AppCompatActivity {
    private ImageView ivAddSuccess, iv1, iv2;
    private Button btChangeColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        ivAddSuccess = findViewById(R.id.iv_add_success);
        iv1 = findViewById(R.id.image1);
        iv2 = findViewById(R.id.image2);
//        btChangeColor = findViewById(R.id.bt_change_color);
//
//        btChangeColor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                changeColor();
//            }
//        });

//        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.src_tick);
//        //简单的使用tint改变drawable颜色
//        Drawable drawable1 = getTintDrawable(drawable, ContextCompat.getColor(this, R.color.red));
//        ivAddSuccess.setImageDrawable(drawable1);
//
//
//        int[] colors = new int[]{ContextCompat.getColor(this, R.color.red), ContextCompat.getColor(this, R.color.green)};
//        int[][] states = new int[2][];
//        states[0] = new int[]{android.R.attr.state_pressed};
//        states[1] = new int[]{};
//
//        //该方法6.0以下有效
//        Drawable drawable2 = getStateDrawable(drawable, colors, states);
//        iv1.setImageDrawable(drawable2);
//
//        StateListDrawable stateListDrawable = getStateListDrawable(drawable, states);
//        Drawable drawable3 = getStateDrawable(stateListDrawable, colors, states);
//        iv2.setImageDrawable(drawable3);
    }

    private Drawable getTintDrawable(Drawable drawable, @ColorInt int color) {
        Drawable.ConstantState state = drawable.getConstantState();
        Drawable drawable1 = DrawableCompat.wrap(state == null ? drawable : state.newDrawable()).mutate();
        drawable1.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        DrawableCompat.setTint(drawable1, color);
        return drawable1;
    }

    private Drawable getStateDrawable(Drawable drawable, int[] colors, int[][] states) {
        ColorStateList colorList = new ColorStateList(states, colors);

        Drawable.ConstantState state = drawable.getConstantState();
        drawable = DrawableCompat.wrap(state == null ? drawable : state.newDrawable()).mutate();
        DrawableCompat.setTintList(drawable, colorList);
        return drawable;
    }

    @NonNull
    private StateListDrawable getStateListDrawable(Drawable drawable, int[][] states) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        for (int[] state : states) {
            stateListDrawable.addState(state, drawable);
        }
        return stateListDrawable;
    }


    private void changeColor() {
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.add_suceess);
        DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.red));
        ivAddSuccess.setImageDrawable(drawable);
    }

    private Bitmap changeBitmap(Bitmap bitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        for (int h = 0; h < bitmap.getHeight(); h++) {
            for (int w = 0; w < bitmap.getWidth(); w++) {
                int color = bitmap.getPixel(w, h);
                int red = Color.red(color);
                int green = Color.green(color);
                int blue = Color.blue(color);
                int alpha = Color.alpha(color);
                if (red < 250 && red > 200 && green < 220 && green > 190 && blue < 100 && blue > 0) {
                    red = 138;
                    green = 43;
                    blue = 226;
                    alpha = 255;
                }
                createBitmap.setPixel(w, h, Color.argb(alpha, red, green, blue));
            }
        }
        return bitmap;
    }

//        Drawable.ConstantState state = drawable.getConstantState();
//        Drawable drawable1 = DrawableCompat.wrap(state == null ? drawable : state.newDrawable()).mutate();

}
