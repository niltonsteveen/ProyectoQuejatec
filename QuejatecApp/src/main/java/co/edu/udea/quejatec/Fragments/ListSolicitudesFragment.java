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
import co.edu.udea.quejatec.Model.Usuario;
import co.edu.udea.quejatec.R;
import co.edu.udea.quejatec.utils.SolicitudAdapter;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ListSolicitudesFragment extends Fragment {
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
    List items=new ArrayList();

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }

    String url= "https://apirest-proyecto-quejatec.herokuapp.com/api";

    public ListSolicitudesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListSolicitudesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListSolicitudesFragment newInstance(String param1, String param2) {
        ListSolicitudesFragment fragment = new ListSolicitudesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        Bundle extras=getActivity().getIntent().getExtras();
        usuario=extras.getParcelable("Usuario");


        restInterface.getSolicitudes(new Callback<List<Solicitud>>() {
            @Override
            public void success(List<Solicitud> solicituds, Response response) {
                for(int i=0;i<solicituds.size();i++){
                    if(Integer.parseInt(solicituds.get(i).getUsuario())!=usuario.getDocumento()){
                        solicituds.remove(solicituds.get(i));
                    }
                }

                recycler = (RecyclerView) view.findViewById(R.id.reciclador1);
                recycler.setHasFixedSize(true);
                lManager = new LinearLayoutManager(getContext());
                recycler.setLayoutManager(lManager);
                adapter = new SolicitudAdapter(solicituds);
                recycler.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getContext(), "Ocurrio un error",Toast.LENGTH_LONG).show();
            }
        });



        floatingActionButton=(FloatingActionButton) view.findViewById(R.id.fabAddSol);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle arguments=new Bundle();
                arguments.putParcelable("Usuario",usuario);

                SolicitudFragment solicitudFragment=SolicitudFragment.newInstance(arguments);

                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.main_content, solicitudFragment)
                        .commit();

            }
        });

        return view;

    }

}
