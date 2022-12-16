import java.util.ArrayList;

public class ConditionalNode extends CfgNode{
    public CfgNode falseNode;
    public Expr expr;
    

    public ConditionalNode(Expr e){
        this.expr = e;
        ArrayList<IdExpr> t=CfgNode.getUses(this);
        for (IdExpr idExpr : t){this.use.add(idExpr); this.useSet.add(idExpr.name);}
    }

    @Override
    public String toString() {
        return "Condition( "+expr.toString()+" )";
    }
}
