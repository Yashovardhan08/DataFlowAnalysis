// package dfa;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class LVAnalysis extends DFA{
    Map<CfgNode,Set<String>> inLV,outLV;
    LVAnalysis(CFG graph){
        super(graph);
        // Collections.reverse(traverseList);
        traverseList.remove(graph.exitNode);
        traverseList.add(graph.exitNode);
        Collections.reverse(traverseList);
        this.inLV = new HashMap<CfgNode,Set<String>>();
        this.outLV = new HashMap<CfgNode,Set<String>>();
        for(CfgNode cfgNode: this.traverseList){
            inLV.put(cfgNode, new HashSet<String>());
            outLV.put(cfgNode, new HashSet<String>());
        }
    }

    @Override
    public void Meet(CfgNode node){
         // OUT OF PRESENT NODE IS UNION OF IN OF SUCCESSOR
        //  System.out.println("Next node is "+node.nextNode+" for node "+node);
         SetOperations.Union(outLV.get(node), inLV.get(node.nextNode));

         // IF NODE HAS TWO SUCCESSORS
         if(node instanceof ConditionalNode)
             SetOperations.Union(outLV.get(node), inLV.get(((ConditionalNode)node).falseNode));

    }

    @Override 
    public Boolean Transfer(CfgNode node){
        Set<String> nextInSet = new HashSet<String>();

        Boolean changed = false;
        // IN SET OF CURRENT NODE = USE + (OUT - DEF)
        SetOperations.Union(nextInSet, node.useSet);
        SetOperations.Union(nextInSet, SetOperations.Negation(outLV.get(node), node.defSet));

        if(checkForChange(inLV, nextInSet, node))changed = true;

        inLV.remove(node);
        inLV.put(node, nextInSet);
        return changed;
    }

    @Override
    public Boolean HandleFirstNode(CfgNode node){
        Boolean changed = false;
        for(String id: node.useSet)
            if(!inLV.get(node).contains(id)){
                inLV.get(node).add(id);
                changed = true;
            }
        return changed;
    }

    // @Override
    // public void Analyze(){
    //     Boolean changed=true;
    //     while(changed){
    //         changed=false;
    //         // TRAVERSE THE NODES 
    //         for(int i=this.traverseList.size()-1;i>=0;--i){
    //             CfgNode node = this.traverseList.get(i);

    //             // HANDLE THE EXIT NODE CASE
    //             if(node == cfg.exitNode){
    //                 for(String id: node.useSet)
    //                     if(!inLV.get(node).contains(id)){
    //                         inLV.get(node).add(id);
    //                         changed = true;
    //                     }
    //                 continue;
    //             }

    //             // OUT OF PRESENT NODE IS UNION OF IN OF SUCCESSOR
    //             SetOperations.Union(outLV.get(node), inLV.get(node.nextNode));

    //             // IF NODE HAS TWO SUCCESSORS
    //             if(node instanceof ConditionalNode)
    //                 SetOperations.Union(outLV.get(node), inLV.get(((ConditionalNode)node).falseNode));

    //             // output of nodes is use[node] U (outLV[node] - def[node])
    //             Set<String> nextInSet = new HashSet<String>();

    //             // IN SET OF CURRENT NODE = USE + (OUT - DEF)
    //             SetOperations.Union(nextInSet, node.useSet);
    //             SetOperations.Union(nextInSet, SetOperations.Negation(outLV.get(node), node.defSet));

    //             if(checkForChange(inLV, nextInSet, node))changed = true;

    //             inLV.remove(node);
    //             inLV.put(node, nextInSet);
    //         }
    //     }
    //     printInOutSets();
    // }

    //HELPER FUNCTIONS TO PRINT OUTPUT
    @Override
    public void printInOutSets(){
        System.out.println("LIVE VARIABLE ANALYSIS :");
        Iterator<CfgNode> it = inLV.keySet().iterator();
        System.out.println("\t \tINSETS:");
        while(it.hasNext()){
            CfgNode node =it.next();
            System.out.println("\t \t \t FOR NODE: "+ node);
            System.out.print("\t \t \t \t \t \t ID's: " );
            for(String idExpr:inLV.get(node)){
                System.out.print(idExpr +" \t");
            }
            System.out.println("\n");
        }
        System.out.println("\n\t \tOUTSETS:");
        it = outLV.keySet().iterator();
        while(it.hasNext()){
            CfgNode node =it.next();
            System.out.println("\t \t \t FOR NODE: "+ node);
            System.out.print("\t \t \t \t \t \t ID's: " );
            for(String idExpr:outLV.get(node)){
                System.out.print(idExpr +" \t");
            }
            System.out.println("\n");
        }
    }
}
