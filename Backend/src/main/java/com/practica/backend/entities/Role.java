package com.practica.backend.entities;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name="roles")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, length = 20)
    private String nombre;

    /**Constructor por defecto**/
    public Role() {
    }
    /**Constructor por defecto**/
    public Role(String nombre) {
        this.nombre = nombre;
    }

    /**Getters and Setters**/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**ToString**/
    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}