package ch.hsr.mge.gadgeothek;

import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

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
        public TextView stateView;
        public ImageView imgView;
        public ViewHolder(View parent,TextView textView, TextView dueView, TextView stateView, ImageView imgView){
            super(parent);
            this.parent = parent;
            this.textview = textView;
            this.dueView = dueView;
            this.stateView = stateView;
            this.imgView = imgView;
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
        View v = layoutInflater.inflate(R.layout.rowlayout_reservation,parent,false);
        TextView textView = (TextView) v.findViewById(R.id.textView);
        TextView  dueView = (TextView) v.findViewById(R.id.resdate);
        TextView stateView = (TextView) v.findViewById(R.id.stateview);
        ImageView imgView = (ImageView) v.findViewById(R.id.stateimgview);
        ViewHolder viewHolder = new ViewHolder(v, textView, dueView, stateView, imgView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Reservation res = dataset.get(position);
        holder.textview.setText(res.getGadget().getName());
        holder.dueView.setText("Reserved: "+ android.text.format.DateFormat.format("dd.MM.yyyy",res.getReservationDate()));
        if(!res.isReady()) {
            holder.imgView.setImageResource(android.R.drawable.presence_away);
            holder.stateView.setText("Status: Waiting on position "+res.getWatingPosition());
        }else{
            holder.imgView.setImageResource(android.R.drawable.presence_online);
            holder.stateView.setText("Status: Ready to pick up");
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
