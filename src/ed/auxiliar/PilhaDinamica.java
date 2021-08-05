package ed.auxiliar;



public class PilhaDinamica<T> implements TADPilha<T> {

	private NoLista<T> topo;
	private int quantidade;
	
	public void push(T e) {
		NoLista<T> novo = new NoLista<T>(e);
		novo.setProximo(topo);
		topo = novo;
		quantidade++;
	}

	public T pop() {
		T r = null;
		if (topo != null) {
			r = topo.getInfo();
			topo = topo.getProximo();
			quantidade--;
		}
		return r;
	}

	public T top() {
		T r = null;
		if (topo != null) {
			r = topo.getInfo();
		}
		return r;
	}

	public String toString() {
		String r = "TOPO -> ";
		for(NoLista<T> aux=topo; aux != null; aux = aux.getProximo()) {
			r += aux.getInfo() + " -> ";
		}
		r += "null";
		return r;
	}
	
	public void print() {
		System.out.println(this);
	}

	public int size() {
		return quantidade;
	}

	public boolean isEmpty() {
		return topo == null;
	}

	public boolean isFull() {
		return false;
	}
	
	public static PilhaDinamica<String> pilha(String texto, boolean t) {
		String[] s = tokenizer(texto);
		PilhaDinamica<String> p = new PilhaDinamica<String>();
		for (int i = 0; i < s.length; i++) {
			if (t == true) {
				if (s[i].equals("+") || s[i].equals("*") || s[i].equals("/") || s[i].equals("-")) {
					p.push(s[i]);
				}
			} else {
				if (!s[i].equals("+") ||!s[i].equals("*") || !s[i].equals("/") || !s[i].equals("-") || !s[i].equals(")") || !s[i].equals("(")) {
					p.push(s[i]);
				}
			}
		}
		return p;
	}
	
	public static String[] tokenizer(String texto) {
		String[] r = texto.split("\\s");
		return r;
	}
	
}
