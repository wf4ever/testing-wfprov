package visual;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class Window extends JFrame{
	
	private PanelConfig pc;
	private PanelConfig2 pc2;
	private JTabbedPane tabbedPane;
	
	
	public Window(){
		this.setTitle("Wfprov analysis");
		this.setSize(900, 750);
		tabbedPane = new JTabbedPane();
		pc=new PanelConfig();
		pc2=new PanelConfig2();
		tabbedPane.addTab("Provenance Trace", pc);
		tabbedPane.addTab("Example Queries", pc2);
		this.add(tabbedPane);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}

}
