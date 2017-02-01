package hu.vk.service.impl;

import hu.vk.model.Game;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Created by vargakrisztian on 2017. 02. 01..
 */
@RunWith(MockitoJUnitRunner.class)
public class GenerationCalculatorServiceImplTest {


    @InjectMocks
    private GenerationCalculatorServiceImpl generationCalculatorService;


    @Test
    public void testNeighbourCount() throws Exception {


        Game game = new Game();

        game.addLivePoint(5, 5);

        game.addLivePoint(4, 5);

        game.addLivePoint(3, 5);

        Assert.assertEquals(generationCalculatorService.getNeighbourCount(game, 4, 5), 2);
    }
}