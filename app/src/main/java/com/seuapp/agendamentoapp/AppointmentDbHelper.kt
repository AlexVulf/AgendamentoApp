package com.seuapp.agendamentoapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.*

class AppointmentDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE appointments (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT,
                data TEXT,
                hora TEXT,
                tipoServico TEXT,
                preco REAL
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS appointments")
        onCreate(db)
    }

    // Método para adicionar um novo agendamento
    fun addAppointment(nome: String, data: String, hora: String, tipoServico: String, preco: Double) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("nome", nome)
            put("data", data)
            put("hora", hora)
            put("tipoServico", tipoServico)
            put("preco", preco)
        }

        db.insert("appointments", null, values)
        db.close()
    }

    // Método para obter os agendamentos futuros
    fun getUpcomingAppointments(): List<Appointment> {
        val appointments = mutableListOf<Appointment>()
        val db = readableDatabase

        // Obtendo a data atual no formato correto
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val today = dateFormat.format(Date())

        // Realizando a consulta no banco de dados
        val cursor = db.rawQuery("SELECT * FROM appointments WHERE data >= ? ORDER BY data, hora", arrayOf(today))

        // Iterando sobre os resultados do banco
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
            val data = cursor.getString(cursor.getColumnIndexOrThrow("data"))
            val hora = cursor.getString(cursor.getColumnIndexOrThrow("hora"))
            val tipoServico = cursor.getString(cursor.getColumnIndexOrThrow("tipoServico"))
            val preco = cursor.getDouble(cursor.getColumnIndexOrThrow("preco"))

            appointments.add(Appointment(id, nome, data, hora, tipoServico, preco))
        }

        cursor.close()
        db.close()

        return appointments
    }


    companion object {
        private const val DATABASE_NAME = "appointments.db"
        private const val DATABASE_VERSION = 1
    }
}
