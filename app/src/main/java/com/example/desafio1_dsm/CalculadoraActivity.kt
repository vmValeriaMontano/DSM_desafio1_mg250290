package com.example.desafio1_dsm

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

        val etNum1 = findViewById<EditText>(R.id.etNum1)
        val etNum2 = findViewById<EditText>(R.id.etNum2)
        val tvResultado = findViewById<TextView>(R.id.tvResultadoCalc)

        val btnSuma = findViewById<Button>(R.id.btnSuma)
        val btnResta = findViewById<Button>(R.id.btnResta)
        val btnMulti = findViewById<Button>(R.id.btnMulti)
        val btnDiv = findViewById<Button>(R.id.btnDiv)
        val btnExp = findViewById<Button>(R.id.btnExp)
        val btnRaiz = findViewById<Button>(R.id.btnRaiz)
        val btnVolver = findViewById<Button>(R.id.btnVolverCalc)

        btnVolver.setOnClickListener { finish() }

        fun obtenerValores(requiereNum2: Boolean = true): Pair<Double, Double>? {
            val txt1 = etNum1.text.toString()
            val txt2 = etNum2.text.toString()

            //Verificamos que no existan valores vacios
            if (txt1.isEmpty()) {
                etNum1.error = getString(R.string.err_campo_vacio)
                return null
            }
            val n1 = txt1.toDouble()

            if (!requiereNum2) return Pair(n1, 0.0)

            if (txt2.isEmpty()) {
                etNum2.error = getString(R.string.err_campo_vacio)
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
}