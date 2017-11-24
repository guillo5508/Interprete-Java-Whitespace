package finalCompi;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import java.awt.Font;

public class Principal extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 619, 457);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 29, 242, 345);
		contentPane.add(scrollPane);

		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(284, 31, 124, 386);
		contentPane.add(scrollPane_1);

		JList list = new JList();
		scrollPane_1.setViewportView(list);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(436, 85, 160, 332);
		contentPane.add(scrollPane_2);

		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane_2.setViewportView(textPane);

		JButton btnNewButton = new JButton("Interpretar");
		btnNewButton.setFont(new Font("Script MT Bold", Font.PLAIN, 25));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String aux = textArea.getText();
				Queue<String> entrada = comando(aux);
				ArrayList<ArrayList<String>> instruccion = new ArrayList<ArrayList<String>>();
				Arbol whiteSpace = new Arbol();
				instrucciones(entrada, instruccion, whiteSpace.getRaiz(), whiteSpace);
				convertirLabels(instruccion);
				convertirPush(instruccion);

				DefaultListModel<String> modelo = new DefaultListModel();
				for (int i = 0; i < instruccion.size(); i++) {
					String aux2="";
					if(instruccion.get(i).get(0).compareTo("label")!=0)
						aux2="      ";
					aux2 = aux2.concat(instruccion.get(i).get(0));

					if (instruccion.get(i).get(1) != null)
						aux2 = aux2.concat(" ".concat(instruccion.get(i).get(1)));
					if(instruccion.get(i).get(0).compareTo("label")==0)
						aux2=aux2.concat(":");
					modelo.addElement(aux2);
				}
				list.setModel(modelo);

				// ------------------------------------
				// ejecutar Instrucciones

				Stack<Integer> pila = new Stack<Integer>();
				ArrayList<ArrayList<Integer>> heap = new ArrayList<ArrayList<Integer>>();

				String salida = "";
				Stack<Integer> retval = new Stack<Integer>();
				for (int i = 0; i < instruccion.size(); i++) {
					switch (instruccion.get(i).get(0)) {
					// Stack manipulation
					case "push":
						push(pila, instruccion, i);
						break;
					case "dup":
						dup(pila);
						break;
					case "copy":
						copy(pila, instruccion, i);
						break;
					case "swap":
						swap(pila);
						break;
					case "drop":
						drop(pila);
						break;
					case "slide":
						slide(pila, instruccion, i);
						break;
					// Arithmetic
					case "add":
						add(pila);
						break;
					case "sub":
						sub(pila);
						break;
					case "mul":
						mul(pila);
						break;
					case "div":
						div(pila);
						break;
					case "mod":
						mod(pila);
						break;
					// heap access

					case "store":
						store(pila, heap);
						break;
					case "retrieve":
						retrieve(pila, heap);
						break;
					case "label":
						// no hace nada,ya esta marcado
						break;
					case "call_label":
						retval.push(i);
						i = call_label(instruccion, i);
						break;
					case "jmp_label":
						i = jmp_label(instruccion, i);
						break;
					case "jz_label":
						i = jz_label(pila, instruccion, i);
						break;
					case "jn_label":
						i = jn_label(pila, instruccion, i);
						break;
					case "ret":
						i = retval.pop();
						break;
					case "end":
						i = instruccion.size();
						break;

					// I/O
					case "printc":
						salida = printc(pila, salida);
						textPane.setText(salida);
						break;
					case "printi":
						salida = printi(pila, salida);
						textPane.setText(salida);
						break;
					case "readc":
						salida = readc(pila, heap, salida);
						textPane.setText(salida);
						break;
					case "readi":
						salida = readi(pila, heap, salida);
						textPane.setText(salida);
						break;

					}
				}

			}
		});
		btnNewButton.setBounds(436, 29, 160, 45);
		contentPane.add(btnNewButton);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
			}
		});
		btnLimpiar.setFont(new Font("Script MT Bold", Font.PLAIN, 16));
		btnLimpiar.setBounds(81, 385, 107, 32);
		contentPane.add(btnLimpiar);

	}

	protected void slide(Stack<Integer> pila, ArrayList<ArrayList<String>> instruccion, int ii) {
		// TODO Auto-generated method stub
		Integer cabeza = pila.pop();
		int j = Integer.valueOf(instruccion.get(ii).get(1));
		for (int i = 0; i < j; i++) {
			pila.pop();
		}
		pila.push(cabeza);
	}

	protected void copy(Stack<Integer> pila, ArrayList<ArrayList<String>> instruccion, int ii) {
		// TODO Auto-generated method stub

		Stack<Integer> aux = new Stack<Integer>();
		int j = Integer.valueOf(instruccion.get(ii).get(1));
		for (int i = 0; i < j - 1; i++) {
			aux.push(pila.pop());
		}
		if (j != 0) {
			Integer copiado = pila.peek();
			while (!aux.isEmpty()) {
				pila.push(aux.pop());
			}
			pila.push(copiado);
		}
	}

	protected String readi(Stack<Integer> pila, ArrayList<ArrayList<Integer>> heap, String salida) {
		// TODO Auto-generated method stub
		String entrada = JOptionPane.showInputDialog("Ingrese numero");
		Integer a = Integer.valueOf(entrada);
		Integer b = pila.pop();

		int i = 0;
		while (i < heap.size() && heap.get(i).get(0) != b)
			i++;

		if (i == heap.size()) {
			ArrayList<Integer> aux = new ArrayList<Integer>();
			aux.add(b);
			aux.add(a);
			heap.add(aux);
		} else {
			heap.get(i).set(i, a);
		}

		salida = salida.concat(entrada);
		salida = salida.concat("\n");
		return salida;
	}

	protected String readc(Stack<Integer> pila, ArrayList<ArrayList<Integer>> heap, String salida) {
		// TODO Auto-generated method stub
		String entrada = JOptionPane.showInputDialog("Ingrese caracter");
		int a = (int) entrada.charAt(0);
		Integer b = pila.pop();

		int i = 0;
		while (i < heap.size() && heap.get(i).get(0) != b)
			i++;

		if (i == heap.size()) {
			ArrayList<Integer> aux = new ArrayList<Integer>();
			aux.add(b);
			aux.add(a);
			heap.add(aux);
		} else {
			heap.get(i).set(i, a);
		}

		salida = salida.concat(entrada);
		salida = salida.concat("\n");
		return salida;
	}

	protected String printc(Stack<Integer> pila, String salida) {
		// TODO Auto-generated method stub
		Integer a = pila.pop();
		return salida.concat(String.valueOf((char) a.intValue()));
	}

	protected int jn_label(Stack<Integer> pila, ArrayList<ArrayList<String>> instruccion, int i) {
		// TODO Auto-generated method stub
		Integer a = pila.pop();
		if (a < 0) {
			int j = 0;
			boolean encontrado = false;
			while (j < instruccion.size() && !encontrado) {
				if (instruccion.get(j).get(0).compareTo("label") == 0) {
					if (instruccion.get(j).get(1).compareTo(instruccion.get(i).get(1)) == 0) {
						encontrado = true;
					}
				}
				j++;
			}
			return (i = j - 1);
		} else {
			return i;
		}
	}

	protected int jz_label(Stack<Integer> pila, ArrayList<ArrayList<String>> instruccion, int i) {
		// TODO Auto-generated method stub
		Integer a = pila.pop();
		if (a == 0) {
			int j = 0;
			boolean encontrado = false;
			while (j < instruccion.size() && !encontrado) {
				if (instruccion.get(j).get(0).compareTo("label") == 0) {
					if (instruccion.get(j).get(1).compareTo(instruccion.get(i).get(1)) == 0) {
						encontrado = true;
					}
				}
				j++;
			}
			return (i = j - 1);
		} else {
			return i;
		}
	}

	protected int jmp_label(ArrayList<ArrayList<String>> instruccion, int i) {
		// TODO Auto-generated method stub
		int j = 0;
		boolean encontrado = false;
		while (j < instruccion.size() && !encontrado) {
			if (instruccion.get(j).get(0).compareTo("label") == 0) {
				if (instruccion.get(j).get(1).compareTo(instruccion.get(i).get(1)) == 0) {
					encontrado = true;
				}
			}
			j++;
		}
		return (i = j - 1);
	}

	protected String printi(Stack<Integer> pila, String salida) {
		// TODO Auto-generated method stub
		Integer a = pila.pop();
		return salida.concat(String.valueOf(a));
	}

	protected int call_label(ArrayList<ArrayList<String>> instruccion, int i) {
		// TODO Auto-generated method stub
		int j = 0;
		boolean encontrado = false;
		while (j < instruccion.size() && !encontrado) {
			if (instruccion.get(j).get(0).compareTo("label") == 0) {
				if (instruccion.get(j).get(1).compareTo(instruccion.get(i).get(1)) == 0) {
					encontrado = true;
				}
			}
			j++;
		}
		return (i = j - 1);
	}

	protected void retrieve(Stack<Integer> pila, ArrayList<ArrayList<Integer>> heap) {
		// TODO Auto-generated method stub
		Integer a = pila.pop();
		int i = 0;
		while (i < heap.size() && a != heap.get(i).get(0))
			i++;

		if (i != heap.size()) {
			pila.push(heap.get(i).get(1));
		}
	}

	protected void store(Stack<Integer> pila, ArrayList<ArrayList<Integer>> heap) {
		Integer a = pila.pop();
		Integer b = pila.pop();
		int i = 0;
		while (i < heap.size() && heap.get(i).get(0) != b)
			i++;

		if (i == heap.size()) {
			ArrayList<Integer> aux = new ArrayList<Integer>();
			aux.add(b);
			aux.add(a);
			heap.add(aux);
		} else {
			heap.get(i).set(1, a);
		}

	}

	protected void mod(Stack<Integer> pila) {
		// TODO Auto-generated method stub
		Integer a = pila.pop();
		Integer b = pila.pop();
		pila.push(b % a);
	}

	protected void div(Stack<Integer> pila) {
		// TODO Auto-generated method stub
		Integer a = pila.pop();
		Integer b = pila.pop();
		pila.push(b / a);
	}

	protected void mul(Stack<Integer> pila) {
		// TODO Auto-generated method stub
		Integer a = pila.pop();
		Integer b = pila.pop();
		pila.push(a * b);
	}

	protected void sub(Stack<Integer> pila) {
		// TODO Auto-generated method stub
		Integer a = pila.pop();
		Integer b = pila.pop();
		pila.push(b - a);
	}

	protected void add(Stack<Integer> pila) {
		// TODO Auto-generated method stub
		Integer a = pila.pop();
		Integer b = pila.pop();
		pila.push(a + b);
	}

	protected void drop(Stack<Integer> pila) {
		// TODO Auto-generated method stub
		pila.pop();
	}

	protected void swap(Stack<Integer> pila) {
		// TODO Auto-generated method stub
		Integer a = pila.pop();
		Integer b = pila.pop();
		pila.push(a);
		pila.push(b);
	}

	protected void dup(Stack<Integer> pila) {
		// TODO Auto-generated method stub
		pila.push(pila.peek());
	}

	protected void push(Stack<Integer> pila, ArrayList<ArrayList<String>> instruccion, int i) {
		pila.push(Integer.valueOf(instruccion.get(i).get(1)));
	}

	public Queue<String> comando(String aux) {
		Queue<String> comandos = new LinkedList<String>();
		for (int i = 0; i < aux.length(); i++) {
			if (aux.charAt(i) == ' ') {
				comandos.add("s");
			}
			if (aux.charAt(i) == '\t') {
				comandos.add("t");
			}
			if (aux.charAt(i) == '\n') {
				comandos.add("e");
			}
		}
		return comandos;
	}

	public int wsToNum(String numws) {
		if (numws.compareTo("s") == 0 || numws.compareTo("t") == 0)
			return 0;
		else {
			int signo;
			if (numws.charAt(0) == 't')
				signo = -1;
			else
				signo = 1;
			String numero = "";
			for (int i = 1; i < numws.length(); i++) {
				if (numws.charAt(i) == 't')
					numero = numero.concat("1");
				else
					numero = numero.concat("0");
			}
			int resultado = Integer.parseInt(numero, 2) * signo;
			return resultado;
		}
	}

	public void convertirPush(ArrayList<ArrayList<String>> instruccion) {
		for (int i = 0; i < instruccion.size(); i++) {
			if (instruccion.get(i).get(0).compareTo("push") == 0 || instruccion.get(i).get(0).compareTo("copy") == 0
					|| instruccion.get(i).get(0).compareTo("slide") == 0) {
				int aux = wsToNum(instruccion.get(i).get(1));
				instruccion.get(i).set(1, String.valueOf(aux));
			}
		}
	}

	public void convertirLabels(ArrayList<ArrayList<String>> instruccion) {
		String losMasBuscados = "label call_label jmp_label jz_label jn_label";
		String labelActual;
		int numeroLabel = 0;

		for (int i = 0; i < instruccion.size(); i++) {
			if (losMasBuscados.contains(instruccion.get(i).get(0)) && !isNumero(instruccion.get(i).get(1))) {
				labelActual = instruccion.get(i).get(1);
				for (int j = 0; j < instruccion.size(); j++) {
					if (instruccion.get(j).get(1) != null && instruccion.get(j).get(1).compareTo(labelActual) == 0) {
						instruccion.get(j).set(1, String.valueOf(numeroLabel));
					}
				}
				numeroLabel++;
			}
		}
		// crear conjunto y preguntar con contains
	}

	public boolean isNumero(String num) {
		String numeros = "0123456789";
		if (numeros.contains(String.valueOf(num.charAt(0)))) {
			return true;
		}
		return false;
	}

	public void instrucciones(Queue<String> entrada, ArrayList<ArrayList<String>> instruccion, Nodo nodo, Arbol a) {
		if (nodo.getLlave() != null) {
			ArrayList<String> aux = new ArrayList<String>();
			aux.add(nodo.getLlave());
			if (nodo.isParametro() == true) {
				String param = "";
				while (!entrada.isEmpty() && entrada.peek().compareTo("e") != 0) {
					param = param.concat(entrada.poll());
				}
				if (!entrada.isEmpty())
					entrada.poll();
				if (param.compareTo("") == 0)
					param = "n";
				aux.add(param);
			} else {
				aux.add(null);
			}

			instruccion.add(aux);
			instrucciones(entrada, instruccion, a.getRaiz(), a);

		} else {
			if (!entrada.isEmpty()) {
				String comando = entrada.poll();
				switch (comando) {
				case "t":
					instrucciones(entrada, instruccion, nodo.getTab(), a);
					break;
				case "s":
					instrucciones(entrada, instruccion, nodo.getSpace(), a);
					break;
				case "e":
					instrucciones(entrada, instruccion, nodo.getEnter(), a);
					break;

				}
			}
		}
	}
}
