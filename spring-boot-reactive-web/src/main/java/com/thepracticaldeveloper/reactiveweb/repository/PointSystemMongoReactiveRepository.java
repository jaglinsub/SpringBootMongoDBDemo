package com.thepracticaldeveloper.reactiveweb.repository;

import com.thepracticaldeveloper.reactiveweb.domain.Interests;
import com.thepracticaldeveloper.reactiveweb.domain.PointSystem;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PointSystemMongoReactiveRepository extends ReactiveCrudRepository<PointSystem, String> {
}
