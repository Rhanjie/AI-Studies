package Lab7


class State(
    private val waitingCannibals: Int,
    private val waitingMissionaries: Int,
    private val numberOfSeats: Int,
    private val boatPosition: Direction,
    private var transportedCannibals: Int = 0,
    private var transportedMissionaries: Int = 0
) {

    enum class Direction { LEFT, RIGHT }
    public var parentState: State? = null

    fun isFinished(): Boolean {
        return waitingCannibals == 0 && waitingMissionaries == 0
    }

    private fun isMoveCorrect(): Boolean {
        return (waitingMissionaries >= 0 && transportedMissionaries >= 0 && waitingCannibals >= 0 && transportedCannibals >= 0
                && (waitingMissionaries == 0 || waitingMissionaries >= waitingCannibals)
                && (transportedMissionaries == 0 || transportedMissionaries >= transportedCannibals))
    }

    private fun getFactorial(number: Int): Int {
        return if (number < 0) 0
        else when (number) {
            0 -> 1
            1 -> number
            else -> number * getFactorial(number - 1)
        }
    }

    private fun combinationRepetitionUtil(combinations: ArrayList<ArrayList<Int>>, chosen: IntArray, arr: IntArray,
                                          index: Int, r: Int, start: Int, end: Int): ArrayList<ArrayList<Int>> {
        if (index == r) {
            val combination = arrayListOf<Int>()
            for (i in 0 until r) {
                combination.add(arr[chosen[i]])
            }

            combinations.add(combination)
            return combinations
        }

        for (i in start..end) {
            chosen[index] = i

            combinationRepetitionUtil(combinations, chosen, arr, index + 1, r, i, end)
        }

        return combinations
    }

    private fun combinationRepetition(arr: IntArray, r: Int): List<List<Int>> {
        val chosen = IntArray(r + 1)
        val combinations = arrayListOf<ArrayList<Int>>();

        return combinationRepetitionUtil(combinations, chosen, arr, 0, r, 0, arr.size - 1)
    }

    fun checkPossibleMoves(): List<State> {
        val possibleMoves: MutableList<State> = ArrayList()

        //0 - puste miejsce, 1 - misjonarz, 2 - kanibal
        val types = intArrayOf(0, 1, 2)
        val moves = combinationRepetition(types, numberOfSeats)
        for (move in moves) {
            val movedMissionaries = move.count { it == 1 }
            val movedCannibals = move.count { it == 2 }

            if (movedMissionaries == 0 && movedCannibals == 0)
                continue

            var multiplierForDirection = 1
            var direction = Direction.RIGHT
            if (boatPosition == Direction.RIGHT) {
                multiplierForDirection = -1
                direction = Direction.LEFT
            }

            checkTheMove(
                possibleMoves, State(
                    waitingCannibals - movedCannibals * multiplierForDirection,
                    waitingMissionaries - movedMissionaries * multiplierForDirection,

                    numberOfSeats, direction,

                    transportedCannibals + movedCannibals * multiplierForDirection,
                    transportedMissionaries + movedMissionaries * multiplierForDirection
                )
            )
        }

        return possibleMoves
    }

    private fun checkTheMove(possibleMoves: MutableList<State>, newState: State) {
        if (newState.isMoveCorrect()) {
            newState.parentState = this

            possibleMoves.add(newState)
        }
    }

    fun displayMove() {
        when (boatPosition) {
            Direction.LEFT ->
                println("Misjonarze $waitingMissionaries, Kanibale $waitingCannibals [LODKA] |=-=-=-=-=|         Misjonarze $transportedMissionaries, Kanibale $transportedCannibals")

            Direction.RIGHT ->
                println("Misjonarze $waitingMissionaries, Kanibale $waitingCannibals         |=-=-=-=-=| [LODKA] Misjonarze $transportedMissionaries, Kanibale $transportedCannibals")
        }
    }
}