package com.example.vectorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var vectorOperations: VectorOperations
    private lateinit var inputTextView: TextView
    private lateinit var resultTextView: TextView
    private lateinit var calculateButton: Button
    private lateinit var generateButton: Button  // ДОБАВЛЕНО

    private var vector1: List<Double> = emptyList()
    private var vector2: List<Double> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vectorOperations = VectorOperations()
        setupViews()
        generateVectors()
    }

    private fun setupViews() {
        inputTextView = findViewById(R.id.inputTextView)
        resultTextView = findViewById(R.id.resultTextView)
        calculateButton = findViewById(R.id.calculateButton)
        generateButton = findViewById(R.id.generateButton)  // ДОБАВЛЕНО

        calculateButton.setOnClickListener {
            calculateDotProduct()
        }

        // ДОБАВЛЕНО - обработчик для новой кнопки
        generateButton.setOnClickListener {
            generateVectors()
        }
    }

    // ВСЁ ОСТАЛЬНОЕ ОСТАЁТСЯ КАК ЕСТЬ ↓
    private fun generateVectors() {
        val vectorSize = 5

        vector1 = vectorOperations.generateRandomVector(vectorSize)
        vector2 = vectorOperations.generateRandomVector(vectorSize)

        val inputText = """
            Вектор 1: ${vectorOperations.formatVector(vector1)}
            Вектор 2: ${vectorOperations.formatVector(vector2)}
            Размерность: $vectorSize
        """.trimIndent()

        inputTextView.text = inputText
        resultTextView.text = "Нажмите кнопку для вычисления"
    }

    private fun calculateDotProduct() {
        val result = vectorOperations.calculateDotProduct(vector1, vector2)

        val resultText = if (result != null) {
            // Создаем детали вычислений
            val details = StringBuilder("Детали вычисления:\n")
            vector1.forEachIndexed { index, value ->
                details.append("${"%.2f".format(value)} × ${"%.2f".format(vector2[index])}")
                if (index < vector1.size - 1) details.append(" + ")
            }
            details.append("\n\n")

            // Объединяем детали и результат
            details.toString() + "Результат: ${"%.4f".format(result)}"
        } else {
            "Ошибка: векторы разной размерности"
        }

        resultTextView.text = resultText
    }
}