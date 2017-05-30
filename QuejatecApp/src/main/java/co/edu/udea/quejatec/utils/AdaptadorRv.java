package co.edu.udea.quejatec.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import co.edu.udea.quejatec.R;

/**
 * Created by nilto on 14/03/2017.
 */

public class AdaptadorRv
        extends RecyclerView.Adapter<AdaptadorRv.EventsViewHolder>
        implements View.OnClickListener {

    Context context;
    private View.OnClickListener listener;
    private List<NoticiasPojo> listaEvents;

    public static class EventsViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView tvNombre,tvFecha,tvInformacion,tvOrganizador,tvPais,tvDepartamento,tvCiudad,
                tvLugar,tvPuntuacion;

        public EventsViewHolder(View v) {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.imagenEventWidget);
            tvNombre = (TextView) v.findViewById(R.id.nombreEventWidget);

            tvInformacion = (TextView) v.findViewById(R.id.informacionEventWidget);
            tvPuntuacion=(TextView) v.findViewById(R.id.puntuacionEventWidget);
        }
    }

    public AdaptadorRv(List<NoticiasPojo> items) {
        this.listaEvents = items;
    }

    @Override
    public int getItemCount() {
        return listaEvents.size();
    }

    @Override
    public EventsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.vistarv, viewGroup, false);
        v.setOnClickListener(this);
        context = viewGroup.getContext();
        return new EventsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EventsViewHolder viewHolder, int i) {

        Picasso.with(context)
                .load(listaEvents.get(i).getFoto())
                .placeholder(R.drawable.cellphone)
                .into(viewHolder.imagen);

        viewHolder.tvNombre.setText(listaEvents.get(i).getTitulo());
        viewHolder.tvInformacion.setText(listaEvents.get(i).getInformación());
    }

    public Bitmap byteImgToBitmap(byte[] blob) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.length);
        return bitmap;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }
}