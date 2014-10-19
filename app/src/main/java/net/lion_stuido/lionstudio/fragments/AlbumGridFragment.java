package net.lion_stuido.lionstudio.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import net.lion_stuido.lionstudio.R;
import net.lion_stuido.lionstudio.adapters.AlbumGridAdapter;
import net.lion_stuido.lionstudio.model.Album;
import net.lion_stuido.lionstudio.utils.AppController;
import net.lion_stuido.lionstudio.utils.GsonRequest;

import java.util.ArrayList;

import static net.lion_stuido.lionstudio.utils.Constants.ALBUM_URL;
import static net.lion_stuido.lionstudio.utils.Constants.DEFAULT_DOMAIN;

/**
 * Created by lester on 10.10.14.
 */
public class AlbumGridFragment extends Fragment implements AdapterView.OnItemClickListener{

    private static final String TAG = "ImageGridFragment";

    private GridView mGridView;
    private AlbumGridAdapter mAdapter;

    public static AlbumGridFragment newInstance() {
        AlbumGridFragment fragment = new AlbumGridFragment();
        return fragment;
    }

    public AlbumGridFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_album_grid, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGridView = (GridView) getActivity().findViewById(R.id.staggered_grid_view);
        mAdapter = new AlbumGridAdapter(getActivity(), android.R.layout.simple_list_item_1, new ArrayList<Album>());
        requestAlbumList();
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);

    }
    @Override
    public void onResume() {
        super.onResume();
        setIndicatorEnabled(true);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Album currentAlbum = (Album) parent.getItemAtPosition(position);
        PhotoGridFragment photoGridFragment = PhotoGridFragment.newInstance(currentAlbum);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, photoGridFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        setIndicatorEnabled(false);
    }
    private void setIndicatorEnabled(boolean status){
        NavigationDrawerFragment mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.getmDrawerToggle().setDrawerIndicatorEnabled(status);
    }
    private void requestAlbumList() {
        GsonRequest<Album[]> albumReq = new GsonRequest<Album[]>(DEFAULT_DOMAIN + ALBUM_URL, Album[].class, new Response.Listener<Album[]>() {
            @Override
            public void onResponse(Album[] albums) {
                for (Album album : albums)
                    mAdapter.add(album);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(albumReq);
    }

}
