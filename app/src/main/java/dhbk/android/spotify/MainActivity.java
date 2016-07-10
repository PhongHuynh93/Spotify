package dhbk.android.spotify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

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
