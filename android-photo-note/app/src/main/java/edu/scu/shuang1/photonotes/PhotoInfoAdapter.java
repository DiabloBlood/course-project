package edu.scu.shuang1.photonotes;

/**
 * Created by Blood on 2016/5/19.
 */
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PhotoInfoAdapter extends RecyclerView.Adapter<PhotoInfoAdapter.PhotoViewHolder> {

    private static Context context;
    private List<PhotoAudioInfo> photoAudioInfoList;

    public PhotoInfoAdapter(Context context, List<PhotoAudioInfo> photoAudioInfoList) {
        this.context = context;
        this.photoAudioInfoList = photoAudioInfoList;
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {

        protected ImageView thumbnailImage;
        protected TextView captionText;
        protected String audioFileName;
        protected double latitude;
        protected double longitude;

        public PhotoViewHolder(View view) {
            super(view);
            captionText = (TextView)  view.findViewById(R.id.custom_row_caption);
            thumbnailImage = (ImageView) view.findViewById(R.id.custom_row_thumbnail_image);

            view.setOnClickListener(new View.OnClickListener() {
                //view photo
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ViewPhotoActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    String caption = captionText.getText().toString();
                    intent.putExtra("caption", caption);


                    thumbnailImage.buildDrawingCache();
                    Bitmap image = thumbnailImage.getDrawingCache();

                    Bundle extrasBundle = new Bundle();
                    extrasBundle.putParcelable("imagebitmap",image);
                    extrasBundle.putString("audioFileName", audioFileName);
                    extrasBundle.putDouble("latitude", latitude);
                    extrasBundle.putDouble("longitude", longitude);
                    intent.putExtras(extrasBundle);

                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return photoAudioInfoList.size();
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder photoViewHolder, int i) {
        PhotoAudioInfo photoAudioInfo = photoAudioInfoList.get(i);
        photoViewHolder.captionText.setText(photoAudioInfo.getCaption());
        photoViewHolder.thumbnailImage.setImageURI(Uri.parse(photoAudioInfo.getImageFilename()));
        photoViewHolder.audioFileName = photoAudioInfo.getAudioFilename();
        photoViewHolder.latitude = photoAudioInfo.getLatitude();
        photoViewHolder.longitude = photoAudioInfo.getLongitude();
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_photo_row, viewGroup, false);
        return new PhotoViewHolder(itemView);
    }

    public void onItemDismissed(int position) {
        photoAudioInfoList.remove(position);
        notifyItemRemoved(position);
    }

    public boolean onItemMove(int fromPosition, int toPosition) {
        PhotoAudioInfo temp = photoAudioInfoList.get(fromPosition);
        photoAudioInfoList.set(fromPosition, photoAudioInfoList.get(toPosition));
        photoAudioInfoList.set(toPosition, temp);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

}
