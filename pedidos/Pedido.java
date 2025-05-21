package pedidos;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private static int contador = 0; // Contador estático para generar IDs únicos
    private final int id; // ID único para cada pedido
    private final List<LineaPedido> lineas; // Lista de las líneas del pedido (final)

    // Constructor para inicializar el pedido
    public Pedido() {
        this.id = ++contador; // Asigna un nuevo ID incrementando el contador
        this.lineas = new ArrayList<>(); // Inicializa la lista de líneas del pedido
    }

    // Método para agregar una línea de producto al pedido
    public void agregarLinea(LineaPedido linea) {
        lineas.add(linea); // Agrega la línea a la lista de líneas
    }

    // Método para calcular el total del pedido (suma de los subtotales de todas las líneas)
    public double calcularTotal() {
        return lineas.stream().mapToDouble(LineaPedido::getSubtotal).sum(); // Suma los subtotales de todas las líneas
    }

    // Método para representar el pedido en formato mas legible
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Pedido #" + id + "\n");
        // Itera sobre cada línea de pedido y agrega la información al StringBuilder
        for (LineaPedido l : lineas) {
            sb.append(l.getProducto().getNombre())
              .append(" x").append(l.getCantidad())
              .append(" = $").append(l.getSubtotal())
              .append("\n");
        }
        sb.append("Total: $").append(calcularTotal()); // Muestra el total del pedido
        return sb.toString(); // Devuelve la representación como cadena
    }
}
