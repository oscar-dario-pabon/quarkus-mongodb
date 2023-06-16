package org.globant.ejerciciopractico.controller;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.globant.ejerciciopractico.service.EmployeeService;
import org.globant.ejerciciopractico.transfer.Employee;

import java.net.URI;

@Path("/employee")
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeService;

    @GET
    public Multi<Employee> getAllEmployee() {
        return employeeService.getAllEmployee();
    }

    @GET
    @Path("/{id}")
    public Uni<Employee> getEmployeeById(@PathParam("id") final String id) {
        return employeeService.getEmployeeById(id);
    }

    @POST
    public Uni<Response> createEmployee(final Employee employeeEntity) {
        return employeeService.createEmployee(employeeEntity).map(employeeEntityCreated -> Response.created(URI.create("/employeeEntity/" + employeeEntityCreated.getId())).entity(employeeEntity).build());
    }

    @PUT
    @Path("/{id}")
    public Uni<Employee> updateEmployee(@PathParam("id") final String id, final Employee employeeEntity) {
        return employeeService.updateEmployee(id, employeeEntity);
    }

    @DELETE
    @Path("/{id}")
    public void deleteEmployee(@PathParam("id") final String id) {
        employeeService.deleteEmployee(id);
    }

}
