package ed.arvore.expressao;

public class NoArvoreExpressao<T> {
	private T info;
	private NoArvoreExpressao<T> direita, esquerda;
	
	public NoArvoreExpressao(T info) {
		
		this.info = info;
	}

	public T getInfo() {
		return info;
	}

	public void setInfo(T info) {
		this.info = info;
	}

	public NoArvoreExpressao<T> getDireita() {
		return direita;
	}

	public void setDireita(NoArvoreExpressao<T> direita) {
		this.direita = direita;
	}

	public NoArvoreExpressao<T> getEsquerda() {
		return esquerda;
	}

	public void setEsquerda(NoArvoreExpressao<T> esquerda) {
		this.esquerda = esquerda;
	}
	
	public String toString() {
		return this.getInfo() + "";
	}
	
}
