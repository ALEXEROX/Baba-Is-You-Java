package Model.Rules;

import Model.Level;

public class Rule {
    private Operand leftPart;
    private Operator operator;
    private Operand rightPart;

    public Rule(Operand left, Operator op, Operand right){
        leftPart = left; operator = op; rightPart = right;
    }

    public void release(Level level){
        operator.executeRule(level, leftPart, rightPart);
    }
}
