import java.util.ArrayList;

public class InstrList{
	public ArrayList<Instr> iList;
	public InstrList(Instr i){
		this.iList = new ArrayList<>();
		if(i!=null)iList.add(i);
	}
	public void addInstr(Instr i){
		this.iList.add(i);
	}
	public String toString() {
		String ret = "";
		for (Instr instr : iList) {
			if(instr instanceof Expr)ret = ret + instr.toString() +";\n"; 
			else if(instr instanceof Assign)ret = ret + instr.toString() +";\n"; 
			else if(instr instanceof Declaration)ret = ret + instr.toString() +";\n"; 
			else ret = ret + instr.toString() +"\n"; 
		}
		return "\n"+ret+"\n";
	}
}