package com.example.segundoparcial.SQL

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AdminSQL (miContexto:Context) : SQLiteOpenHelper(miContexto, "BaseDatos.db", null, 1){
    val crearPacientes:String = "CREATE TABLE Pacientes(codigo INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, edad INTEGER, carnet INTEGER, telefono INTEGER,genero TEXT)"
    val poblarPaciente:String = "INSERT INTO Pacientes(nombre ,edad,carnet,telefono, genero) VALUES ('PACIENTES',16,10369832,123456,'Masculino')"
    val eliminarPaciente: String ="DROP TABLE IF EXISTS Pacientes"

    val crearMedicos:String = "CREATE TABLE Medicos( codigo INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, direccion TEXT, celular INTEGER, codigoEspecialidad INTEGER, FOREIGN KEY(codigoEspecialidad) REFERENCES Especialidad(codigo))"
    val poblarMedicos:String = "INSERT INTO Medicos(nombre, celular,direccion) VALUES ('MEDICOS','Calle Tarija',70317343)"
    val eliminarMedico: String ="DROP TABLE IF EXISTS Medicos"

    val  insertarEspecialidad : String = "INSERT INTO Especialidad(nombre, costo)VALUES ('ESPECIALIDADES', 1)"
    val crearEspecialidad : String = "CREATE TABLE Especialidad(codigo INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, costo INTEGER)"
    val eliminarespecialidad : String = "DROP TABLE IF EXISTS especialidad"

    val crearConsulta = "CREATE TABLE Consultas(codigo INTEGER PRIMARY KEY AUTOINCREMENT,codigoPaciente INTEGER,codigoMedico INTEGER,motivo TEXT,fecha TEXT,costo REAL,FOREIGN KEY (codigoPaciente) REFERENCES Pacientes(codigo),FOREIGN KEY (codigoMedico) REFERENCES Medicos(codigo))"


    val eliminarConsultas = "DROP TABLE IF EXISTS Consultas"


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(crearPacientes)
        db?.execSQL(crearMedicos)
        db?.execSQL(crearEspecialidad)
        db?.execSQL(crearConsulta)

        db?.execSQL(poblarPaciente)
        db?.execSQL(insertarEspecialidad)
        db?.execSQL(poblarMedicos)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(eliminarMedico)
        db?.execSQL(eliminarPaciente)
        db?.execSQL(eliminarespecialidad)
        db?.execSQL(eliminarConsultas)

        onCreate(db)
    }

}