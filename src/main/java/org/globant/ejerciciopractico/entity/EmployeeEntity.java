package org.globant.ejerciciopractico.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeEntity {

    private ObjectId id;
    private String name;
    private BigDecimal age;

    public EmployeeEntity(String name, BigDecimal age) {
        this.name = name;
        this.age = age;
    }
}
