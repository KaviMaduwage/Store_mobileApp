package com.example.assignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.R;
import com.example.assignment.model.SubscriptionDetail;
import com.example.assignment.model.TripDetailForSubscriber;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {


    private Context context;
    private ArrayList<TripDetailForSubscriber> tripDetailList;

    public CardAdapter(Context context, ArrayList<TripDetailForSubscriber> tripDetailList) {
        this.context = context;
        this.tripDetailList = tripDetailList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TripDetailForSubscriber tripDetail = tripDetailList.get(position);
        holder.setDetails(tripDetail);
    }

    @Override
    public int getItemCount() {
        return tripDetailList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView subNameCard, subAddressCard,subTelcard,newsPapers;


        MyViewHolder(View itemView){
            super(itemView);
            subNameCard = itemView.findViewById(R.id.txtSubscriberName);
            subAddressCard = itemView.findViewById(R.id.txtSubscriberAddress);
            subTelcard = itemView.findViewById(R.id.txtSubscriberTel);
            newsPapers = itemView.findViewById(R.id.txtNewsPapers);

        }

        void setDetails(TripDetailForSubscriber tripDetail){
            subNameCard.setText(tripDetail.getSubscriberName());
            subAddressCard.setText(tripDetail.getSubscriberAddress());
            subTelcard.setText(tripDetail.getSubscriberPhoneNUmber());

            String newsPaperList="";
            for(SubscriptionDetail s :tripDetail.getSubscriptionDetailList()){
                newsPaperList += s.getNewsPaper().getDescription()+" from "+s.getStartDate() +" to "+s.getEndDate();
            }
            newsPapers.setText(newsPaperList);
        }
    }
}
