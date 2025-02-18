package com.seuapp.agendamentoapp

data class Appointment(
    val id: Int,
    val nome: String,
    val data: String,
    val hora: String,
    val tipoServico: String,
    val preco: Double
)
