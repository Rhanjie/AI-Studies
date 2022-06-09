package Lab5

class State(
    private val isFarmerTransported: Boolean,
    private val isFoxTransported: Boolean,
    private val isGooseTransported: Boolean,
    private val isGrainTransported: Boolean,
    private val boatPosition: Direction,
) {

    enum class Direction { LEFT, RIGHT }
    public var parentState: State? = null

    fun isFinished(): Boolean {
        return isFarmerTransported && isFoxTransported && isGooseTransported && isGrainTransported
    }

    private fun isMoveCorrect(): Boolean {
        return !(!isFoxTransported && !isGooseTransported && isFarmerTransported) && //ges bez opieki
                !(isFoxTransported && isGooseTransported && !isFarmerTransported) && //ges bez opieki

                !(!isGooseTransported && !isGrainTransported && isFarmerTransported) && //ziarno bez opieki
                !(isGooseTransported && isGrainTransported && !isFarmerTransported)     //ziarno bez opieki
    }

    fun checkPossibleMoves(): List<State> {
        val possibleMoves: MutableList<State> = ArrayList()

        if (boatPosition == Direction.LEFT) {
            checkTheMove(possibleMoves, State(true, isFoxTransported, isGooseTransported, isGrainTransported, Direction.RIGHT))
            checkTheMove(possibleMoves, State(true, true, isGooseTransported, isGrainTransported, Direction.RIGHT))
            checkTheMove(possibleMoves, State(true, isFoxTransported, true, isGrainTransported, Direction.RIGHT))
            checkTheMove(possibleMoves, State(true, isFoxTransported, isGooseTransported, true, Direction.RIGHT))

            return possibleMoves
        }

        checkTheMove(possibleMoves, State(false, isFoxTransported, isGooseTransported, isGrainTransported, Direction.LEFT))
        checkTheMove(possibleMoves, State(false, false, isGooseTransported, isGrainTransported, Direction.LEFT))
        checkTheMove(possibleMoves, State(false, isFoxTransported, false, isGrainTransported, Direction.LEFT))
        checkTheMove(possibleMoves, State(false, isFoxTransported, isGooseTransported, false, Direction.LEFT))

        return possibleMoves
    }

    private fun checkTheMove(possibleMoves: MutableList<State>, newState: State) {
        if (newState.isMoveCorrect()) {
            newState.parentState = this

            possibleMoves.add(newState)
        }
    }

    fun displayMove() {
        var leftSide = ""
        var rightSide = ""

        if (boatPosition == Direction.RIGHT)
        rightSide += "[LODKA] ";

        if (isFarmerTransported)
            rightSide += "Farmer ";
        else leftSide += "Farmer ";

        if (isFoxTransported)
            rightSide += "Lis ";
        else leftSide += "Lis ";

        if (isGooseTransported)
            rightSide += "Ges ";
        else leftSide += "Ges ";

        if (isGrainTransported)
            rightSide += "Ziarno ";
        else leftSide += "Ziarno ";

        if (boatPosition == Direction.LEFT)
            leftSide += "[LODKA] ";

        println("$leftSide|=-=-=-=-=| $rightSide")
    }
}