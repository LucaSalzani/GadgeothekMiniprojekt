package ch.hsr.mge.gadgeothek;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
        public TextView dueView;
        public ImageView stateView;
        public ViewHolder(View parent,TextView textView, TextView dueView, ImageView stateView){
            super(parent);
            this.parent = parent;
            this.textview = textView;
            this.dueView = dueView;
            this.stateView = stateView;
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
        TextView  dueView = (TextView) v.findViewById(R.id.resdate);
        ImageView stateView = (ImageView) v.findViewById(R.id.stateview);
        ViewHolder viewHolder = new ViewHolder(v, textView, dueView, stateView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Reservation res = dataset.get(position);
        holder.textview.setText(res.getGadget().getName());
        holder.dueView.setText("Reserved: "+ android.text.format.DateFormat.format("dd.MM.yyyy",res.getReservationDate()));
        if(!res.isReady()) {
            holder.stateView.setColorFilter(Color.rgb(255,0,0));
        }else{
            holder.stateView.setColorFilter(Color.rgb(0,255,0));
        }

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
