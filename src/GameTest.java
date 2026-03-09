import Model.Level;
import Model.GameObjects.*;
import Model.Rules.Features.*;
import Model.Rules.Operators.IS;
import Model.Rules.*;
import View.Condition;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Level createMinimalLevel(){
        Level level = new Level(3, 2);
        new TextBlock(new SubjectName("BABA"), level, new Position(0, 0));
        new TextBlock(new IS(), level, new Position(1, 0));
        new TextBlock(new YOU(), level, new Position(2, 0));

        return level;
    }

    private Level createPUSHAndSTOPLevel(){
        Level level = new Level(9, 2);
        //BABA IS YOU
        new TextBlock(new SubjectName("BABA"), level, new Position(0, 0));
        new TextBlock(new IS(), level, new Position(1, 0));
        new TextBlock(new YOU(), level, new Position(2, 0));
        //ROCK IS PUSH
        new TextBlock(new SubjectName("ROCK"), level, new Position(3, 0));
        new TextBlock(new IS(), level, new Position(4, 0));
        new TextBlock(new PUSH(), level, new Position(5 , 0));
        //WALL IS STOP
        new TextBlock(new SubjectName("WALL"), level, new Position(6, 0));
        new TextBlock(new IS(), level, new Position(7, 0));
        new TextBlock(new STOP(), level, new Position(8, 0));

        return level;
    }

    private Level createDEFEATLevel(){
        Level level = new Level(9, 2);
        //BABA IS YOU
        new TextBlock(new SubjectName("BABA"), level, new Position(0, 0));
        new TextBlock(new IS(), level, new Position(1, 0));
        new TextBlock(new YOU(), level, new Position(2, 0));
        //ROCK IS PUSH
        new TextBlock(new SubjectName("ROCK"), level, new Position(3, 0));
        new TextBlock(new IS(), level, new Position(4, 0));
        new TextBlock(new PUSH(), level, new Position(5 , 0));
        //WALL IS DEFEAT
        new TextBlock(new SubjectName("WALL"), level, new Position(6, 0));
        new TextBlock(new IS(), level, new Position(7, 0));
        new TextBlock(new DEFEAT(), level, new Position(8, 0));

        return level;
    }

    private Level createOPENandSHUTLevel(){
        Level level = new Level(9, 3);
        //BABA IS YOU
        new TextBlock(new SubjectName("BABA"), level, new Position(0, 0));
        new TextBlock(new IS(), level, new Position(1, 0));
        new TextBlock(new YOU(), level, new Position(2, 0));
        //KEY IS PUSH
        new TextBlock(new SubjectName("KEY"), level, new Position(3, 0));
        new TextBlock(new IS(), level, new Position(4, 0));
        new TextBlock(new PUSH(), level, new Position(5 , 0));
        //KEY IS OPEN
        new TextBlock(new SubjectName("KEY"), level, new Position(6, 0));
        new TextBlock(new IS(), level, new Position(7, 0));
        new TextBlock(new OPEN(), level, new Position(8, 0));
        //DOOR IS SHUT
        new TextBlock(new SubjectName("DOOR"), level, new Position(0, 2));
        new TextBlock(new IS(), level, new Position(1, 2));
        new TextBlock(new SHUT(), level, new Position(2, 2));

        return level;
    }

    private Level createLevelForTransformation(){
        Level level = new Level(6, 3);
        //BABA IS YOU
        new TextBlock(new SubjectName("BABA"), level, new Position(0, 0));
        new TextBlock(new IS(), level, new Position(1, 0));
        new TextBlock(new YOU(), level, new Position(2, 0));
        //WALL IS BABA
        new TextBlock(new SubjectName("WALL"), level, new Position(4, 0));
        new TextBlock(new IS(), level, new Position(3, 1));
        new TextBlock(new SubjectName("BABA"), level, new Position(4 , 2));

        return level;
    }

    @Test
    public void BabaIsMovingTest(){
        Level level = createMinimalLevel();
        Subject baba = new Subject("BABA", level, new Position(0, 1));
        level.processStep(Direction.STAY);

        level.processStep(Direction.RIGHT);

        Position expectedPosition = new Position(1, 1);
        Condition expectedCondition = Condition.RUNNING;

        assertEquals(expectedPosition, baba.getPosition());
        assertEquals(expectedCondition, level.checkSuccess());
    }

    @Test
    public void BabaDontMovingOutOfRange(){
        Level level = createMinimalLevel();
        Subject baba = new Subject("BABA", level, new Position(0, 1));
        level.processStep(Direction.STAY);

        level.processStep(Direction.LEFT);

        Position expectedPosition = new Position(0, 1);
        Condition expectedCondition = Condition.RUNNING;

        assertEquals(expectedPosition, baba.getPosition());
        assertEquals(expectedCondition, level.checkSuccess());
    }

    @Test
    public void rockIsPUSH(){
        Level level = createPUSHAndSTOPLevel();
        Subject baba = new Subject("BABA", level,new Position(0, 1));
        Subject rock = new Subject("ROCK", level, new Position(1, 1));
        level.processStep(Direction.STAY);

        level.processStep(Direction.RIGHT);

        Position expectedBabaPosition = new Position(1, 1);
        Position expectedRockPosition = new Position(2, 1);
        Condition expectedCondition = Condition.RUNNING;

        assertEquals(expectedBabaPosition, baba.getPosition());
        assertEquals(expectedRockPosition, rock.getPosition());
        assertEquals(expectedCondition, level.checkSuccess());
    }

    @Test
    public void twoRocksIsPUSH(){
        Level level = createPUSHAndSTOPLevel();
        Subject baba = new Subject("BABA", level,new Position(0, 1));
        Subject rock1 = new Subject("ROCK", level, new Position(1, 1));
        Subject rock2 = new Subject("ROCK", level, new Position(2, 1));
        level.processStep(Direction.STAY);

        level.processStep(Direction.RIGHT);

        Position expectedBabaPosition = new Position(1, 1);
        Position expectedRock1Position = new Position(2, 1);
        Position expectedRock2Position = new Position(3, 1);
        Condition expectedCondition = Condition.RUNNING;

        assertEquals(expectedBabaPosition, baba.getPosition());
        assertEquals(expectedRock1Position, rock1.getPosition());
        assertEquals(expectedRock2Position, rock2.getPosition());
        assertEquals(expectedCondition, level.checkSuccess());
    }

    @Test
    public void wallIsSTOP(){
        Level level = createPUSHAndSTOPLevel();
        Subject baba = new Subject("BABA", level,new Position(0, 1));
        Subject wall = new Subject("WALL", level, new Position(1, 1));
        level.processStep(Direction.STAY);

        level.processStep(Direction.RIGHT);

        Position expectedBabaPosition = new Position(0, 1);
        Position expectedWallPosition = new Position(1, 1);
        Condition expectedCondition = Condition.RUNNING;

        assertEquals(expectedBabaPosition, baba.getPosition());
        assertEquals(expectedWallPosition, wall.getPosition());
        assertEquals(expectedCondition, level.checkSuccess());
    }

    @Test
    public void rockDontPushWall(){
        Level level = createPUSHAndSTOPLevel();
        Subject baba = new Subject("BABA", level,new Position(0, 1));
        Subject rock = new Subject("ROCK", level, new Position(1, 1));
        Subject wall = new Subject("WALL", level, new Position(2, 1));
        level.processStep(Direction.STAY);

        level.processStep(Direction.RIGHT);

        Position expectedBabaPosition = new Position(0, 1);
        Position expectedRockPosition = new Position(1, 1);
        Position expectedWallPosition = new Position(2, 1);
        Condition expectedCondition = Condition.RUNNING;

        assertEquals(expectedBabaPosition, baba.getPosition());
        assertEquals(expectedRockPosition, rock.getPosition());
        assertEquals(expectedWallPosition, wall.getPosition());
        assertEquals(expectedCondition, level.checkSuccess());
    }

    @Test
    public void twoRocksDontPushWall(){
        Level level = createPUSHAndSTOPLevel();
        Subject baba = new Subject("BABA", level,new Position(0, 1));
        Subject rock1 = new Subject("ROCK", level, new Position(1, 1));
        Subject rock2 = new Subject("ROCK", level, new Position(2, 1));
        Subject wall = new Subject("WALL", level, new Position(3, 1));
        level.processStep(Direction.STAY);

        level.processStep(Direction.RIGHT);

        Position expectedBabaPosition = new Position(0, 1);
        Position expectedRock1Position = new Position(1, 1);
        Position expectedRock2Position = new Position(2, 1);
        Position expectedWallPosition = new Position(3, 1);
        Condition expectedCondition = Condition.RUNNING;

        assertEquals(expectedBabaPosition, baba.getPosition());
        assertEquals(expectedRock1Position, rock1.getPosition());
        assertEquals(expectedRock2Position, rock2.getPosition());
        assertEquals(expectedWallPosition, wall.getPosition());
        assertEquals(expectedCondition, level.checkSuccess());
    }

    @Test
    public void wallIsDefeat(){
        Level level = createDEFEATLevel();
        Subject baba = new Subject("BABA", level,new Position(0, 1));
        Subject wall = new Subject("WALL", level, new Position(1, 1));
        level.processStep(Direction.STAY);

        level.processStep(Direction.RIGHT);

        Position expectedWallPosition = new Position(1, 1);
        Condition expectedCondition = Condition.LOSE;

        assertEquals(0, level.getCell(new Position(0, 1)).size() ); // В первой ячейке никого
        assertEquals(1, level.getCell(new Position(1, 1)).size() ); // Во второй ячейке только один объект
        assertEquals(expectedWallPosition, wall.getPosition()); // и это стена
        assertEquals(expectedCondition, level.checkSuccess());
    }

    @Test
    public void wallDontDefeatRock(){
        Level level = createDEFEATLevel();
        Subject baba = new Subject("BABA", level,new Position(0, 1));
        Subject rock = new Subject("ROCK", level,new Position(1, 1));
        Subject wall = new Subject("WALL", level, new Position(2, 1));
        level.processStep(Direction.STAY);

        level.processStep(Direction.RIGHT);

        Position expectedBabaPosition = new Position(1, 1);
        Position expectedRockPosition = new Position(2, 1);
        Position expectedWallPosition = new Position(2, 1);
        Condition expectedCondition = Condition.RUNNING;

        assertEquals(expectedBabaPosition, baba.getPosition());
        assertEquals(expectedRockPosition, rock.getPosition());
        assertEquals(expectedWallPosition, wall.getPosition());
        assertEquals(expectedCondition, level.checkSuccess());
    }

    @Test
    public void shutDontEnterBaba(){
        Level level = createOPENandSHUTLevel();
        Subject baba = new Subject("BABA", level,new Position(0, 1));
        Subject door = new Subject("DOOR", level, new Position(1, 1));
        level.processStep(Direction.STAY);

        level.processStep(Direction.RIGHT);

        Position expectedBabaPosition = new Position(0, 1);
        Position expectedDoorPosition = new Position(1, 1);
        Condition expectedCondition = Condition.RUNNING;

        assertEquals(expectedBabaPosition, baba.getPosition());
        assertEquals(expectedDoorPosition, door.getPosition());
        assertEquals(expectedCondition, level.checkSuccess());
    }

    @Test
    public void keyOpenDoor(){
        Level level = createOPENandSHUTLevel();
        Subject baba = new Subject("BABA", level,new Position(0, 1));
        Subject key = new Subject("KEY", level,new Position(1, 1));
        Subject door = new Subject("DOOR", level, new Position(2, 1));
        level.processStep(Direction.STAY);

        level.processStep(Direction.RIGHT);

        Position expectedBabaPosition = new Position(1, 1);
        Condition expectedCondition = Condition.RUNNING;

        assertEquals(1, level.getCell(new Position(1, 1)).size());
        assertEquals(0, level.getCell(new Position(2, 1)).size());
        assertEquals(expectedBabaPosition, baba.getPosition());
        assertEquals(expectedCondition, level.checkSuccess());
    }

    @Test
    public void wallIsBABA(){
        Level level = createLevelForTransformation();
        Subject baba = new Subject("BABA", level, new Position(2, 1));
        Subject wall = new Subject("WALL", level, new Position(2, 2));
        level.processStep(Direction.STAY);

        level.processStep(Direction.RIGHT);
        List<GameObject> cell = level.getCell(new Position(2, 2));

        String expectedGameObject = "BABA ( 2 ; 2 )";
        Condition expectedCondition = Condition.RUNNING;

        assertEquals(1, cell.size());
        assertEquals(expectedGameObject, cell.getFirst().toString());
        assertEquals(expectedCondition, level.checkSuccess());
    }

    @Test
    public void wallsIsBABA(){
        Level level = createLevelForTransformation();
        Subject baba = new Subject("BABA", level, new Position(2, 1));
        Subject wall1 = new Subject("WALL", level, new Position(1, 2));
        Subject wall2 = new Subject("WALL", level, new Position(2, 2));
        level.processStep(Direction.STAY);

        level.processStep(Direction.RIGHT);
        List<GameObject> cell1 = level.getCell(new Position(1, 2));
        List<GameObject> cell2 = level.getCell(new Position(2, 2));

        String expectedGameObject1 = "BABA ( 1 ; 2 )";
        String expectedGameObject2 = "BABA ( 2 ; 2 )";
        Condition expectedCondition = Condition.RUNNING;

        assertEquals(1, cell1.size());
        assertEquals(1, cell2.size());
        assertEquals(expectedGameObject1, cell1.getFirst().toString());
        assertEquals(expectedGameObject2, cell2.getFirst().toString());
        assertEquals(expectedCondition, level.checkSuccess());
    }

    @Test
    public void win(){
        Level level = new Level(6, 2);
        //BABA IS YOU
        new TextBlock(new SubjectName("BABA"), level, new Position(0, 0));
        new TextBlock(new IS(), level, new Position(1, 0));
        new TextBlock(new YOU(), level, new Position(2, 0));
        //FLAG IS WIN
        new TextBlock(new SubjectName("FLAG"), level, new Position(3, 0));
        new TextBlock(new IS(), level, new Position(4, 0));
        new TextBlock(new WIN(), level, new Position(5, 0));
        //BABA and FLAG
        new Subject("BABA", level, new Position(0, 1));
        new Subject("FLAG", level, new Position(1, 1));
        level.processStep(Direction.STAY);

        level.processStep(Direction.RIGHT);

        List<String> cell = new ArrayList<>();
        level.getCell(new Position(1, 1)).forEach(x -> cell.add(x.toString()));
        String expectedBaba = "BABA ( 1 ; 1 )";
        String expectedFlag = "FLAG ( 1 ; 1 )";
        Condition expectedCondition = Condition.WIN;

        assertEquals(2, cell.size());
        assertTrue(cell.contains(expectedBaba));
        assertTrue(cell.contains(expectedFlag));
        assertEquals(expectedCondition, level.checkSuccess());
    }

    @Test
    public void lose(){
        Level level = new Level(6, 2);
        //BABA IS YOU
        new TextBlock(new SubjectName("BABA"), level, new Position(0, 0));
        new TextBlock(new IS(), level, new Position(1, 0));
        new TextBlock(new YOU(), level, new Position(2, 0));
        //FLAG IS DEFEAT
        new TextBlock(new SubjectName("FLAG"), level, new Position(3, 0));
        new TextBlock(new IS(), level, new Position(4, 0));
        new TextBlock(new DEFEAT(), level, new Position(5, 0));
        //BABA and FLAG
        new Subject("BABA", level, new Position(0, 1));
        new Subject("FLAG", level, new Position(1, 1));
        level.processStep(Direction.STAY);

        level.processStep(Direction.RIGHT);

        List<String> cell = new ArrayList<>();
        level.getCell(new Position(1, 1)).forEach(x -> cell.add(x.toString()));
        String expectedFlag = "FLAG ( 1 ; 1 )";
        Condition expectedCondition = Condition.LOSE;

        assertEquals(1, cell.size());
        assertTrue(cell.contains(expectedFlag));
        assertEquals(expectedCondition, level.checkSuccess());
    }
}