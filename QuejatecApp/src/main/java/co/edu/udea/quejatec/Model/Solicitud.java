
package co.edu.udea.quejatec.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Solicitud {

    @SerializedName("tiposolicitud")
    @Expose
    private String tiposolicitud;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("sucursal")
    @Expose
    private String sucursal;
    @SerializedName("usuario")
    @Expose
    private String usuario;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getTiposolicitud() {
        return tiposolicitud;
    }

    public void setTiposolicitud(String tiposolicitud) {
        this.tiposolicitud = tiposolicitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
