package iteso.libs.proyecto;


public class Juego {

	public enum Estado {
		JUGANDO, GANADO, PERDIDO
	}

	private final Tablero tablero;
	private Estado estado;

	public Juego(Dificultad dificultad) {
		this.tablero = new Tablero(dificultad);
		this.estado = Estado.JUGANDO;
	}

	//Revela una casilla y actualiza el estado de la partida.
	public void revelar(int fila, int columna) throws CoordenadaInvalidaException {
		if (estaTerminado()) {
			return;
		}

		boolean eraMina = tablero.revelar(fila, columna);

		if (eraMina) {
			estado = Estado.PERDIDO;
			tablero.revelarTodasLasMinas();
		} else if (tablero.todasLasSegurasReveladas()) {
			estado = Estado.GANADO;
		}
	}

	// Pone o quita una bandera. Si la partida ya terminó, no hace nada.
	public void marcar(int fila, int columna) throws CoordenadaInvalidaException {
		if (estaTerminado()) {
			return;
		}
		tablero.marcar(fila, columna);
	}

	// true si ya se ganó o se perdió.
	public boolean estaTerminado() {
		return estado != Estado.JUGANDO;
	}

	public Estado getEstado() {
		return estado;
	}

	public Tablero getTablero() {
		return tablero;
	}
}