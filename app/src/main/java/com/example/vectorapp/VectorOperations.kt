package com.example.vectorapp

class VectorOperations {

    fun generateRandomVector(size: Int): List<Double> {
        return List(size) { (Math.random() * 20 - 10).toDouble() }
    }

    fun calculateDotProduct(vector1: List<Double>, vector2: List<Double>): Double? {
        return if (vector1.size == vector2.size) {
            vector1.zip(vector2) { a, b -> a * b }.sum()
        } else {
            null
        }
    }

    fun formatVector(vector: List<Double>): String {
        return vector.joinToString(
            prefix = "[",
            postfix = "]",
            separator = ", "
        ) { "%.2f".format(it) }
    }
}