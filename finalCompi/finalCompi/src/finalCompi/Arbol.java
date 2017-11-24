package finalCompi;

public class Arbol {
	
	private Nodo raiz;
	
	
	

	public Nodo getRaiz() {
		return raiz;
	}




	public void setRaiz(Nodo raiz) {
		this.raiz = raiz;
	}




	public Arbol() {
		super();
		// subarbol de manejo de pila (space)
		Nodo ss= new Nodo("push",true);
		Nodo sts = new Nodo("copy", true);
		Nodo ste = new Nodo("slide", true);
		Nodo ses = new Nodo("dup",false);
		Nodo set = new Nodo("swap",false);
		Nodo see = new Nodo("drop",false);
		Nodo st = new Nodo(sts, null, ste);
		Nodo se = new Nodo(ses, set, see);
		Nodo s = new Nodo(ss, st, se);// nodo principal
	
//----------------------------------------------------------------
	//arbol de tab
		// subarbol de funciones aritmeticas
		Nodo tsss = new Nodo("add",false);
		Nodo tsst = new Nodo("sub",false);
		Nodo tsse = new Nodo("mul",false);
		
		Nodo tss = new Nodo(tsss, tsst, tsse);
		
		Nodo tsts = new Nodo("div",false);
		Nodo tstt = new Nodo("mod",false);
		Nodo tst = new Nodo(tsts, tstt, null);
		
		Nodo ts = new Nodo(tss, tst, null);
	
		//-----------------------------------------------------------------
		
		//subarbol de heap
		Nodo tts = new Nodo("store",false);
		Nodo ttt = new Nodo("retrieve",false);
		Nodo tt = new Nodo(tts, ttt, null);
		//----------------------------------------------------------------
		
		//subarbol de I/O
		Nodo tess = new Nodo("printc",false);
		Nodo test = new Nodo("printi",false);
		Nodo tes = new Nodo(tess, test, null);
		
		Nodo tets = new Nodo("readc",false);
		Nodo tett = new Nodo("readi",false);
		Nodo tet = new Nodo(tets, tett, null);
		
		Nodo te = new Nodo(tes, tet, null);
		
		Nodo t = new Nodo(ts, tt, te); // nodo principal
		
	
//--------------------------------------------------------------------------
		
		//subarbol de enter
		Nodo ess = new Nodo("label", true);
		Nodo est = new Nodo("call_label", true);
		Nodo ese = new Nodo("jmp_label", true);
		Nodo es = new Nodo(ess, est, ese);
		
		Nodo ets = new Nodo("jz_label", true);
		Nodo ett = new Nodo("jn_label", true);
		Nodo ete = new Nodo("ret", false);
		Nodo et = new Nodo(ets, ett, ete);
		
		Nodo eee = new Nodo("end", false);
		Nodo ee = new Nodo(null, null, eee);
		
		Nodo e = new Nodo(es, et, ee);
		
//--------------------------------------------------------------------------
		
		this.raiz = new Nodo(s, t, e);
			
	}




	public static void main(String[] args) {
		

	}

}
