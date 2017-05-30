package co.edu.udea.quejatec.utils;

/**
 * Created by nilto on 14/03/2017.
 */

public class NoticiasPojo {

    private String id;
    private String titulo;
    private String información;
    private String foto;

    public NoticiasPojo(String información, String foto, String id) {
        this.foto = foto;
        this.id = id;
        this.información = información;

    }

    public NoticiasPojo() {
    }

    public String getTitulo() {return titulo;}

    public void setTitulo(String titulo) {this.titulo = titulo;}

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInformación() {
        return información;
    }

    public void setInformación(String información) {
        this.información = información;
    }
}
