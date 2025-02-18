package com.seuapp.agendamentoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AgendamentoAdapter(private val agendamentos: List<Appointment>) : RecyclerView.Adapter<AgendamentoAdapter.AgendamentoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgendamentoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_agendamento, parent, false)
        return AgendamentoViewHolder(view)
    }

    override fun onBindViewHolder(holder: AgendamentoViewHolder, position: Int) {
        val agendamento = agendamentos[position]
        holder.txtNomeCliente.text = agendamento.nome
        holder.txtDataAgendamento.text = agendamento.data
        holder.txtHoraAgendamento.text = agendamento.hora
    }

    override fun getItemCount(): Int {
        return agendamentos.size
    }

    inner class AgendamentoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNomeCliente: TextView = view.findViewById(R.id.txtNomeCliente)
        val txtDataAgendamento: TextView = view.findViewById(R.id.txtDataAgendamento)
        val txtHoraAgendamento: TextView = view.findViewById(R.id.txtHoraAgendamento)
    }
}
