package Rules;

public abstract class Operator extends RuleText {
    public abstract boolean canInteract(Operand left, Operand right);
}
