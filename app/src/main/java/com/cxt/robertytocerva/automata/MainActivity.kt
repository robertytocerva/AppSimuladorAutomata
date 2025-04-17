package com.cxt.robertytocerva.automata

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
//    val circleView = findViewById<View>(R.id.circleView)
//    val drawable = circleView.background.mutate() as GradientDrawable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Asegúrate que tu XML se llame así o ajusta el nombre

        val editText = findViewById<EditText>(R.id.editTextText)
        editText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                editText.text.clear()
            }
        }
        val tvTran = findViewById<TextView>(R.id.tvTran)
        val btnEvaluar = findViewById<Button>(R.id.btnEvaluation)
        val tvResultado = findViewById<TextView>(R.id.textView4)

        btnEvaluar.setOnClickListener {
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(tvResultado.windowToken, 0)
            val cadena = editText.text.toString().trim()
            val esValida = validarCadena(cadena, tvTran)

            tvResultado.text = if (esValida) "CADENA VÁLIDA" else "CADENA INVÁLIDA"
//            drawable.setColor(Color.RED)
        }
        gradientAnimation()

    }

    fun gradientAnimation() {
        val layout = findViewById<ConstraintLayout>(R.id.main)
        val transition = layout.background as? TransitionDrawable

        var forward = true
        val handler = Handler(Looper.getMainLooper())
        val duration = 2000L // duración de cada transición

        val runnable = object : Runnable {
            override fun run() {
                if (forward) {
                    transition?.startTransition(duration.toInt())
                } else {
                    transition?.reverseTransition(duration.toInt())
                }
                forward = !forward
                handler.postDelayed(this, duration)
            }
        }

        handler.post(runnable)
    }

    fun validarCadena(cadena: String, tvTran: TextView): Boolean {
        var estado = "q0"
        tvTran.text = "Estado inicial: $estado\n"

        for ((indice, caracter) in cadena.withIndex()) {
            val nuevoEstado = transicion(estado, caracter)

            if (nuevoEstado == null) {
                tvTran.append("Entrada inválida: '$caracter' en posición ${indice + 1}, no hay transición desde $estado\n")
                return false
            }

            tvTran.append("Caracter '$caracter' → transita de $estado a $nuevoEstado\n")
            estado = nuevoEstado
        }

        tvTran.append("Estado final: $estado\n")
        return estado == "q2"
    }

    private fun transicion(estado: String, simbolo: Char): String? {
        return when (estado) {
            "q0" -> when (simbolo) {
                'a' -> "q1"
                'b' -> "q3"
                else -> null
            }
            "q1" -> when (simbolo) {
                'a' -> "q1"
                'b' -> "q2"
                else -> null
            }
            "q2" -> when (simbolo) {
                'a', 'b' -> "q3"
                else -> null
            }
            "q3" -> when (simbolo) {
                'a', 'b' -> "q3"
                else -> null
            }
            else -> null
        }
    }
}