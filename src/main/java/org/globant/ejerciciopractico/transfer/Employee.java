package org.globant.ejerciciopractico.transfer;

import io.smallrye.common.constraint.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class Employee {

    private String id;
    @NotNull
    private String name;
    @NotNull
    private BigDecimal age;
}
