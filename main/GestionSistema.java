package main;

// aca importo las clases necesarias de otros paquetes
import excepciones.EstockInsuficiente;
import java.util.*;
import pedidos.*;
import productos.Producto;

// Clase principal que contiene el menú y controla toda la lógica del sistema y demas
public class GestionSistema {

    // Scanner para leer datos del usuario desde la consola
    static Scanner scanner = new Scanner(System.in);

    // Lista que guarda todos los productos registrados
    static List<Producto> productos = new ArrayList<>();

    // Lista que guarda todos los pedidos realizados
    static List<Pedido> pedidos = new ArrayList<>();

    public static void main(String[] args) {
        int opcion = 0;

        // Bucle principal del sist, se repite hasta que el usuario elige salir
        do {
            mostrarMenu(); // Muestra el menú

            try {
                opcion = Integer.parseInt(scanner.nextLine()); // Lee opción ingresada por el usuario
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
                continue; // Vuelve al inicio del bucle si no es un número
            }

            // Lógica para ejecutar una acción dependiendo de la opción elegida
            switch (opcion) {
                case 1 -> agregarProducto();               
                case 2 -> listarProductos();               
                case 3 -> buscarYActualizarProducto();     
                case 4 -> eliminarProducto();              
                case 5 -> crearPedido();                   
                case 6 -> listarPedidos();                 
                case 7 -> System.out.println("Saliendo del sistema..."); 
                default -> System.out.println("Opción inválida.");       
            }
        } while (opcion != 7); // Sale del bucle si se elige 7
    }

    // Método para mostrar el menú principal al usuario
    private static void mostrarMenu() {
        System.out.println("""
                ===== SISTEMA DE GESTIÓN =====
                1) Agregar producto
                2) Listar productos
                3) Buscar/Actualizar producto
                4) Eliminar producto
                5) Crear un pedido
                6) Listar pedidos
                7) Salir
                Elija una opción:
                """);
    }

    // Método para ingresar un nuevo producto
    private static void agregarProducto() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Precio: ");
        double precio = Double.parseDouble(scanner.nextLine());

        System.out.print("Stock: ");
        int stock = Integer.parseInt(scanner.nextLine());

        // Crear nuevo producto y agregarlo a la lista
        productos.add(new Producto(nombre, precio, stock));
        System.out.println("Producto agregado.");
    }

    // Método que muestra todos los productos registrados
    private static void listarProductos() {
        for (Producto p : productos) {
            System.out.println(p);
        }
    }

    // Método para buscar un producto por ID o nombre, y actualizar su precio/stock
    private static void buscarYActualizarProducto() {
        System.out.print("Ingrese ID o nombre: ");
        String entrada = scanner.nextLine();

        Producto encontrado = null;

        // Buscar el producto en la lista
        for (Producto p : productos) {
            if (String.valueOf(p.getId()).equals(entrada) || p.getNombre().equalsIgnoreCase(entrada)) {
                encontrado = p;
                break;
            }
        }

        if (encontrado != null) {
            System.out.println("Producto encontrado: " + encontrado);

            // Opción de actualizar precio
            System.out.print("Actualizar precio? (s/n): ");
            if (scanner.nextLine().equalsIgnoreCase("s")) {
                System.out.print("Nuevo precio: ");
                encontrado.setPrecio(Double.parseDouble(scanner.nextLine()));
            }

            // Opción de actualizar stock
            System.out.print("Actualizar stock? (s/n): ");
            if (scanner.nextLine().equalsIgnoreCase("s")) {
                System.out.print("Nuevo stock: ");
                encontrado.setStock(Integer.parseInt(scanner.nextLine()));
            }

        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    // Método para eliminar un producto por ID
    private static void eliminarProducto() {
        System.out.print("Ingrese ID de producto a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());

        // Elimina el producto si el ID coincide
        productos.removeIf(p -> p.getId() == id);
        System.out.println("Producto eliminado (si existía).");
    }

    // Método para crear un nuevo pedido
    private static void crearPedido() {
        Pedido pedido = new Pedido(); // Nuevo pedido

        while (true) {
            listarProductos(); // Muestra productos disponibles
            System.out.print("Ingrese ID del producto a agregar (o 0 para finalizar): ");
            int id = Integer.parseInt(scanner.nextLine());

            if (id == 0) break; // Finalizar si el usuario ingresa 0

            // Buscar producto por ID
            Producto p = productos.stream()
                                  .filter(prod -> prod.getId() == id)
                                  .findFirst()
                                  .orElse(null);

            if (p == null) {
                System.out.println("Producto no encontrado.");
                continue;
            }

            System.out.print("Cantidad: ");
            int cantidad = Integer.parseInt(scanner.nextLine());

            try {
                // Verificar si hay suficiente stock
                if (cantidad > p.getStock()) {
                    throw new EstockInsuficiente("Stock insuficiente.");
                }

                // Crear una nueva línea de pedido y agregarla al pedido
                pedido.agregarLinea(new LineaPedido(p, cantidad));

                // Reducir el stock del producto
                p.setStock(p.getStock() - cantidad);

                System.out.println("Producto agregado al pedido.");

            } catch (EstockInsuficiente e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        // Si el total del pedido es mayor que cero, agregarlo a la lista de pedidos
        if (pedido.calcularTotal() > 0) {
            pedidos.add(pedido);
            System.out.println("Pedido creado con éxito.");
        } else {
            System.out.println("No se creó ningún pedido.");
        }
    }

    // Método que muestra todos los pedidos realizados
    private static void listarPedidos() {
        for (Pedido p : pedidos) {
            System.out.println(p);
            System.out.println("------------------");
        }
    }
}

