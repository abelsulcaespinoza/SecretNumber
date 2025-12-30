package com.parker.secretnumber.views

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.parker.secretnumber.R

class GameActivity : AppCompatActivity() {
    // Variables globales para el juego
    private var secretNumber = 0
    private var attemptsLeft = 0
    private var maxRange = 0
    private var isGameOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)

        // Configuración de bordes (System Bars)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Recibir datos del Intent
        maxRange = intent.getIntExtra("MAX_RANGE", 10)

        // 2. Inicializar el juego
        attemptsLeft = when (maxRange) {
            10 -> 3
            30 -> 5
            50 -> 6
            100 -> 8
            else -> 1
        }
        secretNumber = (1..maxRange).random()

        // 3. Referenciar Vistas
        val tvRange = findViewById<TextView>(R.id.tvRangeIndicator)
        val tvAttempts = findViewById<TextView>(R.id.tvAttemptsCount)
        val tvMessage = findViewById<TextView>(R.id.tvMessage)
        val etGuess = findViewById<EditText>(R.id.etUserGuess)
        val btnAction = findViewById<Button>(R.id.btnCheckNumber)

        // Iniciar el juego por primera vez
        reiniciarJuego(tvAttempts, tvMessage, etGuess, btnAction, tvRange)

        btnAction.setOnClickListener {
            if (isGameOver) {
                // Si el juego terminó, el botón sirve para REINICIAR
                reiniciarJuego(tvAttempts, tvMessage, etGuess, btnAction, tvRange)
            } else {
                // Si el juego sigue, el botón sirve para COMPROBAR
                comprobarNumero(etGuess, tvAttempts, tvMessage, btnAction)
            }
        }

    }

    private fun comprobarNumero(etGuess: EditText, tvAttempts: TextView, tvMessage: TextView, btn: Button) {
        val userText = etGuess.text.toString()

        if (userText.isNotEmpty()) {
            val userGuess = userText.toInt()
            attemptsLeft--
            tvAttempts.text = "Intentos restantes: $attemptsLeft"

            when {
                userGuess == secretNumber -> {
                    tvMessage.text = "¡Felicidades! Lo adivinaste."
                    tvMessage.setTextColor(Color.parseColor("#B2DFDB"))
                    prepararBotonReinicio(btn, etGuess)
                }
                attemptsLeft <= 0 -> {
                    tvMessage.text = "¡Game Over! El número era $secretNumber."
                    tvMessage.setTextColor(Color.parseColor("#FFCDD2"))
                    prepararBotonReinicio(btn, etGuess)
                }
                userGuess < secretNumber -> {
                    tvMessage.text = "El número secreto es MAYOR ↑."
                    tvMessage.setTextColor(Color.parseColor("#FFECB3"))
                }
                else -> {
                    tvMessage.text = "El número secreto es MENOR ↓."
                    tvMessage.setTextColor(Color.parseColor("#FFCDD2"))
                }
            }
            etGuess.text.clear()
        } else {
            etGuess.error = "Escribe un número."
        }
    }

    private fun prepararBotonReinicio(btn: Button, etGuess: EditText) {
        isGameOver = true
        btn.text = "Volver a jugar"
        etGuess.isEnabled = false // Bloqueamos el input
        // Opcional: Cambiar el color del botón a un gris o azul para indicar reinicio
        btn.backgroundTintList = android.content.res.ColorStateList.valueOf(Color.LTGRAY)
    }

    private fun reiniciarJuego(tvAttempts: TextView, tvMessage: TextView, etGuess: EditText, btn: Button, tvRange: TextView) {
        // 1. Resetear variables
        isGameOver = false
        attemptsLeft = when (maxRange) {
            10 -> 3
            30 -> 5
            50 -> 6
            100 -> 8
            else -> 1
        }
        secretNumber = (1..maxRange).random()

        // 2. Resetear Interfaz
        btn.text = "Comprobar número"
        etGuess.isEnabled = true
        etGuess.text.clear()
        tvMessage.text = ""
        tvAttempts.text = "Intentos restantes: $attemptsLeft"

        // 3. Volver a aplicar los colores originales de dificultad
        setupDifficultyUI(maxRange, tvRange, btn)
    }

    private fun setupDifficultyUI(range: Int, tvRange: TextView, btn: Button) {
        tvRange.text = "Nivel: 1 al $range"
        val colorHex = when (range) {
            10 -> "#B2DFDB"
            30 -> "#FFECB3"
            50 -> "#FFCDD2"
            100 -> "#D1C4E9"
            else -> "#9E9E9E"
        }
        val colorInt = Color.parseColor(colorHex)
        btn.backgroundTintList = android.content.res.ColorStateList.valueOf(colorInt)
    }
}