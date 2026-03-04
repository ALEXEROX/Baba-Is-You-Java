package Model.Rules;

public class SubjectName extends Operand{

    public SubjectName(String word){
        this.word = word;
    }

    @Override
    public boolean isSubjectName(){
        return true;
    }
}
