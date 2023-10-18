package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import dao.DepartamentosFunciones;
import dao.EmpleadosFunciones;
import io.IO;
import model.Departamento;
import model.Empleado;

public class Menu {

	public static void main(String[] args) {
		DepartamentosFunciones dep = new DepartamentosFunciones();
		EmpleadosFunciones emp = new EmpleadosFunciones();
//		agenda.drop();
		
		List<String> opciones = List.of( 
				"1: Mostrar departamentos", 
				"2: Mostrar empleados", 
				"3: Añadir departamento", 
				"4: Añadir empleado",
				"5: Eliminar departamento",
				"6: Eliminar empleado",
				"7: Salir");
		
		while (true) {
			System.out.println(opciones);
			switch (IO.readInt()) {
			case 1:
				mostrar(dep);
				break;
			case 2:
				mostrar(emp);
				break;
			case 3:
				anadir(dep);
				break;
			case 4:
				anadir(emp);
				break;
			case 5:
				eliminar(dep);
				break;
			case 6:
				eliminar(emp);
				break;
			case 7:
				cerrar(dep,emp);
				return;
			default:
			}
		}		
		
	}
//aaaaaaaaaaaaaaaa	
	

	private static void cerrar(DepartamentosFunciones dep, EmpleadosFunciones emp) {
		// TODO Auto-generated method stub
		
	}


	private static void eliminar(EmpleadosFunciones emp) {
		// TODO Auto-generated method stub
		
	}


	private static void eliminar(DepartamentosFunciones dep) {
		// TODO Auto-generated method stub
		
	}


	private static void mostrar(DepartamentosFunciones dep) {
		System.out.println(dep.show());
	}

	private static void mostrar(EmpleadosFunciones emp) {
		System.out.println(emp.show());
	}
	
	private static void anadir(DepartamentosFunciones dep) {
		IO.print("Nombre ? ");
		String nombre = IO.readString();
		IO.print("jefe uuid ");
		Empleado jefe = dep.buscarJefe(IO.readString());
		boolean anadido =dep.add(new Departamento(nombre, jefe));
		IO.println(anadido ? "Añadido" : "No se ha podido añadir");
	}
	private static void anadir(EmpleadosFunciones emp) {
		IO.print("Nombre ? ");
		String nombre = IO.readString();
		IO.print("Salario ? ");
		double salario = IO.readDouble();
		IO.print("fecha nacimiento");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
		LocalDate nacimiento = LocalDate.parse(IO.readString(),formatter);
		IO.print("fecha nacimiento");
		String iddep = IO.readString();
		Departamento dep = emp.buscarDep(iddep);
		boolean anadido =emp.add(new Empleado(nombre, salario,nacimiento,dep));
		IO.println(anadido ? "Añadido" : "No se ha podido añadir");
	}


}
