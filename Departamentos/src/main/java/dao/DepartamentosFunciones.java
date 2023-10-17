package dao;

import java.sql.Connection;

import model.Departamento;
import model.Empleado;

public class DepartamentosFunciones {

	private Connection conn = null;
	public DepartamentosFunciones() {
		conn = BD.getConnection();
	}
	public String show() {
		
		return null;
	}
	public boolean add(Departamento departamento) {
		
		return false;
	}
	public Empleado buscarJefe(String readString) {
		
		return null;
	}

}
