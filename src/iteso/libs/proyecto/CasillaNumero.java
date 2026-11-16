package iteso.libs.proyecto;
//Hereda Casilla , representa la casilla segura 
//Almacena cuantas minas en sus 8 vecinas existen 
//Si no tiene es 0 , y presenta el .
public class CasillaNumero extends Casilla{
	private int minasAlrededor; //0 a 8 
	public CasillaNumero(int fila, int columna) {
		//se invoca super para obtener los atributos de la clase casilla 
		super(fila,columna);
		this.minasAlrededor=0;
	}
	//Getters , para devolver las minas alrededor
	public int getMinasAlrededor() {
		return minasAlrededor;
	}
	public void setMinasAlrededor(int minasAlrededor) {
		this.minasAlrededor =minasAlrededor;
	}
	public boolean estaVacia() {
		return minasAlrededor==0; 
	}
	//Metodos de la clase Casilla Polimorfismo 
	@Override
	public boolean esMina() {
		return false;
	}
	@Override
	public String getSimbolo() {
		return minasAlrededor ==0 ?".":String.valueOf(minasAlrededor);
		
	}
	
}
