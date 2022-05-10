package Lab7

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

private fun bfsAlgorithm(initialState: State): State? {
    if (initialState.isFinished()) {
        return initialState
    }
    
    val queue: Queue<State> = LinkedList<State>()
    val checked: MutableSet<State> = HashSet<State>()
    queue.add(initialState)
    
    while (true) {
        if (queue.isEmpty()) {
            return null
        }

        val currentState: State = queue.poll()
        checked.add(currentState)

        val possibleMoves: List<State> = currentState.checkPossibleMoves()
        for (move in possibleMoves) {
            if (!checked.contains(move) || !queue.contains(move)) {
                if (move.isFinished()) {
                    return move
                }

                queue.add(move)
            }
        }
    }
}

private fun displaySolution(solution: State?) {
    if (solution == null) {
        print("Nie znaleziono zadnych rozwiazan!")
        
        return
    }

    val path: MutableList<State> = ArrayList<State>()
    var currentState: State? = solution
    
    while (currentState != null) {
        path.add(currentState)
        
        currentState = currentState.parentState
    }

    val step = path.size - 1
    for (i in step downTo 0) {
        currentState = path[i]

        currentState.displayMove()
        if (currentState.isFinished()) {
            println("\nIlość wszystkich ruchów: $step")

            return
        }
    }
}

fun main(args: Array<String>) {
    println("Marcin Dyla - Lab6 - Misjonarze i kanibale\n")

    var text: String? = null

    print("Podaj liczbe misjonarzy: ")
    text = readLine()
    val numberOfMissionaries: Int = text!!.toInt()

    print("Podaj liczbe kanibali: ")
    text = readLine()
    val numberOfCannibals: Int = text!!.toInt()

    print("Podaj ilość miejsc na łódce: ")
    text = readLine()
    val numberOfSeats: Int = text!!.toInt()

    println("")

    val startState = State(numberOfCannibals, numberOfMissionaries, numberOfSeats, State.Direction.LEFT, 0, 0)
    val solution: State? = bfsAlgorithm(startState)

    displaySolution(solution)
}