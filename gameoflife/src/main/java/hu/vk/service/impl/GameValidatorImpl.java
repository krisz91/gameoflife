package hu.vk.service.impl;

import hu.vk.model.Game;
import hu.vk.repository.GameRepository;
import hu.vk.service.api.GameValidator;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by vargakrisztian on 2017. 02. 01..
 */
@Component
public class GameValidatorImpl implements GameValidator {

    private final GameRepository gameRepository;

    @Autowired
    public GameValidatorImpl(GameRepository gameRepository) {
        Assert.notNull(gameRepository, "GameRepository must be not null");
        this.gameRepository = gameRepository;
    }

    @Override
    public boolean isValidName(Game game) {


        List<Game> games = gameRepository.findByName(game.getName());


        if (CollectionUtils.isEmpty(games)) {
            return true;
        }

        return false;
    }
}
