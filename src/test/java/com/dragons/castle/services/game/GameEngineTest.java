package com.dragons.castle.services.game;

import com.dragons.castle.repository.GameLogger;
import com.dragons.castle.repository.model.GameLog;
import com.dragons.castle.services.game.model.Dragon;
import com.dragons.castle.services.game.model.Game;
import com.dragons.castle.services.game.model.Knight;
import com.dragons.castle.services.game.model.Result;
import com.dragons.castle.services.weather.WeatherService;
import com.dragons.castle.services.weather.model.WeatherReport;
import com.dragons.castle.strategies.FightStrategy;
import com.dragons.castle.strategies.FightStrategyRegistry;
import io.reactivex.Maybe;
import io.reactivex.Single;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class GameEngineTest {

    private GameEngine testObject;

    @Mock
    private GameService gameService;

    @Mock
    private WeatherService weatherService;

    @Mock
    private GameLogger gameLogger;

    @Mock
    private FightStrategyRegistry fightRegistry;

    @Mock
    private FightStrategy fightStrategy;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        testObject = new GameEngine(gameService, weatherService, gameLogger, fightRegistry);

        when(gameService.beginGame()).thenReturn(createMockedGame());
        when(weatherService.getWeatherReportFor(any())).thenReturn(createMockedReport("NMR"));
        when(fightRegistry.getFightStrategy(any())).thenReturn(fightStrategy);
        when(gameService.solve(any(), any())).thenReturn(createMockedResult());
        when(fightStrategy.createDragonForBattle(any())).thenReturn(createMockedDragon());
    }

    @Test
    public void shouldSuccessfullyPassFlow() {
        List<GameLog> logs = testObject.play(1).toList().blockingGet();

        assertGame(logs);
    }

    @Test
    public void shouldSwitchToEmptySolutionIfDragonIsNotPresent() {
        when(fightStrategy.createDragonForBattle(any())).thenReturn(Maybe.empty());

        List<GameLog> logs = testObject.play(1).toList().blockingGet();

        assertNotNull("Logs not null", logs);
        assertEquals("Logs contains 1 element", 1, logs.size());
        assertEquals("Game is won", "Victory", logs.get(0).getResult().getStatus());
        assertNotNull("Game object is present", logs.get(0).getGame());
        assertNull("Dragon object is not present", logs.get(0).getDragon());
    }

    @Test
    public void shouldSwitchIfWeatherServiceIsNotAvailable() {
        when(weatherService.getWeatherReportFor(eq(1))).thenReturn(Single.never());

        List<GameLog> logs = testObject.play(1).toList().blockingGet();

        assertGame(logs);
        assertEquals("DEFAULT", logs.get(0).getGame().getWeatherReport().getCode());
    }

    @Test
    public void shouldRetryOnWeatherReportTimeout() {
        when(weatherService.getWeatherReportFor(any()))
            .thenReturn(withDelay(() -> createMockedReport("NMR")));

        List<GameLog> logs = testObject.play(1).toList().blockingGet();

        assertGame(logs);
        assertEquals("NMR", logs.get(0).getGame().getWeatherReport().getCode());
    }


    @Test
    public void shouldRetryOnBeginGameTimeout() {
        when(gameService.beginGame()).thenReturn(withDelay(this::createMockedGame));

        List<GameLog> logs = testObject.play(1).toList().blockingGet();

        assertGame(logs);
    }

    @Test
    public void shouldRetryOnSolvingGameTimeout() {
        when(gameService.solve(any(), any()))
            .thenReturn(withDelay(this::createMockedResult));

        List<GameLog> logs = testObject.play(1).toList().blockingGet();

        assertGame(logs);
    }

    private void assertGame(List<GameLog> logs) {
        assertNotNull("Logs not null", logs);
        assertEquals("Logs contains 1 element", 1, logs.size());
        assertEquals("Game is won", "Victory", logs.get(0).getResult().getStatus());
        assertNotNull("Game object is present", logs.get(0).getGame());
        assertNotNull("Dragon object is present", logs.get(0).getDragon());
    }

    private <T> Single<T> withDelay(Supplier<Single<T>> supplier) {
        LocalDateTime time = LocalDateTime.now();
        return Single.defer(() -> {
            if ((LocalDateTime.now().compareTo(time.plusSeconds(2)) == 1)) {
                return supplier.get();
            }
            return Single.never();
        });
    }

    private Maybe<Dragon> createMockedDragon() {
        return Maybe.just(new Dragon(5, 5, 5, 5));
    }

    private Single<Result> createMockedResult() {
        return Single.just(new Result(
            "Victory", "Dragon won!"
        ));
    }

    private Single<WeatherReport> createMockedReport(String code) {
        return Single.just(new WeatherReport(code));
    }

    private Single<Game> createMockedGame() {
        return Single.just(
            new Game(1, new Knight(
                "MockedKnight", 5, 5, 5, 5
            ))
        );
    }
}