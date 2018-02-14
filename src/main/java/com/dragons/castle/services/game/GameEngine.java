package com.dragons.castle.services.game;

import com.dragons.castle.repository.GameLogger;
import com.dragons.castle.repository.model.GameLog;
import com.dragons.castle.services.game.model.Game;
import com.dragons.castle.services.game.model.Result;
import com.dragons.castle.services.game.model.Solution;
import com.dragons.castle.services.weather.WeatherService;
import com.dragons.castle.services.weather.model.WeatherReport;
import com.dragons.castle.strategies.FightStrategyRegistry;
import io.reactivex.Observable;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Game engine
 * </br>
 * Responsible for game execution
 */
@Slf4j
public class GameEngine {

    private static final int RETRY_TIMES = 2;
    private static final int TIMEOUT_TIME = 2;
    private static final int BURST_TIME = 2;

    private final GameService gameService;
    private final WeatherService weatherService;
    private final GameLogger gameLogger;
    private final FightStrategyRegistry fightRegistry;

    public GameEngine(GameService gameService, WeatherService weatherService,
                      GameLogger gameLogger, FightStrategyRegistry fightRegistry) {
        this.gameService = gameService;
        this.weatherService = weatherService;
        this.gameLogger = gameLogger;
        this.fightRegistry = fightRegistry;
    }

    /**
     * Plays the game number of times
     *
     * @param times number of times to play
     * @return observable, which produces game logs
     */
    public Observable<GameLog> play(int times) {
        return Observable.intervalRange(0, times, 0, BURST_TIME, TimeUnit.SECONDS)
            .flatMapSingle(aLong -> beginGame()
                .doOnSuccess(game -> log.info(String.valueOf(game)))
                .flatMap(game -> getWeatherReportFor(game)
                    .doOnSuccess(report -> log.info(String.valueOf(report)))
                    .map(game::addWeatherReport))
                .flatMap(game -> fightRegistry.getFightStrategy(game.getWeatherReport())
                    .createDragonForBattle(game.getKnight())
                        .map(dragon -> new Solution(dragon))
                        .defaultIfEmpty(new Solution())
                        .flatMapSingle(solution -> solveGame(game, solution)
                            .map(gameResult ->
                                new GameLog(game, solution.getDragon(), gameResult)))
                        .doOnSuccess(gameLogger::write)
                        .doOnSuccess(gameLog -> log.info(String.valueOf(gameLog)))
                )
            );
    }

    private Single<Game> beginGame() {
        return addTimeoutRetry(gameService.beginGame());
    }

    private Single<Result> solveGame(Game game, Solution solution) {
        return addTimeoutRetry(gameService.solve(game.getGameId(), solution));
    }

    private Single<WeatherReport> getWeatherReportFor(Game game) {
        return addTimeoutRetry(
                    weatherService.getWeatherReportFor(game.getGameId()),
                    this::defaultWeather);
    }

    private WeatherReport defaultWeather(Throwable throwable) {
        log.error("Weather report not found, switching to default weather!", throwable);
        return new WeatherReport("DEFAULT");
    }

    private <T> Single<T> addTimeoutRetry(Single<T> single) {
        return single
            .timeout(TIMEOUT_TIME, TimeUnit.SECONDS)
            .doOnError(throwable -> log.error("Service time outed!", throwable))
            .retry(RETRY_TIMES);
    }

    private <T> Single<T> addTimeoutRetry(Single<T> single,
                                          Function<Throwable, T> resumeFunction) {
        return single
            .timeout(TIMEOUT_TIME, TimeUnit.SECONDS)
            .doOnError(throwable -> log.error("Service time outed!", throwable))
            .retry(RETRY_TIMES)
            .onErrorReturn(throwable -> resumeFunction.apply(throwable));
    }
}
