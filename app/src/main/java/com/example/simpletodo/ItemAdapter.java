package com.example.simpletodo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.viewHolder> {

    public  interface OnClickListener {
        void onItemClicked(int position) ;
    }
    public interface  OnLongClickListener {

        void onItemLongClicked(int position) ;

    }
    List<String> items ;
    OnLongClickListener longClickListener ;
    OnClickListener clickListener ;

    public ItemAdapter(List<String> items , OnLongClickListener longClickListener, OnClickListener clickListener) {
        this.items = items ;
        this.longClickListener = longClickListener ;
        this.clickListener = clickListener ;


    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


      View todoView =   LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_list_item_1,viewGroup,false) ;

    return new viewHolder(todoView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder Holder, int i) {
        String item  = items.get(i) ;
        Holder.bind(item) ;




    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{

        TextView tvitem ;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvitem = itemView.findViewById(android.R.id.text1) ;


        }

        public void bind(String item) {
            tvitem.setText(item);
            tvitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v )
            {
                clickListener.onItemClicked(getAdapterPosition());

            }


            });



            tvitem.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {

                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return true ;

                }
            });


        }
    }
}
