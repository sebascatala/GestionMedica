package com.example.tercerparcial.DAO

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.segundoparcial.SQL.AdminSQL
import com.example.tercerparcial.Entidades.Especialidad


class daoEspecialidad(contexto: Context) {
    val conexion:AdminSQL = AdminSQL(contexto)
    fun insertarEspecialidad(e: Especialidad): Long {
        val db: SQLiteDatabase = conexion.writableDatabase
        val cv = ContentValues()
        cv.put("nombre", e.nombre)
        cv.put("costo", e.costo)

        val id = db.insert("Especialidad", null, cv)
        db.close()
        return id
    }

    fun obtenerEspecialidades(): ArrayList<Especialidad> {
        val lista = ArrayList<Especialidad>()
        val db: SQLiteDatabase = conexion.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Especialidad", null)
        if (cursor.moveToFirst()) {
            do {
                val especialidad = Especialidad(
                    codigo = cursor.getInt(0),
                    nombre = cursor.getString(1),
                    costo = cursor.getDouble(2)
                )
                lista.add(especialidad)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return lista
    }

    fun actualizarEspecialidad(e: Especialidad): Int {
        val db: SQLiteDatabase = conexion.writableDatabase
        val cv = ContentValues()
        cv.put("nombre", e.nombre)
        cv.put("costo", e.costo)

        // Actualiza donde el código sea igual al del paciente
        val result = db.update("Especialidad", cv, "codigo = ?", arrayOf(e.codigo.toString()))
        db.close()
        return result
    }
}