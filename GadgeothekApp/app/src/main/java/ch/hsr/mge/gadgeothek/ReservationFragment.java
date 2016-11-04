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

import ch.hsr.mge.gadgeothek.domain.Reservation;
import ch.hsr.mge.gadgeothek.helper.ItemSelectionListener;
import ch.hsr.mge.gadgeothek.helper.SimpleDividerItemDecoration;
import ch.hsr.mge.gadgeothek.service.Callback;
import ch.hsr.mge.gadgeothek.service.LibraryService;


public class ReservationFragment extends Fragment implements ItemSelectionListener {

    private TextView emptyView;
    private ReservationAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    protected boolean onCreateViewCalled = false;

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

        adapter = new ReservationAdapter(new ArrayList<Reservation>(), this);
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
        onCreateViewCalled = true;
        return rootView;
    }

    public boolean hasOnCreateViewBeenCalled()
    {
        return onCreateViewCalled;
    }

    private void checkifAdapterEmpty(){
        if(adapter.getItemCount() == 0){
            emptyView.setVisibility(View.VISIBLE);
        }else{
            emptyView.setVisibility(View.GONE);
        }
    }

    private void refreshData() {
        LibraryService.getReservationsForCustomer(new Callback<List<Reservation>>(){
            @Override
            public void onCompletion(List<Reservation> input){
                adapter.setData(input);
                adapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
            @Override
            public void onError(String message) {
                Toast.makeText(getContext(), String.format("Get reservations failed: %s", message), Toast.LENGTH_LONG).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }});
    }

    public void updateView(){
        refreshData();
    }

    @Override
    public void onItemSelected(Reservation res) {
        LibraryService.deleteReservation(res, new Callback<Boolean>() {
            @Override
            public void onCompletion(Boolean input) {
                Toast.makeText(getContext(), R.string.ReservationDeleted, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(getContext(), getString(R.string.ReservationDeletedFailed) + message, Toast.LENGTH_LONG).show();
            }
        });
        refreshData();
    }
}
