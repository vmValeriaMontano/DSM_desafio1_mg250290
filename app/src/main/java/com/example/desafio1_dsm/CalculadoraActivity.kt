package com.example.desafio1_dsm

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow
import kotlin.math.sqrt

class CalculadoraActivity : AppCompatActivity() {

    private val ARCHIVO_HISTORIAL = "historial_operaciones.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculadora)

        val btnVerHistorial = findViewById<Button>(R.id.btnVerHistorial)
        val tvHistorialCompleto = findViewById<TextView>(R.id.tvHistorialCompleto)

        val etNum1 = findViewById<EditText>(R.id.etNum1)
        val etNum2 = findViewById<EditText>(R.id.etNum2)
        val tvResultado = findViewById<TextView>(R.id.tvResultadoCalc)

        val btnSuma = findViewById<ImageButton>(R.id.btnSuma)
        val btnResta = findViewById<ImageButton>(R.id.btnResta)
        val btnMulti = findViewById<ImageButton>(R.id.btnMulti)
        val btnDiv = findViewById<ImageButton>(R.id.btnDiv)
        val btnExp = findViewById<ImageButton>(R.id.btnExp)
        val btnRaiz = findViewById<ImageButton>(R.id.btnRaiz)
        val btnVolver = findViewById<Button>(R.id.btnVolverCalc)

        btnVerHistorial.setOnClickListener {
            val textoHistorial = leerHistorial()
            if (textoHistorial.isEmpty()) {
                tvHistorialCompleto.text = "El historial está vacío."
            } else {
                tvHistorialCompleto.text = "Historial Guardado:\n\n$textoHistorial"
            }
        }

        btnVolver.setOnClickListener { finish() }

        fun obtenerValores(requiereNum2: Boolean = true): Pair<Double, Double>? {
            val txt1 = etNum1.text.toString()
            val txt2 = etNum2.text.toString()

            //Verificamos que no existan valores vacios
            if (txt1.isEmpty()) {
                etNum1.error = getString(R.string.err_campo_vacio)
                vibrarDispositivo()
                return null
            }
            val n1 = txt1.toDouble()

            if (!requiereNum2) return Pair(n1, 0.0)

            if (txt2.isEmpty()) {
                etNum2.error = getString(R.string.err_campo_vacio)
                vibrarDispositivo()
                return null
            }
            val n2 = txt2.toDouble()

            return Pair(n1, n2)
        }

        //FUNCIONES de operaciones
        btnSuma.setOnClickListener {
            obtenerValores()?.let { (n1, n2) ->
                val res = n1 + n2
                mostrarYGuardar("$n1 + $n2 = $res", res, tvResultado)
            }
        }

        btnResta.setOnClickListener {
            obtenerValores()?.let { (n1, n2) ->
                val res = n1 - n2
                mostrarYGuardar("$n1 - $n2 = $res", res, tvResultado)
            }
        }

        btnMulti.setOnClickListener {
            obtenerValores()?.let { (n1, n2) ->
                val res = n1 * n2
                mostrarYGuardar("$n1 * $n2 = $res", res, tvResultado)
            }
        }

        btnDiv.setOnClickListener {
            obtenerValores()?.let { (n1, n2) ->
                if (n2 == 0.0) {
                    etNum2.error = getString(R.string.err_div_zero)
                } else {
                    val res = n1 / n2
                    mostrarYGuardar("$n1 / $n2 = $res", res, tvResultado)
                }
            }
        }

        btnExp.setOnClickListener {
            obtenerValores()?.let { (n1, n2) ->
                val res = n1.pow(n2)
                mostrarYGuardar("$n1 ^ $n2 = $res", res, tvResultado)
            }
        }

        btnRaiz.setOnClickListener {
            obtenerValores(requiereNum2 = false)?.let { (n1, _) ->
                if (n1 < 0) {
                    etNum1.error = getString(R.string.err_raiz_negativa)
                } else {
                    val res = sqrt(n1)
                    mostrarYGuardar("√$n1 = $res", res, tvResultado)
                }
            }
        }
    }

    // Funcion para el historial
    private fun leerHistorial(): String {
        return try {
            //interno NO require permiso
            openFileInput(ARCHIVO_HISTORIAL).bufferedReader().use { reader ->
                reader.readText() // Lee el archivo
            }
        } catch (e: Exception) {
            // Si no hay nada, devuelve un texto vacío
            ""
        }
    }
    private fun mostrarYGuardar(operacion: String, resultado: Double, tv: TextView) {
        tv.text = "Resultado: $resultado"
        guardarEnHistorial(operacion)
    }

    // Persistencia Interna
    private fun guardarEnHistorial(registro: String) {
        try {
            val linea = "$registro\n"
            openFileOutput(ARCHIVO_HISTORIAL, Context.MODE_APPEND).use {
                it.write(linea.toByteArray())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
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