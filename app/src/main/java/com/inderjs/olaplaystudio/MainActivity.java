package com.inderjs.olaplaystudio;

import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {





    private String PLAY_URL =
            "http://starlord.hackerearth.com/studio";


    private SongsAdapter mAdapter;

    private ListView songListView;

    public MediaPlayer mediaPlayer = new MediaPlayer();

    private Song song;
    private int songIndex = 1;
    private ImageView mPlayIcon, mNextSong, mPlayerCover;
    public CardView mSongCard, mPlayerCard;

    public TextView mSongCardTv, mArtistCardTv, mPlayerSongName, mPlayerArtist;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
           // case  R.id.menu_setting :
               // startActivity(new Intent(this,SettingActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mPlayIcon = (ImageView)findViewById(R.id.playIcon);


        mSongCard = (CardView)findViewById(R.id.playCard);

        mSongCardTv = (TextView)findViewById(R.id.songNameCardTv);
        mArtistCardTv = (TextView)findViewById(R.id.artistCardTv);



        mPlayIcon = (ImageView)findViewById(R.id.playIcon);
        mNextSong = (ImageView)findViewById(R.id.forwardIcon);

        mSongCard.setVisibility(View.GONE);

        mArtistCardTv.setSelected(true);



        mPlayIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    mPlayIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_arrow));
                }else {
                    mediaPlayer.start();
                    mPlayIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                }
            }
        });





        SongAsyncTask task = new SongAsyncTask();
        task.execute(PLAY_URL);




        songListView = (ListView)findViewById(R.id.list);
        mAdapter = new SongsAdapter(MainActivity.this, new ArrayList<Song>());
        songListView.setAdapter(mAdapter);





        songListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                song = mAdapter.getItem(i);
                songIndex = i;
                Uri songUri = Uri.parse(song.getmUrl());



                mediaPlayer.stop();
                mediaPlayer.reset();


                mPlayIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                mSongCard.setVisibility(View.VISIBLE);
                mSongCardTv.setText(song.getmSongName());
                mArtistCardTv.setText(song.getmArtist());





                SongPlayAsyncTask taskPlay = new SongPlayAsyncTask();
                taskPlay.execute(songUri.toString());

            }
        });







        mNextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mediaPlayer.stop();
                mediaPlayer.reset();



                songIndex +=1;

                if(songIndex==10){
                    songIndex=0;
                }


                song = mAdapter.getItem(songIndex);

                Uri songUri = Uri.parse(song.getmUrl());

                mPlayIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                mSongCard.setVisibility(View.VISIBLE);
                mSongCardTv.setText(song.getmSongName());
                mArtistCardTv.setText(song.getmArtist());


                SongPlayAsyncTask taskPlay = new SongPlayAsyncTask();
                taskPlay.execute(songUri.toString());





            }
        });

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }




    private class SongAsyncTask extends AsyncTask<String,Void,List<Song>> {

        private ProgressDialog progressDialog;

        @Override
        protected List<Song> doInBackground(String... strings) {
            List<Song> result = QueryUtils.fetchSongData(PLAY_URL);
            return result;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(List<Song> data) {
            mAdapter.clear();
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }

            if (data != null && !data.isEmpty()){
                mAdapter.addAll(data);
            }
        }
    }




    private class SongPlayAsyncTask extends AsyncTask<String,Void,String> {

        private ProgressDialog progressDialog;


        @Override
        protected String doInBackground(String... params) {

            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(params[0]);
                mediaPlayer.prepare();
                mediaPlayer.start();


            } catch (Exception e) {
                // TODO: handle exception
            }

            return null;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


        }
    }



}
