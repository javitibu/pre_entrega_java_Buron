package excepciones;
// Si no hay productoo
public class EstockInsuficiente extends Exception {
    public EstockInsuficiente(String mensaje) {
        super(mensaje);
    }
}
