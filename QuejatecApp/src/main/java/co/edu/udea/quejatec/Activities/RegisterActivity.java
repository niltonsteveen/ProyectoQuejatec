package co.edu.udea.quejatec.Activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import co.edu.udea.quejatec.Model.RestInterface;
import co.edu.udea.quejatec.Model.Usuario;
import co.edu.udea.quejatec.R;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText etName;
    private TextInputEditText etContact;
    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private TextInputEditText etAddress;
    private TextInputEditText etDocument;
    private int tipoUsuario;
    private Button btnRegister;
    String url= "https://apirest-proyecto-quejatec.herokuapp.com/api";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName=(TextInputEditText) findViewById(R.id.etNameR);
        etContact=(TextInputEditText) findViewById(R.id.etContactR);
        etEmail=(TextInputEditText) findViewById(R.id.etEmailR);
        etPassword=(TextInputEditText) findViewById(R.id.etPasswordR);
        etAddress=(TextInputEditText) findViewById(R.id.etDirectionR);
        etDocument=(TextInputEditText) findViewById(R.id.etDocumentR);
        tipoUsuario=1;
        btnRegister = (Button) findViewById(R.id.btnRegister);


        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(url).build();

        final RestInterface restInterface=restAdapter.create(RestInterface.class);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidInfo()) {
                    restInterface.getUserById(etDocument.getText().toString(), new Callback<Usuario>() {
                        @Override
                        public void success(Usuario usuario, Response response) {
                            etDocument.setError(getString(R.string.errorUnicidad));
                            etDocument.requestFocus();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Usuario user=new Usuario();
                            user.setDocumento(Integer.parseInt(etDocument.getText().toString()));
                            user.setPassword(etPassword.getText().toString());
                            user.setEmail(etEmail.getText().toString());
                            user.setNombre(etName.getText().toString());
                            user.setContacto(etContact.getText().toString());
                            user.setDireccion(etAddress.getText().toString());
                            user.setTipoUsuario(String.valueOf(tipoUsuario));

                            restInterface.createUser(user, new Callback<Usuario>() {
                                @Override
                                public void success(Usuario usuario, Response response) {
                                    Toast.makeText(getBaseContext(), "Se ha registrado correctamente", Toast.LENGTH_LONG).show();
                                    Intent intent= new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    Toast.makeText(getBaseContext(), "Se ocasionó un error en el registro", Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                    });
                }
            }
        });
    }


    private boolean isValidInfo() {
        boolean res;
        if(TextUtils.isEmpty(etDocument.getText().toString())){
            etDocument.setError(getString(R.string.errorDocument));
            etDocument.requestFocus();
            res=false;
        }else if(!TextUtils.isDigitsOnly(etDocument.getText().toString())){
            etDocument.setError(getString(R.string.errorNumeric));
            etDocument.requestFocus();
            res=false;
        }else if(TextUtils.isEmpty(etPassword.getText().toString())){
            etPassword.setError(getString(R.string.errorPassword));
            etPassword.requestFocus();
            res=false;
        }else if(TextUtils.isEmpty(etEmail.getText().toString())){
            etEmail.setError(getString(R.string.errorEmail1));
            etEmail.requestFocus();
            res=false;
        }else if(!validateEmail(etEmail.getText().toString())){
            etEmail.setError(getString(R.string.errorEmail));
            etEmail.requestFocus();
            res=false;
        }else if(TextUtils.isEmpty(etName.getText().toString())){
            etName.setError(getString(R.string.errorName));
            etName.requestFocus();
            res=false;
        }else if(TextUtils.isEmpty(etContact.getText().toString())){
            etContact.setError(getString(R.string.errorContact));
            etContact.requestFocus();
            res=false;
        }else if(!TextUtils.isDigitsOnly(etContact.getText().toString())){
            etContact.setError(getString(R.string.errorNumeric));
            etContact.requestFocus();
            res=false;
        }else if(etContact.getText().toString().length()<=7){
            etContact.setError(getString(R.string.longContact));
            etContact.requestFocus();
            res=false;
        }else if(TextUtils.isEmpty(etAddress.getText().toString())){
            etAddress.setError(getString(R.string.errorGeneral));
            etAddress.requestFocus();
            res=false;
        }else{
            res = true;
        }
        return res;
    }

    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private boolean validateEmail(String s) {
        // Compiles the given regular expression into a pattern.
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);

        // Match the given input against this pattern
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();

    }
}
