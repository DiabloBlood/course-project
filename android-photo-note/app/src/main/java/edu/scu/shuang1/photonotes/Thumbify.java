package edu.scu.shuang1.photonotes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;

import java.io.FileOutputStream;

/**
 * Created by Blood on 2016/5/19.
 */
public class Thumbify {
    static public void generateThumbnail(String imgFile, String thumbFile) {
        try {
            Bitmap picture = BitmapFactory.decodeFile(imgFile);
            Bitmap resized = ThumbnailUtils.extractThumbnail(picture, 120, 120);
            FileOutputStream fileOutputStream = new FileOutputStream(thumbFile);
            resized.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
