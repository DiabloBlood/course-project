package edu.scu.shuang1.photonotes;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class AddPhotoActivity extends AppCompatActivity implements
        MediaPlayer.OnCompletionListener, SensorEventListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {

    //photo proportion 500*888
    //request code 用来识别一个request，收到result intent以后，callback提供相同的code
    public final static int TAKE_PHOTO_REQUEST_CODE = 1234;
    private String imageFileName;
    private String caption;
    boolean ifTake = false;
    //voice note feature
    private static final String LOG_TAG = "AudioRecordTest";
    private String mAudioFilename = "";
    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;
    //canvas drawing feature
    TouchDrawView touchDrawView;
    //shaking sensor
    private SensorManager mSensorManager;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity
    //geo get location
    GoogleApiClient mGoogleApiClient = null;
    Location mLastLocation;
    Double latitude = -1.0;
    Double longitude = -1.0;
    boolean ifConnection = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        touchDrawView = (TouchDrawView) findViewById(R.id.view_draw_line);
        if (touchDrawView == null) {
            Log.e("sichao", "we have a problem");
        }

        //take and  save photo feature
        takePhoto();
        savePhoto();
        acquireRunTimePermissions();

        //voice note
        initVoiceNote();
        //canvas drawing
        initShakingSensor();
        //google location
        initGoogleLocation();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        toast("Location services connected");
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {
        toast("onConnectionSuspended() is called : i=" + i);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        ifConnection = false;
        toast("onConnectionFailed() is called");
    }

    private void startTrackLocation() {
        toast("Start record location!");
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(2000);
        mLocationRequest.setFastestInterval(500);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, AddPhotoActivity.this);
    }

    private void stopTrackLocation() {
        toast("Stop record location");
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, AddPhotoActivity.this);
    }

    void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    public void initGoogleLocation() {
        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    public void initShakingSensor() {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public void onSensorChanged(SensorEvent se) {
        float x = se.values[0];
        float y = se.values[1];
        float z = se.values[2];
        mAccelLast = mAccelCurrent;
        mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
        float delta = mAccelCurrent - mAccelLast;
        mAccel = mAccel * 0.9f + delta * 0.1f; // perform low-cut filter

        displayAcceleration();
    }

    private void displayAcceleration() {
        View view = findViewById(android.R.id.content);
        float accel = Math.abs( mAccel);
        if (accel > 1.0f) {
            touchDrawView.clear();
        }
    }

    public void takePhoto() {
        Button takePhotoButton = (Button) findViewById(R.id.button_take_photo);
        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ifTake = true;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) == null) {
                    Toast.makeText(getApplicationContext(), "Cannot take pictures on this device!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //geo
                if(ifConnection) {
                    startTrackLocation();
                }

                imageFileName = getOutputFileName();
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(imageFileName));
                startActivityForResult(intent, TAKE_PHOTO_REQUEST_CODE);
            }
        });
    }

    public void savePhoto() {
        Button savePhotoButton = (Button) findViewById(R.id.button_save_photo);
        assert savePhotoButton != null;
        savePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ifTake == true) {
                    if(ifConnection) {
                        stopTrackLocation();
                    }

                    if (mLastLocation != null) {
                        latitude = mLastLocation.getLatitude();
                        longitude = mLastLocation.getLongitude();
                    }

                    EditText editText = (EditText) findViewById(R.id.caption_edit_field);
                    caption = editText.getText().toString();
                    PhotoAudioInfo photoinfo = new PhotoAudioInfo(caption, imageFileName, mAudioFilename, latitude, longitude);
                    Intent intent = new Intent(AddPhotoActivity.this, ListActivity.class);
                    intent.putExtra("photoinfo", photoinfo);
                    touchDrawView.setDrawingCacheEnabled(true);
                    Bitmap newPhoto = touchDrawView.getDrawingCache();
                    String pathName = imageFileName.substring(imageFileName.indexOf("/storage"));
                    try {
                        newPhoto.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(pathName));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    setResult(100, intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Please Take A Photo!!!", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private String getOutputFileName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename =
                "file://"
                        + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        + "/JPEG_"
                        + timeStamp
                        + ".jpg";
        return filename;
    }

    private String getmAudioFilename() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename =""
                        + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
                        + "/Audio_"
                        + timeStamp
                        + ".3gp";
        return filename;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode != TAKE_PHOTO_REQUEST_CODE || resultCode != RESULT_OK) {
            return;
        }

        String pathName = imageFileName.substring(imageFileName.indexOf("/storage"));
        Drawable background = BitmapDrawable.createFromPath(pathName);
        touchDrawView.setBackground(background);
    }


    private void acquireRunTimePermissions() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    111);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode != 111) return;
        if (grantResults.length >= 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Great! We have the permission!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Cannot write to external storage! App will not work properly!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Toast.makeText(getApplicationContext(), "playback is completed", Toast.LENGTH_SHORT).show();
    }

    public void initVoiceNote() {


        final Button recordButton = (Button)findViewById(R.id.button_record);
        recordButton.setText("Start recording");
        recordButton.setOnClickListener(new View.OnClickListener() {
            boolean mStartRecording = true;

            @Override
            public void onClick(View v) {
                if (mStartRecording) {
                    recordButton.setText("Stop recording");
                    startRecording();
                } else {
                    recordButton.setText("Start recording");
                    stopRecording();
                }
                mStartRecording = !mStartRecording;

            }
        });

        final Button playButton = (Button)findViewById(R.id.button_playback);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer = new MediaPlayer();
                mPlayer.setOnCompletionListener(AddPhotoActivity.this);
                try {
                    mPlayer.setDataSource(mAudioFilename);
                    mPlayer.prepare();
                    mPlayer.start();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "prepare() failed");
                }
            }
        });
    }

    private void startRecording() {
        mAudioFilename = getmAudioFilename();
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mAudioFilename);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }
}

