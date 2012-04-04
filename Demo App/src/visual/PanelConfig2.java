package visual;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import sparql.Queries;
import sparql.RunQuery;

public class PanelConfig2 extends JPanel{
	
	private Queries listQueries;
	private RunQuery runQuery;
	private JComboBox queries;
	private JButton bQuery;
	private JTextField param;
	private JScrollPane areaScrollPane;
	private JTextArea result;
	private String[] qs= {
			"Get all the Wf runs (no param needed)",//0
			"Get processes of Wf Run",//1
			"Get Inputs/Outputs of a Wf Run",//2
			"Get Inputs of a Process",//3
			"Get Outputs of a Process",//4
			"Get Template of a Process",//5
			"Get Wf Runs that have a process template",//6
			"Get Wf Runs/Processes that have an input"//7
			
	};
	
	
	public PanelConfig2(){
		init();
		functionality();
		visual();
	}
	private void init(){
		listQueries=new Queries();
		runQuery=new RunQuery();
		bQuery=new JButton("Ok");
		queries=new JComboBox(qs);
		param= new JTextField(30);
		result= new JTextArea();
		result.setEditable(false);
		
	}
	private void visual(){
		this.setLayout(new GridBagLayout());
		JPanel panel=new JPanel();
		panel.add(param);
		panel.add(queries);
		panel.add(bQuery);
		panel.setBorder(BorderFactory.createTitledBorder("Select Query and set the parameter:"));
		
		areaScrollPane= new JScrollPane(result);
		areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setPreferredSize(new Dimension(600, 600));
		
		addItem(this, panel,0,0,1,1,GridBagConstraints.CENTER);
		addItem(this, areaScrollPane,0,1,1,20,GridBagConstraints.CENTER);
	}
	private void functionality(){
		bQuery.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
		    	String r=null;
		    	switch (queries.getSelectedIndex()){
		    	case 0: r=runQuery.runQuery(listQueries.getListExecutions()); break;
		    	case 1: r=runQuery.runQuery(listQueries.getExecutedProcesses(param.getText())); break;
		    	case 2: r=runQuery.runQuery(listQueries.getIOs(param.getText())); break;
		    	case 3: r=runQuery.runQuery(listQueries.getInputs(param.getText())); break;
		    	case 4: r=runQuery.runQuery(listQueries.getOutputs(param.getText())); break;
		    	case 5: r=runQuery.runQuery(listQueries.getTemplate(param.getText())); break;
		    	case 6: r=runQuery.runQuery(listQueries.getRunsOfATemplate(param.getText())); break;
		    	case 7: r=runQuery.runQuery(listQueries.getUsesOfAnInput(param.getText())); break;
		    	}
		    	setResults(r);
		    }
		});
	}
	
	public void setResults(String r){
		result.setText(r);
	}

	private void addItem(JPanel p, JComponent c, int x, int y, int width, int height, int align) {
	    GridBagConstraints gc = new GridBagConstraints();
	    gc.gridx = x;
	    gc.gridy = y;
	    gc.gridwidth = width;
	    gc.gridheight = height;
	    gc.weightx = 100.0;
	    gc.weighty = 100.0;
	    gc.insets = new Insets(3, 8, 3, 8);
	    gc.anchor = align;
	    gc.fill = GridBagConstraints.BOTH;
	    p.add(c, gc);
	  }
}
