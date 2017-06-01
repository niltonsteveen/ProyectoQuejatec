package co.edu.udea.quejatec.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import co.edu.udea.quejatec.Fragments.SolicitudDetail;
import co.edu.udea.quejatec.Fragments.SolicitudRespuesta;
import co.edu.udea.quejatec.Model.Noticia;
import co.edu.udea.quejatec.Model.Solicitud;
import co.edu.udea.quejatec.R;

/**
 * Creado por Hermosa Programación
 */
public class NoticiasAdapter extends RecyclerView.Adapter<NoticiasAdapter.NoticiaViewHolder> {
    private List<Noticia> items;
    private Context context;
    FragmentManager fragmentManager;

    public static class NoticiaViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView titulo;
        public TextView descripcion;
        public View view;


        public NoticiaViewHolder(View v) {
            super(v);
            this.view= v;
            imagen = (ImageView) v.findViewById(R.id.imagenEventWidget);
            descripcion = (TextView) v.findViewById(R.id.informacionEventWidget);
            titulo=(TextView) v.findViewById(R.id.nombreEventWidget);

        }
    }

    public NoticiasAdapter(List<Noticia> items, Context context, FragmentManager fragmentManager) {
        this.items = items;
        this.context=context;
        this.fragmentManager=fragmentManager;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public NoticiaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.vistarv, viewGroup, false);
        return new NoticiaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NoticiaViewHolder viewHolder, final int i) {
        Picasso.with(context).load(this.items.get(i).getUrl()).into(viewHolder.imagen);
        viewHolder.titulo.setText(items.get(i).getTitulo());
        viewHolder.descripcion.setText(items.get(i).getInformacion());
        setAnimation(viewHolder.view);
    }

    private void setAnimation(View viewToAnimate) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.left_animation);
        viewToAnimate.startAnimation(animation);
    }
}
