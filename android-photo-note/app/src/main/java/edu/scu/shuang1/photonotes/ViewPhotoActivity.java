package edu.scu.shuang1.photonotes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class ViewPhotoActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {
    private String audioFileName = null;
    private MediaPlayer mPlayer = null;
    private static final String LOG_TAG = "AudioRecordTest";
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo);
        showPhotoAudioInfo();
        initMapButton();
    }

    private void initMapButton() {
        Button MapButton = (Button) findViewById(R.id.button_show_map);
        MapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewPhotoActivity.this, MapViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putDouble("latitude", latitude);
                bundle.putDouble("longitude", longitude);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void showPhotoAudioInfo() {
        Intent intent = getIntent();
        String caption = (String) intent.getStringExtra("caption");

        Bundle extrasBundle = getIntent().getExtras();
        Bitmap bitmap = (Bitmap)extrasBundle.getParcelable("imagebitmap");

        audioFileName = (String) extrasBundle.getString("audioFileName");
        latitude = extrasBundle.getDouble("latitude");
        longitude = extrasBundle.getDouble("longitude");
        Button playButton = (Button)findViewById(R.id.button_play_audio);
        if(audioFileName.equals("")) {
            playButton.setVisibility(playButton.INVISIBLE);
        }

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!audioFileName.equals("")) {
                    mPlayer = new MediaPlayer();
                    mPlayer.setOnCompletionListener(ViewPhotoActivity.this);
                    try {
                        mPlayer.setDataSource(audioFileName);
                        mPlayer.prepare();
                        mPlayer.start();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "prepare() failed");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No Audio was recorded!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView textView = (TextView)findViewById(R.id.textView_caption);
        ImageView imageView = (ImageView) findViewById(R.id.imageView_photo);


        textView.setText(caption);
        imageView.setImageBitmap(bitmap);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Toast.makeText(getApplicationContext(), "playback is completed", Toast.LENGTH_SHORT).show();
    }
}
