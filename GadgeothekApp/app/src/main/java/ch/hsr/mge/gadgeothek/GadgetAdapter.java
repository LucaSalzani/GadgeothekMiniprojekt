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


class GadgetAdapter extends  RecyclerView.Adapter<GadgetAdapter.ViewHolder> {
    private List<Gadget> dataset;

    class ViewHolder extends RecyclerView.ViewHolder {
        View parent;
        TextView textvtitle;
        TextView textvprice;
        TextView textvcondition;
        TextView textvmanufactur;
        ImageButton imageButton;
        ViewHolder(View parent, TextView textvtitle, TextView textvprice, TextView textvcondition, TextView textvmanufactur, ImageButton imageButton){
            super(parent);
            this.parent = parent;
            this.textvtitle = textvtitle;
            this.textvprice = textvprice;
            this.textvcondition = textvcondition;
            this.textvmanufactur = textvmanufactur;
            this.imageButton = imageButton;
        }
    }

    GadgetAdapter(List<Gadget> gadgets) {
        dataset = gadgets;
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
        return new ViewHolder(v, textvtitle, textvprice, textvcondition, textvmanufactur, imageButton);

    }

    @Override
    public void onBindViewHolder(ViewHolder holderv, final int position) {
        final Gadget gadget = dataset.get(position);
        final ViewHolder holder = holderv;

        holder.textvtitle.setText(gadget.getName());
        holder.textvmanufactur.setText(String.format("Manufacturer: %s", gadget.getManufacturer()));
        holder.textvprice.setText(String.format("Price: %s CHF", gadget.getPrice()));
        holder.textvcondition.setText(String.format("Condition: %s", gadget.getCondition()));

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LibraryService.reserveGadget(gadget, new Callback<Boolean>() {
                    @Override
                    public void onCompletion(Boolean input) {
                        if(input){
                            Toast.makeText(holder.parent.getContext(), R.string.ReservationOk, Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(holder.parent.getContext(), R.string.ReservationNok, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(holder.parent.getContext(), String.format("Gadget reservation failed: %s", message), Toast.LENGTH_LONG).show();
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
