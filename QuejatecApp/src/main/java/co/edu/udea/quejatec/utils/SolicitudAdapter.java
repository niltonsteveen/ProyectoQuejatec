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

import java.util.List;

import co.edu.udea.quejatec.Fragments.SolicitudDetail;
import co.edu.udea.quejatec.Fragments.SolicitudRespuesta;
import co.edu.udea.quejatec.Model.Solicitud;
import co.edu.udea.quejatec.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Creado por Hermosa Programación
 */
public class SolicitudAdapter extends RecyclerView.Adapter<SolicitudAdapter.SolicitudViewHolder> {
    private List<Solicitud> items;
    private Context context;
    FragmentManager fragmentManager;
    boolean esEmpleado;
    public static class SolicitudViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView imagen;
        public TextView descripcion;
        public CardView cardImg;
        public View view;
        public Button verSol;


        public SolicitudViewHolder(View v) {
            super(v);
            this.view= v;
            imagen = (TextView) v.findViewById(R.id.tvLetterCardView);
            cardImg=(CardView) v.findViewById(R.id.cardImg);
            descripcion = (TextView) v.findViewById(R.id.descripcion);
            verSol=(Button) v.findViewById(R.id.verSol);

        }
    }

    public SolicitudAdapter(List<Solicitud> items, Context context, FragmentManager fragmentManager, boolean esEmpleado) {
        this.items = items;
        this.context=context;
        this.fragmentManager=fragmentManager;
        this.esEmpleado=esEmpleado;
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
    public void onBindViewHolder(SolicitudViewHolder viewHolder, final int i) {
        int solicitud=Integer.parseInt(items.get(i).getTiposolicitud());

        if(solicitud==1){
            viewHolder.imagen.setText("Q");
            viewHolder.cardImg.setCardBackgroundColor(Color.parseColor("#5882FA"));
        }else if(solicitud==2){
            viewHolder.imagen.setText("P");
            viewHolder.cardImg.setCardBackgroundColor(Color.parseColor("#82FA58"));
        }else if(solicitud==3){
            viewHolder.imagen.setText("R");
            viewHolder.cardImg.setCardBackgroundColor(Color.parseColor("#FA5858"));
        }
        viewHolder.descripcion.setText(items.get(i).getDescripcion());
        if(esEmpleado){
            viewHolder.verSol.setText("VER SOLICITUD");
            viewHolder.verSol.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle arg= new Bundle();
                    arg.putParcelable("Solicitud",items.get(i));
                    SolicitudDetail solicitudDetail=SolicitudDetail.newInstance(arg);
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.main_content, solicitudDetail)
                            .commit();
                }
            });
        }else{
            viewHolder.verSol.setText("RESPONDER");
            viewHolder.verSol.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle arg= new Bundle();
                    arg.putParcelable("Solicitud",items.get(i));
                    SolicitudRespuesta solicitudRespuesta=SolicitudRespuesta.newInstance(arg);
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.main_content, solicitudRespuesta)
                            .commit();
                }
            });
        }
        setAnimation(viewHolder.view);




    }

    private void setAnimation(View viewToAnimate) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.left_animation);
        viewToAnimate.startAnimation(animation);
    }
}
