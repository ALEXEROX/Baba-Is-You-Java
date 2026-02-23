package Rules;

import GameObjects.Position;

public abstract class RuleText {
    protected String _word;

    public String getText(){
        return _word;
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
