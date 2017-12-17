package com.inderjs.olaplaystudio;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Inderjeet on 15-Dec-17.
 */

public class SongsAdapter extends ArrayAdapter<Song> {

    /**
     * Constructs a new {@link SongsAdapter}.
     ** @param context     of the app
     * @param songs is the list of songs, which is the data source of the adapter
     */
    public SongsAdapter(Context context, List<Song> songs) {
        super(context, 0,songs);
    }

    /**
     * Returns a list item view that displays information about the song at the given position
     * in the list of songs.
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.song_list_item, parent, false);
        }
        Song currentSong = getItem(position);
        TextView txtSongName = (TextView) listItemView.findViewById(R.id.songNameTv);
        TextView txtArtist = (TextView) listItemView.findViewById(R.id.artistTv);
        final ImageView imgCover = (ImageView) listItemView.findViewById(R.id.coverImg);

        txtSongName.setText(currentSong.getmSongName());
        txtArtist.setText(currentSong.getmArtist());



        Picasso.Builder builder = new Picasso.Builder(getContext());
        builder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
               imgCover.setImageDrawable(getContext().getResources().getDrawable(R.drawable.songicon));
            }
        });
        builder.downloader(new OkHttpDownloader(getContext()));
        builder.build().load(currentSong.getmCover()).into(imgCover);



        return listItemView;
    }

}