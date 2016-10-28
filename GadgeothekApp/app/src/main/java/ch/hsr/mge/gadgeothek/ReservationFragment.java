package ch.hsr.mge.gadgeothek;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.mge.gadgeothek.domain.Reservation;
import ch.hsr.mge.gadgeothek.helper.ItemSelectionListener;
import ch.hsr.mge.gadgeothek.helper.SimpleDividerItemDecoration;
import ch.hsr.mge.gadgeothek.service.Callback;
import ch.hsr.mge.gadgeothek.service.LibraryService;


public class ReservationFragment extends Fragment implements ItemSelectionListener {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ReservationAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        // Eine Optimierung, wenn sich die Displaygroesse der Liste nicht aendern wird.
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ReservationAdapter(new ArrayList<Reservation>(), this);
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
        LibraryService.getReservationsForCustomer(new Callback<List<Reservation>>(){
            @Override
            public void onCompletion(List<Reservation> input){
                adapter.setData(input);
                adapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
            @Override
            public void onError(String message) {
                Toast.makeText(getContext(), "Get reservations failed: " + message, Toast.LENGTH_LONG).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }});
    }

    @Override
    public void onItemSelected(Reservation res) {
        LibraryService.deleteReservation(res, new Callback<Boolean>() {
            @Override
            public void onCompletion(Boolean input) {
                Toast.makeText(getContext(), "Reservation deleted", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(getContext(), "Delete reservation failed: " + message, Toast.LENGTH_LONG).show();
            }
        });
        refreshData();
    }
}
