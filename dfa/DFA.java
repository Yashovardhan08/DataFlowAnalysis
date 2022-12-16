// package dfa;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


public abstract class DFA {
    // Boolean forwardDirection;
    CFG cfg;
    ArrayList<CfgNode> traverseList = new ArrayList<CfgNode>();

    public DFA(CFG graph){
        this.cfg=graph;

        Queue<CfgNode> q = new LinkedList<>();
        Set<CfgNode> finishedNodeSet = new HashSet<CfgNode>();
        q.add(cfg.inNode);
        while( q.peek()!=null ){
            CfgNode node = q.poll();
            this.traverseList.add(node);
            if(node.nextNode != null && finishedNodeSet.contains(node.nextNode)==false)q.add(node.nextNode);
            if(node instanceof ConditionalNode && ((ConditionalNode)node).falseNode!=null && !finishedNodeSet.contains(((ConditionalNode)node).falseNode))q.add(((ConditionalNode)node).falseNode);
            finishedNodeSet.add(node);
        }
    }

    public abstract Boolean HandleFirstNode(CfgNode node);
    public abstract void printInOutSets();
    public abstract void Meet(CfgNode node);
    public abstract Boolean Transfer(CfgNode node);

    public void Analyze(){
        Boolean changed =true;
        while(changed){
            Boolean firstNode = true;
            changed = false;

            for(CfgNode node: traverseList){
                if(firstNode){
                    changed = HandleFirstNode(node);
                    firstNode = false;
                    continue;
                }

                Meet(node);
                changed = Transfer(node);
            }
        }
        printInOutSets();
    }

    // HELPER FUNCTION TO GET KILL SET
    public HashSet<CfgNode> getKillSet(CfgNode node,Set<CfgNode> outSet,CFG cfg){
        HashSet<CfgNode> ret = new HashSet<CfgNode>();
        if(node instanceof BasicBlockNode && ((BasicBlockNode)node).instr instanceof Assign){
            String id = ((Assign)(((BasicBlockNode)node).instr)).id.name;
            outSet.add(node);
            ret.addAll(cfg.genSet.get(id));
            ret.remove(node);
        }
        return ret;
    }

    // HELPER FUNCTION FOR IDENTIFYING CHANGE
    public <T> Boolean checkForChange(Map<CfgNode,Set<T>> a,Set<T> b,CfgNode node){
        if(b.size()!=a.get(node).size())return true;
        else 
            for(T cfgNode:a.get(node))
                if(!b.contains(cfgNode))return true;
        return false;
    }    
    
}


