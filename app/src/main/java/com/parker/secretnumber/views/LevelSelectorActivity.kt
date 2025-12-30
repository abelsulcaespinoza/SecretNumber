package com.parker.secretnumber.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.parker.secretnumber.R

class LevelSelectorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_level_selector)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnEasy = findViewById<Button>(R.id.btnEasy)
        val btnNormal = findViewById<Button>(R.id.btnNormal)
        val btnHard = findViewById<Button>(R.id.btnHard)
        val btnVeryHard = findViewById<Button>(R.id.btnVeryHard)


        btnEasy.setOnClickListener { navigateToGame(10) }
        btnNormal.setOnClickListener { navigateToGame(30) }
        btnHard.setOnClickListener { navigateToGame(50) }
        btnVeryHard.setOnClickListener { navigateToGame(100) }
    }

    private fun navigateToGame(maxRange: Int) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("MAX_RANGE", maxRange)
        startActivity(intent)
    }
}