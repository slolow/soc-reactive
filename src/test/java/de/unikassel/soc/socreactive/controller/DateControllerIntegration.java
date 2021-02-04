package de.unikassel.soc.socreactive.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@ExtendWith(SpringExtension.class)
@WebFluxTest(DateController.class)
public class DateControllerIntegration {

    @Autowired
    WebTestClient webTestClient;

    final String DATETIME_FLUX_PATH = "/api/v1/date";

    @Test
    void createDateTimeFluxStream() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        FluxExchangeResult<String> stringTimeFlux = webTestClient.get().uri(DATETIME_FLUX_PATH)
                .exchange()
                .expectStatus().isOk()
                .returnResult(String.class);

        StepVerifier.create(stringTimeFlux.getResponseBody())
                .expectSubscription()
                .expectNext(dtf.format(LocalDateTime.now()))
                .expectNext(dtf.format(LocalDateTime.now().plusSeconds(1)))
                .expectNext(dtf.format(LocalDateTime.now().plusSeconds(2)))
                .thenCancel()
                .verify();
    }

}