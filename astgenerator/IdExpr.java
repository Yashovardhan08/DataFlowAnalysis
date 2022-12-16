public class IdExpr implements Expr {
    public final String name;
      public IdExpr(String name) {
        this.name = name;
    }
      public int evaluate() { return 0; }
      public String toString() { return this.name; }
  }
  