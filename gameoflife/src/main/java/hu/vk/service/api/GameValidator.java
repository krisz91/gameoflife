package hu.vk.service.api;

import hu.vk.model.Game;

/**
 * Created by vargakrisztian on 2017. 02. 01..
 */
public interface GameValidator {

    boolean isValidName(Game game);
}
