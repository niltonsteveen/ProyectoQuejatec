package co.edu.udea.quejatec.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Noticia {

    @SerializedName("informacion")
    @Expose
    private String informacion;
    @SerializedName("titulo")
    @Expose
    private String titulo;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}