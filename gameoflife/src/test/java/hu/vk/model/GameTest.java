package hu.vk.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Created by vargakrisztian on 2017. 02. 01..
 */
@RunWith(MockitoJUnitRunner.class)
public class GameTest {

    @Test
    public void testContains() throws Exception {

        Game game = new Game();
        game.addLivePoint(1,1);
        game.addLivePoint(1,5);


        Assert.assertTrue(game.isLive(1,1));
        Assert.assertTrue(game.isLive(1,5));
    }
}