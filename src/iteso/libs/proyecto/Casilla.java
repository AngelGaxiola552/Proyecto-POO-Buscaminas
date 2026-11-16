package iteso.libs.proyecto;
//Esta clase representa cualquier casilla del tablero

//No se puede instanciar directamente 
public abstract class Casilla {
	//Creación de atributos private 
	private final int fila; 
	private final int columna; 
	//Banderas , las iniciamos en falso 
	private boolean revelada; 
	private boolean marcado; 
	
	public Casilla(int fila, int columna) {
		this.fila=fila; 
		this.columna=columna;
		this.revelada= false; 
		this.marcado=false;	
	}
	public int getFila() {
		return fila;
	}
	public int getColumna() {
		return columna; 	
	}
	//Metodo que se usaran como comun en las clases CasillaMina 
	//CasillaNumero
	public boolean isReveleda() {
		//retorna si fue revelada la casilla 
		return revelada;
	}
	public boolean isMarcada() {
		return marcado;
	}
	//Poner y quitar la bandera solo si no esta revelada 
	public void alternarMarca() {
		if(!revelada) {
			this.marcado=!this.marcado;
		}
	}
	//abstractas
	public abstract boolean esMina();
    //Lo que se ve cuando ya se revela 
	public abstract String getSimbolo();
	@Override
    public String toString() {
        if (marcado) {
            return "F";
        }
        if (!revelada) {
            return "■";
        }
        return getSimbolo();
    }
	
}
