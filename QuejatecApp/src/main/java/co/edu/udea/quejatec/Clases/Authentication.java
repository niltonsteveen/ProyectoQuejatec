package co.edu.udea.quejatec.Clases;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

import java.util.concurrent.Executor;

import co.edu.udea.quejatec.Activities.LoginActivity;


public class Authentication {
    String TAG = "TEST";
    FirebaseAuth mAuth;

    public Authentication() {
        mAuth = FirebaseAuth.getInstance();
    }

    public boolean crearCuenta(String correo, String clave){
        final boolean[] respuesta = {true};
        mAuth.createUserWithEmailAndPassword(correo, clave)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            respuesta[0] = true;
                        }


                    }
                });
        return respuesta[0];
    }

    private boolean autentificarse(String email, String password, final Context context ) {
        final boolean[] respuesta = {true};
        Log.d(TAG, "signIn:" + email);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        try {
                            AuthResult resultado = task.getResult();
                            Toast.makeText(context, resultado.getUser().getUid(), Toast.LENGTH_SHORT).show();
                            // obtener excepción
                            if(task != null) {
                                throw task.getException();
                            }
                        }
                        catch (FirebaseAuthInvalidCredentialsException e){
                            // mostrar excepción
                            respuesta[0] = false;
                            Toast.makeText(context, "Datos incompletos", Toast.LENGTH_SHORT).show();
                            return;
                        }catch (RuntimeExecutionException e) {
                            e.printStackTrace();
                            if( e.getMessage().equals("com.google.firebase.auth.FirebaseAuthInvalidCredentialsException: The email address is badly formatted.") ){
                                respuesta[0] = false;
                                Toast.makeText(context, "Ingrese una dirección de correo valida", Toast.LENGTH_SHORT).show();
                            }
                            else if( e.getMessage().equals("com.google.firebase.auth.FirebaseAuthInvalidCredentialsException: The password is invalid or the user does not have a password.") ){
                                respuesta[0] = false;
                                Toast.makeText(context,"Datos incorrectos", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                respuesta[0] = false;
                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            //Toast.makeText(LoginActivity.this, e.getMessage()+"  oli", Toast.LENGTH_SHORT).show();
                        }

                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            //Toast.makeText(ActivityFirebase.this, R.string.auth_failed,Toast.LENGTH_SHORT).show();
                        }
                        if (!task.isSuccessful()) {
                        }
                    }
                });
        return respuesta[0];
    }

}
