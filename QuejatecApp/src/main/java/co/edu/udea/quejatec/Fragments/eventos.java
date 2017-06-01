package co.edu.udea.quejatec.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import co.edu.udea.quejatec.Model.Noticia;
import co.edu.udea.quejatec.Model.RestInterface;
import co.edu.udea.quejatec.R;
import co.edu.udea.quejatec.utils.NoticiasAdapter;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class eventos extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView rv;

    FloatingActionButton bf;
    List<NoticiasPojo> listaEventos;
    List<NoticiasPojo> listaEventos1;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    FragmentManager fragmentManager=getFragmentManager();
    String url= "https://apirest-proyecto-quejatec.herokuapp.com/api";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public eventos() {
        // Required empty public constructor
    }

    public void setListaEventos1(List<NoticiasPojo> listaEventos1) {
        this.listaEventos1 = listaEventos1;
    }

    public void setLista(List<NoticiasPojo> listaEventos) {
        this.listaEventos = listaEventos;
    }

    public static eventos newInstance(Bundle arguments){
        eventos f= new eventos();
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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_event, container, false);

        rv = (RecyclerView)view.findViewById(R.id.reciclador);
        rv.setHasFixedSize(true);
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(url).build();
        final RestInterface restInterface=restAdapter.create(RestInterface.class);

        restInterface.getNoticias(new Callback<List<Noticia>>() {
            @Override
            public void success(List<Noticia> noticias, Response response) {


                recycler = (RecyclerView) view.findViewById(R.id.reciclador);
                recycler.setHasFixedSize(true);
                lManager = new LinearLayoutManager(getContext());
                recycler.setLayoutManager(lManager);
                adapter = new NoticiasAdapter(noticias, getContext(), fragmentManager);
                recycler.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
