package edu.scu.shuang1.photonotes;

import java.io.Serializable;

/**
 * Created by Blood on 2016/5/19.
 */
public class PhotoAudioInfo implements Serializable {

        private String caption;
        private String imageFilename;
        private String audioFilename;
        private double latitude;
        private double longitude;


        public static final String CAPTION_PREFIX = "Caption_";
        public static final String IMAGE_FILENAME_PREFIX = "ImageFileName_";
        public static final String AUDIO_FILENAME_PREFIX = "AudioFileName_";


        public PhotoAudioInfo(String caption, String imageFilename, String audioFilename, double latitude, double longitude) {
            this.caption = caption;
            this.imageFilename = imageFilename;
            this.audioFilename = audioFilename;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public String getCaption(){ return caption; }

        public void setCaption(String caption) {this.caption = caption;}

        public String getImageFilename() { return imageFilename; }

        public void setImageFilename(String imageFilename){this.imageFilename = imageFilename;}

        public void setAudioFilename(String audioFilename) { this.audioFilename = audioFilename; }

        public String getAudioFilename() { return audioFilename; }

        public double getLatitude() {
                return latitude;
        }

        public double getLongitude() {
                return longitude;
        }
}
