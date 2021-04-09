package com.example.hesnalmuslimclone.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hesnalmuslimclone.R;
import com.example.hesnalmuslimclone.helper.ThekrClickListener;
import com.example.hesnalmuslimclone.models.Zekr;

import java.util.List;


public class AthkarAdapter extends RecyclerView.Adapter<AthkarAdapter.ThekrViewHolder>{
    private static final String TAG = "AthkarAdapter";
    private List<Zekr> athkar;
    private ThekrClickListener thekrClickListener;

    public AthkarAdapter(List<Zekr> athkar, ThekrClickListener thekrClickListener) {
        this.athkar = athkar;
        this.thekrClickListener = thekrClickListener;
    }

    @NonNull
    @Override
    public ThekrViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.thekr_item_layout, parent, false);
        return new ThekrViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThekrViewHolder holder, int position) {
        Zekr zekr = athkar.get(holder.getAdapterPosition());
        holder.bind(zekr);

    }

    @Override
    public int getItemCount() {
        return athkar.size();
    }



    class ThekrViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private final TextView thekrDescriptionTextView;
        private final TextView thekrHintTextView;
        private final TextView thekrAndTotalNumberTextView;
        private TextView thekrCounterTextView;
        private final TextView numberOfTimesArabicTextView;


        public ThekrViewHolder(@NonNull View itemView)  {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            thekrDescriptionTextView = itemView.findViewById(R.id.thekrDescriptionTextView);
            thekrHintTextView = itemView.findViewById(R.id.thekrHintTextView);
            thekrAndTotalNumberTextView = itemView.findViewById(R.id.thekrAndTotalNumberTextView);
            thekrCounterTextView =  itemView.findViewById(R.id.thekrCounterTextView);
            numberOfTimesArabicTextView =  itemView.findViewById(R.id.numberOfTimesArabicTextView);
        }

        @SuppressLint("SetTextI18n")
        void bind(Zekr zekr){
            thekrDescriptionTextView.setText(zekr.description);
            thekrHintTextView.setText(zekr.hint);

            String partialThekr = String.valueOf(getAdapterPosition() + 1);
            String totalThekr = String.valueOf(athkar.size());

            thekrAndTotalNumberTextView.setText(" الذكر " + partialThekr + " من " + totalThekr);
            String thekrCounter = (zekr.counter == null) ? "مرَّةً واحِدَةً" : zekr.counter;
            numberOfTimesArabicTextView.setText(thekrCounter);

            thekrCounterTextView.setText(String.valueOf(zekr.counterNumber));
        }



        @Override
        public void onClick(View v) {

            Zekr currentThekr = athkar.get(getAdapterPosition());
            if (getAdapterPosition() <= athkar.size() - 1) {

                if (getAdapterPosition() < athkar.size() - 1) {
                    currentThekr.counterNumber--;
                    thekrCounterTextView.setText(String.valueOf(currentThekr.counterNumber));
                    if (currentThekr.counterNumber == 0 ){
                        thekrClickListener.onThekrClicked(currentThekr, getAdapterPosition() + 1);
                    }
                }
                else if (getAdapterPosition() == athkar.size() - 1){
                    if (currentThekr.counterNumber == 0 ){
                        thekrCounterTextView.setText(String.valueOf(0));
                        Toast.makeText(itemView.getContext(), "تمَّ بحَمْدِ اللَّـهِ", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        currentThekr.counterNumber--;
                        thekrCounterTextView.setText(String.valueOf(currentThekr.counterNumber));
                    }

                }
            }
        }

        @Override
        public boolean onLongClick(View v) {
            Zekr currentThekr = athkar.get(getAdapterPosition());
            thekrClickListener.onThekrLongClicked(currentThekr, getAdapterPosition());
            return true;
        }

    }
}
