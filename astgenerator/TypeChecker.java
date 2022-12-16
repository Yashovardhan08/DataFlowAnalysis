import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;


class Pair<T1,T2>{
    public T1 first;
    public T2 second;
    Pair(T1 f,T2 s){
        this.first = f;
        this.second = s;
    }
}

class FunctionEntry{
    public String id;
    public Type rType;
    public ArrayList<Pair<String,Type>> argList;
    FunctionEntry(String id, Type rtype, DeclarationList d){
        this.id = id;
        this.rType = rtype;
        this.argList = new ArrayList<Pair<String,Type>>();
        for(Declaration dec: d.dList){
            this.argList.add(new Pair<String,Type>(dec.varname,dec.type));
        }
    }
}

public class TypeChecker {
    public Boolean checkingFunction=false;
    public Type functionType= null;
    public Program program;
    public HashMap<String,LinkedList<Type>> typeEnv;
    public HashMap<String,FunctionEntry> funcEnv; // id -> return type arguement [type and name]
    
    TypeChecker(Program program){
        this.typeEnv = new HashMap<String,LinkedList<Type>>();
        this.funcEnv = new HashMap<String,FunctionEntry>();
        this.program = program;
    }

    public void reverseIlist(InstrList i){
        Collections.reverse(i.iList);
    }

    public Type TypeCheck() throws Exception {
        reverseIlist(program.instrList);

        // For Recursion 
        for(Instr i: program.instrList.iList){
            if(i instanceof FunctionDeclaration){
                FunctionDeclaration fd = (FunctionDeclaration)i;
                FunctionEntry fEntry = new FunctionEntry(fd.id.name, fd.type,fd.dl);
                this.funcEnv.put(fd.id.name, fEntry);
            }
        }

        Boolean returnFound = false;
        Type rType = new VoidType();
        for(Instr i: program.instrList.iList){
            if(i instanceof FunctionDeclaration){
                TypeCheckFunctionDeclaration((FunctionDeclaration)i);
            }
            else if(i instanceof Expr){
                TypeCheckExpr((Expr)i);
            }
            else if(i instanceof Declaration){
                TypeCheckDeclaration((Declaration)i);
            }
            else if(i instanceof Assign){
                TypeCheckAssignment((Assign)i);
            }
            // else if(i instanceof RetExpr){
            //     TypeCheckReturn((RetExpr)i);
            // }
            else throw new Exception("\nInstruction :\n\t"+i.toString()+"\t\ndid not type check(instruction Not Found in Abstract syntax)\n");
        }
        return rType;
    }

    public Type TypeCheckFunctionDeclaration(FunctionDeclaration fDeclaration) throws Exception{
        checkingFunction = true;
        functionType = fDeclaration.type;

        for(Declaration d:fDeclaration.dl.dList){
            TypeCheckDeclaration(d);
        }

        Type blockType = TypeCheckBlock(fDeclaration.bl);
        if(funcEnv.get(fDeclaration.id.name).rType.getClass() != blockType.getClass())
            throw new Exception("Return type of Function "+fDeclaration.id.name+" and body do not match\n");

        for(Declaration d:fDeclaration.dl.dList){
            typeEnv.get(d.varname).removeLast();
        }
        checkingFunction = false;
        functionType = null;
        return new VoidType();
    }

    public Type TypeCheckExpr(Expr e) throws Exception{
        Type ret = null;
        if(e instanceof AddExpr){
            ret = TypeCheckAddExpr((AddExpr)e);
        }
        else if(e instanceof SubExpr){
            ret = TypeCheckSubExpr((SubExpr)e);
        }
        else if(e instanceof MulExpr){
            ret = TypeCheckMulExpr((MulExpr)e);
        }
        else if(e instanceof NegExpr){
            ret = TypeCheckNegExpr((NegExpr)e);
        }
        else if(e instanceof NumExpr){
            ret = TypeCheckNumExpr((NumExpr)e);
        }
        else if(e instanceof BoolConstExpr){
            ret = new BoolType();
        }
        else if(e instanceof IdExpr){
            ret = TypeCheckIdExpr((IdExpr)e);
        }
        else if(e instanceof EqualsBoolExpr){
            ret = TypeCheckEqualsBoolExpr((EqualsBoolExpr)e);
        }
        else if(e instanceof LEqualsBoolExpr){
            ret = TypeCheckLEqualsBoolExpr((LEqualsBoolExpr)e);
        }
        else if(e instanceof GEqualsBoolExpr){
            ret = TypeCheckGEqualsBoolExpr((GEqualsBoolExpr)e);
        }
        else if(e instanceof LesserBoolExpr){
            ret = TypeCheckLessersBoolExpr((LesserBoolExpr)e);
        }
        else if(e instanceof GreaterBoolExpr){
            ret = TypeCheckGreaterBoolExpr((GreaterBoolExpr)e);
        }
        else if(e instanceof FCall){
            ret = TypeCheckFunctionCall((FCall)e);
        }
        else if(e instanceof IfStatement){
            ret = TypeCheckIfStatement((IfStatement)e);
        }
        else if(e instanceof WhileExpr){
            ret = TypeCheckWhile((WhileExpr)e);
        }
        else throw new Exception("Expression:"+"\t"+ e.toString()+" \t"+" not in Abstract Syntax \n");
        
        return ret;
    }

    /*  INTEGER EXPRESSIONS */
    public Type TypeCheckAddExpr(AddExpr e) throws Exception{
        if((TypeCheckExpr(e.left)).getClass() != (TypeCheckExpr(e.right)).getClass()){
            throw new Exception("Add Expression :\n\t"+e.toString()+"\n did not typecheck\n");
        }
        return new IntType();
    }

    public Type TypeCheckSubExpr(SubExpr e) throws Exception{
        if((TypeCheckExpr(e.left)).getClass() != (TypeCheckExpr(e.right)).getClass()){
            throw new Exception("Sub Expression :\n\t"+e.toString()+"\n did not typecheck\n");
        }
        return new IntType();
    }

    public Type TypeCheckMulExpr(MulExpr e) throws Exception{
        if((TypeCheckExpr(e.left)).getClass() != (TypeCheckExpr(e.right)).getClass()){
            throw new Exception("Multiply Expression :\n\t"+e.toString()+"\n did not typecheck\n");
        }
        return new IntType();
    }

    public Type TypeCheckNegExpr(NegExpr e) throws Exception{
        return TypeCheckExpr(e.expr);
    }

    public Type TypeCheckNumExpr(NumExpr e) throws Exception{
        return new IntType();
    }

    public Type TypeCheckIdExpr(IdExpr e) throws Exception {
        if(typeEnv.get(e.name)== null && typeEnv.get(e.name).getLast()==null)
            throw new Exception("Identifier "+e.name +" not found \n");
        return typeEnv.get(e.name).getLast();
    }


    /*  BOOLEAN EXPRESSIONS */
    public Type TypeCheckEqualsBoolExpr(EqualsBoolExpr e) throws Exception{
        if(TypeCheckExpr(e.e1).getClass() != TypeCheckExpr(e.e2).getClass())
            throw new Exception("Boolean expr\n\t"+e.toString()+"\n did not type check");
        return new BoolType();
    }
    public Type TypeCheckLEqualsBoolExpr(LEqualsBoolExpr e) throws Exception{
        if(TypeCheckExpr(e.e1).getClass() != TypeCheckExpr(e.e2).getClass())
            throw new Exception("Boolean expr\n\t"+e.toString()+"\n did not type check");
        return new BoolType();
    }
    public Type TypeCheckGEqualsBoolExpr(GEqualsBoolExpr e) throws Exception{
        if(TypeCheckExpr(e.e1).getClass() != TypeCheckExpr(e.e2).getClass())
            throw new Exception("Boolean expr\n\t"+e.toString()+"\n did not type check");
        return new BoolType();
    }
    public Type TypeCheckLessersBoolExpr(LesserBoolExpr e) throws Exception{
        if(TypeCheckExpr(e.e1).getClass() != TypeCheckExpr(e.e2).getClass())
            throw new Exception("Boolean expr\n\t"+e.toString()+"\n did not type check");
        return new BoolType();
    }
    public Type TypeCheckGreaterBoolExpr(GreaterBoolExpr e) throws Exception{
        if(TypeCheckExpr(e.e1).getClass() != TypeCheckExpr(e.e2).getClass())
            throw new Exception("Boolean expr\n\t"+e.toString()+"\n did not type check");
        return new BoolType();
    }

    /* FUNCTION CALL */
    public Type TypeCheckFunctionCall(FCall f) throws Exception {
        // Arguement Expressions should type check
        int idx=0;
        for(Expr e: f.args.exprList){
            if(funcEnv.get(f.fname).argList.get(idx).second.getClass() != TypeCheckExpr(e).getClass())
                throw new Exception("Arguement Expression :\n\t"+e.toString()+"\n did not type check\n");
            idx++;
        }

        return funcEnv.get(f.fname).rType;
    }

    /* BLOCK */
    public Type TypeCheckBlock(Block b) throws Exception{
        Type ret = null;
        reverseIlist(b.ilist);

        for(Instr i: b.ilist.iList){
            if(i instanceof IfStatement){
                Type t = TypeCheckIfStatement((IfStatement)i);
                if(t instanceof IntType || t instanceof BoolType){
                    if(ret == null)ret = t;
                    else if(ret.getClass() != t.getClass())
                        throw new Exception("Return types do not match\n");
                }
            }
            else if(i instanceof WhileExpr){
                Type t = TypeCheckWhile((WhileExpr)i);
                if(t instanceof IntType || t instanceof BoolType){
                    if(ret == null)ret = t;
                    else if(ret.getClass() != t.getClass())
                        throw new Exception("Return types do not match\n");
                }
            }
            else if(i instanceof Expr){
                TypeCheckExpr((Expr)i);
            }
            else if(i instanceof Declaration){
                TypeCheckDeclaration((Declaration)i);
            }
            else if(i instanceof Assign){
                TypeCheckAssignment((Assign)i);
            }
            else if(i instanceof RetExpr){
                Type temp = TypeCheckReturn((RetExpr)i);
                if(ret==null){
                    ret=temp;
                }
                else if(temp.getClass() != ret.getClass()){
                    throw new Exception("Multiple return types in same block\n");
                }
            }
            else throw new Exception("\nInstruction :\n\t"+i.toString()+"\t\ndid not type check.\n");
        }

        // Remove all the declarations added to the type environment
        for(Instr i: b.ilist.iList){
            if(i instanceof Declaration){
                typeEnv.get(((Declaration)i).varname).removeLast();
            }
        }

        return ret==null? new VoidType():ret;
    }

    /* DECLARATION */
    public Type TypeCheckDeclaration(Declaration d) throws Exception{
        if(typeEnv.get(d.varname)==null){
            typeEnv.put(d.varname,new LinkedList<>());
            typeEnv.get(d.varname).addLast(d.type);
        }
        else typeEnv.get(d.varname).addLast(d.type);
        return new VoidType();
    }

    /* ASSIGNMENT */
    public Type TypeCheckAssignment(Assign a) throws Exception{
        if(typeEnv.get(a.id.name)==null)
            throw new Exception("Identifier: "+ a.id.name +" not declared in scope\n");
        Type idType = typeEnv.get(a.id.name).getLast();
        Type exprType = TypeCheckExpr(a.expr);
        if(idType.getClass() != exprType.getClass())
            throw new Exception("Identifier and Expression not of same type in "+a.toString()+"\n");
        return new VoidType();
    }

    /* RETURN */
    public Type TypeCheckReturn(RetExpr r) throws Exception {
        Type t= TypeCheckExpr(r.expr);
        if(checkingFunction && functionType.getClass()!=t.getClass())
            throw new Exception("Return type of function and return statement do not match\n");
        return t;
    }

    /* IF */
    public Type TypeCheckIfStatement(IfStatement i) throws Exception{
        Type expType = TypeCheckExpr(i.e1);
        Type b1Type = TypeCheckBlock(i.b1);
        Type b2Type = new VoidType();
        if(i.b2!=null)TypeCheckBlock(i.b2);
        if(expType instanceof BoolType){
            if(b1Type instanceof VoidType)return b2Type;
            return b1Type;
        }
        else throw new Exception("Expression "+i.e1.toString() + " in IF statement does not type check to boolean type\n");
    }

    /* WHILE  */
    public Type TypeCheckWhile(WhileExpr w) throws Exception{
        Type expType = TypeCheckExpr(w.e);
        Type b1Type = TypeCheckBlock(w.b);
        if(expType instanceof BoolType){
            return b1Type;
        }
        else throw new Exception("Expression "+w.e.toString() + " in IF statement does not type check to boolean type\n");
    }
}
