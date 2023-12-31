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

		EmpleadosFunciones emp = new EmpleadosFunciones();
		DepartamentosFunciones dep = new DepartamentosFunciones();

//		agenda.drop();

		List<String> opciones = List.of("1: Mostrar departamentos", "2: Mostrar empleados", "3: Añadir departamento",
				"4: Añadir empleado", "5: Eliminar departamento", "6: Eliminar empleado", "7: Modificar departamento",
				"8: Modificar empleado", "9: Salir");

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
				modificar(dep, emp);
				break;
			case 8:
				modificar(emp, dep);
				break;
			case 9:
				cerrar(dep, emp);
				return;
			default:
			}
		}

	}

	private static void modificar(EmpleadosFunciones emp, DepartamentosFunciones dep) {
		IO.print("Código del contacto a modificar ? ");
		String id = IO.readString();
		UUID uuid = UUID.fromString(id);
		Empleado empleado = dep.buscarJefe(id);
		IO.print("Nombre [" + empleado.getNombre() + "] ? ");
		String nombre = IO.readString();
		if (!nombre.isBlank()) {
			empleado.setNombre(nombre);
		}
		IO.print("Salario [" + empleado.getSalario() + "] ? ");
		double salario = IO.readDouble();
		empleado.setSalario(salario);
		IO.print("Nacimiento [" + empleado.getNacimiento() + "] ? ");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate nacimiento = LocalDate.parse(IO.readString(), formatter);
		empleado.setNacimiento(nacimiento);
		IO.print("Departamento [" + empleado.getDepartamento() + "] ? ");
		Departamento departamento = emp.buscarDepartamento(IO.readString());
		empleado.setDepartamento(departamento);
		boolean anadido = emp.update(empleado);
		IO.println(anadido ? "Modificado" : "No se ha podido modificar");
	}

	private static void modificar(DepartamentosFunciones dep, EmpleadosFunciones emp) {
		IO.print("Código del departamento a modificar ? ");
		String id = IO.readString();
		UUID uuid = UUID.fromString(id);
		Departamento departamento = emp.buscarDepartamento(id);
		IO.print("Nombre [" + departamento.getNombre() + "] ? ");
		String nombre = IO.readString();
		if (!nombre.isBlank()) {
			departamento.setNombre(nombre);
		}
		IO.print("Jefe [" + departamento.getJefe() + "] ? ");
		Empleado empleado = dep.buscarJefe(IO.readString());
		departamento.setJefe(empleado);
		boolean anadido = dep.update(departamento);
		IO.println(anadido ? "Modificado" : "No se ha podido modificar");
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
		boolean anadido = dep.add(new Departamento(nombre, jefe));
		IO.println(anadido ? "Añadido" : "No se ha podido añadir");
	}

	private static void anadir(EmpleadosFunciones emp) {
		IO.print("Nombre ? ");
		String nombre = IO.readString();
		IO.print("Salario ? ");
		double salario = IO.readDouble();
		IO.print("fecha nacimiento");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate nacimiento = LocalDate.parse(IO.readString(), formatter);
		IO.print("departamento uuid");
		Departamento departamento = emp.buscarDepartamento(IO.readString());
		boolean anadido = emp.add(new Empleado(nombre, salario, nacimiento, departamento));
		IO.println(anadido ? "Añadido" : "No se ha podido añadir");
	}

	private static void eliminar(DepartamentosFunciones dep) {
		IO.print("Código ? ");
		String id = IO.readString();
		boolean borrado = dep.delete(id);
		IO.println(borrado ? "Borrado" : "No se ha podido borrar o no existe");

	}

	private static void eliminar(EmpleadosFunciones emp) {
		IO.print("Código ? ");
		String id = IO.readString();
		boolean borrado = emp.delete(id);
		IO.println(borrado ? "Borrado" : "No se ha podido borrar");

	}

	private static void cerrar(DepartamentosFunciones dep, EmpleadosFunciones emp) {
		dep.close();
		emp.close();
	}
}
