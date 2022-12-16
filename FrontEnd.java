import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import javax.management.Query;


import java.io.FileNotFoundException;
import java.io.FileReader;

public class FrontEnd {
	public static void main(String[] args) {
    String input = args[0];
		try {
		  	System.out.println("Parsing " + input);
      		parser p = new parser(new IdLexer(new FileReader(input)));
			Program program = (Program)(p.parse().value);

			TypeChecker typeChecker = new TypeChecker(program);
			typeChecker.TypeCheck();
			System.out.println(program);

			System.out.println("Parsed the program \n");

			CFG cfg = CFG.MakeCFG(program.instrList);// CFG of the program 
			CFG.updateGenSet(cfg.genSet, cfg);// generate the gen set for the final cfg

			// DFA dfaObject = new DFA(cfg,"union");
			// dfaObject.Analyze(true);
			// dfaObject.Analyze(false);
			DFA dfa = new LVAnalysis(cfg);
			dfa.Analyze();
			DFA dfa2 = new RDAnalysis(cfg);
			dfa2.Analyze();
		}
		catch(FileNotFoundException e) {
      System.out.println("File not found!");
			System.exit(1);
    }
		catch(Exception e) {
      System.out.println("Unknown error!");
			e.printStackTrace();
			System.exit(1);
    }
	}
} 

