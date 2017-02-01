package hu.vk.service.impl;

import hu.vk.exception.ServiceException;
import hu.vk.model.Game;
import hu.vk.repository.GameRepository;
import hu.vk.service.api.GameService;
import hu.vk.service.api.GameValidator;
import hu.vk.service.api.dto.GetGameDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by vargakrisztian on 2017. 02. 01..
 */
@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;


    private final GameValidator gameValidator;


    @Autowired
    public GameServiceImpl(GameRepository gameRepository, GameValidator gameValidator) {
        Assert.notNull(gameRepository, "GameRepository must be not null");
        Assert.notNull(gameValidator, "GameValidator must be not null");
        this.gameRepository = gameRepository;
        this.gameValidator = gameValidator;
    }

    @Override
    public void storeGame(Game game) {

        try {
            gameRepository.save(game);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }


    }

    @Override
    public List<GetGameDto> getGameNames() {
        try {
            return gameRepository.findAll().stream().map(game -> {
                GetGameDto getGameDto = new GetGameDto();
                getGameDto.setId(game.getId());
                getGameDto.setName(game.getName());
                return getGameDto;
            }).collect(Collectors.toList());
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Game getGame(String id) {

        try {
            Game game = gameRepository.findOne(id);

            return game;
        } catch (final Exception e) {
            throw new ServiceException(e);
        }


    }
}
