package com.seuapp.agendamentoapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NovoAgendamentoActivity : AppCompatActivity() {

    private lateinit var etClienteNome: EditText
    private lateinit var etDataConsulta: EditText
    private lateinit var etHoraConsulta: EditText
    private lateinit var spServiceType: Spinner
    private lateinit var btnSalvarAgendamento: Button
    private lateinit var btnVoltar: Button
    private lateinit var dbHelper: AppointmentDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_agendamento)

        etClienteNome = findViewById(R.id.etClienteNome)
        etDataConsulta = findViewById(R.id.etDataConsulta)
        etHoraConsulta = findViewById(R.id.etHoraConsulta)
        spServiceType = findViewById(R.id.spServiceType)
        btnSalvarAgendamento = findViewById(R.id.btnSalvarAgendamento)
        btnVoltar = findViewById(R.id.btnVoltar)  // Referência ao botão de voltar

        dbHelper = AppointmentDbHelper(this)

        btnSalvarAgendamento.setOnClickListener {
            val nome = etClienteNome.text.toString()
            val data = etDataConsulta.text.toString()
            val hora = etHoraConsulta.text.toString()
            val tipoServico = spServiceType.selectedItem.toString()

            val precoServico = calcularPrecoServico(tipoServico)

            // Salva o agendamento no banco de dados
            dbHelper.addAppointment(nome, data, hora, tipoServico, precoServico)

            Toast.makeText(this, "Agendamento salvo com sucesso!", Toast.LENGTH_SHORT).show()
            finish()  // Fecha a tela de novo agendamento e volta para a tela principal
        }

        // Lógica para voltar à MainActivity
        btnVoltar.setOnClickListener {
            finish()  // Finaliza a activity atual e volta para a MainActivity
        }
    }

    private fun calcularPrecoServico(servico: String): Double {
        return when (servico) {
            "Manicure (mãos)" -> 35.00
            "Pedicure (pés)" -> 40.00
            "Ambos" -> 65.00
            "Pacote (Novo Cliente)" -> 140.00
            "Pacote (Cliente Recorrente)" -> 0.00
            else -> 0.00
        }
    }
}
