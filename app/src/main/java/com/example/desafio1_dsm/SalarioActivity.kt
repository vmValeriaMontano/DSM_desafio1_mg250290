package com.example.desafio1_dsm

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class SalarioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_descuentos)

        val etNombre = findViewById<EditText>(R.id.etNombreEmpleado)
        val etSalario = findViewById<EditText>(R.id.etSalarioBase)
        val tvBruto = findViewById<TextView>(R.id.tvSalarioBruto)
        val tvDescuentos = findViewById<TextView>(R.id.tvDescuentos)
        val tvNeto = findViewById<TextView>(R.id.tvSalarioNeto)
        val btnCalcular = findViewById<Button>(R.id.btnCalcularSalario)
        val btnVolver = findViewById<Button>(R.id.btnVolverSalario)

        btnVolver.setOnClickListener { finish() }

        //Con onClick accionaremos los calculos de salarios
        btnCalcular.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val salarioStr = etSalario.text.toString()
            val salarioBase = salarioStr.toDoubleOrNull()

            //Verificamos que no este vacio
            if (nombre.isEmpty()) {
                etNombre.error = getString(R.string.err_campo_vacio)
                //hacemos uso de Vibracion al tener errores
                vibrarDispositivo()
                return@setOnClickListener
            }

            if (salarioBase == null || salarioBase <= 0) {
                etSalario.error = getString(R.string.err_salario_invalido)
                vibrarDispositivo()
                return@setOnClickListener
            }

            // Cálculos
            val isss = if (salarioBase > 1000.0) 30.0 else salarioBase * 0.03
            val afp = salarioBase * 0.0725
            val salarioGravable = salarioBase - isss - afp
            val renta = calcularRenta(salarioGravable)
            val totalDescuentos = isss + afp + renta
            val salarioNeto = salarioBase - totalDescuentos

            val df = DecimalFormat("$#,##0.00")

            tvBruto.text = "Empleado: $nombre\nSalario Bruto: ${df.format(salarioBase)}"
            tvDescuentos.text = "Descuentos:\n • ISSS (3%): ${df.format(isss)}\n • AFP (7.25%): ${df.format(afp)}\n • Renta: ${df.format(renta)}\n Total Descuentos: ${df.format(totalDescuentos)}"
            tvNeto.text = "Salario Neto A Pagar: ${df.format(salarioNeto)}"
        }
    }

    // Renta calculada segun salario
    private fun calcularRenta(salarioGravable: Double): Double {
        return when {
            salarioGravable <= 472.00 -> 0.0
            salarioGravable <= 895.24 -> ((salarioGravable - 472.00) * 0.10) + 17.67
            salarioGravable <= 2038.10 -> ((salarioGravable - 895.24) * 0.20) + 60.00
            else -> ((salarioGravable - 2038.10) * 0.30) + 288.57
        }
    }

    // Logica de vibracion
    private fun vibrarDispositivo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            val vibrator = vibratorManager.defaultVibrator
            vibrator.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            @Suppress("DEPRECATION")
            vibrator.vibrate(300)
        }
    }
}