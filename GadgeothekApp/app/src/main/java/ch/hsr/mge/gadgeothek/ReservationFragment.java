package ch.hsr.mge.gadgeothek;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.mge.gadgeothek.domain.Reservation;
import ch.hsr.mge.gadgeothek.helper.SimpleDividerItemDecoration;
import ch.hsr.mge.gadgeothek.service.Callback;
import ch.hsr.mge.gadgeothek.service.LibraryService;


public class ReservationFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ReservationAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        // Eine Optimierung, wenn sich die Displaygroesse der Liste nicht aendern wird.
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ReservationAdapter(new ArrayList<Reservation>());
        LibraryService.getReservationsForCustomer(new Callback<List<Reservation>>(){
            @Override
            public void onCompletion(List<Reservation> input){
                adapter.setData(input);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onError(String message) {
                Toast.makeText(getContext(), "Get reservations failed: " + message, Toast.LENGTH_LONG).show();
            }});

        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        recyclerView.setAdapter(adapter);

        return rootView;
    }

}
