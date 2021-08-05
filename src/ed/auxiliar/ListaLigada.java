package ed.auxiliar;

public class ListaLigada<T> implements TADLista<T>{

	protected int quantidade;
	protected NoLista<T> inicio;
	
	
	@Override
	public void add(T e) {
		NoLista<T> novo = new NoLista<T>(e);
		novo.setProximo(inicio);
		inicio = novo;
		this.quantidade++;	
	}

	@Override
	public boolean remove(T e) {
		boolean r = false;
		if (inicio != null && inicio.getInfo().equals(e)) {
			inicio = inicio.getProximo();
			this.quantidade--;
			r=true;
		} else {
			NoLista<T> p = inicio;
			while (p.getProximo() != null) {
				if (p.getProximo().getInfo().equals(e)) {
					p.setProximo(p.getProximo().getProximo());
					this.quantidade--;
					r = true;
				} else {
					p=p.getProximo();
				}
			}
		}
		return r;
	}

	@Override
	public boolean contains(T e) {
		boolean r = false;
		NoLista<T> aux = inicio;
		while (aux != null) {
			if (aux.getInfo().equals(e)) {
				r=true;
				break;
			}
			aux=aux.getProximo();
		}
		return r;
	}

	@Override
	public int getIndex(T e) {
		int r=-1, i=0;
		NoLista<T> aux = inicio;
		while (aux != null) {
			if (aux.getInfo().equals(e)) {
				r=i;
				break;
			}
			aux=aux.getProximo();
			i++;
		}
		return r;
	}

	@Override
	public T get(int i) {
		T r = null;
		int contador = 0;
		NoLista<T> aux = inicio;
		while (aux != null && contador <= i) {
			if(contador ==i) {
				r = aux.getInfo();
				break;
			}
			aux = aux.getProximo();
			contador++;
			
		}
		return r;
	}

	@Override
	public void print() {
		System.out.println(this);
		
	}

	@Override
	public int size() {
		return this.quantidade;
	}
	
	public String toString() {
		String r = "";
		for(NoLista<T> aux=inicio; aux != null; aux = aux.getProximo()) {
			r += aux.getInfo() + " -> ";
		}
		r += "null";
		return r;
	}
	
	
}
