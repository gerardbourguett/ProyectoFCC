package com.example.gabc_.appprueba;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView img;
    private TextView mVibrant;
    private TextView mVibrantDark;
    private TextView mVibrantLight;
    private TextView mMuted;
    private TextView mMutedDark;
    private TextView mMutedLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVibrant = (TextView) findViewById(R.id.vibrant);
        mVibrantDark = (TextView) findViewById(R.id.darkVibrant);
        mVibrantLight = (TextView) findViewById(R.id.lightVibrant);
        mMuted = (TextView) findViewById(R.id.muted);
        mMutedDark = (TextView) findViewById(R.id.darkMuted);
        mMutedLight = (TextView) findViewById(R.id.lightMuted);


        Button btn = findViewById(R.id.tomarFoto);
        img = findViewById(R.id.imageId);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });


    }

    private void setViewSwatch(TextView view, Palette.Swatch swatch, final String title) {
        if (swatch != null) {
            // Set the background color of a layout based on the vibrant color
            view.setBackgroundColor(swatch.getRgb());
            view.setText(title);
            view.setTextColor(swatch.getTitleTextColor());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();

        Bitmap bitmap = (Bitmap) data.getExtras().get("data");

        if (bitmap != null) {
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    setViewSwatch(mVibrant, palette.getVibrantSwatch(), "Vibrant");
                    setViewSwatch(mVibrantDark, palette.getDarkVibrantSwatch(), "Dark Vibrant");
                    setViewSwatch(mVibrantLight, palette.getLightVibrantSwatch(), "Light Vibrant");
                    setViewSwatch(mMuted, palette.getMutedSwatch(), "Muted");
                    setViewSwatch(mMutedDark, palette.getDarkMutedSwatch(), "Dark Muted");
                    setViewSwatch(mMutedLight, palette.getLightMutedSwatch(), "Light Muted");
                }
            });
        }
        img.setImageBitmap(bitmap);
    }
}
