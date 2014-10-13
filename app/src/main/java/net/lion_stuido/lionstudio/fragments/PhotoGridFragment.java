package net.lion_stuido.lionstudio.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import net.lion_stuido.lionstudio.R;
import net.lion_stuido.lionstudio.adapters.PhotoGridAdapter;
import net.lion_stuido.lionstudio.model.Photo;
import net.lion_stuido.lionstudio.utils.AppController;
import net.lion_stuido.lionstudio.utils.GsonRequest;

import java.util.ArrayList;

import static net.lion_stuido.lionstudio.utils.Constants.DEFAULT_DOMAIN;
import static net.lion_stuido.lionstudio.utils.Constants.PHOTO_URL;

/**
 * Created by lester on 12.10.14.
 */
public class PhotoGridFragment extends Fragment implements AdapterView.OnItemClickListener{
    private static final String TAG = "PhotoGridFragment";

    private GridView mGridView;
    private PhotoGridAdapter mAdapter;
    private int album_id;

    public static PhotoGridFragment newInstance(int album_id) {
        PhotoGridFragment fragment = new PhotoGridFragment();
        fragment.album_id = album_id;
        return fragment;
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
        requestPhotoList(album_id);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);
    }

    private void requestPhotoList(int album) {
        String uri = String.format(DEFAULT_DOMAIN + PHOTO_URL + "?album_id=%d",
                album);
        GsonRequest<Photo[]> photosReq = new GsonRequest<Photo[]>(uri,
                Photo[].class, new Response.Listener<Photo[]>() {
            @Override
            public void onResponse(Photo[] photos) {
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(),position+"",Toast.LENGTH_SHORT).show();
    }
}
