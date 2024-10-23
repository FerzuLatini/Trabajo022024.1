package cl.virginiogomez.trabajo022024.ModeloFB;

public class categoria {

    private String id;
    private String nombre;
    private String descripcion;

    public categoria() {
        // Constructor vacío para Firebase
    }

    public categoria(String id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return nombre + ", " + descripcion; // Muestra nombre y descripción
    }


}


