package visual;
import java.util.ArrayList;
import java.util.Scanner;

import model.Content;

import rdf.VarExtractor;
import model.Process;
import sparql.Queries;
import sparql.RunQuery;


public class RunExample {
	private Queries q;
	private RunQuery rQ;
	private VarExtractor extractor;
	private Content content;
	
	public RunExample(String wfRun){
		q = new Queries();
		rQ = new RunQuery();
		extractor = new VarExtractor();
		content = new Content();
		
		//System.out.println("Starting extraction process...");
		this.extractProcesses(wfRun);
		//System.out.println("Linking inputs/outputs...");
		this.setInputsOutputs();
		//System.out.println("Analyzing provenance...");
		this.createTrace();
		//System.out.println("Provenance trace:");
		//content.printTrace();
		
	}
	
	private void createTrace() {
		content.inicializeContent();
		content.createTrace();
		
	}

	private void setInputsOutputs() {
		for (Process p: content.getProcessList()){
			this.setTemplate(p);
			this.setInputs(p);
			this.setOutputs(p);
		}
		
	}
	
	private void setTemplate(Process p) {
		String rdf=null;
		rdf=rQ.runQuery(q.getTemplate(p.getName()));
		//System.out.println(rdf);
		Scanner scanner = new Scanner(rdf);
		String line=null;
		String template=null;
		while (scanner.hasNextLine()) {
		  line = scanner.nextLine();
		  if (extractor.checkAppereance(line, "template")){
			  template=extractor.SimpleExtract(line);
			  p.setTemplate(template);
		  }
		}
	}

	private void setInputs(Process p){
		String rdf=null;
		rdf=rQ.runQuery(q.getInputs(p.getName()));
		//System.out.println(rdf);
		Scanner scanner = new Scanner(rdf);
		String line=null;
		String input=null;
		while (scanner.hasNextLine()) {
		  line = scanner.nextLine();
		  if (extractor.checkAppereance(line, "input")){
			  input=extractor.SimpleExtract(line);
			  content.addIO(input);
			  content.addInputUsage(input, p.getName());
		  }
		}
	}
	
	private void setOutputs(Process p){
		String rdf=null;
		rdf=rQ.runQuery(q.getOutputs(p.getName()));
		//System.out.println(rdf);
		Scanner scanner = new Scanner(rdf);
		String line=null;
		String output=null;
		while (scanner.hasNextLine()) {
		  line = scanner.nextLine();
		  if (extractor.checkAppereance(line, "output")){
			  output=extractor.SimpleExtract(line);
			  content.addIO(output);
			  content.addOutputUsage(output, p.getName());
		  }
		}
	}


	private void extractProcesses(String wfRun){
		String rdf= rQ.runQuery(q.getExecutedProcesses(wfRun));
		//System.out.println(rdf);
		Scanner scanner = new Scanner(rdf);
		String line=null;;
		String p=null;
		while (scanner.hasNextLine()) {
		  line = scanner.nextLine();
		  if (extractor.checkAppereance(line, "process")){
			  p=extractor.SimpleExtract(line);
			  content.addProcess(p);
		  }
		}
	}
	
	public ArrayList<Process> getExecutionTrace(){
		return content.getExecutionTrace();
	}

}
