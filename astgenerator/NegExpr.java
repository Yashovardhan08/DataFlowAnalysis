public class NegExpr implements Expr {
  Expr expr;
	public NegExpr(Expr expr) {
	  this.expr = expr;
  }

	public int evaluate() { return this.expr.evaluate(); }
	public String toString() { return "-(" + this.expr.toString() + ")"; }
}
