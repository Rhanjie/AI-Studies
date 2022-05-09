package Lab6

class State(
    private val waitingMissionaries: Int,
    private val waitingCannibals: Int,
    private val boatPosition: Direction,
    private var transportedMissionaries: Int = 0,
    private var transportedCannibals: Int = 0
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

    fun checkPossibleMoves(): List<State> {
        val possibleMoves: MutableList<State> = ArrayList()
        
        if (boatPosition == Direction.LEFT) {
            checkTheMove(
                possibleMoves, State(
                    waitingCannibals, waitingMissionaries - 2,
                    Direction.RIGHT, transportedCannibals, transportedMissionaries + 2
                )
            )
            
            checkTheMove(
                possibleMoves, State(
                    waitingCannibals - 2, waitingMissionaries,
                    Direction.RIGHT, transportedCannibals + 2, transportedMissionaries
                )
            )

            checkTheMove(
                possibleMoves, State(waitingCannibals - 1, waitingMissionaries - 1,
                    Direction.RIGHT, transportedCannibals + 1, transportedMissionaries + 1
                )
            )

            checkTheMove(
                possibleMoves, State(waitingCannibals, waitingMissionaries - 1,
                    Direction.RIGHT, transportedCannibals, transportedMissionaries + 1
                )
            )

            checkTheMove(
                possibleMoves, State(waitingCannibals - 1, waitingMissionaries,
                    Direction.RIGHT, transportedCannibals + 1, transportedMissionaries
                )
            )

            return possibleMoves
        }

        checkTheMove(
            possibleMoves, State(
                waitingCannibals, waitingMissionaries + 2,
                Direction.LEFT, transportedCannibals, transportedMissionaries - 2
            )
        )

        checkTheMove(
            possibleMoves, State(
                waitingCannibals + 2, waitingMissionaries,
                Direction.LEFT, transportedCannibals - 2, transportedMissionaries
            )
        )

        checkTheMove(
            possibleMoves, State(
                waitingCannibals + 1, waitingMissionaries + 1,
                Direction.LEFT, transportedCannibals - 1, transportedMissionaries - 1
            )
        )

        checkTheMove(
            possibleMoves, State(
                waitingCannibals, waitingMissionaries + 1,
                Direction.LEFT, transportedCannibals, transportedMissionaries - 1
            )
        )

        checkTheMove(
            possibleMoves, State(
                waitingCannibals + 1, waitingMissionaries,
                Direction.LEFT, transportedCannibals - 1, transportedMissionaries
            )
        )
        
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