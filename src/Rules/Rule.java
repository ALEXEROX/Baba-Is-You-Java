package Rules;

public class Rule {
    private Operand _leftPart;
    private Operator _operator;
    private Operand _rightPart;

    public Rule(Operand left, Operator op, Operand right){
        _leftPart = left; _operator = op; _rightPart = right;
    }
}
