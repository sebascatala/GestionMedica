package com.example.tercerparcial.DAO

import android.content.ContentValues
import android.content.Context
import com.example.segundoparcial.SQL.AdminSQL
import com.example.tercerparcial.Entidades.Consulta

class daoConsultas(contexto: Context) {


    val conexion = AdminSQL(contexto)

    fun insertarConsulta(consulta: Consulta): Long {
        val db = conexion.writableDatabase

        val cursorEsp = db.rawQuery("SELECT codigoEspecialidad FROM Medicos WHERE codigo = ?", arrayOf(consulta.codigoMedico.toString()))
        var codigoEspecialidad = 0
        if (cursorEsp.moveToFirst()) {
            codigoEspecialidad = cursorEsp.getInt(0)
        }
        cursorEsp.close()

        val cursorCosto = db.rawQuery("SELECT costo FROM Especialidad WHERE codigo = ?", arrayOf(codigoEspecialidad.toString()))
        var costo = 0.0
        if (cursorCosto.moveToFirst()) {
            costo = cursorCosto.getDouble(0)
        }
        cursorCosto.close()

        val valores = ContentValues().apply {
            put("codigoPaciente", consulta.codigoPaciente)
            put("codigoMedico", consulta.codigoMedico)
            put("motivo", consulta.motivo)
            put("fecha", consulta.fecha)
            put("costo", costo)
        }

        val resultado = db.insert("Consultas", null, valores)
        db.close()
        return resultado
    }


}