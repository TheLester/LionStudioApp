package net.lion_stuido.lionstudio.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import net.lion_stuido.lionstudio.R;
import net.lion_stuido.lionstudio.adapters.CommentsAdapter;
import net.lion_stuido.lionstudio.model.Comment;
import net.lion_stuido.lionstudio.model.Photo;
import net.lion_stuido.lionstudio.utils.AppController;
import net.lion_stuido.lionstudio.utils.GsonRequest;

import java.util.ArrayList;

import static net.lion_stuido.lionstudio.utils.Constants.COMMENT_URL;
import static net.lion_stuido.lionstudio.utils.Constants.DEFAULT_DOMAIN;

/**
 * Created by lester on 21.10.14.
 */
public class PhotoCommentFragment extends Fragment {
    private final String TAG = "PhotoCommentFragment";
    private Photo photo;
    private CommentsAdapter adapter;

    public static PhotoCommentFragment newInstance(Photo photo) {
        PhotoCommentFragment fragment = new PhotoCommentFragment();
        fragment.photo = photo;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_photo_comment, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listview = (ListView) getActivity().findViewById(R.id.messagesContainer);
        adapter = new CommentsAdapter(getActivity(), photo, new ArrayList<Comment>());
        requestComments();
        listview.setAdapter(adapter);
    }

    private void requestComments() {
        GsonRequest<Comment[]> commentsRequest = new GsonRequest<Comment[]>(DEFAULT_DOMAIN + COMMENT_URL + "?photo_id=" + photo.getId(), Comment[].class, new Response.Listener<Comment[]>() {
            @Override
            public void onResponse(Comment[] comments) {
                for (Comment comment : comments)
                    adapter.addItem(comment);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(commentsRequest);
    }
}