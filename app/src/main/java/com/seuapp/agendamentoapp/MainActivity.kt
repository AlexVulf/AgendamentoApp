package com.seuapp.agendamentoapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var btnNovoAgendamento: Button
    private lateinit var btnVisualizarAgendamentos: Button
    private lateinit var btnExportarXls: Button
    private lateinit var btnFecharApp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializando os botões
        btnNovoAgendamento = findViewById(R.id.btnNovoAgendamento)
        btnVisualizarAgendamentos = findViewById(R.id.btnVisualizarAgendamentos)
        btnExportarXls = findViewById(R.id.btnExportarXls)
        btnFecharApp = findViewById(R.id.btnFecharApp)

        // Verificando e solicitando permissões de leitura e escrita no armazenamento externo
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        }

        // Navegar para a tela de novo agendamento
        btnNovoAgendamento.setOnClickListener {
            val intent = Intent(this@MainActivity, NovoAgendamentoActivity::class.java)
            startActivity(intent)
        }

        // Navegar para a tela de visualização de agendamentos
        btnVisualizarAgendamentos.setOnClickListener {
            val intent = Intent(this, VisualizarAgendaActivity::class.java)
            startActivity(intent)
        }

        // Lógica para exportar .xls
        btnExportarXls.setOnClickListener {
            generateXlsFile()
        }

        // Lógica para fechar o app
        btnFecharApp.setOnClickListener {
            finish() // Fecha o aplicativo
        }
    }

    // Função para gerar o arquivo .xls
    private fun generateXlsFile() {
        // Caminho para a pasta Downloads
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(downloadsDir, "agendamentos.xls")

        // Criando o workbook e sheet
        val workbook = HSSFWorkbook() // Usando HSSFWorkbook para gerar arquivos .xls
        val sheet: Sheet = workbook.createSheet("Agendamentos")

        // Criando a primeira linha (header)
        val header: Row = sheet.createRow(0)
        header.createCell(0).setCellValue("Nome do Cliente")
        header.createCell(1).setCellValue("Data")
        header.createCell(2).setCellValue("Hora")
        header.createCell(3).setCellValue("Tipo de Serviço")
        header.createCell(4).setCellValue("Preço")

        // Recuperando os agendamentos do banco de dados
        val dbHelper = AppointmentDbHelper(this)
        val agendamentos = dbHelper.getUpcomingAppointments()

        // Preenchendo a planilha com os dados
        for ((index, agendamento) in agendamentos.withIndex()) {
            val row: Row = sheet.createRow(index + 1)
            row.createCell(0).setCellValue(agendamento.nome)
            row.createCell(1).setCellValue(agendamento.data)
            row.createCell(2).setCellValue(agendamento.hora)
            row.createCell(3).setCellValue(agendamento.tipoServico)
            row.createCell(4).setCellValue(agendamento.preco)
        }

        // Salvando o arquivo no diretório Downloads
        try {
            val fileOut = FileOutputStream(file)
            workbook.write(fileOut)
            fileOut.close()
            Toast.makeText(this, "Arquivo exportado com sucesso!", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Erro ao exportar o arquivo.", Toast.LENGTH_SHORT).show()
        }
    }
}
