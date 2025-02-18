package com.seuapp.agendamentoapp

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VisualizarAgendaActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnVoltar: Button
    private lateinit var dbHelper: AppointmentDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizar_agenda)

        recyclerView = findViewById(R.id.recyclerView)
        btnVoltar = findViewById(R.id.btnVoltar)

        // Configurando RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        dbHelper = AppointmentDbHelper(this)

        // Obtendo os agendamentos do banco de dados
        val agendamentos = dbHelper.getUpcomingAppointments()

        // Configurando o Adapter
        val adapter = AgendamentoAdapter(agendamentos)
        recyclerView.adapter = adapter

        // Botão de voltar
        btnVoltar.setOnClickListener {
            finish()
        }
    }
}
