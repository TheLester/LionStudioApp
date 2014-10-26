package net.lion_stuido.lionstudio.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import net.lion_stuido.lionstudio.R;
import net.lion_stuido.lionstudio.adapters.CommentsAdapter;
import net.lion_stuido.lionstudio.model.Comment;
import net.lion_stuido.lionstudio.model.Photo;
import net.lion_stuido.lionstudio.model.ResponseStatus;
import net.lion_stuido.lionstudio.utils.AppController;
import net.lion_stuido.lionstudio.utils.GsonRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
        ListView listview = (ListView) rootView.findViewById(R.id.messagesContainer);
        adapter = new CommentsAdapter(getActivity(), photo, new ArrayList<Comment>());
        requestComments();
        listview.setAdapter(adapter);
        setSendButtonListener(rootView);
        return rootView;
    }

    private void requestComments() {
        GsonRequest<Comment[]> commentsRequest = new GsonRequest<Comment[]>(DEFAULT_DOMAIN + COMMENT_URL + "?photo_id=" + photo.getId(), Comment[].class, new Response.Listener<Comment[]>() {
            @Override
            public void onResponse(Comment[] comments) {
                adapter.add(Arrays.asList(comments));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(commentsRequest);
    }

    private void setSendButtonListener(View rootView) {
        Button sendButton = (Button) rootView.findViewById(R.id.btn_send_comment);
        final EditText editTextAuthor = (EditText) rootView.findViewById(R.id.edit_text_author);
        final EditText editTextComment = (EditText) rootView.findViewById(R.id.edit_text_comment);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty(editTextAuthor) || isEmpty(editTextComment)) {
                    Toast.makeText(getActivity(), R.string.empty_fields, Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    String author = editTextAuthor.getText().toString();
                    String comment = editTextComment.getText().toString();
                    sendComment(author, comment);
                    editTextAuthor.setText("");
                    editTextComment.setText("");
                }
            }
        });
    }

    private void sendComment(final String author, final String commentText) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("photo_id", String.valueOf(photo.getId()));
        params.put("author", author);
        params.put("comment", commentText);
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(getResources().getString(R.string.loading));
        pDialog.show();
        GsonRequest<ResponseStatus> photosReq = new GsonRequest<ResponseStatus>(DEFAULT_DOMAIN + COMMENT_URL,
                ResponseStatus.class, params, new Response.Listener<ResponseStatus>() {
            @Override
            public void onResponse(ResponseStatus responseStatus) {
                if (responseStatus.getStatus().equals("OK")) {
                    Comment comment = new Comment();
                    comment.setData(getResources().getString(R.string.now));
                    comment.setName(author);
                    comment.setText(commentText);
                    showComment(comment);
                } else if (responseStatus.getStatus().equals("Error"))
                    Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_LONG).show();
                pDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialog.dismiss();
                Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(photosReq);
    }

    private boolean isEmpty(EditText myeditText) {
        return myeditText.getText().toString().trim().length() == 0;
    }

    private void showComment(final Comment comment) {
        adapter.add(comment);
        ListView messagesContainer = (ListView) getActivity().findViewById(R.id.messagesContainer);
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }
}