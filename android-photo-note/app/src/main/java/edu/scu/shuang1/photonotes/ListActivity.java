package edu.scu.shuang1.photonotes;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    PhotoInfoDbHelper photoInfoDbHelper;
    Cursor cursor;
    List<PhotoAudioInfo> photoAudioInfoList;
    PhotoInfoAdapter photoInfoAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        initDatabase();
        initRecyclerView();

        createActionBar();

        TouchHelperCallback callback = new TouchHelperCallback(photoInfoAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add_photo:
                Intent addPhotoIntent = new Intent(ListActivity.this, AddPhotoActivity.class);
                startActivityForResult(addPhotoIntent, 1234);
                break;
            case R.id.unistall:
                Uri packageURI = Uri.parse("package:"+ ListActivity.class.getPackage().getName());
                Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
                startActivity(uninstallIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Advance Photo Notes");
        actionBar.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.actionbar_background));
    }

    private void initDatabase() {
        photoInfoDbHelper = new PhotoInfoDbHelper(this);
        cursor = photoInfoDbHelper.fetchAll();
        photoAudioInfoList = photoInfoDbHelper.getPhotoInfoList();
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_photo_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        photoInfoAdapter = new PhotoInfoAdapter(getApplicationContext(), photoAudioInfoList);
        recyclerView.setAdapter(photoInfoAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != 100) {
            return;
        }

        PhotoAudioInfo photoAudioInfo = (PhotoAudioInfo) data.getSerializableExtra("photoinfo");
        photoInfoDbHelper.add(photoAudioInfo);
        photoAudioInfoList.add(photoAudioInfo);
        recyclerView.setAdapter(photoInfoAdapter);
    }

}
