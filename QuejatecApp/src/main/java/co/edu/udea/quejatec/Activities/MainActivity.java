package co.edu.udea.quejatec.Activities;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import co.edu.udea.quejatec.Fragments.FragmentPerfil;
import co.edu.udea.quejatec.Fragments.FragmentoCuenta;
import co.edu.udea.quejatec.Model.Usuario;
import co.edu.udea.quejatec.R;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Usuario usuario;

    public MainActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras=getIntent().getExtras();

        usuario=(Usuario)extras.getParcelable("Usuario");

        setToolbar();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (navigationView != null) {
            prepararDrawer(navigationView);
            // Seleccionar item por defecto
            seleccionarItem(navigationView.getMenu().getItem(0));
        }
    }

    private void prepararDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        seleccionarItem(menuItem);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });

    }

    private void seleccionarItem(MenuItem itemDrawer) {
        FragmentoCuenta fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (itemDrawer.getItemId()) {

            case R.id.item_cuenta:
                fragmentoGenerico = new FragmentoCuenta();

                break;
           /* case R.id.item_solicitudes:
                fragmentoGenerico = new FragmentoCategorias();
                break;
            case R.id.item_configuracion:
                startActivity(new Intent(this, ActividadConfiguracion.class));
                break;*/
        }
        if (fragmentoGenerico != null) {
            fragmentoGenerico.setUserLoged(usuario);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_content, fragmentoGenerico)
                    .commit();
        }

        // Setear título actual
        setTitle(itemDrawer.getTitle());
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actividad_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
