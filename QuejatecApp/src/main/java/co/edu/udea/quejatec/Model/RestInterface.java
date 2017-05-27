package co.edu.udea.quejatec.Model;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;


/**
 * Created by nilto on 1/05/2017.
 */

public interface RestInterface {

    @GET("/usuarios")
    void getUsers(Callback<List<Usuario>> cb) ;

    @POST("/usuarios")
    void createUser(@Body Usuario user, Callback<Usuario> cb);

    @GET("/usuarios/{id}")
    void getUserById(@Path("id") String id, Callback<Usuario> cb);

    @PUT("/usuarios")
    void updateUser(@Body Usuario user, Callback<Usuario> cb);

    @POST("/solicitudes")
    void createSolicitud(@Body Solicitud solicitud, Callback<Solicitud> cb);

    @PUT("/solicitudes")
    void updateSolicitud(@Body Solicitud solicitud, Callback<Solicitud> cb);

    @GET("/solicitudes")
    void getSolicitudes(Callback<List<Solicitud>> cb) ;


}
