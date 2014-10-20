package net.lion_stuido.lionstudio.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import net.lion_stuido.lionstudio.R;
import net.lion_stuido.lionstudio.adapters.FullScreenImageAdapter;
import net.lion_stuido.lionstudio.model.Photo;

import java.util.List;

/**
 * Created by lester on 19.10.14.
 */
public class PhotoPagerFragment extends Fragment {
    private List<Photo> photoList;
    private FullScreenImageAdapter adapter;
    private ViewPager viewPager;
    private int position;

    public static PhotoPagerFragment newInstance(List<Photo> photoList, int position) {
        PhotoPagerFragment fragment = new PhotoPagerFragment();
        fragment.photoList = photoList;
        fragment.position = position;
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
        View rootView = inflater.inflate(R.layout.fragment_photo_pager, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
        adapter = new FullScreenImageAdapter(photoList, getActivity());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
    }
}
