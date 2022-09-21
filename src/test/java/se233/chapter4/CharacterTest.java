package se233.chapter4;
import javafx.embed.swing.JFXPanel;
import javafx.scene.input.KeyCode;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals ;
import static org.junit.Assert.assertTrue;
import se233.chapter4.controller.DrawingLoop;
import se233.chapter4.controller.GameLoop;
import se233.chapter4.model.Character ;
import se233.chapter4.view.Platform;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public  class CharacterTest {
    private Character floatingCharacter ;
    private ArrayList<Character> characterListUnderTest ;
    private Platform platformUnderTest ;
    private GameLoop gameLoopUnderTest ;
    private DrawingLoop drawingLoopUnderTest ;
    private Method updateMethod, redrawMethod ;

    @Before
    public void setup() {
        JFXPanel jfxPanel = new JFXPanel() ;
        floatingCharacter = new Character(30,30,0,0, KeyCode.A , KeyCode.D,KeyCode.W, "MarioSheet",1,1,7,17) ;

        characterListUnderTest = new ArrayList<Character>() ;
        characterListUnderTest.add(floatingCharacter) ;
        platformUnderTest = new Platform() ;
        gameLoopUnderTest = new GameLoop(platformUnderTest) ;
        drawingLoopUnderTest = new DrawingLoop(platformUnderTest) ;

        try{
            updateMethod = GameLoop.class.getDeclaredMethod("update", ArrayList.class) ;
            redrawMethod = DrawingLoop.class.getDeclaredMethod("paint", ArrayList.class) ;
            updateMethod.setAccessible(true);
            redrawMethod.setAccessible(true);
        }catch (NoSuchMethodException e){
            e.printStackTrace();
            updateMethod = null ;
            redrawMethod = null ;
        }

    }
    @Test
    public void characterInitialValueShouldMatchConstructorArguments() throws IllegalAccessException , InvocationTargetException, NoSuchFieldException {

        assertEquals("Initial x", 30 , floatingCharacter.getX(), 0);
        assertEquals("Initial Y", 30 , floatingCharacter.getY() , 0);
        assertEquals("Offset x" , 0, floatingCharacter.getOffSetX(), 0.0);
        assertEquals("Offset Y",0,floatingCharacter.getOffSetY(), 0.0);
        assertEquals("Left key", KeyCode.A , floatingCharacter.getLeftKey());
        assertEquals("Right key", KeyCode.D, floatingCharacter.getRightKey());
        assertEquals("Up key", KeyCode.W, floatingCharacter.getUpKey());

        Character characterUnderTest = characterListUnderTest.get(0);
        int startX = characterUnderTest.getX() ;
        platformUnderTest.getKeys().add(KeyCode.A);
        updateMethod.invoke(gameLoopUnderTest, characterListUnderTest) ;
        redrawMethod.invoke(drawingLoopUnderTest, characterListUnderTest) ;
        Field isMoveLeft = characterUnderTest.getClass().getDeclaredField("isMovingLeft") ;
        isMoveLeft.setAccessible(true);

        assertTrue("Controller: LeftKey pressing is acknowledged", platformUnderTest.getKeys().isPressed(KeyCode.A));
        assertTrue("Model: Character moving left state is set" , isMoveLeft.getBoolean(characterUnderTest));
        assertTrue("View: Character is moving left", characterUnderTest.getX() < startX);
    }
}