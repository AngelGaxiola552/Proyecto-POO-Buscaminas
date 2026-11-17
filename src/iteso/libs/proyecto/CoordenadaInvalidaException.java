package iteso.libs.proyecto;

/**
  Excepción que se lanza cuando el jugador escribe una fila o columna
  que está fuera del tablero.
 */
public class CoordenadaInvalidaException extends Exception {

	
	private static final long serialVersionUID = 1L;

	public CoordenadaInvalidaException(int fila, int columna) {
		
		super("La coordenada (" + fila + ", " + columna + ") esta fuera del tablero");
	}
}