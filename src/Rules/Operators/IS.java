package Rules.Operators;

import Rules.Operand;
import Rules.Operator;

public class IS extends Operator {

    public IS(){
        _word = "IS";
    }

    @Override
    public boolean canInteract(Operand left, Operand right) {
        return false;
    }
}
