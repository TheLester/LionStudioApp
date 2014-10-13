package net.lion_stuido.lionstudio.fragments;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.etsy.android.grid.StaggeredGridView;

import net.lion_stuido.lionstudio.R;
import net.lion_stuido.lionstudio.adapters.StaggeredGridAdapter;
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

    private StaggeredGridView mGridView;
    private StaggeredGridAdapter mAdapter;

    public static AlbumGridFragment newInstance() {
        AlbumGridFragment fragment = new AlbumGridFragment();
        return fragment;
    }

    public AlbumGridFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
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
        mGridView = (StaggeredGridView) getActivity().findViewById(R.id.staggered_grid_view);
        mAdapter = new StaggeredGridAdapter(getActivity(), android.R.layout.simple_list_item_1, new ArrayList<Album>());
        requestAlbumList();
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PhotoGridFragment photoGridFragment = PhotoGridFragment.newInstance(((Album) parent.getItemAtPosition(position)).getId());
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, photoGridFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Get item selected and deal with it
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        return true;
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
