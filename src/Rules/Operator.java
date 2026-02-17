package Rules;

import Core.*;

public abstract class Operator extends RuleText {

    public abstract boolean canInteract(Operand left, Operand right);
    public abstract void action(Level level, Operand left, Operand right);
}
