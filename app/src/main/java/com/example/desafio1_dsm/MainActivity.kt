package com.example.desafio1_dsm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var btnEjercicio1: Button
    private lateinit var btnEjercicio2: Button
    private lateinit var btnEjercicio3: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnEjercicio1 = findViewById(R.id.Promediobtn)
        btnEjercicio2 = findViewById(R.id.Salariobtn)
        btnEjercicio3 = findViewById(R.id.Calculadorabtn)

        btnEjercicio1.setOnClickListener {
            // Acción para el Botón 1
            val ir= Intent(this, Promedio::class.java)
            startActivity(ir)
        }

        btnEjercicio2.setOnClickListener {
            // Acción para el Botón 2
            val ir= Intent(this, Descuentos::class.java)
            startActivity(ir)

        }

        btnEjercicio3.setOnClickListener {
            // Acción para el Botón 3
            val ir= Intent(this, Calculadora::class.java)
            startActivity(ir)

        }

    }//Fin onCreate
}