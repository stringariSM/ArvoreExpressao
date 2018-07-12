import java.io.*;
import java.util.*;

class Node
{
    public char valor;
    public Node filhoEsquerda;
    public Node filhoDireita;

    public Node(char x)
    {
        valor = x;
    }

    public char getValor()
    {
        return valor;
    }

    public void mostraNaTela()
    {
        System.out.print(valor);
    }
}

class Stack1
{
    private Node[] a;
    private int    top, m;

    public Stack1(int max)
    {
        m = max;
        a = new Node[m];
        top = -1;
    }

    public void push(Node key)
    {
        a[++top] = key;
    }

    public Node pop()
    {
        return (a[top--]);
    }

    public boolean ehVazio()
    {
        return (top == -1);
    }
}

class Stack2
{
    private char[] a;
    private int    top, m;

    public Stack2(int max)
    {
        m = max;
        a = new char[m];
        top = -1;
    }

    public void push(char key)
    {
        a[++top] = key;
    }

    public char pop()
    {
        return (a[top--]);
    }

    public boolean ehVazio()
    {
        return (top == -1);
    }
}

class Conversao
{
    private Stack2 s;
    private String input;
    private String output = "";

    public Conversao(String str)
    {
        input = str;
        s = new Stack2(str.length());
    }

    public String taNoPost()
    {
        for (int i = 0; i < input.length(); i++)
        {
            char ch = input.charAt(i);
            switch (ch)
            {
                case '+':
                case '-':
                    temOperador(ch, 1);
                    break;
                case '*':
                case '/':
                    temOperador(ch, 2);
                    break;
                case '(':
                    s.push(ch);
                    break;
                case ')':
                    temParenteses();
                    break;
                default:
                    output = output + ch;
            }
        }
        while (!s.ehVazio())
            output = output + s.pop();
        return output;
    }

    //classe que vai calcular essa zona toda
    private void temOperador(char opThis, int prec1)
    {
        while (!s.ehVazio())
        {
            char opTop = s.pop();
            if (opTop == '(')
            {
                s.push(opTop);
                break;
            } else
            {
                int prec2;
                if (opTop == '+' || opTop == '-')
                    prec2 = 1;
                else
                    prec2 = 2;
                if (prec2 < prec1)
                {
                    s.push(opTop);
                    break;
                } else
                    output = output + opTop;
            }
        }
        s.push(opThis);
    }

    private void temParenteses()
    {
        while (!s.ehVazio())
        {
            char ch = s.pop();
            if (ch == '(')
                break;
            else
                output = output + ch;
        }
    }
}

class Arvore
{
    private Node raiz;
    private static String postFix;

    public Arvore()
    {
        raiz = null;
    }

    public void insert(String s)
    {
        Conversao c = new Conversao(s);
        s = c.taNoPost();
        Stack1 stk = new Stack1(s.length());
        s = s + "#";
        int i = 0;
        char simbolo = s.charAt(i);
        Node novoNode;
        while (simbolo != '#')
        {
            if (simbolo >= '0' && simbolo <= '9' || simbolo >= 'A'
                    && simbolo <= 'Z' || simbolo >= 'a' && simbolo <= 'z')
            {
                novoNode = new Node(simbolo);
                stk.push(novoNode);
            } else if (simbolo == '+' || simbolo == '-' || simbolo == '/'
                    || simbolo == '*')
            {
                Node ptr1 = stk.pop();
                Node ptr2 = stk.pop();
                novoNode = new Node(simbolo);
                novoNode.filhoEsquerda = ptr2;
                novoNode.filhoDireita = ptr1;
                stk.push(novoNode);
            }
            simbolo = s.charAt(++i);
        }
        raiz = stk.pop();
    }

    //Esse cara vai retornar os dados na tela
    public void busca(int type)
    {
        switch (type)
        {
            case 1:
                System.out.print("PREFIXA: ");
                preOrder(raiz);
                break;
            case 2:
                System.out.print("POSFIXA: ");
                postOrder(raiz);
                break;
            case 3:
                System.out.print("RESULTADO: " + calcular());
            default:
                System.out.println("");
        }
    }

    //Esse tio vai fazer o prefix
    private void preOrder(Node pRaiz)
    {
        if (pRaiz != null)
        {
            pRaiz.mostraNaTela();
            preOrder(pRaiz.filhoEsquerda);
            preOrder(pRaiz.filhoDireita);
        }
    }

    //Esse tio vai fazer o postfix
    public String getPostFix(){
        return postFix;
    }
    //Esse tio vai fazer o postfix
    private void postOrder(Node pRaiz)
    {
        if (pRaiz != null)
        {
            postOrder(pRaiz.filhoEsquerda);
            postOrder(pRaiz.filhoDireita);
            pRaiz.mostraNaTela();

            if(postFix != null)
                postFix = postFix + " " + Character.toString(pRaiz.getValor());
            else
                postFix = Character.toString(pRaiz.getValor());
        }
    }

    public static int calcular() {
 	Stack<Integer> stack = new Stack<Integer> ();
	Scanner caracteresExpressao = new Scanner(postFix);
	//Confia em Deus que vai funcionar
	while (caracteresExpressao.hasNext()) {
		if (caracteresExpressao.hasNextInt()) {
		    //System.out.print("Passou aqui");
		    stack.push(caracteresExpressao.nextInt());
		} else {
			int num2 = stack.pop();
			int num1 = stack.pop();
			String op = caracteresExpressao.next();
			//System.out.print("Passou aqui 2");
			if (op.equals("+")) {
				stack.push(num1 + num2);
			} else if (op.equals("-")) {
				stack.push(num1 - num2);
			} else if (op.equals("*")) {
				stack.push(num1 * num2);
			} else {
				stack.push(num1 / num2);
			}
                }
	}
	return stack.pop();
    }
}

public class Trabalho_2
{
    public static void main(String args[]) throws IOException
    {
        String a = "";
        DataInputStream inp = new DataInputStream(System.in);
        while (!a.equals("q"))
        {
            //Vamos ler a expressão
            Arvore t1 = new Arvore();
            System.out.println("Digite Q para sair ou uma expressão no formato. Ex. ((8+5)*(7-1)+(5*5))");
            a = inp.readLine();
            t1.insert(a);

            if(!a.equals("q"))
            {
                //Colcoar ela na árvore e transformar para prefixa ou posfixa
                t1.busca(1);
                System.out.println("");
                t1.busca(2);
                System.out.println("");
                t1.busca(3);
                System.out.println("\n");
            }else
            {
                System.out.println("Obrigado!");
            }
        }
    }
}