package ch.hsr.mge.gadgeothek;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.mge.gadgeothek.domain.Loan;
import ch.hsr.mge.gadgeothek.service.Callback;
import ch.hsr.mge.gadgeothek.service.LibraryService;


public class IssueFragment extends Fragment {
    //private ItemSelectionListener itemSelectionCallback = null;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private IssueAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_issue, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        // Eine Optimierung, wenn sich die Displaygroesse der Liste nicht aendern wird.
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new IssueAdapter(new ArrayList<Loan>());
        LibraryService.getLoansForCustomer( new Callback<List<Loan>>(){
            @Override
            public void onCompletion(List<Loan> input){
                    adapter.setData(input);
                    adapter.notifyDataSetChanged();
                }
            @Override
            public void onError(String message) {
                Toast.makeText(getContext(),"Get loans failed: " + message, Toast.LENGTH_LONG).show();
            }});

        recyclerView.setAdapter(adapter);

        return rootView;
    }

    /*
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        if (!(activity instanceof ItemSelectionListener)) {
            throw new IllegalStateException("Activity must implement ItemSelectionListener");
        }

        itemSelectionCallback = (ItemSelectionListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        itemSelectionCallback = null;
    }
    */
}
