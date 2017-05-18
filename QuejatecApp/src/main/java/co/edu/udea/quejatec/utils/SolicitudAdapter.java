package co.edu.udea.quejatec.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.edu.udea.quejatec.Model.Solicitud;
import co.edu.udea.quejatec.R;

/**
 * Creado por Hermosa Programación
 */
public class SolicitudAdapter extends RecyclerView.Adapter<SolicitudAdapter.SolicitudViewHolder> {
    private List<Solicitud> items;

    public static class SolicitudViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView descripcion;

        public SolicitudViewHolder(View v) {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.imagen);
            descripcion = (TextView) v.findViewById(R.id.descripcion);
        }
    }

    public SolicitudAdapter(List<Solicitud> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public SolicitudViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.solicitud_card, viewGroup, false);
        return new SolicitudViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SolicitudViewHolder viewHolder, int i) {
        int solicitud=Integer.parseInt(items.get(i).getTiposolicitud());
        System.out.println(solicitud);

        if(solicitud==1){
            viewHolder.imagen.setImageResource(R.drawable.queja);
        }else if(solicitud==2){
            viewHolder.imagen.setImageResource(R.drawable.peticion);
        }else if(solicitud==3){
            viewHolder.imagen.setImageResource(R.drawable.reclamo);
        }
        viewHolder.descripcion.setText(items.get(i).getDescripcion());
    }
}
