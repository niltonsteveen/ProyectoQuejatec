package co.edu.udea.quejatec.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Date;

import co.edu.udea.quejatec.Model.RestInterface;
import co.edu.udea.quejatec.Model.Solicitud;
import co.edu.udea.quejatec.Model.Usuario;
import co.edu.udea.quejatec.R;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SolicitudFragment extends Fragment {
   private Spinner spTipoSol;
    private Spinner spSucursal;
    private Usuario user;
    private EditText etDocumento;
    private Button btnIngresarPQR;
    private EditText etDescripcion;
    private int tipoSolicitud;
    private int sucursal;
    String url= "https://apirest-proyecto-quejatec.herokuapp.com/api";

    public SolicitudFragment() {
        // Required empty public constructor
    }


    public static SolicitudFragment newInstance(Bundle arguments) {
        SolicitudFragment fragment = new SolicitudFragment();
        if(arguments != null){
            fragment.setArguments(arguments);
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_solicitud, container, false);

        Bundle extras=getArguments();
        user=extras.getParcelable("Usuario");

      //  System.out.println(user.getNombre()+"--------********---------------*******------------------");
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(url).build();
        final RestInterface restInterface=restAdapter.create(RestInterface.class);

        etDocumento=(EditText)view.findViewById(R.id.etDocumentS);
        etDocumento.setText(user.getDocumento().toString());
        etDocumento.setEnabled(false);

        spSucursal=(Spinner)view.findViewById(R.id.spSucursalS);
        ArrayAdapter spinner_adapter_sucursales = ArrayAdapter.createFromResource( this.getContext(), R.array.sucursales , android.R.layout.simple_spinner_item);
        spinner_adapter_sucursales.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSucursal.setAdapter(spinner_adapter_sucursales);
        spSucursal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sucursal=position+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spTipoSol=(Spinner)view.findViewById(R.id.spTipoSol);
        ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( this.getContext(), R.array.tipo_solicitudes , android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoSol.setAdapter(spinner_adapter);
        spTipoSol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoSolicitud=position+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etDescripcion=(EditText)view.findViewById(R.id.etDescriptionS);
        btnIngresarPQR=(Button) view.findViewById(R.id.btnCrearPqr);

        btnIngresarPQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Solicitud solicitud=new Solicitud();
                solicitud.setDescripcion(etDescripcion.getText().toString());
                solicitud.setSucursal(String.valueOf(sucursal));
                solicitud.setTiposolicitud(String.valueOf(tipoSolicitud));
                solicitud.setUsuario(String.valueOf(user.getDocumento()));
                Date fecha=new Date();
                solicitud.setFechaCreacion(fecha.toString());
                restInterface.createSolicitud(solicitud, new Callback<Solicitud>() {
                    @Override
                    public void success(Solicitud solicitud, Response response) {
                        Toast.makeText(getContext(), "Solicitud creada correctamente",Toast.LENGTH_LONG).show();
                        //ListSolicitudesFragmentPend sFragment=new ListSolicitudesFragmentPend();
                        Bundle arguments=new Bundle();
                        arguments.putParcelable("Usuario",user);
                        ListSolicitudesFragmentPend fragmentoSolicitud= ListSolicitudesFragmentPend.newInstance(arguments);

                        FragmentManager fragmentManager=getFragmentManager();
                        fragmentManager
                                .beginTransaction()
                                .replace(R.id.main_content, fragmentoSolicitud)
                                .commit();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getContext(), "algun eror ocurrió en el envío",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });








        return view;
    }

}
