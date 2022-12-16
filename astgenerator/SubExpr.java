public class SubExpr implements Expr {
	public final Expr left;
	  public final Expr right;
  
	public SubExpr(Expr left, Expr right) {
	  this.left = left;
		  this.right = right;
	  }
  
	  public int evaluate() { return this.left.evaluate() - this.right.evaluate(); }
	  public String toString() { return this.left.toString() + " - " + this.right.toString(); }
  }
  
