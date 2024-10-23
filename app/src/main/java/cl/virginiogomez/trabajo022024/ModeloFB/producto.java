package cl.virginiogomez.trabajo022024.ModeloFB;

public class producto {
    private String id;
    private String nombre;
    private double precio;
    private int cantidad;
    private String nombreCategoria;

    // Constructor
    public producto(String id, String nombre, double precio, int cantidad, String nombreCategoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.nombreCategoria = nombreCategoria;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    @Override
    public String toString() {
        return nombre + " - " + nombreCategoria + ": " + precio + " (" + cantidad + ")";
    }
}
