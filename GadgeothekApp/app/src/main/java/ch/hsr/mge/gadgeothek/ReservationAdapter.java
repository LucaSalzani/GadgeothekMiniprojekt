package ch.hsr.mge.gadgeothek;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;
import java.util.Locale;

import ch.hsr.mge.gadgeothek.domain.Reservation;
import ch.hsr.mge.gadgeothek.helper.ItemSelectionListener;
import ch.hsr.mge.gadgeothek.service.Callback;
import ch.hsr.mge.gadgeothek.service.LibraryService;

import static java.security.AccessController.getContext;


class ReservationAdapter extends  RecyclerView.Adapter<ReservationAdapter.ViewHolder> {
    private List<Reservation> dataset;
    private ItemSelectionListener selectionListener;

    class ViewHolder extends RecyclerView.ViewHolder {
        View parent;
        public TextView textview;
        TextView dueView;
        TextView stateView;
        ImageView imgView;
        ImageButton imgButton;
        ViewHolder(View parent, TextView textView, TextView dueView, TextView stateView, ImageView imgView, ImageButton imgButton){
            super(parent);
            this.parent = parent;
            this.textview = textView;
            this.dueView = dueView;
            this.stateView = stateView;
            this.imgView = imgView;
            this.imgButton = imgButton;
        }
    }

    ReservationAdapter(List<Reservation> loans, ItemSelectionListener selectionListener) {
        dataset = loans;
        this.selectionListener = selectionListener;
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
        ImageButton imgButton = (ImageButton) v.findViewById(R.id.imageButtonRem);
        return new ViewHolder(v, textView, dueView, stateView, imgView, imgButton);

    }

    @Override
    public void onBindViewHolder(ViewHolder holderv, final int position) {
        final Reservation res = dataset.get(position);
        holderv.textview.setText(res.getGadget().getName());
        holderv.dueView.setText(String.format("Reserved: %s", android.text.format.DateFormat.format("dd.MM.yyyy", res.getReservationDate())));
        if(!res.isReady()) {
            holderv.imgView.setImageResource(android.R.drawable.presence_away);
            holderv.stateView.setText(String.format(Locale.GERMAN, "Status: Waiting on position %d", res.getWatingPosition()));
        }else{
            holderv.imgView.setImageResource(android.R.drawable.presence_online);
            holderv.stateView.setText(R.string.ReadyToPickup);
        }


        holderv.imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionListener.onItemSelected(res);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
