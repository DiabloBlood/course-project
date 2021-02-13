package edu.scu.shuang1.photonotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Blood on 2016/5/19.
 */
public class PhotoInfoDbHelper extends SQLiteOpenHelper {

    private static final int VERSION= 1;
    private static final String DB_NAME="Photo-Notes.db";

    static private final String SQL_CREATE_TABLE =
            "CREATE TABLE PhotoAudioInfo (" +
                    "  _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  caption TEXT," +
                    "  imageFilename TEXT," +
                    "  audioFileName TEXT," +
                    "  latitude REAL," +
                    "  longitude REAL);";

    private final String SQL_DROP_TABLE = "DROP TABLE PhotoAudioInfo";

    Context context;

    public PhotoInfoDbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // a simple crude implementation that does not preserve data on upgrade
        db.execSQL(SQL_DROP_TABLE);
        db.execSQL(SQL_CREATE_TABLE);

        Toast.makeText(context, "Upgrading DB and dropping data!!!", Toast.LENGTH_SHORT).show();
    }

    public int getMaxRecID() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(_id) FROM PhotoAudioInfo;", null);

        if (cursor.getCount() == 0) {
            return 0;
        } else {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
    }

    public Cursor fetchAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM PhotoAudioInfo;", null);
    }

    public void add(PhotoAudioInfo photoAudioInfo) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("caption", photoAudioInfo.getCaption());
        contentValues.put("imageFilename", photoAudioInfo.getImageFilename());
        contentValues.put("audioFileName", photoAudioInfo.getAudioFilename());
        contentValues.put("latitude", photoAudioInfo.getLatitude());
        contentValues.put("longitude", photoAudioInfo.getLongitude());

        db.insert("PhotoAudioInfo", null, contentValues);
    }

    public void delete(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete("PhotoAudioInfo","_id=?", new String[]{String.valueOf(id)});
    }

    public List<PhotoAudioInfo> getPhotoInfoList() {
        //要加载所有的sets到recyclerView当中
        Cursor cursor = fetchAll();
        cursor.moveToFirst();
        List<PhotoAudioInfo> photoAudioInfoList = new ArrayList<>();

        for(int i = 0; i < cursor.getCount();i++) {
            String caption = cursor.getString(cursor.getColumnIndex("caption"));
            String imageFileName = cursor.getString(cursor.getColumnIndex("imageFilename"));
            String audioFileName = cursor.getString(cursor.getColumnIndex("audioFileName"));
            double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
            double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));

            PhotoAudioInfo photoAudioInfo = new PhotoAudioInfo(caption, imageFileName, audioFileName, latitude, longitude );
            photoAudioInfoList.add(photoAudioInfo);
            cursor.moveToNext();
        }
        return photoAudioInfoList;
    }
}
