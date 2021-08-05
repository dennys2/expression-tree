package ed.auxiliar;

public interface TADLista<T> {
	public void add(T e);
	public boolean remove(T e);
	public boolean contains(T e);
	public int getIndex(T e);
	public T get(int i);
	public void print ();
	public int size();
		
	
}
