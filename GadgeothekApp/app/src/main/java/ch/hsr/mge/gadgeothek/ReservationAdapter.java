package ch.hsr.mge.gadgeothek;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ch.hsr.mge.gadgeothek.domain.Reservation;


/**
 * Created by fguebeli on 21.10.2016.
 */

public class ReservationAdapter extends  RecyclerView.Adapter<ReservationAdapter.ViewHolder> {
    private List<Reservation> dataset;
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

    public ReservationAdapter(List<Reservation> loans) {
        dataset = loans;
        //this.selectionListener = selectionListener;
    }

    public void setData(List<Reservation> loans){
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
        final Reservation res = dataset.get(position);
        holder.textview.setText(res.getGadget().getName());

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
