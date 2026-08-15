package com.example.desafio1_dsm

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import java.text.DecimalFormat

class PromedioActivity : AppCompatActivity() {

    //uso de CHANNEL_ID para decirle
    //al usuario que se calculo su nota
    private val CHANNEL_ID = "canal_promedio"

    //Objeto que espera la respuesta del usuario al cartel de permiso
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "Permiso de notificaciones concedido", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "No recibirás notificaciones", Toast.LENGTH_LONG).show()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        // Revisamos el permiso
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {

                // Si no tiene el permiso, lanzamos el cartel flotante
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promedio)

        createNotificationChannel()

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etNota1 = findViewById<EditText>(R.id.etNota1)
        val etNota2 = findViewById<EditText>(R.id.etNota2)
        val etNota3 = findViewById<EditText>(R.id.etNota3)
        val etNota4 = findViewById<EditText>(R.id.etNota4)
        val etNota5 = findViewById<EditText>(R.id.etNota5)
        val tvResultado = findViewById<TextView>(R.id.tvResultadoPromedio)
        val btnCalcular = findViewById<Button>(R.id.btnCalcularPromedio)
        val btnVolver = findViewById<Button>(R.id.btnVolverMenu)

        btnVolver.setOnClickListener { finish() }

        //Ponemos un onClick para que al
        // presioarlo se accione su logica
        btnCalcular.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val inputs = listOf(etNota1, etNota2, etNota3, etNota4, etNota5)

            //Verificar que no este vacio y evitar errores
            if (nombre.isEmpty()) {
                etNombre.error = getString(R.string.err_campo_vacio)
                vibrarDispositivo()
                return@setOnClickListener
            }

            val notas = mutableListOf<Double>()
            for (input in inputs) {
                val txt = input.text.toString()
                if (txt.isEmpty()) {
                    input.error = getString(R.string.err_campo_vacio)
                    return@setOnClickListener
                }
                val valor = txt.toDoubleOrNull()
                if (valor == null || valor < 0.0 || valor > 10.0) {
                    input.error = getString(R.string.err_nota_invalida)
                    return@setOnClickListener
                }
                notas.add(valor)
            }

            // lógica
            val promedio = calcularPromedio(notas)
            val df = DecimalFormat("#.00")
            val promedioFormateado = df.format(promedio)

            val estado = if (promedio >= 6.0) "APROBADO" else "REPROBADO"
            val mensajeFinal = "$nombre: Promedio = $promedioFormateado ($estado)"

            tvResultado.text = mensajeFinal

            // Disparar Notificación al usuario
            enviarNotificacion("Resultado de Promedio", mensajeFinal)
        }
    } //Fin do OnCreate

    // Lógica matemática
    private fun calcularPromedio(notas: List<Double>): Double {
        // Ejemplo de ponderaciones iguales (20% cada una)
        return notas.sum() / notas.size
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Promedio Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun enviarNotificacion(titulo: String, mensaje: String) {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(titulo)
            .setContentText(mensaje)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, builder.build())
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