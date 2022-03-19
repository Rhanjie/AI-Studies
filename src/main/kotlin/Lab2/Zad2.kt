package Lab2

data class NextMove(
    var nx: Int = 0,
    var ny: Int = 0
)

fun tryJump(array: Array<IntArray>, n: Int, x: Int, y: Int, indeks: Int): Boolean {
    val nextMove = NextMove();

    array[x][y] = indeks
    if (indeks == n * n) return true
    for (variant in 1..8) {
        if (tryGetNextMove(array, n, variant, x, y, nextMove)) {
            if (tryJump(array, n, nextMove.nx, nextMove.ny, indeks + 1)) {
                return true
            }
        }
    }

    array[x][y] = 0
    return false
}

private fun tryGetNextMove(array: Array<IntArray>, N: Int, variant: Int, x: Int, y: Int, nextMove: NextMove): Boolean {
    when (variant) {
        1 -> {
            nextMove.nx = x + 1
            nextMove.ny = y - 2
        }
        2 -> {
            nextMove.nx = x + 2
            nextMove.ny = y - 1
        }
        3 -> {
            nextMove.nx = x + 2
            nextMove.ny = y + 1
        }
        4 -> {
            nextMove.nx = x + 1
            nextMove.ny = y + 2
        }
        5 -> {
            nextMove.nx = x - 1
            nextMove.ny = y + 2
        }
        6 -> {
            nextMove.nx = x - 2
            nextMove.ny = y + 1
        }
        7 -> {
            nextMove.nx = x - 2
            nextMove.ny = y - 1
        }
        8 -> {
            nextMove.nx = x - 1
            nextMove.ny = y - 2
        }
        
        else -> println("Nieznany ruch!")
    }
    
    return (nextMove.nx in 0 until N) && (0 <= nextMove.ny) &&
           (nextMove.ny < N) && array[nextMove.nx][nextMove.ny] == 0
}

fun main(args: Array<String>) {
    println("Marcin Dyla - Lab2 - Problem skoczka szachowego\n")

    print("Podaj rozmiar tablicy szachowej: ")
    val text = readLine()
    val size: Int = text!!.toInt()

    val array = Array(size) { IntArray(size) }

    if (tryJump(array, size, 0, 0, 1)) {
        for (i in 0 until size) {
            for (j in 0 until size) {
                val index = array[j][i]

                if (index < 10) {
                    print("$index  ")
                }

                else print("$index ")
            }
            println()
        }
    }

    else println("Skoczkowi nie udalo sie przejsc calej szachownicy odwiedzajac kazde pole tylko raz!")
}