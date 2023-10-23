package model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Departamento {
	UUID id; 
	String nombre; 
	Empleado jefe;
	

	
	public Departamento(String nombre,Empleado jefe) {
		setId(UUID.randomUUID());
		setNombre(nombre);
		setJefe(jefe);
	}

	
	public String toString() {
		if(jefe==null)
		return String.format("%s | %s  ", id, nombre);
		return String.format("%s | %s | %s", id, nombre,jefe.getId());
	}
}