package com.mixdor.covertpots.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.mixdor.covertpots.R;
import com.mixdor.covertpots.model.mPlanta;

import java.util.ArrayList;
import java.util.List;


public class AdpPlants extends RecyclerView.Adapter<AdpPlants.ViewHolder>{

    private List<mPlanta> items;
    private List<mPlanta> originalItems;
    private RecyclerItemClick itemClick;
    private RecyclerItemLongClick itemLongClick;
    private boolean ModoSeleccion = false;
    private ArrayList<mPlanta> itemsSelected = new ArrayList<>();


    public AdpPlants(List<mPlanta> items, RecyclerItemClick itemClick, RecyclerItemLongClick itemLongClick) {
        this.items = items;
        this.itemClick = itemClick;
        this.itemLongClick = itemLongClick;
        this.originalItems = new ArrayList<>();
        originalItems.addAll(items);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private MaterialCardView cardView;
        private TextView nombre;
        private TextView serial;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = (MaterialCardView)itemView.findViewById(R.id.mCardPlanta);
            nombre = (TextView)itemView.findViewById(R.id.cardNombrePlanta);
            serial = (TextView)itemView.findViewById(R.id.cardSerialPlanta);

            itemView.setOnClickListener(view -> {
                Toast.makeText(view.getContext(),"Click",Toast.LENGTH_SHORT).show();

            });

            itemView.setOnLongClickListener(view -> {

                Toast.makeText(view.getContext(),"Click Long",Toast.LENGTH_SHORT).show();

                return false;
            });

        }

    }

    @NonNull
    @Override
    public AdpPlants.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_planta, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpPlants.ViewHolder holder, int position) {
        final mPlanta item = items.get(position);

        holder.nombre.setText(item.getNombre());
        holder.serial.setText(String.valueOf(item.getId()));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public int getItemSelectCount() { return itemsSelected.size(); }
// va muy lag al stremear voy cortar

    public interface RecyclerItemClick {
        void mostrarMenuSeleccion();
    }

    public interface RecyclerItemLongClick {
        void ocultarMenuSeleccion();
    }
}
