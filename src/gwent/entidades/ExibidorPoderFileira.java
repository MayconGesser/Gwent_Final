package gwent.entidades;

import javax.swing.JLabel;
import javax.swing.JPanel;

import br.ufsc.inf.leobr.cliente.Jogada;

public class ExibidorPoderFileira extends JPanel implements Jogada{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel poderTotalFileira;
	
	public ExibidorPoderFileira(){
		this.poderTotalFileira = new JLabel();
		this.add(poderTotalFileira);
		setPreferredSize(new java.awt.Dimension(46,37));
	}
	
	public void alterarPoder(int poder){
		this.poderTotalFileira.setText(String.valueOf(poder));
		repaint();
	}
}
