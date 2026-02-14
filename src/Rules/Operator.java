package Rules;

import GameObjects.Subject;

public abstract class Operator extends RuleText {
    public abstract boolean canInteract(Operand left, Operand right);
    public abstract void action(Subject subject, Operand right);
}
