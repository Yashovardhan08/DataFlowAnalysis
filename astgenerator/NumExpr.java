public class NumExpr implements Expr {
    public final Integer value;
      public NumExpr(Integer value) {
        this.value = value;
    }
  
      public int evaluate() { return this.value; }
      public String toString() { return this.value.toString(); }
    
  }
  