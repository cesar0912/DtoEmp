package dao;

import java.sql.Connection;

public class EmpleadosFunciones {

	private Connection conn = null;
	public EmpleadosFunciones() {
		conn = BD.getConnection();
	}
	public String show() {
		// TODO Auto-generated method stub
		return null;
	}

}
