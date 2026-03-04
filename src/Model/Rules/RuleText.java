package Model.Rules;

public abstract class RuleText {
    protected String word;

    public String getText(){
        return word;
    }
    public boolean isOperand(){
        return false;
    }

    public boolean isOperator(){
        return false;
    }

    public boolean isSubjectName(){
        return false;
    }

    public boolean isFeature(){
        return false;
    }
}
