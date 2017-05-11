package co.edu.udea.quejatec.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import co.edu.udea.quejatec.Model.RestInterface;
import co.edu.udea.quejatec.Model.Usuario;
import co.edu.udea.quejatec.R;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends AppCompatActivity {

    private TextView tvRegister;
    EditText etDocument;
    EditText etPassword;
    Button btnIngresar;
    String url= "https://apirest-proyecto-quejatec.herokuapp.com/api";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etDocument = (EditText) findViewById(R.id.etDocumentL);
        etPassword = (EditText) findViewById(R.id.etPasswordL);
        btnIngresar= (Button) findViewById(R.id.btnIngresar);

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(url).build();

        final RestInterface restInterface=restAdapter.create(RestInterface.class);

        tvRegister=(TextView) findViewById(R.id.txtvRegister);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegisterActivity = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intentRegisterActivity);
            }
        });

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(isValidInfo()){
                    restInterface.getUserById(etDocument.getText().toString(), new Callback<Usuario>() {
                        @Override
                        public void success(Usuario usuario, Response response) {
                            if(!usuario.getPassword().equals(etPassword.getText().toString())){
                                Toast.makeText(getBaseContext(), "Contraseña incorrecta",Toast.LENGTH_LONG).show();
                            }else{

                                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("Usuario",usuario);
                                startActivity(intent);

                            }

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Toast.makeText(getBaseContext(), "Usuario no registrado",Toast.LENGTH_LONG).show();
                        }
                    });

               }
            }
        });
    }

    private boolean isValidInfo() {
        boolean res=false;
        if(TextUtils.isEmpty(etDocument.getText().toString())){
            etDocument.setError(getString(R.string.errorUsername));
            etDocument.requestFocus();
            res=false;
        }else if(TextUtils.isEmpty(etPassword.getText().toString())){
            etPassword.setError(getString(R.string.errorPassword));
            etPassword.requestFocus();
            res=false;
        }else{
            res=true;
        }
        return res;
    }
}
