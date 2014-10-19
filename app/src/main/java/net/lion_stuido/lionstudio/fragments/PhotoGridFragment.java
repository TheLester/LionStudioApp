package net.lion_stuido.lionstudio.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import net.lion_stuido.lionstudio.R;
import net.lion_stuido.lionstudio.adapters.PhotoGridAdapter;
import net.lion_stuido.lionstudio.model.Album;
import net.lion_stuido.lionstudio.model.Photo;
import net.lion_stuido.lionstudio.utils.AppController;
import net.lion_stuido.lionstudio.utils.GsonRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.lion_stuido.lionstudio.utils.Constants.DEFAULT_DOMAIN;
import static net.lion_stuido.lionstudio.utils.Constants.PHOTO_URL;

/**
 * Created by lester on 12.10.14.
 */
public class PhotoGridFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final String TAG = "PhotoGridFragment";

    private GridView mGridView;
    private PhotoGridAdapter mAdapter;
    private Album currentAlbum;
    private List<Photo> photoList;

    public static PhotoGridFragment newInstance(Album album) {
        PhotoGridFragment fragment = new PhotoGridFragment();
        fragment.currentAlbum = album;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photo_grid, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGridView = (GridView) getActivity().findViewById(R.id.gridView);
        mAdapter = new PhotoGridAdapter(getActivity(), android.R.layout.simple_list_item_1, new ArrayList<Photo>());
        requestPhotoList(currentAlbum.getId());
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);
    }

    private void requestPhotoList(int album_id) {
        String uri = String.format(DEFAULT_DOMAIN + PHOTO_URL + "?album_id=%d",
                album_id);
        GsonRequest<Photo[]> photosReq = new GsonRequest<Photo[]>(uri,
                Photo[].class, new Response.Listener<Photo[]>() {
            @Override
            public void onResponse(Photo[] photos) {
                photoList = new ArrayList<Photo>(Arrays.asList(photos));
                for (Photo photo : photos) {
                    mAdapter.add(photo);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(photosReq);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Get item selected and deal with it
        switch (item.getItemId()) {
            case android.R.id.home:
                FragmentManager manager = getFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                ft.remove(this);
                ft.commit();
                manager.popBackStack();
                return true;
        }
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PhotoPagerFragment photoPagerFragment = PhotoPagerFragment.newInstance(photoList,position);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, photoPagerFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
