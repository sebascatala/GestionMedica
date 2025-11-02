package com.example.tercerparcial.DAO

import android.content.Context
import com.example.segundoparcial.SQL.AdminSQL

class daoReportes(context: Context) {
    val conexion: AdminSQL = AdminSQL(context)


    fun obtenerDatosPorPaciente(idPaciente: Int): Pair<Int, Double> {
        val db = conexion.readableDatabase
        val cursor = db.rawQuery(" SELECT COUNT(*), SUM(costo) FROM Consultas WHERE codigoPaciente = ?", arrayOf(idPaciente.toString()))

        var cantidad = 0
        var total = 0.0
        if (cursor.moveToFirst()) {
            cantidad = cursor.getInt(0)
            total = cursor.getDouble(1)
        }
        cursor.close()
        db.close()
        return Pair(cantidad, total)
    }

    fun obtenerDatosPorEspecialidad(idEspecialidad: Int): Triple<Int, Int, Double> {
        val db = conexion.readableDatabase
        val cursor = db.rawQuery("""
            SELECT 
                (SELECT COUNT(*) FROM Medicos WHERE codigoEspecialidad = ?),
                (SELECT COUNT(*) FROM Consultas c JOIN Medicos m ON c.codigoMedico = m.codigo WHERE m.codigoEspecialidad = ?),
                (SELECT IFNULL(SUM(costo), 0) FROM Consultas c JOIN Medicos m ON c.codigoMedico = m.codigo WHERE m.codigoEspecialidad = ?)
             """.trimIndent(), arrayOf(idEspecialidad.toString(), idEspecialidad.toString(), idEspecialidad.toString()))


        var medicos = 0
        var consultas = 0
        var total = 0.0
        if (cursor.moveToFirst()) {
            medicos = cursor.getInt(0)
            consultas = cursor.getInt(1)
            total = cursor.getDouble(2)
        }
        cursor.close()
        db.close()
        return Triple(medicos, consultas, total)
    }


    fun obtenerDatosPorMedico(idMedico: Int): Triple<String, Int, Double> {
        val db = conexion.readableDatabase
        val cursor = db.rawQuery("""
        SELECT 
            (SELECT e.nombre FROM Especialidad e JOIN Medicos m ON m.codigoEspecialidad = e.codigo WHERE m.codigo = ?),
            (SELECT COUNT(*) FROM Consultas WHERE codigoMedico = ?),
            (SELECT IFNULL(SUM(costo), 0) FROM Consultas WHERE codigoMedico = ?)
    """, arrayOf(idMedico.toString(), idMedico.toString(), idMedico.toString()))

        var nombreEspecialidad = "Sin Especialidad"
        var consultas = 0
        var total = 0.0
        if (cursor.moveToFirst()) {
            nombreEspecialidad = cursor.getString(0) ?: "Sin Especialidad"
            consultas = cursor.getInt(1)
            total = cursor.getDouble(2)
        }
        cursor.close()
        db.close()
        return Triple(nombreEspecialidad, consultas, total)
    }

    fun obtenerMedicosYFechasPorPaciente(idPaciente: Int): List<Pair<String, String>> {
        val db = conexion.readableDatabase
        val lista = mutableListOf<Pair<String, String>>()

        val cursor = db.rawQuery("""
        SELECT m.nombre, c.fecha
        FROM Consultas c
        JOIN Medicos m ON c.codigoMedico = m.codigo
        WHERE c.codigoPaciente = ?
        ORDER BY c.fecha ASC
    """.trimIndent(), arrayOf(idPaciente.toString()))

        if (cursor.moveToFirst()) {
            do {
                val nombreMedico = cursor.getString(0)
                val fechaConsulta = cursor.getString(1)
                lista.add(Pair(nombreMedico, fechaConsulta))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return lista
    }



}