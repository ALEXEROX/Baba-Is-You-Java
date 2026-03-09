package Model.Rules;

public abstract class RuleWord {
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

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof RuleWord ruleWord){
            return ruleWord.getText().equals(getText());
        }

        return false;
    }
}
