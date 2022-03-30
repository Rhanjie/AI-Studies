package Lab3

import java.util.*
import java.util.concurrent.ThreadLocalRandom

data class Cell(
    var x: Int,
    var y: Int
)

data class NextMove(
    var nx: Int = 0,
    var ny: Int = 0
)

private var size: Int = 0

val nextMoves = arrayListOf<Lab3.NextMove>(
    NextMove(1, 2),
    NextMove(1, -2),
    NextMove(2, 1),
    NextMove(2, -1),
    NextMove(-1, 2),
    NextMove(-1, -2),
    NextMove(-2, 1),
    NextMove(-2, -1),
)

//Zalozenia: Poczatkowa pozycja skoczka jest na srodku tablicy
fun simulateMoving(): Boolean {
    val chessboard = IntArray(size * size)
    for (i in 0 until size * size) {
        chessboard[i] = -1
    }

    val sx = size / 2 - 1
    val sy = size / 2 - 1
    val cell = Cell(sx, sy)
    chessboard[cell.y * size + cell.x] = 1

    var nextCell: Cell? = null
    for (i in 0 until size * size - 1) {
        nextCell = getNextMove(chessboard, cell)
        if (nextCell == null) {
            return false
        }
    }

    if (nextCell == null) {
        return false
    }

    if (!checkPossibleReturn(nextCell.x, nextCell.y, sx, sy)) {
        return false
    }

    renderChessboard(chessboard)

    return true
}

fun getNextMove(chessboard: IntArray, cell: Cell): Cell? {
    var minIndex = -1
    var minField = size + 1

    var field: Int = 0

    val nextMove = NextMove();
    val randomStartIndex = ThreadLocalRandom.current().nextInt(1000) % size

    for (amount in 0 until size) {
        val index = (randomStartIndex + amount) % 8

        nextMove.nx = cell.x + nextMoves[index].nx
        nextMove.ny = cell.y + nextMoves[index].ny

        val emptyFieldsAmount = getEmptyFieldsAmount(chessboard, nextMove).also { field = it }

        if (isEmpty(chessboard, nextMove) && emptyFieldsAmount < minField) {
            minIndex = index
            minField = field
        }
    }

    if (minIndex == -1)
        return null

    nextMove.nx = cell.x + nextMoves[minIndex].nx
    nextMove.ny = cell.y + nextMoves[minIndex].ny

    chessboard[nextMove.ny * size + nextMove.nx] = chessboard[cell.y * size + cell.x] + 1

    cell.x = nextMove.nx
    cell.y = nextMove.ny

    return cell
}

fun checkPossibleReturn(x: Int, y: Int, xx: Int, yy: Int): Boolean {
    for (i in 0..7) {
        if (x + nextMoves[i].nx == xx && y + nextMoves[i].ny == yy) {
            return true
        }
    }

    return false
}

fun renderChessboard(chessboard: IntArray) {
    for (i in 0 until size) {
        for (j in 0 until size) {
            System.out.printf("%d\t", chessboard[j * size + i])
        }

        print("\n\n")
    }
}

fun checkBorder(x: Int, y: Int): Boolean {
    return x >= 0 && y >= 0 && x < size && y < size
}

fun isEmpty(chessboard: IntArray, nextMove: NextMove): Boolean {
    return checkBorder(nextMove.nx, nextMove.ny) && chessboard[nextMove.ny * size + nextMove.nx] < 0
}

fun getEmptyFieldsAmount(chessboard: IntArray, nextMove: NextMove): Int {
    var amount = 0;

    for (i in 0 until 8) {
        val secondMove = NextMove(
            nextMove.nx + nextMoves[i].nx,
            nextMove.ny + nextMoves[i].ny
        )

        if (isEmpty(chessboard, secondMove)) {
            amount++;
        }
    }

    return amount;
}

fun main(args: Array<String>) {
    println("Marcin Dyla - Lab3 - Problem skoczka szachowego z heurystyką\n")

    print("Podaj parzysta wielkosc szachownicy: ")
    val scanner = Scanner(System.`in`)

    size = scanner.nextInt()

    if (size % 2 != 0) {
        print("Musisz podac parzysty size tablicy!")
        return
    }

    var isFound: Boolean
    do {
        isFound = simulateMoving()
    } while (!isFound)
}