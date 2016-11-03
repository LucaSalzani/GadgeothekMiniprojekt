package ch.hsr.mge.gadgeothek;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.mge.gadgeothek.domain.Gadget;
import ch.hsr.mge.gadgeothek.helper.SimpleDividerItemDecoration;
import ch.hsr.mge.gadgeothek.service.Callback;
import ch.hsr.mge.gadgeothek.service.LibraryService;


public class GadgetFragment extends Fragment {
    private TextView emptyView;
    private GadgetAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        emptyView = (TextView) rootView.findViewById(R.id.emptyView);

        // Eine Optimierung, wenn sich die Displaygroesse der Liste nicht aendern wird.
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new GadgetAdapter(new ArrayList<Gadget>());
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver(){
            @Override
            public void onChanged() {
                super.onChanged();
                checkifAdapterEmpty();
            }
        });
        refreshData();

        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        recyclerView.setAdapter(adapter);


        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        return rootView;
    }

    private void refreshData() {
        LibraryService.getGadgets( new Callback<List<Gadget>>(){
            @Override
            public void onCompletion(List<Gadget> input){
                adapter.setData(input);
                adapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
            @Override
            public void onError(String message) {
                Toast.makeText(getContext(), String.format("Get gadgets failed: %s", message), Toast.LENGTH_LONG).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }});
    }

    private void checkifAdapterEmpty(){
        if(adapter.getItemCount() == 0){
            emptyView.setVisibility(View.VISIBLE);
        }else{
            emptyView.setVisibility(View.GONE);
        }
    }
}
