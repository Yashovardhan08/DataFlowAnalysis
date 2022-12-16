// package dfa;
import java.util.HashSet;
import java.util.Set;

public class SetOperations {
    public static <T> void Union(Set<T> a,Set<T> b){
        for(T t:b)
            a.add(t);
    }
    public static <T> HashSet<T> Negation(Set<T> a,Set<T> b){// a-b
        HashSet<T> ret = new HashSet<T>();
        for(T t: a){
            if(!b.contains(t))ret.add(t);
        }
        return ret;
    }
}
