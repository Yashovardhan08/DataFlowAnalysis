import java.util.ArrayList;

public class BasicBlockNode extends CfgNode{
    public Instr instr;

    public BasicBlockNode(Instr e){
        this.instr = e;
        ArrayList<IdExpr> t=CfgNode.getDefs(this);
        for (IdExpr idExpr : t){this.def.add(idExpr); this.defSet.add(idExpr.name);}
        t = CfgNode.getUses(this);
        for (IdExpr idExpr : t){this.use.add(idExpr); this.useSet.add(idExpr.name);}
    }

    @Override
    public String toString() {
        return instr.toString();
    }
}