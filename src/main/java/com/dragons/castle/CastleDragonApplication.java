package com.dragons.castle;

import com.dragons.castle.repository.model.GameLog;
import com.dragons.castle.services.game.GameEngine;
import com.dragons.castle.services.game.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.stream.Stream;

@SpringBootApplication
@Slf4j
public class CastleDragonApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(CastleDragonApplication.class, args);
        GameEngine engine = context.getBean(GameEngine.class);

        int times = getTimes(args);
        engine.play(times)
                .filter(CastleDragonApplication::isVictorious)
                .count()
                .toObservable()
                .blockingSubscribe(victories ->
                        log.info(String.format("\n\n###: Played %s time(s), won %s\n\n", times, victories)),
                        throwable -> log.error("Error occurred!", throwable)
                );
    }

    private static Integer getTimes(String[] args) {
        return Stream.of(args)
                .map(Integer::valueOf)
                .findFirst().orElse(1);
    }

    private static boolean isVictorious(GameLog gameLog) {
        return Result.VICTORY.equals(gameLog.getResult().getStatus());
    }

}
