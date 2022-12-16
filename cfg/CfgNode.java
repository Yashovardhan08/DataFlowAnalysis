import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class CfgNode {
    public CfgNode nextNode;
    public ArrayList<CfgNode> inNodes;
    public ArrayList<IdExpr> def;
    public ArrayList<IdExpr> use;
    public Set<String> defSet = new HashSet<String>();
    public Set<String> useSet = new HashSet<String>();

    public CfgNode(){
        this.def = new ArrayList<IdExpr>();
        this.use = new ArrayList<IdExpr>();
        this.inNodes = new ArrayList<CfgNode>();
    }

    // get the IDs involved, a helper function for get Uses and get Defs
    public static ArrayList<IdExpr> getIds(Expr e){
        ArrayList<IdExpr> ret = new ArrayList<IdExpr>();
        ArrayList<IdExpr> temp = new ArrayList<IdExpr>();

        if(e instanceof AddExpr){
            temp = getIds(((AddExpr)e).left);
            for(IdExpr idexpr:temp)ret.add(idexpr);
            temp = getIds(((AddExpr)e).right);
            for(IdExpr idexpr:temp)ret.add(idexpr);

        }
        else if(e instanceof SubExpr){
            temp = getIds(((SubExpr)e).left);
            for(IdExpr idexpr:temp)ret.add(idexpr);
            temp = getIds(((SubExpr)e).right);
            for(IdExpr idexpr:temp)ret.add(idexpr);
        }
        else if( e instanceof MulExpr){
            temp = getIds(((MulExpr)e).left);
            for(IdExpr idexpr:temp)ret.add(idexpr);
            temp = getIds(((MulExpr)e).right);
            for(IdExpr idexpr:temp)ret.add(idexpr);
        }
        else if(e instanceof IdExpr){
            ret.add((IdExpr)e);
        }
        else if(e instanceof NegExpr){
            temp = getIds(((NegExpr)e).expr);
            for(IdExpr idexpr:temp)ret.add(idexpr);
        }
        else if(e instanceof GEqualsBoolExpr){
            temp = getIds(((GEqualsBoolExpr)e).e1);
            for(IdExpr idExpr:temp)ret.add(idExpr);
            temp = getIds(((GEqualsBoolExpr)e).e2);
            for(IdExpr idExpr:temp)ret.add(idExpr);
        }
        else if(e instanceof LEqualsBoolExpr){
            temp = getIds(((LEqualsBoolExpr)e).e1);
            for(IdExpr idExpr:temp)ret.add(idExpr);
            temp = getIds(((LEqualsBoolExpr)e).e2);
            for(IdExpr idExpr:temp)ret.add(idExpr);
        }
        else if(e instanceof LesserBoolExpr){
            temp = getIds(((LesserBoolExpr)e).e1);
            for(IdExpr idExpr:temp)ret.add(idExpr);
            temp = getIds(((LesserBoolExpr)e).e2);
            for(IdExpr idExpr:temp)ret.add(idExpr);
        }
        else if(e instanceof GreaterBoolExpr){
            temp = getIds(((GreaterBoolExpr)e).e1);
            for(IdExpr idExpr:temp)ret.add(idExpr);
            temp = getIds(((GreaterBoolExpr)e).e2);
            for(IdExpr idExpr:temp)ret.add(idExpr);
        }
        else if(e instanceof EqualsBoolExpr){
            temp = getIds(((EqualsBoolExpr)e).e1);
            for(IdExpr idExpr:temp)ret.add(idExpr);
            temp = getIds(((EqualsBoolExpr)e).e2);
            for(IdExpr idExpr:temp)ret.add(idExpr);
        }

        return ret;
    }


    public static ArrayList<IdExpr> getDefs(CfgNode node){
        if(node instanceof ConditionalNode || node instanceof VoidNode)return new ArrayList<IdExpr>();
        else {
            ArrayList<IdExpr> ret = new ArrayList<IdExpr>();
            BasicBlockNode b = (BasicBlockNode)node;
            if(b.instr instanceof Assign){
                ret.add(((Assign)b.instr).id);
            }
            return ret;
        }
    }
    public static ArrayList<IdExpr> getUses(CfgNode node){
        if(node instanceof VoidNode)return new ArrayList<IdExpr>();
        else if(node instanceof ConditionalNode){
            ConditionalNode c = (ConditionalNode)node;
            ArrayList<IdExpr> ret = getIds(c.expr);
            return ret;
        }
        BasicBlockNode b = (BasicBlockNode)node;
        if(b.instr instanceof FCall){
            ArrayList<IdExpr> ret = new ArrayList<IdExpr>(); 
            FCall fc = (FCall)b.instr;
            for (Expr e : fc.args.exprList) {
                ArrayList<IdExpr> temp = getIds(e);
                for (IdExpr idExpr : temp) {
                    ret.add(idExpr);
                }
            }
            return ret;
        }
        else if(b.instr instanceof Assign){
            Assign a = (Assign)b.instr;
            return getIds(a.expr);
        }
        return new ArrayList<IdExpr>();
    }
}