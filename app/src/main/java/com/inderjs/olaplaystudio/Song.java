package com.inderjs.olaplaystudio;

/**
 * Created by Inderjeet on 15-Dec-17.
 */

public class Song {

    public final String mSongName;
    public final String mArtist;
    public final String mCover;
    public final String mUrl;

    Song(String songName, String artists, String cover_image, String url) {
        mSongName = songName;
        mArtist = artists;
        mCover= cover_image;
        mUrl = url;
    }

    public String getmSongName() {
        return mSongName;
    }

    public String getmArtist() {
        return mArtist;
    }

    public String getmCover() {
        return mCover;
    }

    public String getmUrl() {
        return mUrl;
    }




}
