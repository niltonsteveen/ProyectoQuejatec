
package co.edu.udea.quejatec.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Solicitud implements Parcelable {

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
    @SerializedName("respuesta")
    @Expose
    private String respuesta;
    @SerializedName("fecha_creacion")
    @Expose
    private String fechaCreacion;
    @SerializedName("fecha_respuesta")
    @Expose
    private String fechaRespuesta;
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

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaRespuesta() {
        return fechaRespuesta;
    }

    public void setFechaRespuesta(String fechaRespuesta) {
        this.fechaRespuesta = fechaRespuesta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Solicitud(){}

    protected Solicitud(Parcel in) {
        tiposolicitud = in.readString();
        descripcion = in.readString();
        sucursal = in.readString();
        usuario = in.readString();
        respuesta = in.readString();
        fechaCreacion = in.readString();
        fechaRespuesta = in.readString();
        id = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tiposolicitud);
        dest.writeString(descripcion);
        dest.writeString(sucursal);
        dest.writeString(usuario);
        dest.writeString(respuesta);
        dest.writeString(fechaCreacion);
        dest.writeString(fechaRespuesta);
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Solicitud> CREATOR = new Parcelable.Creator<Solicitud>() {
        @Override
        public Solicitud createFromParcel(Parcel in) {
            return new Solicitud(in);
        }

        @Override
        public Solicitud[] newArray(int size) {
            return new Solicitud[size];
        }
    };
}