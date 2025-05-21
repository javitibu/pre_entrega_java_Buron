package productos;

public class Producto {
    private static int contador = 0;           // contador estático para generar IDs únicos
    private final int id;                      // ID único por producto (no cambia → final)
    private final String nombre;               // nombre no cambia una vez creado → final
    private double precio;                     // precio puede cambiar con setter
    private int stock;                         // stock puede cambiar con setter

    // Constructor
    public Producto(String nombre, double precio, int stock) {
        this.id = ++contador;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    // Setters
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // metodo para representar el producto como texto, ordenado y listo
    @Override
    public String toString() {
        return "ID: " + id + " | " + nombre + " | $" + precio + " | Stock: " + stock;
    }
}