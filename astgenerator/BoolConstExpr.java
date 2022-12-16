public class BoolConstExpr implements Expr {
	public final Boolean value;
	  public BoolConstExpr(Boolean value) {
		this.value = value;
	}
  
	  public int evaluate() { return this.value?1:0; }
	  public String toString() { return this.value.toString(); }
	
  }
  