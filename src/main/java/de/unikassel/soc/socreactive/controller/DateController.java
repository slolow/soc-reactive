package de.unikassel.soc.socreactive.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class DateController {

    @GetMapping(value = "/date", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> returnDateTimeFluxStream() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return Flux.interval(Duration.ofSeconds(1)).map(l -> dtf.format(LocalDateTime.now())).log();
    }
}
