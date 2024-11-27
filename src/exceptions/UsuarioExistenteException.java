package exceptions;


@SuppressWarnings("serial")
public class UsuarioExistenteException extends Exception {
    public UsuarioExistenteException() {
        super("El usuario ya existe.");
    }

    public UsuarioExistenteException(String mensaje) {
        super(mensaje);
    }
}