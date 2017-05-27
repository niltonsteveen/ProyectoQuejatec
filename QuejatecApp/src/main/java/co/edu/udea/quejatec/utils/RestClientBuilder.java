package co.edu.udea.quejatec.utils;

import co.edu.udea.quejatec.Model.RestInterface;
import retrofit.RestAdapter;

/**
 * Created by AW 13 on 15/05/2017.
 */

public class RestClientBuilder {


    public static final String URL_APP = "https://apirest-proyecto-quejatec.herokuapp.com/api";
    public static RestInterface restInterface;


    public static RestInterface restInterface(){

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(URL_APP).build();
        return  restAdapter.create(RestInterface.class);
    }

}
