package Lab5

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
    println("Marcin Dyla - Lab5 - Lis, ges i ziarno\n")

    val startState = State(false, false, false, false, State.Direction.LEFT)
    val solution: State? = bfsAlgorithm(startState)

    displaySolution(solution)
}