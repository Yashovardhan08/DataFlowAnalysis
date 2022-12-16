// package dfa;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;



public class RDAnalysis extends DFA{
    Map<CfgNode,Set<CfgNode>> inRD,outRD;

    RDAnalysis(CFG cfg){
        super(cfg);
        this.inRD = new HashMap<CfgNode,Set<CfgNode>>();
        this.outRD =new HashMap<CfgNode,Set<CfgNode>>();
        for(CfgNode cfgNode: this.traverseList){
            this.inRD.put(cfgNode, new HashSet<CfgNode>());
            this.outRD.put(cfgNode, new HashSet<CfgNode>());
        }
    }


    @Override
    public Boolean HandleFirstNode(CfgNode node){
        return false;
    }

    @Override
    public void Meet(CfgNode node){
       // IN SET IS UNION OUT OF PREVIOUS NODES 
       for(CfgNode inNode: node.inNodes)
       SetOperations.Union(inRD.get(node), outRD.get(inNode));
    }

    @Override
    public Boolean Transfer(CfgNode node){
        Boolean changed = false;
        Set<CfgNode> outSet = new HashSet<CfgNode>();
        Set<CfgNode> killSet = getKillSet(node,outSet,cfg);
        
        // GEN + (IN - KILL)
        SetOperations.Union(outSet, SetOperations.Negation(inRD.get(node),killSet));

        if(checkForChange(outRD,outSet,node))changed = true;

        outRD.remove(node);
        outRD.put(node, outSet);
        return changed;
    }

    // @Override
    // public void Analyze(){
    //     Boolean changed=true;
    //     while(changed){
    //         changed=false;
    //         for(CfgNode node:this.traverseList){
    //             if(node.inNodes.size()==0)continue;
    //             // IN SET IS UNION OUT OF PREVIOUS NODES 
    //             for(CfgNode inNode: node.inNodes)
    //                 SetOperations.Union(inRD.get(node), outRD.get(inNode));

    //             // GENERATE A OUTSET AND KILL SET
    //             Set<CfgNode> outSet = new HashSet<CfgNode>();
    //             Set<CfgNode> killSet = getKillSet(node,outSet,cfg);
                
    //             // GEN + (IN - KILL)
    //             SetOperations.Union(outSet, SetOperations.Negation(inRD.get(node),killSet));

    //             if(checkForChange(outRD,outSet,node))changed = true;

    //             outRD.remove(node);
    //             outRD.put(node, outSet);
    //         }
    //     }
    //     printInOutSets();
    // }


    //HELPER FUNCTIONS TO PRINT OUTPUT
    @Override
    public void printInOutSets(){
        System.out.println("REACHING DEFINITIONS");
        Iterator<CfgNode> it = inRD.keySet().iterator();
            System.out.println("\t \t INSET:");
            while(it.hasNext()){
                CfgNode node =it.next();
                System.out.println("\t \t \t FOR NODE: "+ node);
                System.out.print("\t \t \t \t \t \tDEF's : " );
                for(CfgNode tempNode:inRD.get(node)){
                    System.out.print(tempNode +" \t");
                }
                System.out.println("\n");
            }
            System.out.println("\n\t \t OUTSET:");
            it = outRD.keySet().iterator();
            while(it.hasNext()){
                CfgNode node =it.next();
                System.out.println("\t \t \t FOR NODE: "+ node);
                System.out.print("\t \t \t \t \t \t  DEF's : " );
                for(CfgNode tempNode:outRD.get(node)){
                    System.out.print(tempNode +" \t");
                }
                System.out.println("\n");
            }
            System.out.println("\n");
    }
}