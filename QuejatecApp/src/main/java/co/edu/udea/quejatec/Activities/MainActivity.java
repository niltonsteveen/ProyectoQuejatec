package co.edu.udea.quejatec.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

import co.edu.udea.quejatec.Fragments.FragmentoTabs;
import co.edu.udea.quejatec.Fragments.ListSolicitudesFragmentPend;
import co.edu.udea.quejatec.Model.RestInterface;
import co.edu.udea.quejatec.Model.Usuario;
import co.edu.udea.quejatec.R;
import co.edu.udea.quejatec.utils.MyFirebaseMessagingService;
import co.edu.udea.quejatec.utils.PreFragConf;
import co.edu.udea.quejatec.utils.RestClientBuilder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Usuario usuario;
    private TextView tvEmailHeader;
    private CircleImageView circleImageView;
    private TextView tvNombreHeader;
    private static final int item_inicio1= 1;
    private static final int item_cuenta1= 2;
    private static final int item_solicitudes1= 3;
    private static final int item_configuracion1= 4;
    private static final int item_usuarios1= 5;
    private static final int item_logout1= 6;


    public MainActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras=getIntent().getExtras();
        usuario=extras.getParcelable("Usuario");

       // Group group=(Group) findViewById(R.id.grupo1);

        setToolbar();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView=null;
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu=navigationView.getMenu();
        if(usuario.getTipoUsuario().equals("1")){
            menu.add(R.id.grupo1,item_inicio1,Menu.NONE, "Inicio").setChecked(true).setIcon(R.drawable.home).setCheckable(true);
            menu.add(R.id.grupo1,item_cuenta1,Menu.NONE, "Perfil").setIcon(R.drawable.account).setCheckable(true);
            menu.add(R.id.grupo1,item_solicitudes1,Menu.NONE, "Solicitudes").setIcon(R.drawable.belll).setCheckable(true);
            menu.add(R.id.grupo1,item_configuracion1,Menu.NONE, "Configuración").setIcon(R.drawable.settings).setCheckable(true);
            menu.add(R.id.grupo1,item_logout1,Menu.NONE, "Cerrar Sesión").setIcon(R.drawable.account_off).setCheckable(true);

        }else{
            menu.add(R.id.grupo2,item_solicitudes1,Menu.NONE, "Solicitudes").setChecked(true).setIcon(R.drawable.home).setCheckable(true);
            menu.add(R.id.grupo2,item_cuenta1,Menu.NONE, "Perfil").setIcon(R.drawable.account).setCheckable(true);
            menu.add(R.id.grupo2,item_usuarios1,Menu.NONE, "Usuarios").setIcon(R.drawable.belll).setCheckable(true);
            menu.add(R.id.grupo1,item_logout1,Menu.NONE, "Cerrar Sesión").setIcon(R.drawable.account_off).setCheckable(true);
           // menu.add(R.id.grupo1,item_configuracion1,Menu.NONE, "Configuración").setIcon(R.drawable.settings).setCheckable(true);
        }



        circleImageView=(CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.imgPerfilCir);
        if(!usuario.getTipoUsuario().equals("1")){
            circleImageView.setImageResource(R.drawable.usuario_u);
        }else{
            circleImageView.setImageResource(R.drawable.user_2);
        }
        tvNombreHeader=(TextView)navigationView.getHeaderView(0).findViewById(R.id.tvNameHeader);
        tvNombreHeader.setText(usuario.getNombre());
        tvEmailHeader=(TextView) navigationView.getHeaderView(0).findViewById(R.id.tvEmailHeader);
        tvEmailHeader.setText(usuario.getEmail());

        if (navigationView != null) {
            prepararDrawer(navigationView);
            // Seleccionar item por defecto
            seleccionarItem(navigationView.getMenu().getItem(0));
        }
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("token", "Refreshed token: " + refreshedToken);
        usuario.setToken(refreshedToken);

        sendRegistrationToServer(usuario);
        startService(new Intent(this, MyFirebaseMessagingService.class));

    }

    private void sendRegistrationToServer(Usuario  user) {
        final RestInterface restInterface= RestClientBuilder.restInterface();

        restInterface.updateUser(user,  new Callback<Usuario>() {
            @Override
            public void success(Usuario usuario, Response response) {
                Log.e("tokenupdated", usuario.getToken());

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("fallo1", error.getMessage());
            }
        });
        //final String token = user.getToken();
        //final String message ="mensaje de prueba app to app";
        // NotificationSender.sendNotification(token, message);
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
        FragmentoTabs fragmentoTabs = null;
        ListSolicitudesFragmentPend fragmentoSolicitud = null;

        FragmentManager fragmentManager = getSupportFragmentManager();
        boolean valor=false;
        switch (itemDrawer.getItemId()) {

            case item_cuenta1:
                valor=true;
                fragmentoTabs = new FragmentoTabs();
                break;
           case item_solicitudes1:
                valor=false;
                fragmentoTabs = new FragmentoTabs();
                break;
            case item_configuracion1:
                getFragmentManager().beginTransaction().replace(R.id.main_content, new PreFragConf()).commit();
                break;
            case item_logout1:
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("sesion_activa",false);
                editor.commit();
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        if (fragmentoTabs != null) {
            fragmentoTabs.setUserLoged(usuario);
            fragmentoTabs.setValor(valor);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_content, fragmentoTabs)
                    .commit();
        }
        /*if (fragmentoSolicitud != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_content, fragmentoSolicitud)
                    .commit();
        }*/

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
