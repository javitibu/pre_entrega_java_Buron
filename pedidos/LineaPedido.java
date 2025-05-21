package pedidos;

import productos.Producto; //importo la clase Producto desde el paquete productos

// esta clase representa una línea individual dentro de un pedido
// cada línea contiene un producto específico y la cantidad 

public class LineaPedido {
    private final Producto producto;
    private final int cantidad;

    public LineaPedido(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }

    public double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }
}
