package dhbk.android.spotify.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;

import dhbk.android.spotify.R;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Tracks;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.android.MainThreadExecutor;
import retrofit.client.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopArtistSongsFragment extends Fragment {


    public TopArtistSongsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_artist_songs, container, false);
    }

    // TODO: 7/10/16 after search list of artist, listen when click a particle artist, open a top strack of that artist
    public void onSearchArtistTopTracks(String artistId) {
        getArtistTopTrack(artistId);
    }

    private void getArtistTopTrack(String artistId) {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setMessage(getString(R.string.downloading_title));

        SpotifyApi spotifyApi = new SpotifyApi(Executors.newSingleThreadExecutor(), new MainThreadExecutor());
        SpotifyService spotifyService = spotifyApi.getService();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("country", Locale.getDefault().getCountry());
        spotifyService.getArtistTopTrack(artistId, parameters, new Callback<Tracks>() {
            @Override
            public void success(final Tracks tracks, Response response) {

                if (response.getStatus() == 200) {
                    if (tracks != null && getActivity() != null) {

                        progressDialog.dismiss();
                        errorText.setVisibility(View.GONE);
                        topArtistSongsAdapter.setItems(tracks.tracks);

                    } else {
                        topArtistSongsAdapter.clearData();
                        progressDialog.dismiss();
                        errorText.setVisibility(View.VISIBLE);
                    }
                } else {
                    progressDialog.dismiss();
                    errorText.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                progressDialog.dismiss();
                errorText.setVisibility(View.VISIBLE);

            }
        });
    }
}
