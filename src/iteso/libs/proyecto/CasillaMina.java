package iteso.libs.proyecto;
//Hereda de casilla ya que es clase abstracta
//Es una casilla que contiene una mina 
public class CasillaMina extends Casilla {
	
	public CasillaMina(int fila, int columna) {
		super(fila, columna); //Herede de casilla
	}
	
	@Override
    public boolean esMina() {
        return true;
    }

    @Override
    public String getSimbolo() {
        return "*";
    }
	
	

}
