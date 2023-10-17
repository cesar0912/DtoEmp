package model;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empleado {
	UUID id; 
	String nombre; 
	int salario;
	LocalDate nacimiento;
	Departamento departamento;
	

	
	public Empleado(String nombre, int salario, LocalDate nacimiento, Departamento departamento) {
		setId(UUID.randomUUID());
		setNombre(nombre);
		setSalario(salario);
		setNacimiento(nacimiento);
		setDepartamento(departamento);
	}

	
	public String toString() {
		return String.format("%s | %s | salario: %d | %s | %s", id, nombre, salario,nacimiento,departamento.getId() );
		
	}
}
