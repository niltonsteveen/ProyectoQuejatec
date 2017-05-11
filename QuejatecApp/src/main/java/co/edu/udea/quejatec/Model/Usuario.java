
package co.edu.udea.quejatec.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Usuario implements Parcelable {

    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("contacto")
    @Expose
    private String contacto;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("tipoUsuario")
    @Expose
    private String tipoUsuario;
    @SerializedName("documento")
    @Expose
    private Integer documento;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("calificacion")
    @Expose
    private String calificacion;
    @SerializedName("direccion")
    @Expose
    private String direccion;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Integer getDocumento() {
        return documento;
    }

    public void setDocumento(Integer documento) {
        this.documento = documento;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Usuario(){}

    protected Usuario(Parcel in) {
        nombre = in.readString();
        contacto = in.readString();
        email = in.readString();
        tipoUsuario = in.readString();
        documento = in.readByte() == 0x00 ? null : in.readInt();
        password = in.readString();
        calificacion = in.readString();
        direccion = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(contacto);
        dest.writeString(email);
        dest.writeString(tipoUsuario);
        if (documento == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(documento);
        }
        dest.writeString(password);
        dest.writeString(calificacion);
        dest.writeString(direccion);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Usuario> CREATOR = new Parcelable.Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };
}
