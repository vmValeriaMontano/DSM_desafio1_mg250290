package com.example.desafio1_dsm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var btn3: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btn1 = findViewById(R.id.button1)
        btn2 = findViewById(R.id.button2)
        btn3 = findViewById(R.id.button3)

        btn1.setOnClickListener {
            // Acción para el Botón 1
            val ir= Intent(this, Promedio::class.java)
            startActivity(ir)
        }

        btn2.setOnClickListener {
            // Acción para el Botón 2
            val ir= Intent(this, Descuentos::class.java)
            startActivity(ir)

        }

        btn3.setOnClickListener {
            // Acción para el Botón 3
            val ir= Intent(this, Calculadora::class.java)
            startActivity(ir)

        }

    }//Fin onCreate
}