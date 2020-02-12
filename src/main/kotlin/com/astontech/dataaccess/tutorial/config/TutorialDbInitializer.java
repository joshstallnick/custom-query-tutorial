package com.astontech.dataaccess.tutorial.config;

import com.astontech.dataaccess.tutorial.services.videoGames.VideoGame;
import com.astontech.dataaccess.tutorial.services.videoGames.VideoGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TutorialDbInitializer implements ApplicationListener<ContextRefreshedEvent> {

  @Autowired
  private VideoGameService videoGameService;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    List<VideoGame> games = Arrays.asList(
        new VideoGame("Super Mario 64", 1996, LocalDate.of(1996, 6, 23), new BigDecimal("96.41"), 12000000L, true),
        new VideoGame("Legend of Zelda: Majora's Mask", 2000, LocalDate.of(2000, 4, 27), new BigDecimal("94.87"), 3360000L, true),
        new VideoGame("Cyberpunk 2077"),
        new VideoGame("XCOM: Enemy Unknown", 2012, LocalDate.of(2012, 10, 9), new BigDecimal("89.34"), null, false),
        new VideoGame("Halo: Combat Evolved", 2001, LocalDate.of(2001, 11, 15), new BigDecimal(97), 65000000L, false),
        new VideoGame("Disco Elysium", 2019, LocalDate.of(2019, 10, 15), new BigDecimal(91), 100000L, false),
        new VideoGame("The Outer Worlds", 2019, LocalDate.of(2019, 10, 25), new BigDecimal("84.56"), 2000000L, false),
        new VideoGame("Nancy Drew: The Deadly Device", 2012, LocalDate.of(2012, 10, 23), BigDecimal.ZERO, null, false),
        new VideoGame("Legend of Dragoon", 1999, LocalDate.of(1999, 12, 2), new BigDecimal("74.12"), 1200345L, true),
        new VideoGame("Luigi's Mansion 3", 2019, LocalDate.of(2019, 10, 31), new BigDecimal("86.68"), 5370000L, false),
        new VideoGame("Divinity: Original Sin 2", 2017, LocalDate.of(2017, 9, 14), new BigDecimal("94.04"), 1034987L, false),
        new VideoGame("Far Cry 4", 2017, LocalDate.of(2017, 11, 18), new BigDecimal(82), 7000000L, false)
    );

    List<VideoGame> savedGames = games.stream().map(game -> videoGameService.save(game)).collect(Collectors.toList());

    Stream<VideoGame> gameStream = new ArrayList<>(savedGames).stream();

    Stream<VideoGame> gameParallelStream = new ArrayList<>(savedGames).parallelStream();

    Consumer<VideoGame> print = System.out::println;

    System.out.println("------------- STREAM");

    gameStream.forEach(print);

    System.out.println("------------- PARALLEL STREAM");

    gameParallelStream.forEach(print);
  }
}