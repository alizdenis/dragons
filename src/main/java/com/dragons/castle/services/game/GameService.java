package com.dragons.castle.services.game;

import com.dragons.castle.services.game.model.Game;
import com.dragons.castle.services.game.model.Result;
import com.dragons.castle.services.game.model.Solution;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Game service api
 */
public interface GameService {

    /**
     * Starts the game
     * @return initial game information
     */
    @GET("api/game")
    Single<Game> beginGame();

    /**
     * Sends game solution
     * @param gameId id of the game to solve
     * @param solution for the game
     * @return a game fight outcome with details
     */
    @PUT("api/game/{gameid}/solution")
    Single<Result> solve(@Path("gameid") Integer gameId, @Body Solution solution);
}