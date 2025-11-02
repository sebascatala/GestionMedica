package com.example.tercerparcial.DAO

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.segundoparcial.SQL.AdminSQL
import com.example.tercerparcial.Entidades.Paciente

class daoPacientes(contexto:Context) {
    val conexion: AdminSQL = AdminSQL(contexto)
    fun insertarPaciente(p: Paciente): Long {
        val db: SQLiteDatabase = conexion.writableDatabase
        val cv = ContentValues()
        cv.put("nombre", p.nombre)
        cv.put("edad", p.edad)
        cv.put("carnet", p.carnet)
        cv.put("telefono", p.telefono)
        cv.put("genero", p.genero)
        val id = db.insert("Pacientes", null, cv)
        db.close()
        return id
    }

    fun obtenerPacientes(): ArrayList<Paciente> {
        val lista = ArrayList<Paciente>()
        val db: SQLiteDatabase = conexion.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Pacientes", null)
        if (cursor.moveToFirst()) {
            do {
                val paciente = Paciente(
                    codigo = cursor.getInt(0),
                    nombre = cursor.getString(1),
                    edad = cursor.getInt(2),
                    carnet = cursor.getInt(3),
                    telefono = cursor.getInt(4),
                    genero = cursor.getString(5)
                )
                lista.add(paciente)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return lista
    }

    fun actualizarPaciente(p: Paciente): Int {
        val db: SQLiteDatabase = conexion.writableDatabase
        val cv = ContentValues()
        cv.put("nombre", p.nombre)
        cv.put("edad", p.edad)
        cv.put("carnet", p.carnet)
        cv.put("telefono", p.telefono)
        cv.put("genero", p.genero)

        // Actualiza donde el código sea igual al del paciente
        val result = db.update("Pacientes", cv, "codigo = ?", arrayOf(p.codigo.toString()))
        db.close()
        return result
    }




}