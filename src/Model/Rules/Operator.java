package Model.Rules;

import Model.Level;

public abstract class Operator extends RuleWord {

    /**
     * Может ли оператор составить правило между двумя операндами
     */
    public abstract boolean canCreateRule(Operand left, Operand right);

    /**
     * Выполнить действие оператора.
     * Например IS - либо присвоить свойство объекту,
     *               либо превратить один объект в другой
     */
    public abstract void executeRule(Level level, Operand left, Operand right);

    @Override
    public boolean isOperator(){
        return true;
    }
}
