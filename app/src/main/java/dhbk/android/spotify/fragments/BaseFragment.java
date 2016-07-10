package dhbk.android.spotify.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;


/**
 * Created by huynhducthanhphong on 7/10/16.
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(activateRetainInstance());
        onCreateFragment(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View commonView = inflater.inflate(fragmentLayoutResource(), container, false);
        ButterKnife.bind(BaseFragment.this, commonView);
        return commonView;
    }

    protected int fragmentLayoutResource() {
        return 0;
    }

    protected boolean activateRetainInstance() {
        return false;
    }

    protected void onCreateFragment(Bundle savedInstanceState) {
    }


}