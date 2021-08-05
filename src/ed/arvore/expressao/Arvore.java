package ed.arvore.expressao;


import java.util.Scanner;

import ed.auxiliar.PilhaDinamica;

public class Arvore<T> {
	private static NoArvoreExpressao<String> raiz;
	private static PilhaDinamica<NoArvoreExpressao<String>> operador = new PilhaDinamica<NoArvoreExpressao<String>>();
	private static PilhaDinamica<NoArvoreExpressao<String>> raizSubArvore = new PilhaDinamica<NoArvoreExpressao<String>>();
	
	/*
	 * Método verifica as posicoes dos parenteses e retorna se a expressao é valida ou nao
	 */
	
	public static boolean verificaExpressao(String texto) {
		PilhaDinamica<Character> p = new PilhaDinamica<Character>();

		for (int i = 0; i < texto.length(); i++) {
			if (texto.charAt(i) == '(') {
				p.push(texto.charAt(i));
			}
		}

		if (p.isEmpty()) {
			return false;
		}

		for (int i = 0; i < texto.length(); i++) {
			if (!p.isEmpty()) {
				if (texto.charAt(i) == ')') {
					if (p.top() == '(') {
						p.pop();
					} else {
						return false;
					}
				}
			} else if (i < texto.length()) {
				return false;
			}
		}

		if (!p.isEmpty()) {
			return false;
		}

		return true;
	}
	
	/*
	 * Retorna um array de String com elementos divididos por espaco
	 */
	public static String[] tokenizer(String texto) {
		String[] r = texto.split("\\s");
		return r;
	}
	
	/*
	 * Monta a arvore
	 */
	public static void criarArvore(String texto) {
		
		if (!verificaExpressao(texto)) {
			System.out.println("Erro na posicao dos parenteses");
		} else {
			String[] s = tokenizer(texto);
			NoArvoreExpressao<String> subRaiz=null;
			for (int i = 0; i < s.length; i++) {
				if (s[i].equals((")"))) { //Se a posicao i no vetor for ), monta a sub arvore
					if (operador.top() != null) {
						NoArvoreExpressao<String> noOperador = operador.pop(); //No é o mais recente operador da pilha de operador
						NoArvoreExpressao<String> noOperando1 = raizSubArvore.pop(); //No é o mais recente operando da pilha de operando
						NoArvoreExpressao<String> noOperando2 = raizSubArvore.pop(); //No é o atual mais recente operando da pilha de operando
						subRaiz = noOperador; //Raiz da sub arvore é o operador
						subRaiz.setDireita(noOperando1); //Direita da raiz é o mais recente elemento da pilha de operando em determinado momento
						subRaiz.setEsquerda(noOperando2); //Esquerda da raiz é o segundo mais recente elemento da pilha de operando em determinado momento
						raizSubArvore.push(subRaiz); //Adiciona a pilha de operando a raiz da sub arvore
					}
				} else if (s[i].equals("+") || s[i].equals("*") || s[i].equals("/") || s[i].equals("-")) { //Se a posicao i no vetor for um operador, 																									
					NoArvoreExpressao<String> aux = new NoArvoreExpressao<String>(s[i]);					//adiciona um no de String com o elemento na pilha
					operador.push(aux);	
				} else if (!s[i].equals("(")) { //Se a posicao i no vetor nao for nenhuma das outras opcoes, ele adiciona na pilha de operando
					NoArvoreExpressao<String> aux = new NoArvoreExpressao<String>(s[i]);
					raizSubArvore.push(aux);
				}
			}
			raiz=subRaiz;
		}
	}
	
	/*
	 * Percurso em ordem para imprimir a arvore formada 
	 */
	public static void printEmOrdem(NoArvoreExpressao<String> raizAux) {
		if (raizAux != null) {
			if (raizAux.getEsquerda()!=null) { 
				System.out.print("( ");
			}
			printEmOrdem(raizAux.getEsquerda());
			System.out.print(raizAux.getInfo() + " ");
			printEmOrdem(raizAux.getDireita());		
			if (raizAux.getDireita()!=null) {
				System.out.print(") ");
			}
		}
	}
	
	
	/*
	 * Metodo responsavel por substituir uma variavel por um valor numerico
	 */
	public static void valorNaVariavel(NoArvoreExpressao<String> raizAux) {
		if (raizAux != null) {
			Scanner s = new Scanner(System.in);
			valorNaVariavel(raizAux.getEsquerda());
			if (!verificaVariavel(raizAux.getInfo()) && !raizAux.getInfo().equals("+") && !raizAux.getInfo().equals("*") && !raizAux.getInfo().equals("/") 
					&& !raizAux.getInfo().equals("-")) { //Se nao for numero ou operador, é variavel
				
				if (raizAux.getInfo().charAt(0)!='-') { //Tratamento para valores negativos, caso contrario, ele tratará -x (exemplo) como variavel positiva
					System.out.println("Digite um valor para a variável " + raizAux.getInfo() + ": ");
					String r=s.next();
					while (!verificaVariavel(r)) {
						System.out.println("Digite um valor NUMERICO para a variável " + raizAux.getInfo() + ": ");
						r=s.next();
					}
					raizAux.setInfo(r);
				}
				
			}
			valorNaVariavel(raizAux.getDireita());		
		}
	}
	
	/*
	 * Retorna se texto é numero ou letra	
	 */
	public static boolean verificaVariavel(String texto) {
	    char[] c = texto.toCharArray();
	    for (int i = 0; i < c.length; i++ ) {
	        if ((!Character.isDigit(c[i]) && c[i]!='-') && c[i]!='.') { //aceita valores negativos ou decimais para substituir as variaveis
	            return false;
	        }
	    }
	    return true;
	}
	
	/*
	 * Calcula os valores na arvore e retorna se conseguiu fazer os calculos
	 */
	public static boolean calcularExpressao(NoArvoreExpressao<String> raizAux) {
		if (raizAux != null) {
			double j=0;
			try {
				calcularExpressao(raizAux.getEsquerda());
				calcularExpressao(raizAux.getDireita());
				if (raizAux.getInfo().equals("*")) {
					j =Double.parseDouble(raizAux.getEsquerda().getInfo()) * Double.parseDouble(raizAux.getDireita().getInfo());

					raizAux.setInfo(Double.toString(j));
					raizAux.setEsquerda(null);
					raizAux.setDireita(null);	
				} else if (raizAux.getInfo().equals("+")) {
					j = Double.parseDouble(raizAux.getEsquerda().getInfo()) + Double.parseDouble(raizAux.getDireita().getInfo());

					raizAux.setInfo(Double.toString(j));
					raizAux.setEsquerda(null);
					raizAux.setDireita(null);				
				} else if (raizAux.getInfo().equals("-")) {
					j = Double.parseDouble(raizAux.getEsquerda().getInfo()) - Double.parseDouble(raizAux.getDireita().getInfo());

					raizAux.setInfo(Double.toString(j));
					raizAux.setEsquerda(null);
					raizAux.setDireita(null);	
				} else if (raizAux.getInfo().equals("/")) {
					if (!raizAux.getDireita().getInfo().equals("0")) {
						j = Double.parseDouble(raizAux.getEsquerda().getInfo()) / Double.parseDouble(raizAux.getDireita().getInfo());
						raizAux.setInfo(Double.toString(j));
						raizAux.setEsquerda(null);
						raizAux.setDireita(null);
					} else {
						return false;
					}
					
				}
			} catch (Exception e) { //Em caso de exception, retorna negativo, sinalizando que nao foi possivel calcular a expressao
				return false;
			}
		}
		return true;
	}
	
	/*
	 * Metodo inicia o programa chamando os metodos necessarios
	 */
	public static void inicializa () {
		Scanner s = new Scanner(System.in);
		System.out.println("Digite sua expressao numerica: ");
		criarArvore(s.nextLine());
		valorNaVariavel(raiz);
		printEmOrdem(raiz);
		if (calcularExpressao(raiz) && raiz!=null) {
			System.out.println(" =  " + raiz);
		} else {
			System.out.println("Parece que o que voce digitou é uma expressao impossivel ou nao identificada pelo programa");
		} 
	}
}
