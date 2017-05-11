package co.edu.udea.quejatec.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import co.edu.udea.quejatec.Model.Usuario;
import co.edu.udea.quejatec.R;
import co.edu.udea.quejatec.utils.UtilClass;


public class FragmentPerfil extends Fragment {
    private Usuario usuario;
    private GridLayout grid;
    private TextView tvNombre;
    private TextView tvDocumento;
    private TextView tvPassword;
    private TextView tvEmail;
    private TextView tvContacto;
    private TextView tvDireccion;

    public FragmentPerfil() {

    }

    public static FragmentPerfil newInstance(Bundle arguments){
        FragmentPerfil f= new FragmentPerfil();
        if(arguments != null){
            f.setArguments(arguments);
        }
        return f;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragmento_perfil, container, false);
        Bundle extras=getArguments();
        usuario=extras.getParcelable("Usuario");

        tvNombre=(TextView) view.findViewById(R.id.texto_nombre);
        tvNombre.setText(usuario.getNombre());
        tvDocumento=(TextView) view.findViewById(R.id.tvDocumentoF);
        tvDocumento.setText(usuario.getDocumento().toString());
        tvContacto=(TextView) view.findViewById(R.id.texto_contacto);
        tvContacto.setText(usuario.getContacto());
        tvDireccion=(TextView) view.findViewById(R.id.texto_direction);
        tvDireccion.setText(usuario.getDireccion());
        tvEmail=(TextView) view.findViewById(R.id.texto_email);
        tvEmail.setText(usuario.getEmail());
        tvPassword=(TextView) view.findViewById(R.id.texto_pwd);
        String pwd=UtilClass.hidePassword(usuario.getPassword().length());
        tvPassword.setText(pwd);

        return view;

    }
}