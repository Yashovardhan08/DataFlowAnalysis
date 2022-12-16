public class Declaration implements Instr{
	public final String varname;
	  public final Type type;
  
	  public Declaration(String varname, Type type) {
	  this.varname = varname;
		  this.type = type;
	  }
  
	  public String toString() {
	  return this.type.toString() + " " + this.varname;
	  }
  }