package co.edu.udea.quejatec.Fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import co.edu.udea.quejatec.Model.RestInterface;
import co.edu.udea.quejatec.Model.Solicitud;
import co.edu.udea.quejatec.R;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SolicitudDetail extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Solicitud solicitud;
    private TextView tvTitulo;
    private TextInputEditText etDocumento;
    private TextView tvTipoSol;
    private Spinner spTipoSol;
    private Spinner spSucursal;
    private TextView tvSucursal;
    private TextInputEditText etDescripcion;
    private Button btnUpdate;
    private int tipoSolicitud;
    private int sucursal;
    String url= "https://apirest-proyecto-quejatec.herokuapp.com/api";

    public SolicitudDetail() {
        // Required empty public constructor
    }

    public static SolicitudDetail newInstance(Bundle arguments){
        SolicitudDetail f= new SolicitudDetail();
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
        View view= inflater.inflate(R.layout.fragment_solicitud_detail, container, false);
        Bundle extras=getArguments();
        solicitud=extras.getParcelable("Solicitud");

        tvTitulo=(TextView) view.findViewById(R.id.tvSolD);
        etDocumento=(TextInputEditText)view.findViewById(R.id.etDocumentSD);
        tvTipoSol=(TextView) view.findViewById(R.id.tvTipoSolD);
        spTipoSol=(Spinner) view.findViewById(R.id.spTipoSolD);
        ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( this.getContext(), R.array.tipo_solicitudes , android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoSol.setAdapter(spinner_adapter);
        spSucursal=(Spinner) view.findViewById(R.id.spSucursalSD);
        ArrayAdapter spinner_adapter_sucursales = ArrayAdapter.createFromResource( this.getContext(), R.array.sucursales , android.R.layout.simple_spinner_item);
        spinner_adapter_sucursales.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSucursal.setAdapter(spinner_adapter_sucursales);
        tvSucursal=(TextView) view.findViewById(R.id.textViewSucursalD);
        etDescripcion=(TextInputEditText) view.findViewById(R.id.etDescriptionSD);
        btnUpdate=(Button) view.findViewById(R.id.btnCrearPqrD);


        tvTitulo.setText("Solicitud:");
        if(solicitud.getTiposolicitud().equals("1")){
            tvTipoSol.setText("QUEJA");
            spTipoSol.setEnabled(false);
        }else if(solicitud.getTiposolicitud().equals("2")){
            tvTipoSol.setText("PETICIÓN");
            spTipoSol.setEnabled(false);
        }else if(solicitud.getTiposolicitud().equals("3")){
            tvTipoSol.setText("RECLAMO");
            spTipoSol.setEnabled(false);
        }

        if(solicitud.getSucursal().equals("1")){
            tvSucursal.setText("Medellín");
            spSucursal.setEnabled(false);
        }else if(solicitud.getSucursal().equals("2")){
            tvSucursal.setText("Bogotá");
            spSucursal.setEnabled(false);
        }else if(solicitud.getSucursal().equals("3")){
            tvSucursal.setText("Manizales");
            spSucursal.setEnabled(false);
        }else if(solicitud.getSucursal().equals("4")){
            tvSucursal.setText("Barranquilla");
            spSucursal.setEnabled(false);
        }

        etDescripcion.setText(solicitud.getDescripcion());
        etDescripcion.setEnabled(false);
        etDocumento.setText(solicitud.getUsuario());
        etDocumento.setEnabled(false);

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(url).build();
        final RestInterface restInterface=restAdapter.create(RestInterface.class);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spTipoSol.setEnabled(true);
                spSucursal.setEnabled(true);
                etDescripcion.setEnabled(true);
                btnUpdate.setText("ACTUALIZAR");
                spSucursal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        sucursal=position+1;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                spTipoSol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        tipoSolicitud=position+1;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        solicitud.setDescripcion(etDescripcion.getText().toString());
                        solicitud.setSucursal(String.valueOf(sucursal));
                        solicitud.setTiposolicitud(String.valueOf(tipoSolicitud));
                        restInterface.updateSolicitud(solicitud, new Callback<Solicitud>() {
                            @Override
                            public void success(Solicitud solicitud, Response response) {
                                FragmentManager fragmentManager=getFragmentManager();
                                ListSolicitudesFragmentPend listSolicitudesFragmentPend =new ListSolicitudesFragmentPend();
                                Toast.makeText(getContext(), "Solicitud actualizada correctamente",Toast.LENGTH_LONG).show();
                                fragmentManager
                                        .beginTransaction()
                                        .replace(R.id.main_content, listSolicitudesFragmentPend)
                                        .commit();
                            }

                            @Override
                            public void failure(RetrofitError error) {

                            }
                        });
                    }
                });
            }
        });


        return view;
    }

}
