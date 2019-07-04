package app;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MainControllerTest {

    MainController mainController;

    @Before
    public void setUp() {
        mainController = new MainController();
    }

    @Test
    public void WhenStartItReturnsCorrectViewName() {
        assertEquals("forecast", mainController.mainPage());
    }
}