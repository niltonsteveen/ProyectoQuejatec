package co.edu.udea.quejatec.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.quejatec.Model.Usuario;
import co.edu.udea.quejatec.R;


/**
 * Fragmento de la sección "Mi Cuenta"
 */
public class FragmentoCuenta extends Fragment {

    private AppBarLayout appBar;
    private TabLayout pestanas;
    private ViewPager viewPager;
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public FragmentoCuenta(Usuario user) {
        this.usuario=user;
    }

    public FragmentoCuenta( ) {

    }

    public interface Comunicador{
        public void enviar(Usuario user);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paginado, container, false);

        if (savedInstanceState == null) {
            insertarTabs(container);

            // Setear adaptador al viewpager.
            viewPager = (ViewPager) view.findViewById(R.id.pager);
            poblarViewPager(viewPager);
            pestanas.setupWithViewPager(viewPager);
        }

        return view;
    }

    public void setUserLoged(Usuario usuario){
        this.usuario=usuario;
    }

    private void insertarTabs(ViewGroup container) {
        View padre = (View) container.getParent();
        appBar = (AppBarLayout) padre.findViewById(R.id.appbar);
        pestanas = new TabLayout(getActivity());
        pestanas.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"));
        appBar.addView(pestanas);
    }

    private void poblarViewPager(ViewPager viewPager) {
        AdaptadorSecciones adapter = new AdaptadorSecciones(getFragmentManager());
        Bundle arguments=new Bundle();
        arguments.putParcelable("Usuario",this.getUsuario());
        FragmentPerfil fragmentPerfil=FragmentPerfil.newInstance(arguments);
        adapter.addFragment(fragmentPerfil, getString(R.string.titulo_tab_perfil));
        adapter.addFragment(new FragmentoTarjetas(), getString(R.string.titulo_tab_targetas));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        appBar.removeView(pestanas);
    }

    /**
     * Un {@link FragmentStatePagerAdapter} que gestiona las secciones, fragmentos y
     * títulos de las pestañas
     */
    public class AdaptadorSecciones extends FragmentStatePagerAdapter {
        private final List<Fragment> fragmentos = new ArrayList<>();
        private final List<String> titulosFragmentos = new ArrayList<>();

        public AdaptadorSecciones(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragmentos.get(position);
        }

        @Override
        public int getCount() {
            return fragmentos.size();
        }

        public void addFragment(android.support.v4.app.Fragment fragment, String title) {
            fragmentos.add(fragment);
            titulosFragmentos.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titulosFragmentos.get(position);
        }
    }
}
