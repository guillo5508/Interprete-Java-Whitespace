package finalCompi;

public class Nodo {
	
	private String llave;
	private Nodo space;
	private Nodo tab;
	private Nodo enter;
	private boolean parametro;
	public String getLlave() {
		return llave;
	}
	public void setLlave(String llave) {
		this.llave = llave;
	}
	public Nodo getSpace() {
		return space;
	}
	public void setSpace(Nodo space) {
		this.space = space;
	}
	public Nodo getTab() {
		return tab;
	}
	public void setTab(Nodo tab) {
		this.tab = tab;
	}
	public Nodo getEnter() {
		return enter;
	}
	public void setEnter(Nodo enter) {
		this.enter = enter;
	}
	
	
	
	public boolean isParametro() {
		return parametro;
	}
	public void setParametro(boolean parametro) {
		this.parametro = parametro;
	}
	public Nodo(Nodo space, Nodo tab, Nodo enter) {
		super();
		this.llave = null;
		this.parametro = false;
		this.space = space;
		this.tab = tab;
		this.enter = enter;
	}
	public Nodo(String llave, boolean parametro) {
		super();
		this.llave = llave;
		this.parametro = parametro;
		this.space = null;
		this.tab = null;
		this.enter = null;
	}
	
	
	
	

}
