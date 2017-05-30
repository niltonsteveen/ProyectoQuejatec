package co.edu.udea.quejatec.Fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import co.edu.udea.quejatec.Model.RestInterface;
import co.edu.udea.quejatec.Model.Solicitud;
import co.edu.udea.quejatec.Model.Usuario;
import co.edu.udea.quejatec.R;
import co.edu.udea.quejatec.utils.NotificationSender;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SolicitudRespuesta extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView tvTitulo;
    private TextView tvUsuario;
    private TextView tvDescripcion;
    private TextInputEditText etRespuesta;
    private Button btnResponder;
    private Solicitud solicitud;
    private Usuario user;
    String url= "https://apirest-proyecto-quejatec.herokuapp.com/api";

    public SolicitudRespuesta() {
        // Required empty public constructor
    }

    public static SolicitudRespuesta newInstance(Bundle arguments){
        SolicitudRespuesta f= new SolicitudRespuesta();
        if(arguments != null){
            f.setArguments(arguments);
        }
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_solicitud_respuesta, container, false);
        Bundle extras=getArguments();
        solicitud=extras.getParcelable("Solicitud");
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(url).build();
        final RestInterface restInterface=restAdapter.create(RestInterface.class);

        restInterface.getUserById(solicitud.getUsuario(), new Callback<Usuario>() {
            @Override
            public void success(Usuario usuario, Response response) {
                user=new Usuario();
                user.setCalificacion(usuario.getCalificacion());
                user.setContacto(usuario.getContacto());
                user.setDireccion(usuario.getDireccion());
                user.setDocumento(usuario.getDocumento());
                user.setEmail(usuario.getEmail());
                user.setNombre(usuario.getNombre());
                user.setToken(usuario.getToken());
                user.setPassword(usuario.getPassword());
                user.setTipoUsuario(usuario.getTipoUsuario());
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        tvTitulo=(TextView)view.findViewById(R.id.tvSolR);
        tvUsuario=(TextView) view.findViewById(R.id.tvUsuarioR);
        tvUsuario.setText(solicitud.getUsuario());
        tvDescripcion=(TextView) view.findViewById(R.id.tvDescripcionR);
        tvDescripcion.setText(solicitud.getDescripcion());
        etRespuesta=(TextInputEditText) view.findViewById(R.id.etRespuestaR);
        btnResponder=(Button)view.findViewById(R.id.btnResponderPQR);


        btnResponder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicitud.setRespuesta(etRespuesta.getText().toString());
                solicitud.setFechaRespuesta(new Date().toString());
                restInterface.updateSolicitud(solicitud, new Callback<Solicitud>() {
                    @Override
                    public void success(Solicitud solicitud, Response response) {
                        Bundle arguments=new Bundle();
                        arguments.putParcelable("Usuario",user);
                        ListSolicitudesFragmentPend fragmentoSolicitud= ListSolicitudesFragmentPend.newInstance(arguments);
                        FragmentManager fragmentManager=getFragmentManager();
                        enviarNotificacion(solicitud.getUsuario(),solicitud);
                        Toast.makeText(getContext(), "Solicitud respondida correctamente",Toast.LENGTH_LONG).show();
                        fragmentManager
                                .beginTransaction()
                                .replace(R.id.main_content, fragmentoSolicitud)
                                .commit();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        error.printStackTrace();
                    }
                });
            }
        });


        return view;
    }

    private void enviarNotificacion(String usr, final Solicitud solicitud) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(url).build();
        final RestInterface restInterface=restAdapter.create(RestInterface.class);

        restInterface.getUserById(usr, new Callback<Usuario>() {
            @Override
            public void success(Usuario usuario, Response response) {
                String msg="Tu solicitud "+ solicitud.getId()+ "ha sido respondida";
                NotificationSender.sendNotification(usuario.getToken(),msg);
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });

    }
}
