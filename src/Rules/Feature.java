package Rules;

import GameObjects.*;

public abstract class Feature extends Operand{

    /**
     * Выполнить действие над объектом
     */
    public abstract void action(GameObject subject, Direction direction);

    /**
     * Выполнить взаимодействие между двумя объектами
     * @param first Исходный объект, содержащий свойства
     * @param second Объкт, который собирается войти в ячейку с исходным объектом
     */
    public abstract  void interaction(GameObject first, GameObject second, Direction direction);

    @Override
    public boolean isFeature(){
        return true;
    }
}
