package org.globant.ejerciciopractico.repository;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.globant.ejerciciopractico.entity.EmployeeEntity;


@ApplicationScoped
public class EmployeeRepository implements ReactivePanacheMongoRepository<EmployeeEntity> {
}
