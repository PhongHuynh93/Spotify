package dhbk.android.spotify.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.internal.Utils;
import dhbk.android.spotify.R;
import dhbk.android.spotify.adapters.ArtistSearchAdapter;
import dhbk.android.spotify.interfaces.OnSearchItemClickListener;
import dhbk.android.spotify.models.Artist;


/**
 * A simple {@link Fragment} subclass.
 */
public class SpotifyArtistSearchFragment extends BaseListFragment {

    @BindView(R.id.artist_search_list)
    ListView artistAlbumsList;
    @BindView(R.id.artist_search_error_text)
    TextView errorText;

    private OnSearchItemClickListener onSearchItemClickListener;
    private ArtistSearchAdapter artistSearchAdapter;

    public SpotifyArtistSearchFragment() {
        // Required empty public constructor
    }

    public static SpotifyArtistSearchFragment newInstance() {
        return new SpotifyArtistSearchFragment();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSearchItemClickListener) {
            onSearchItemClickListener = (OnSearchItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        artistSearchAdapter = new ArtistSearchAdapter();
    }

    @Override
    protected boolean activateRetainInstance() {
        return true;
    }

    // TODO: 7/10/16 this call onCreateView in baseFragment
    @Override
    protected int fragmentLayoutResource() {
        return R.layout.fragment_spotify_artist_search;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Gson gson = new GsonBuilder().create();
        Type artistAdapterType = new TypeToken<List<Artist>>() {
        }.getType();
        String adapterItems = gson.toJson(artistSearchAdapter.getItems(), artistAdapterType);
        outState.putString("adapter_items", adapterItems);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        artistAlbumsList.setAdapter(artistSearchAdapter);
        // TODO: 7/10/16 start search on spotify
        startArtistSearch((EditText) view.findViewById(R.id.search_spotify_streamer), view.getContext());
    }

    // start search on internet
    private void startArtistSearch(final EditText artistSearcher, final Context context) {
        artistSearcher.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        searchArtistAlbums(context, artistSearcher.getText().toString());
                        Utils.hideKeyBoard(textView);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

}
