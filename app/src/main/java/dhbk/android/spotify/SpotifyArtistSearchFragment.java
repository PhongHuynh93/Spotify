package dhbk.android.spotify;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class SpotifyArtistSearchFragment extends Fragment {


    private OnSearchItemClickListener onSearchItemClickListener;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spotify_artist_search, container, false);
    }

}
