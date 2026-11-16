package iteso.libs.proyecto;
//Esta clase es para definir los niveles de dificultadad 
//Se usa el tamaño del tablaero (filas y columnas) 
//Cantidad de minas correspondiente.
public enum Dificultad {
	FACIL(8,8,10), 
	MEDIO(12,12,25),
	DIFICIL(16,16,40);
	
	private final int filas; 
	private final int columnas; 
	private final int minas; 
	Dificultad(int filas, int columnas , int minas){
		this.filas=filas; 
		this.columnas=columnas; 
		this.minas=minas;
	}
	public int getFilas() {
		return filas; 
	}
	public int getColumnas() {
		return columnas;
	}
	public int getMinas() {
		return minas; 
	}

}
