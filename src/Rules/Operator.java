package Rules;

import Core.*;

public abstract class Operator extends RuleText {

    /**
     * Может ли оператор составить правило между двумя операндами
     */
    public abstract boolean canInteract(Operand left, Operand right);

    /**
     * Выполнить действие оператора.
     * Например IS - либо присвоить свойство объекту,
     *               либо превратить один объект в другой
     */
    public abstract void action(Level level, Operand left, Operand right);
}
