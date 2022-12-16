public class VoidNode extends CfgNode{

    @Override
    public String toString() {

        String ret= "VOID NODE" + " from ";
        for(int i=0;i< inNodes.size();++i){
            ret+= " "+inNodes.get(i).toString();
        }
        return ret;
    }
}
