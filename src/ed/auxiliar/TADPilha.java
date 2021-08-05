package ed.auxiliar;

public interface TADPilha<T> {
	public void push(T e);
	public T pop();
	public T top();
	public void print();
	public int size();
	public boolean isEmpty();
	public boolean isFull();
}
