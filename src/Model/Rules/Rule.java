package Model.Rules;

import Model.Level;

public class Rule {
    private Operand _leftPart;
    private Operator _operator;
    private Operand _rightPart;

    public Rule(Operand left, Operator op, Operand right){
        _leftPart = left; _operator = op; _rightPart = right;
    }

    public void release(Level level){
        _operator.action(level, _leftPart, _rightPart);
    }
}
