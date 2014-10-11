package net.lion_stuido.lionstudio.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

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
public class ImageGridFragment extends Fragment implements AbsListView.OnScrollListener, AbsListView.OnItemClickListener {

    private static final String TAG = "ImageGridFragment";

    private StaggeredGridView mGridView;
    private StaggeredGridAdapter mAdapter;

    public static ImageGridFragment newInstance() {
        ImageGridFragment fragment = new ImageGridFragment();
        return fragment;
    }

    public ImageGridFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image_grid, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGridView = (StaggeredGridView) getActivity().findViewById(R.id.grid_view);
        mAdapter = new StaggeredGridAdapter(getActivity(), android.R.layout.simple_list_item_1, new ArrayList<Album>());
        requestAlbumList();
        mGridView.setAdapter(mAdapter);
        mGridView.setOnScrollListener(this);
        mGridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "GOT " + position, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Log.d(TAG, "onScroll firstVisibleItem:" + firstVisibleItem +
                " visibleItemCount:" + visibleItemCount +
                " totalItemCount:" + totalItemCount);
        /*if (!mHasRequestedMore) {
            int lastInScreen = firstVisibleItem + visibleItemCount;
            if (lastInScreen >= totalItemCount) {
                Log.d(TAG, "onScroll lastInScreen - so load more");
                mHasRequestedMore = true;
                onLoadMoreItems();
            }
        }*/

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
