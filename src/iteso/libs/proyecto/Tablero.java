package iteso.libs.proyecto;

import java.util.Random;

/**
	Representa el tablero del Buscaminas: guarda la matriz de casillas,
  	coloca las minas, calcula los números y revela en cascada.
 */
public class Tablero {

	private final int filas;
	private final int columnas;
	private final int totalMinas;
	private Casilla[][] casillas;
	private boolean minasColocadas;   
	private int casillasReveladas;    

	// ---------------------- Constructor ------------------------ //

	public Tablero(Dificultad dificultad) {
		this.filas = dificultad.getFilas();
		this.columnas = dificultad.getColumnas();
		this.totalMinas = dificultad.getMinas();
		this.casillas = new Casilla[filas][columnas];
		this.minasColocadas = false;
		this.casillasReveladas = 0;

		// Al inicio todo el tablero son casillas seguras.
		// Las minas se colocan hasta la primera jugada.
		for (int f = 0; f < filas; f++) {
			for (int c = 0; c < columnas; c++) {
				casillas[f][c] = new CasillaNumero(f, c);
			}
		}
	}

	// -------------------- Métodos públicos ------------------------//

	/**
	  Revela la casilla indicada.
	  return true si la casilla era una mina (el jugador pierde)
	 */
	public boolean revelar(int fila, int columna) throws CoordenadaInvalidaException {
		validar(fila, columna);

		// Primera jugada: se colocan las minas evitando esta casilla
		if (!minasColocadas) {
			colocarMinas(fila, columna);
			calcularNumeros();
			minasColocadas = true;
		}

		Casilla casilla = casillas[fila][columna];

		// No se puede revelar algo ya revelado o con bandera
		if (casilla.isRevelada() || casilla.isMarcada()) {
			return false;
		}

		// No sabemos si es mina o número, solo preguntamos
		if (casilla.esMina()) {
			casilla.revelar();
			return true;
		}

		revelarEnCascada(fila, columna);
		return false;
	}

	/** Pone o quita una bandera en la casilla indicada. */
	public void marcar(int fila, int columna) throws CoordenadaInvalidaException {
		validar(fila, columna);
		casillas[fila][columna].alternarMarca();
	}

	/** Se gana cuando todas las casillas sin mina están reveladas. */
	public boolean todasLasSegurasReveladas() {
		return casillasReveladas == filas * columnas - totalMinas;
	}

	/** Al perder se muestran todas las minas. */
	public void revelarTodasLasMinas() {
		for (int f = 0; f < filas; f++) {
			for (int c = 0; c < columnas; c++) {
				if (casillas[f][c].esMina()) {
					casillas[f][c].revelar();
				}
			}
		}
	}

	/** Minas totales menos banderas puestas. */
	public int getMinasRestantes() {
		int banderas = 0;
		for (int f = 0; f < filas; f++) {
			for (int c = 0; c < columnas; c++) {
				if (casillas[f][c].isMarcada()) {
					banderas++;
				}
			}
		}
		return totalMinas - banderas;
	}

	/** Lo que se debe imprimir en esa posición ("■", "F", "*", "." o un número). */
	public String simboloEn(int fila, int columna) {
		return casillas[fila][columna].toString();
	}

	public int getFilas() {
		return filas;
	}

	public int getColumnas() {
		return columnas;
	}

	// -------------------------- Métodos privados ----------------------/

	/** Coloca las minas al azar, sin repetir y sin pisar la casilla segura. */
	private void colocarMinas(int filaSegura, int columnaSegura) {
		Random random = new Random();
		int colocadas = 0;

		while (colocadas < totalMinas) {
			int f = random.nextInt(filas);
			int c = random.nextInt(columnas);

			boolean esLaSegura = (f == filaSegura && c == columnaSegura);
			if (esLaSegura || casillas[f][c].esMina()) {
				continue; // se intenta con otra posición
			}

			// Si el jugador ya había puesto bandera ahí, se conserva
			boolean teniaBandera = casillas[f][c].isMarcada();
			casillas[f][c] = new CasillaMina(f, c);
			if (teniaBandera) {
				casillas[f][c].alternarMarca();
			}
			colocadas++;
		}
	}

	/** A cada casilla segura le asigna cuántas minas tiene alrededor. */
	private void calcularNumeros() {
		for (int f = 0; f < filas; f++) {
			for (int c = 0; c < columnas; c++) {
				if (!casillas[f][c].esMina()) {
					// Sabemos que no es mina, así que es CasillaNumero (cast seguro)
					CasillaNumero numero = (CasillaNumero) casillas[f][c];
					numero.setMinasAlrededor(contarMinasVecinas(f, c));
				}
			}
		}
	}

	/** Cuenta las minas en las 8 casillas vecinas. */
	private int contarMinasVecinas(int fila, int columna) {
		int contador = 0;
		for (int df = -1; df <= 1; df++) {
			for (int dc = -1; dc <= 1; dc++) {
				int f = fila + df;
				int c = columna + dc;
				if (esValida(f, c) && casillas[f][c].esMina()) {
					contador++;
				}
			}
		}
		return contador;
	}

	/**
	  Revela una casilla y, si no tiene minas alrededor,
	  revela también sus 8 vecinas de manera recursiva.
	 */
	private void revelarEnCascada(int fila, int columna) {
		// Casos en los que se detiene la recursión
		if (!esValida(fila, columna)) {
			return;
		}
		Casilla casilla = casillas[fila][columna];
		if (casilla.isRevelada() || casilla.isMarcada() || casilla.esMina()) {
			return;
		}

		casilla.revelar();
		casillasReveladas++;

		CasillaNumero numero = (CasillaNumero) casilla;
		if (numero.estaVacia()) {
			for (int df = -1; df <= 1; df++) {
				for (int dc = -1; dc <= 1; dc++) {
					if (df != 0 || dc != 0) {
						revelarEnCascada(fila + df, columna + dc);
					}
				}
			}
		}
	}

	/** true si la coordenada está dentro del tablero. */
	private boolean esValida(int fila, int columna) {
		return fila >= 0 && fila < filas && columna >= 0 && columna < columnas;
	}

	/** Lanza la excepción si la coordenada está fuera del tablero. */
	private void validar(int fila, int columna) throws CoordenadaInvalidaException {
		if (!esValida(fila, columna)) {
			throw new CoordenadaInvalidaException(fila, columna);
		}
	}
}