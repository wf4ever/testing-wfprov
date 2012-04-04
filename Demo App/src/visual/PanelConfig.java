package visual;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import model.Process;


public class PanelConfig extends JPanel{
	
	private JComboBox executions;
	private JButton bOk;
	private JTree tree;
	private enum options{WINGS1,WINGS2,TAVERNA};
	private JScrollPane areaScrollPane;
	private RunExample run;
	
	
	
	public PanelConfig(){
		inits();
		functionality();
		configView();
		
	}



	private void functionality() {
		bOk.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
		    	switch (options.valueOf(executions.getSelectedItem().toString())){
		    	case WINGS1: {showMessage(); run=new RunExample("http://wings.isi.edu/opmexport/resource/Account/ACCOUNT1332778606534");}; break;
		    	case WINGS2: {showMessage(); run=new RunExample("http://wings.isi.edu/opmexport/resource/Account/ACCOUNT1332778615941");}; break;
		    	case TAVERNA: { showMessage(); run=new RunExample("http://ns.taverna.org.uk/2011/run/479c9612-4862-4bcb-ad09-315b7b864260/");}; break;
		    	}
		    	createTree();
		    }
		});
		
	}


	public void createTree(){
		ArrayList<Process> trace=run.getExecutionTrace();
		DefaultMutableTreeNode provenance = new DefaultMutableTreeNode("Provenance trace");
        //DefaultTreeModel modelo = new DefaultTreeModel(provenance);
        tree = new JTree(provenance);
        
        int num=0;
        for (Process p: trace){
        	DefaultMutableTreeNode step=new DefaultMutableTreeNode("Step "+num);
        	provenance.add(step);
        	setStepContent(step,p);
        	num++;
        }
        resetView();
	}
	




	private void setStepContent(DefaultMutableTreeNode step, Process p) {
		DefaultMutableTreeNode pr = new DefaultMutableTreeNode("Process");
		DefaultMutableTreeNode t = new DefaultMutableTreeNode("Template");
		DefaultMutableTreeNode i = new DefaultMutableTreeNode("Instance");
		DefaultMutableTreeNode template = new DefaultMutableTreeNode(p.getTemplate());
		DefaultMutableTreeNode name = new DefaultMutableTreeNode(p.getName());
		step.add(pr);
		pr.add(t);
		pr.add(i);
		t.add(template);
		i.add(name);
		DefaultMutableTreeNode inputs = new DefaultMutableTreeNode("Inputs ("+p.getNumInputs()+")");
		DefaultMutableTreeNode outputs = new DefaultMutableTreeNode("Outputs ("+p.getNumOutputs()+")");
		step.add(inputs);
		step.add(outputs);
		setInputContent(inputs,p);
		setOutputContent(outputs,p);
	}



	private void setOutputContent(DefaultMutableTreeNode outputs, Process p) {
		int num=p.getNumOutputs();
		for (int i=0; i<num; i++){
			DefaultMutableTreeNode output = new DefaultMutableTreeNode(p.getOutputName(i));
			outputs.add(output);
		}	
	}



	private void setInputContent(DefaultMutableTreeNode inputs, Process p) {
		int num=p.getNumInputs();
		for (int i=0; i<num; i++){
			DefaultMutableTreeNode input = new DefaultMutableTreeNode(p.getInputName(i));
			inputs.add(input);
		}
	}



	private void resetView(){
		this.removeAll();
		this.setLayout(new GridBagLayout());
		JPanel panel=new JPanel();
		panel.add(executions);
		panel.add(bOk);
		panel.setBorder(BorderFactory.createTitledBorder("Select Provenance Trace:"));
		
		areaScrollPane= new JScrollPane(tree);
		areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setPreferredSize(new Dimension(600, 600));
		
		addItem(this, panel,0,0,1,1,GridBagConstraints.CENTER);
		addItem(this, areaScrollPane,0,1,1,20,GridBagConstraints.CENTER);
		this.setVisible(false);
		this.setVisible(true);
		this.repaint();
	}
	

	private void configView() {
		this.setLayout(new GridBagLayout());
		JPanel panel=new JPanel();
		panel.add(executions);
		panel.add(bOk);
		panel.setBorder(BorderFactory.createTitledBorder("Select Provenance Trace:"));
		
		DefaultMutableTreeNode provenance = new DefaultMutableTreeNode("Empty");
        //DefaultTreeModel modelo = new DefaultTreeModel(provenance);
        tree = new JTree(provenance);
		
		areaScrollPane= new JScrollPane(tree);
		areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setPreferredSize(new Dimension(600, 600));
		
		addItem(this, panel,0,0,1,1,GridBagConstraints.CENTER);
		addItem(this, areaScrollPane,0,1,1,20,GridBagConstraints.CENTER);
		
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

	private void inits() {
		executions=new JComboBox(options.values());
		tree=new JTree();
		bOk=new JButton("Show Results");
		
		
	}
	
	private void showMessage(){
		JOptionPane.showMessageDialog(null,
			    "This process may take several seconds");
	}


}
