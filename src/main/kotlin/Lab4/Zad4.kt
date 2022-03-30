package Lab4

import java.util.*
import kotlin.system.exitProcess

var size = 8

private fun prepareChessboard(chessboard: Array<CharArray>) {
    for (i in 0 until size) {
        Arrays.fill(chessboard[i], '–')
    }
}

private fun checkCollisions(chessboard: Array<CharArray>, row: Int, column: Int): Boolean {
    //First condition
    for (i in 0 until row) {
        if (chessboard[i][column] == '#') {
            return false
        }
    }

    //Second condition
    var i = row
    var j = column
    while (i >= 0 && j >= 0) {
        if (chessboard[i][j] == '#') {
            return false
        }

        i -= 1
        j -= 1
    }

    //Third condition
    i = row
    j = column
    while (i >= 0 && j < size) {
        if (chessboard[i][j] == '#') {
            return false
        }

        i -= 1
        j += 1
    }

    return true
}

private fun renderChessboard(chessboard: Array<CharArray>) {
    for (y in 0 until size) {
        for (x in 0 until size) {
            print("|${chessboard[y][x]}| ")
        }

        println()
    }
    println()
}

private fun hetmani(chessboard: Array<CharArray>, row: Int) {
    if (row == size) {
        renderChessboard(chessboard)

        exitProcess(0)
    }
    
    for (column in 0 until size) {
        if (checkCollisions(chessboard, row, column)) {
            chessboard[row][column] = '#'
            hetmani(chessboard, row + 1)
            chessboard[row][column] = '–'
        }
    }
}

fun main(args: Array<String>) {
    println("Marcin Dyla - Lab4 - Problem ośmiu hetmanów\n")

    print("Podaj rozmiar szachownicy: ")
    val text = readLine()
    size = text!!.toInt()

    val chessboard = Array(size) { CharArray(size) }

    prepareChessboard(chessboard);
    hetmani(chessboard, 0)
}