package Lab8

import kotlin.math.abs
import kotlin.random.Random

fun predictLearn(row: FloatArray, weights: FloatArray): Float {
    var activation = weights[0]
    for (i in 0 until row.size - 1) {
        activation += weights[i + 1] * row[i]
    }

    return if (activation >= 0.0) 1.0f else 0f
}

fun splaAlgorithm(train: Array<FloatArray>, l_rate: Float): FloatArray {
    val weights = FloatArray(train[0].size)
    for (i in 0 until train[0].size) {
        weights[i] = Random.nextFloat() / 5.0f
    }

    while (true) {
        var sumError = 0.0f

        for (i in 0 until train.size - 1) {
            val prediction = predictLearn(train[i], weights)
            val lastIndex: Int = train[i].size - 1
            val error = train[i][lastIndex] - prediction

            sumError += error * error

            if (abs(error) < 0.01f)
                continue

            weights[0] = weights[0] + l_rate * error

            for (j in 0 until train[i].size - 1) {
                weights[j + 1] = weights[j + 1] + l_rate * error * train[i][j]
            }
        }

        if (sumError < 0.01) {
            break
        }
    }

    return weights
}

fun main(args: Array<String>) {
    println("Marcin Dyla - Lab8 - Perceptron\n")

    //Niestety nie udalo sie znalezc optymalny zestaw wag
    //Dla AND sytuacja (0, 1) daje 1 zamiast 0
    //Dla XOR sytuacja (1, 1) daje 1 zamiast 0

    val rate = 0.0001f

    val andTrainData = arrayOf(
        floatArrayOf(0f, 0f, 0f),
        floatArrayOf(1f, 0f, 0f),
        floatArrayOf(1f, 1f, 1f),
        floatArrayOf(0f, 1f, 0f)
    )

    val orTrainData = arrayOf(
        floatArrayOf(0f, 0f, 0f),
        floatArrayOf(1f, 0f, 1f),
        floatArrayOf(0f, 1f, 1f),
        floatArrayOf(1f, 1f, 1f)
    )

    val xorTrainData = arrayOf(
        floatArrayOf(0f, 0f, 0f),
        floatArrayOf(1f, 0f, 1f),
        floatArrayOf(0f, 1f, 1f),
        floatArrayOf(1f, 1f, 0f)
    )

    println("[AND]")

    try {
        val and_weights = splaAlgorithm(andTrainData, rate)
        println("Wagi: ${and_weights.contentToString()}")

        println("(0, 0) -> " + predictLearn(floatArrayOf(0f, 0f, 0f), and_weights))
        println("(1, 0) -> " + predictLearn(floatArrayOf(1f, 0f, 0f), and_weights))
        println("(0, 1) -> " + predictLearn(floatArrayOf(0f, 1f, 0f), and_weights))
        println("(1, 1) -> " + predictLearn(floatArrayOf(1f, 1f, 0f), and_weights))
    }

    catch (exception: Exception) {
        exception.printStackTrace()
    }

    println()
    println("[OR]")

    try {
        val or_weights = splaAlgorithm(orTrainData, rate)
        println("Wagi: ${or_weights.contentToString()}")

        println("(0, 0) -> " + predictLearn(floatArrayOf(0f, 0f, 0f), or_weights))
        println("(1, 0) -> " + predictLearn(floatArrayOf(1f, 0f, 0f), or_weights))
        println("(0, 1) -> " + predictLearn(floatArrayOf(0f, 1f, 0f), or_weights))
        println("(1, 1) -> " + predictLearn(floatArrayOf(1f, 1f, 0f), or_weights))
    }

    catch (exception: Exception) {
        exception.printStackTrace()
    }

    println()
    println("[XOR]")

    try {
        val xor_weights = splaAlgorithm(xorTrainData, rate)
        println("Wagi: ${xor_weights.contentToString()}")

        println("(0, 0) -> " + predictLearn(floatArrayOf(0f, 0f, 0f), xor_weights))
        println("(1, 0) -> " + predictLearn(floatArrayOf(1f, 0f, 0f), xor_weights))
        println("(0, 1) -> " + predictLearn(floatArrayOf(0f, 1f, 0f), xor_weights))
        println("(1, 1) -> " + predictLearn(floatArrayOf(1f, 1f, 0f), xor_weights))
    }

    catch (exception: Exception) {
        exception.printStackTrace()
    }
}