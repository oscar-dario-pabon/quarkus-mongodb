package org.globant.ejerciciopractico.service;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.globant.ejerciciopractico.entity.EmployeeEntity;
import org.globant.ejerciciopractico.repository.EmployeeRepository;
import org.globant.ejerciciopractico.transfer.Employee;


@ApplicationScoped
@AllArgsConstructor
public class EmployeeService {

    public static final String EMPLOYEE_NOT_FOUND = "Employee not found";
    private EmployeeRepository employeeRepository;

    public Multi<Employee> getAllEmployee() {
        return employeeRepository.streamAll().map(employeeEntity -> new Employee(employeeEntity.getId().toString(), employeeEntity.getName(), employeeEntity.getAge()));
    }

    public Uni<Employee> getEmployeeById(final String id) {
        return employeeRepository.findByIdOptional(new ObjectId(id)).map(employeeEntity -> {
            final EmployeeEntity findEmployeeEntity = employeeEntity.orElseThrow(() -> new RuntimeException(EMPLOYEE_NOT_FOUND));
            return new Employee(findEmployeeEntity.getId().toString(), findEmployeeEntity.getName(), findEmployeeEntity.getAge());
        });
    }

    public Uni<Employee> createEmployee(final Employee unsavedEmployee) {
        Uni<EmployeeEntity> savedEmployee = employeeRepository.persist(new EmployeeEntity(unsavedEmployee.getName(), unsavedEmployee.getAge()));
        return savedEmployee.map(employeeEntity -> new Employee(employeeEntity.getId().toString(), employeeEntity.getName(), employeeEntity.getAge()));
    }

    public Uni<Employee> updateEmployee(final String id, final Employee employeeEntityUpdate) {
        final Uni<EmployeeEntity> employeeFind = employeeRepository.findByIdOptional(new ObjectId(id)).map(employeeEntity -> employeeEntity.orElseThrow(() -> new RuntimeException(EMPLOYEE_NOT_FOUND)));
        return employeeFind.onItem().transform(employeeEntity -> {
            employeeEntity.setName(employeeEntityUpdate.getName());
            employeeEntity.setAge(employeeEntityUpdate.getAge());
            return employeeEntity;
        }).call(employeeEntity -> employeeRepository.persistOrUpdate(employeeEntity)).map(employeeEntity -> new Employee(employeeEntity.getId().toString(), employeeEntity.getName(), employeeEntity.getAge()));
    }

    public Uni<Void> deleteEmployee(final String id) {
        final Uni<EmployeeEntity> employeeFind = employeeRepository.findByIdOptional(new ObjectId(id)).map(employeeEntity -> employeeEntity.orElseThrow(() -> new RuntimeException(EMPLOYEE_NOT_FOUND)));
        return employeeFind.call(employeeEntity -> employeeRepository.delete(employeeEntity)).replaceWithVoid();
    }
}
