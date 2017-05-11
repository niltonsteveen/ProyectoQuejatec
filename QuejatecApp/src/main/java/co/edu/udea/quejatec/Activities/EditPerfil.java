package co.edu.udea.quejatec.Activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import co.edu.udea.quejatec.Model.RestInterface;
import co.edu.udea.quejatec.Model.Usuario;
import co.edu.udea.quejatec.R;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class EditPerfil extends AppCompatActivity {

    private TextInputEditText documento;
    private TextInputEditText password;
    private TextInputEditText contacto;
    private TextInputEditText email;
    private TextInputEditText direccion;
    private TextInputEditText nombre;
    private Usuario usuario;
    private Button btnUpdate;
    String url= "https://apirest-proyecto-quejatec.herokuapp.com/api";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_perfil);

        Bundle extras=getIntent().getExtras();
        usuario=(Usuario)extras.getParcelable("Usuario");
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(url).build();
        final RestInterface restInterface=restAdapter.create(RestInterface.class);

        documento=(TextInputEditText) findViewById(R.id.etDocumentU);
        documento.setText(usuario.getDocumento().toString());
        documento.setEnabled(false);

        password=(TextInputEditText) findViewById(R.id.etPasswordU);
        password.setText(usuario.getPassword());

        contacto=(TextInputEditText) findViewById(R.id.etContactU);
        contacto.setText(usuario.getContacto());

        email=(TextInputEditText) findViewById(R.id.etEmailU);
        email.setText(usuario.getEmail());

        direccion=(TextInputEditText) findViewById(R.id.etDirectionU);
        direccion.setText(usuario.getDireccion());

        nombre=(TextInputEditText) findViewById(R.id.etNameU);
        nombre.setText(usuario.getNombre());

        btnUpdate=(Button)findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario.setContacto(contacto.getText().toString());
                usuario.setNombre(nombre.getText().toString());
                usuario.setDireccion(direccion.getText().toString());
                usuario.setEmail(email.getText().toString());
                usuario.setPassword(password.getText().toString());

                restInterface.updateUser(usuario,new Callback<Usuario>() {
                    @Override
                    public void success(Usuario user, Response response) {
                        Toast.makeText(getBaseContext(), "Datos actualizados corectamente",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(EditPerfil.this, MainActivity.class);
                        intent.putExtra("Usuario",user);
                        startActivity(intent);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getBaseContext(), "Error en la actualizacion",Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
    }
}
