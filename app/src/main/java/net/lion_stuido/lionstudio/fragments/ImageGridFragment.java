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

import com.etsy.android.grid.StaggeredGridView;

import net.lion_stuido.lionstudio.R;
import net.lion_stuido.lionstudio.adapters.StaggeredGridAdapter;

import java.util.ArrayList;

/**
 * Created by lester on 10.10.14.
 */
public class ImageGridFragment extends Fragment implements AbsListView.OnScrollListener, AbsListView.OnItemClickListener{

    private static final String TAG = "ImageGridFragment";
    private static final String SAVED_DATA_KEY = "SAVED_DATA";

    private StaggeredGridView mGridView;
    private StaggeredGridAdapter mAdapter;
    private boolean mHasRequestedMore;
    ArrayList<String> mData;

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

        mAdapter = new StaggeredGridAdapter(getActivity(),android.R.layout.simple_list_item_1, generateHardcodedData());

        if (savedInstanceState != null) {
            mData = savedInstanceState.getStringArrayList(SAVED_DATA_KEY);
        }

        if (mData == null) {
            mData = generateHardcodedData();
        }

        for (String data : mData) {
            mAdapter.add(data);
        }
        mGridView.setAdapter(mAdapter);
        mGridView.setOnScrollListener(this);
        mGridView.setOnItemClickListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(SAVED_DATA_KEY, mData);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(),"GOT "+position,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Log.d(TAG, "onScroll firstVisibleItem:" + firstVisibleItem +
                " visibleItemCount:" + visibleItemCount +
                " totalItemCount:" + totalItemCount);
        // our handling
        if (!mHasRequestedMore) {
            int lastInScreen = firstVisibleItem + visibleItemCount;
            if (lastInScreen >= totalItemCount) {
                Log.d(TAG, "onScroll lastInScreen - so load more");
                mHasRequestedMore = true;
              //  onLoadMoreItems();
            }
        }

    }
    private void onLoadMoreItems() {
        final ArrayList<String> sampleData = generateHardcodedData();
        for (String data : sampleData) {
            mAdapter.add(data);
        }
        // stash all the data in our backing store
        mData.addAll(sampleData);
        // notify the adapter that we can update now
        mAdapter.notifyDataSetChanged();
        mHasRequestedMore = false;
    }
    private ArrayList<String> generateHardcodedData() {
        ArrayList<String> listData = new ArrayList<String>();
        listData.add("http://i62.tinypic.com/2iitkhx.jpg");
        listData.add("http://i61.tinypic.com/w0omeb.jpg");
        listData.add("1ds1.jpg");
        listData.add("http://i60.tinypic.com/iw6kh2.jpg");
        listData.add("http://i57.tinypic.com/ru08c8.jpg");
        listData.add("http://i60.tinypic.com/k12r10.jpg");
        listData.add("http://i58.tinypic.com/2e3daug.jpg");
        listData.add("http://i59.tinypic.com/2igznfr.jpg");

        return listData;
    }
}
