import Model.Level;
import Model.GameObjects.*;
import Model.Rules.Features.*;
import Model.Rules.Operators.IS;
import Model.Rules.*;
import org.junit.Test;

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

    @Test
    public void BabaIsMovingTest(){
        Level level = createMinimalLevel();
        Subject baba = new Subject("BABA", level, new Position(0, 1));
        level.processStep(Direction.STAY);

        level.processStep(Direction.RIGHT);

        Position expectedPosition = new Position(1, 1);

        assertEquals(expectedPosition, baba.getPosition());
    }

    @Test
    public void BabaDontMovingOutOfRange(){
        Level level = createMinimalLevel();
        Subject baba = new Subject("BABA", level, new Position(0, 1));
        level.processStep(Direction.STAY);

        level.processStep(Direction.LEFT);

        Position expectedPosition = new Position(0, 1);

        assertEquals(expectedPosition, baba.getPosition());
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

        assertEquals(expectedBabaPosition, baba.getPosition());
        assertEquals(expectedRockPosition, rock.getPosition());
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

        assertEquals(expectedBabaPosition, baba.getPosition());
        assertEquals(expectedRock1Position, rock1.getPosition());
        assertEquals(expectedRock2Position, rock2.getPosition());
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

        assertEquals(expectedBabaPosition, baba.getPosition());
        assertEquals(expectedWallPosition, wall.getPosition());
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

        assertEquals(expectedBabaPosition, baba.getPosition());
        assertEquals(expectedRockPosition, rock.getPosition());
        assertEquals(expectedWallPosition, wall.getPosition());
    }
}