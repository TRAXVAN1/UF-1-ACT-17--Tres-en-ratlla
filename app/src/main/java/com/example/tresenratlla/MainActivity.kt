package com.example.tresenratlla

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var gridLayout: GridLayout
    private lateinit var statusText: TextView
    private var currentPlayer = "X"
    private val board = Array(3) { Array(3) { "" } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridLayout = findViewById(R.id.gridLayout)
        statusText = findViewById(R.id.statusText)

        // Configurar la cuadrícula de botones
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                val button = Button(this)
                button.textSize = 32f
                button.setOnClickListener { onButtonClick(i, j, button) }
                gridLayout.addView(button, GridLayout.LayoutParams().apply {
                    width = 0
                    height = 0
                    columnSpec = GridLayout.spec(j, 1, 1f)
                    rowSpec = GridLayout.spec(i, 1, 1f)
                    setMargins(5, 5, 5, 5)
                })
            }
        }
    }

    private fun onButtonClick(row: Int, col: Int, button: Button) {
        if (button.text.isEmpty()) {
            button.text = currentPlayer
            board[row][col] = currentPlayer
            if (checkWin()) {
                statusText.text = "¡$currentPlayer ha ganado!"
                disableButtons()
            } else if (isDraw()) {
                statusText.text = "¡Es un empate!"
            } else {
                currentPlayer = if (currentPlayer == "X") "O" else "X"
                statusText.text = "Turno de $currentPlayer"
            }
        }
    }

    private fun checkWin(): Boolean {
        // Comprobar filas y columnas
        for (i in 0 until 3) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) return true
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) return true
        }

        // Comprobar diagonales
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) return true
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) return true

        return false
    }

    private fun isDraw(): Boolean {
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                if (board[i][j].isEmpty()) return false
            }
        }
        return true
    }

    private fun disableButtons() {
        for (i in 0 until gridLayout.childCount) {
            (gridLayout.getChildAt(i) as Button).isEnabled = false
        }
    }
}
