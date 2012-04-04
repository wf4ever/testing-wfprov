import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import visual.RunExample;


public class UserInterface {
	
	private BufferedReader reader;
	private InputStreamReader in;
	private RunExample r;
	
	public UserInterface(){
		in = new InputStreamReader(System.in);
		reader= new BufferedReader(in);
		printMenu();
	}
	
	public void printMenu(){
				String lineRead=null;
				System.out.println("Please, choose which provenance trace you would like to get (enter the corresponding number):");
				System.out.println("1 Wings - Ligand Binding Sites Comparison A");
				System.out.println("2 Wings - Ligand Binding Sites Comparison B");
				System.out.println("3 Taverna - Bioaid Protein Discovery");
				System.out.println("0 -Exit-");
				try {
					lineRead = reader.readLine();
					int number = Integer.parseInt(lineRead);
					switch (number){
					case 1: wings1();break;
					case 2: wings2();break;
					case 3: taverna();break;
					//case 4: otherWf();break;
					case 0: exit();break;
					default: System.out.println("Invalid option");
					
					}
				} catch (IOException e) {
					System.out.println("Invalid option:");
				}
	}
	
	private void otherWf() {
		System.out.println("Please, enter the ID of a wfExecution:");
		String name=null;
		try {
			name= reader.readLine();
		} catch (IOException e) {
			System.out.println("Invalid name");
		}
		r=new RunExample(name);
		printMenu();
		
	}

	private void taverna() {
		r=new RunExample("http://ns.taverna.org.uk/2011/run/479c9612-4862-4bcb-ad09-315b7b864260/");
		printMenu();
	}

	private void wings2() {
		r=new RunExample("http://wings.isi.edu/opmexport/resource/Account/ACCOUNT1332778615941");
		printMenu();
	}

	private void wings1() {
		r=new RunExample("http://wings.isi.edu/opmexport/resource/Account/ACCOUNT1332778606534");
		printMenu();
	}

	public void exit(){
		//nothing to do here...
	}

}
