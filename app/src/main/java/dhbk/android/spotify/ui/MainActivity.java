package dhbk.android.spotify.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import dhbk.android.spotify.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    // TODO: 7/10/16 start spotify
    public void onStartSpotifyStreamer(View view) {
        Intent spotifySearch = new Intent(MainActivity.this, SearcherActivity.class);
        startActivity(spotifySearch);
    }

}
