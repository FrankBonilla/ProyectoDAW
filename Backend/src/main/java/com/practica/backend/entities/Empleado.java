package com.practica.backend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.util.Objects;


@Entity
@Table(name="EM_EMPLEADOS")
public class Empleado {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="idEmpleado")
	private int idEmpleado;
	@Column(name="nif",nullable= false,length =9)
	private String nif;
	@Column(name="nombre",nullable= false,length= 30)
	private String nombre;
	@Column(name="apellido1",nullable= false,length= 40)
	private String apellido1;
	@Column(name="apellido2",nullable= false,length= 40)
	private String apellido2;
	@Column(name="fechaNacimiento",nullable= false)
	private Date nacimiento;
	@Column(name="telefono1",nullable=false,length= 12)
	private String telefono1;
	@Column(name="telefono2",length= 12)
    private String telefono2;
	@Column(name="email",nullable= false,length= 40)
    private String email;
	@Column(name="fechaAlta",nullable= false)
    private Date fechaAlta;
	@Column(name="fechaBaja")
    private Date fechaBaja;
	@Column(name="edoCivil",nullable= false, length= 1)
    private String edoCivil;
	@Column(name="serMilitar",nullable= false, length= 1)
    private String serMilitar;
	
	/**Constructor por defecto **/
    public Empleado() {
    	
    }
	/**Constructor con parámetros **/
	public Empleado(String nIF, String nombre, String apellido1, String apellido2, Date nacimiento, String telefono1,
					String telefono2, String email, Date fechaAlta, String edoCivil, String serMilitar) {
		super();
		this.nif = nIF;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.nacimiento = nacimiento;
		this.telefono1 = telefono1;
		this.telefono2 = telefono2;
		this.email = email;
		this.fechaAlta = fechaAlta;
		this.edoCivil = edoCivil;
		this.serMilitar = serMilitar;
	}
	
	/**Getters and Setters **/
	public int getIdEmpleado() {
		return idEmpleado;
	}
	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nIF) {
		nif = nIF;
	}
	public String getApellido1() {
		return apellido1;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public String getApellido2() {
		return apellido2;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	public Date getNacimiento() {
		return nacimiento;
	}
	public void setNacimiento(Date nacimiento) {
		this.nacimiento = nacimiento;
	}
	public String getTelefono1() {
		return telefono1;
	}
	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}
	public String getTelefono2() {
		return telefono2;
	}
	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public Date getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	public String getEdoCivil() {
		return edoCivil;
	}
	public void setEdoCivil(String edoCivil) {
		this.edoCivil = edoCivil;
	}
	public String getSerMilitar() {
		return serMilitar;
	}
	public void setSerMilitar(String serMilitar) {
		this.serMilitar = serMilitar;
	}

	/**Equals and HashCode **/
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Empleado empleado = (Empleado) o;
		return idEmpleado == empleado.idEmpleado && Objects.equals(nif, empleado.nif) && Objects.equals(nombre, empleado.nombre) && Objects.equals(apellido1, empleado.apellido1) && Objects.equals(apellido2, empleado.apellido2) && Objects.equals(nacimiento, empleado.nacimiento) && Objects.equals(telefono1, empleado.telefono1) && Objects.equals(telefono2, empleado.telefono2) && Objects.equals(email, empleado.email) && Objects.equals(fechaAlta, empleado.fechaAlta) && Objects.equals(fechaBaja, empleado.fechaBaja) && Objects.equals(edoCivil, empleado.edoCivil) && Objects.equals(serMilitar, empleado.serMilitar);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idEmpleado, nif, nombre, apellido1, apellido2, nacimiento, telefono1, telefono2, email, fechaAlta, fechaBaja, edoCivil, serMilitar);
	}
	/**toString **/
	@Override
	public String toString() {
		return "Empleado{" +
				"idEmpleado=" + idEmpleado +
				", nif='" + nif + '\'' +
				", nombre='" + nombre + '\'' +
				", apellido1='" + apellido1 + '\'' +
				", apellido2='" + apellido2 + '\'' +
				", nacimiento=" + nacimiento +
				", telefono1='" + telefono1 + '\'' +
				", telefono2='" + telefono2 + '\'' +
				", email='" + email + '\'' +
				", fechaAlta=" + fechaAlta +
				", fechaBaja=" + fechaBaja +
				", edoCivil='" + edoCivil + '\'' +
				", serMilitar='" + serMilitar + '\'' +
				'}';
	}
}
