package co.edu.udea.quejatec.Fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.quejatec.Model.RestInterface;
import co.edu.udea.quejatec.Model.Solicitud;
import co.edu.udea.quejatec.Model.Usuario;
import co.edu.udea.quejatec.R;
import co.edu.udea.quejatec.utils.SolicitudAdapter;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ListSolicitudesFragmentPend extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Usuario usuario;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    FragmentManager fragmentManager=getFragmentManager();

    List items=new ArrayList();

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }

    String url= "https://apirest-proyecto-quejatec.herokuapp.com/api";

    public ListSolicitudesFragmentPend() {
        // Required empty public constructor
    }

    public static ListSolicitudesFragmentPend newInstance(Bundle arguments){
        ListSolicitudesFragmentPend f= new ListSolicitudesFragmentPend();
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

        final View view= inflater.inflate(R.layout.fragment_list_solicitudes, container, false);
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(url).build();
        final RestInterface restInterface=restAdapter.create(RestInterface.class);
        Bundle extras=getArguments();
        usuario=extras.getParcelable("Usuario");
       // System.out.println(usuario.getNombre()+"--------------******************---------------------------");
        fragmentManager=getFragmentManager();

        if(usuario.getTipoUsuario().equals("1")) {
            restInterface.getSolicitudes(new Callback<List<Solicitud>>() {
                @Override
                public void success(List<Solicitud> solicituds, Response response) {
                    List usuarios = new ArrayList();
                    for (int i = 0; i < solicituds.size(); i++) {
                        if (Integer.parseInt(solicituds.get(i).getUsuario()) == usuario.getDocumento()&&
                                solicituds.get(i).getRespuesta()==null) {
                            usuarios.add(solicituds.get(i));
                        }
                    }

                    recycler = (RecyclerView) view.findViewById(R.id.reciclador1);
                    recycler.setHasFixedSize(true);
                    lManager = new LinearLayoutManager(getContext());
                    recycler.setLayoutManager(lManager);
                    adapter = new SolicitudAdapter(usuarios, getContext(), fragmentManager, true, false);
                    recycler.setAdapter(adapter);
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getContext(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                }
            });

        }else{
            restInterface.getSolicitudes(new Callback<List<Solicitud>>() {


                @Override
                public void success(List<Solicitud> solicituds, Response response) {
                    List solicitudes = new ArrayList();
                    for (int i = 0; i < solicituds.size(); i++) {
                        if (solicituds.get(i).getRespuesta()==null) {
                            solicitudes.add(solicituds.get(i));
                        }
                    }

                    recycler = (RecyclerView) view.findViewById(R.id.reciclador1);
                    recycler.setHasFixedSize(true);
                    lManager = new LinearLayoutManager(getContext());
                    recycler.setLayoutManager(lManager);
                    adapter = new SolicitudAdapter(solicitudes, getContext(), fragmentManager, false,false);
                    recycler.setAdapter(adapter);
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getContext(), "Ocurrio un error", Toast.LENGTH_LONG).show();
                }
            });
        }

        floatingActionButton=(FloatingActionButton) view.findViewById(R.id.fabAddSol);
        if(!usuario.getTipoUsuario().equals("1")){
            floatingActionButton.setVisibility(view.GONE);
        }else{
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle arguments=new Bundle();
                    arguments.putParcelable("Usuario",usuario);

                    SolicitudFragment solicitudFragment=SolicitudFragment.newInstance(arguments);


                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.main_content, solicitudFragment)
                            .commit();

                }
            });
        }



        return view;

    }

}
