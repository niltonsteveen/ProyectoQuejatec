package co.edu.udea.quejatec.utils;

/**
 * Created by nilto on 9/05/2017.
 */

public class UtilClass {

    public static String hidePassword(int longitud) {
        String res="";
        for (int i=0;i<longitud;i++){
            res=res+"*";
        }
        return res;
    }
}
