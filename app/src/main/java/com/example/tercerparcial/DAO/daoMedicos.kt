package com.example.tercerparcial.DAO

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.segundoparcial.SQL.AdminSQL
import com.example.tercerparcial.Entidades.Medico

class daoMedicos(context: Context) {
    val conexion: AdminSQL = AdminSQL(context)
    fun insertarMedicos(medico: Medico):Long{

        var cv = ContentValues()
        val db: SQLiteDatabase = conexion.writableDatabase
        cv.put("nombre",medico.nombre)
        cv.put("celular", medico.celular)
        cv.put("direccion", medico.direccion)
        cv.put("codigoEspecialidad", medico.codigoEspecialidad)
        val id=db.insert("Medicos",null, cv)
        db.close()
        return id
    }

    fun updateMedicos(medico: Medico):Int{
        val db: SQLiteDatabase = conexion.writableDatabase
        val cv = ContentValues()
        cv.put("nombre",medico.nombre)
        cv.put("celular", medico.celular)
        cv.put("direccion", medico.direccion)
        cv.put("especialidad", medico.codigoEspecialidad)
        val id=db.update("Medicos",cv,"codigoMedico=?", arrayOf(medico.codigoMedico.toString()))
        db.close()
        return id

    }
    fun MostrarMedicos():ArrayList<Medico>{
        val lista=ArrayList<Medico>()
        val db: SQLiteDatabase =conexion.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM MEDICOS", null)
        if (cursor.moveToFirst())
        {
            do {
                val medico=Medico( codigoMedico = cursor.getInt(0), nombre = cursor.getString(1),
                    direccion = cursor.getString(2),
                    celular = cursor.getInt(3),
                    codigoEspecialidad = cursor.getInt(4),
                )
                lista.add(medico)
            }while (cursor.moveToNext())

        }
        cursor.close()
        db.close()
        return lista

    }


}