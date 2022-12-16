import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CFG {
    public CfgNode inNode;
    public CfgNode exitNode;
    public Map<String,Set<CfgNode>> genSet;

    public CFG(CfgNode iNode,CfgNode eNode){// CONSTRUCTOR
        this.inNode = iNode;
        this.exitNode = eNode;
    }
    // HELPER FUNCTION TO CHECK IF ITS A CONDITIONAL NODE
    public static Boolean IsConditionalNode(CfgNode n) {
        if(n instanceof ConditionalNode)return true;
        return false;
    }

    // HELPER FUNCTION TO UPDATE PREVIOUS NODES FOR CFGNODES
    public static void UpdatePreviousNode(CfgNode[] arrNodes){// prevNode, node
        if(arrNodes[0]!=null){
            if(IsConditionalNode(arrNodes[0]))((ConditionalNode)arrNodes[0]).falseNode = arrNodes[1];
            else arrNodes[0].nextNode = arrNodes[1];
            arrNodes[1].inNodes.add(arrNodes[0]);
        }
    }

    // STATIC FUNCTION TO UPDATE THE "tempGenSet" USING THE GEN SET OF A CFG
    public static void updateGenSet(Map<String,Set<CfgNode>> tempGenSet,CFG cfg){
        Iterator<String> it = cfg.genSet.keySet().iterator();
        while(it.hasNext()){
            String next = it.next();
            if(tempGenSet.get(next)==null){
                // ADD A NEW SET FOR AN IDENTIFIER AND UPDATE IT
                tempGenSet.put(next,new HashSet<CfgNode>());
                for(CfgNode cNode : cfg.genSet.get(next))
                    tempGenSet.get(next).add(cNode);

            }
            else {// UPDATE THE EXISTING GEN SET FOR IDENTIFIER "next"
                for(CfgNode cNode : cfg.genSet.get(next))
                    tempGenSet.get(next).add(cNode);

            }
        }
    }

    // GENERATES THE CFG FOR A GIVEN INSTRUCTION LIST
    public static CFG MakeCFG(InstrList instrList){
        CfgNode prevNode=null,firsNode=null;
        Map<String,Set<CfgNode>> tempGenSet = new HashMap<>();// GENSET FOR THE CFG
        
        for(Instr instr: instrList.iList){
            CfgNode node = null;
            if(instr instanceof WhileExpr){
                WhileExpr whileInstr = (WhileExpr)instr;
                node = new ConditionalNode(whileInstr.e);

                // updating previous node and inNodes pointers
                CfgNode[] arr = {prevNode,node};
                UpdatePreviousNode(arr);

                // Generate CFG for the while block
                CFG trueCfg = MakeCFG(whileInstr.b.ilist);
                ((ConditionalNode)node).nextNode = trueCfg.inNode;
                trueCfg.inNode.inNodes.add(node);

                // Update the pointers for the block
                trueCfg.exitNode.nextNode = node;
                // Specific for while LOOP BACK
                node.inNodes.add(trueCfg.exitNode);
                //update the gen set
                CFG.updateGenSet(tempGenSet,trueCfg);
                prevNode = node;
            }
            else if(instr instanceof IfStatement){
                IfStatement ifInstr = (IfStatement)instr;
                node = new ConditionalNode(ifInstr.e1);

                // updating previous node and inNodes pointers
                CfgNode[] arr = {prevNode,node};
                UpdatePreviousNode(arr);

                // Generate CFG for the true block
                CFG trueCfg = MakeCFG(ifInstr.b1.ilist),falseCfg=null;
                node.nextNode = trueCfg.inNode;
                trueCfg.inNode.inNodes.add(node);

                // Make an empty node for the previous node for next instruction
                CfgNode emptyNode = new VoidNode();
                emptyNode.inNodes.add(trueCfg.exitNode);
                trueCfg.exitNode.nextNode=emptyNode;

                if(ifInstr.b2!=null){// there exists an else block
                    falseCfg = MakeCFG(ifInstr.b2.ilist);
                    falseCfg.inNode.inNodes.add(node);
                    ((ConditionalNode)node).falseNode = falseCfg.inNode;
                    emptyNode.inNodes.add(falseCfg.exitNode);
                    falseCfg.exitNode.nextNode = emptyNode;
                    CFG.updateGenSet(tempGenSet, falseCfg);
                }
                else {// there is no else block
                    ((ConditionalNode)node).falseNode = emptyNode;
                    emptyNode.inNodes.add(node);
                }

                // update gen set 
                CFG.updateGenSet(tempGenSet,trueCfg);
                prevNode = emptyNode;

            }
            else {
                node = new BasicBlockNode(instr);
                CfgNode[] arr = {prevNode,node};
                UpdatePreviousNode(arr);
                prevNode = node;
                // Update the Gen Set
                if(instr instanceof Assign){
                    // add a new set if it does not exist
                    if( tempGenSet.get(((Assign)instr).id.name)==null)tempGenSet.put(((Assign)instr).id.name, new HashSet<>());
                    // add the instruction to the gen set
                    tempGenSet.get(((Assign)instr).id.name).add(node);
                }
            }
            if(firsNode == null)firsNode = node;
        }
        CFG ret = new CFG(firsNode, prevNode);
        ret.genSet = tempGenSet;
        return ret;
    }
}
