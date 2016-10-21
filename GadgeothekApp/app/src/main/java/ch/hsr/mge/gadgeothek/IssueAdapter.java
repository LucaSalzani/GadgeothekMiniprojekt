package ch.hsr.mge.gadgeothek;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.mge.gadgeothek.domain.Loan;


/**
 * Created by fguebeli on 21.10.2016.
 */

public class IssueAdapter extends  RecyclerView.Adapter<IssueAdapter.ViewHolder> {
    private List<Loan> dataset;
    ///private ItemSelectionListener selectionListener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View parent;
        public TextView textview;
        public ViewHolder(View parent,TextView textView){
            super(parent);
            this.parent = parent;
            this.textview = textView;
        }
    }

    public IssueAdapter(List<Loan> loans) {
        dataset = loans;
        //this.selectionListener = selectionListener;
    }

    public void setData(List<Loan> loans){
        dataset = loans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.rowlayout,parent,false);
        TextView textView = (TextView) v.findViewById(R.id.textView);
        ViewHolder viewHolder = new ViewHolder(v, textView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Loan loan = dataset.get(position);
        holder.textview.setText(loan.getGadget().getName());

        /*
        holder.textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionListener.onItemSelected(position);
            }
        });
        */
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
