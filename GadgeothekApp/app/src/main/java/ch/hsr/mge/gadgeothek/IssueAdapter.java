package ch.hsr.mge.gadgeothek;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import ch.hsr.mge.gadgeothek.domain.Loan;



class IssueAdapter extends  RecyclerView.Adapter<IssueAdapter.ViewHolder> {
    private List<Loan> dataset;

    class ViewHolder extends RecyclerView.ViewHolder {
        View parent;
        public TextView textview;
        TextView pickView;
        TextView retView;
        ImageView imgView;

        ViewHolder(View parent, TextView textView, TextView pickView, TextView retView, ImageView imgView){
            super(parent);
            this.parent = parent;
            this.textview = textView;
            this.pickView = pickView;
            this.retView = retView;
            this.imgView = imgView;
        }
    }

    IssueAdapter(List<Loan> loans) {
        dataset = loans;
    }

    public void setData(List<Loan> loans){
        dataset = loans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.rowlayout_issue,parent,false);
        TextView textView = (TextView) v.findViewById(R.id.textView);
        TextView pickView = (TextView) v.findViewById(R.id.pickdate);
        TextView retView = (TextView) v.findViewById(R.id.retdate);
        ImageView imgView = (ImageView) v.findViewById(R.id.stateimgview);
        return new ViewHolder(v, textView, pickView, retView, imgView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Loan loan = dataset.get(position);
        holder.textview.setText(loan.getGadget().getName());
        holder.pickView.setText(String.format("Pickup Date: %s", android.text.format.DateFormat.format("dd.MM.yyyy", loan.getPickupDate())));

        if(!loan.isOverdue()) {
            holder.imgView.setImageResource(android.R.drawable.presence_busy);
            holder.retView.setText(R.string.overdue);
            holder.retView.setTextColor(Color.RED);
        }else{
            long timediff = loan.overDueDate().getTime() - new Date().getTime();
            long days = TimeUnit.DAYS.convert(timediff,TimeUnit.MILLISECONDS);
            holder.retView.setText(String.format(Locale.GERMAN, "Days until returning: %d", days));
            holder.imgView.setImageResource(android.R.drawable.presence_online);
        }

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
