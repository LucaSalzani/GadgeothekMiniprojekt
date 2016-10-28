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

import ch.hsr.mge.gadgeothek.domain.Gadget;
import ch.hsr.mge.gadgeothek.domain.Reservation;
import ch.hsr.mge.gadgeothek.service.Callback;
import ch.hsr.mge.gadgeothek.service.LibraryService;


/**
 * Created by fguebeli on 21.10.2016.
 */

public class GadgetAdapter extends  RecyclerView.Adapter<GadgetAdapter.ViewHolder> {
    private List<Gadget> dataset;
    ///private ItemSelectionListener selectionListener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View parent;
        public TextView textvtitle;
        public TextView textvprice;
        public TextView textvcondition;
        public TextView textvmanufactur;
        public ImageButton imageButton;
        public ViewHolder(View parent,TextView textvtitle, TextView textvprice, TextView textvcondition, TextView textvmanufactur, ImageButton imageButton){
            super(parent);
            this.parent = parent;
            this.textvtitle = textvtitle;
            this.textvprice = textvprice;
            this.textvcondition = textvcondition;
            this.textvmanufactur = textvmanufactur;
            this.imageButton = imageButton;
        }
    }

    public GadgetAdapter(List<Gadget> gadgets) {
        dataset = gadgets;
        //this.selectionListener = selectionListener;
    }

    public void setData(List<Gadget> gadgets){
        dataset = gadgets;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.rowlayout_gadget,parent,false);
        TextView textvtitle = (TextView) v.findViewById(R.id.textvtitle);
        TextView textvprice = (TextView) v.findViewById(R.id.textvprice);
        TextView textvcondition = (TextView) v.findViewById(R.id.textvcondition);
        TextView textvmanufactur = (TextView) v.findViewById(R.id.textvmanufactur);
        ImageButton imageButton = (ImageButton) v.findViewById(R.id.imageButtonAdd);
        ViewHolder viewHolder = new ViewHolder(v, textvtitle, textvprice, textvcondition, textvmanufactur, imageButton);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holderv, final int position) {
        final Gadget gadget = dataset.get(position);
        final ViewHolder holder = holderv;

        holder.textvtitle.setText(gadget.getName());
        holder.textvmanufactur.setText("Manufactur: "+ gadget.getManufacturer());
        holder.textvprice.setText("Price: "+ gadget.getPrice()+" CHF");
        holder.textvcondition.setText("Condition: "+gadget.getCondition());

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LibraryService.reserveGadget(gadget, new Callback<Boolean>() {
                    @Override
                    public void onCompletion(Boolean input) {
                        if(input){
                            Toast.makeText(holder.parent.getContext(), "Gadget sucscessful reservated", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(holder.parent.getContext(), "Gadget reservation not possible", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(holder.parent.getContext(), "Gadget reservation failed: " + message, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
