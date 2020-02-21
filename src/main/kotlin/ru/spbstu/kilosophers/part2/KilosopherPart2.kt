package ru.spbstu.kilosophers.part2

import java.util.concurrent.Semaphore

class KilosopherPart2(left: AdvancedFork,
                      right: AdvancedFork,
                      private val index: Int,
                      servietteManager: Semaphore) : AdvancedAbstractKilosopher(left, right, servietteManager) {

    internal enum class State {
        WAITS_SERVIETTE,
        WAITS_BOTH,
        WAITS_RIGHT,
        EATS,
        HOLDS_SERVIETTE,
        HOLDS_BOTH,
        HOLDS_LEFT,
        THINKS
    }



    private var state = State.WAITS_BOTH

    override fun nextAction(): AdvancedAction {

        return when (state) {
            State.WAITS_BOTH -> AdvancedActionKind.TAKE_LEFT(10)
            State.WAITS_RIGHT -> AdvancedActionKind.TAKE_RIGHT(10)
            State.EATS -> AdvancedActionKind.EAT(50)
            State.HOLDS_BOTH -> AdvancedActionKind.DROP_RIGHT(10)
            State.HOLDS_LEFT -> AdvancedActionKind.DROP_LEFT(10)
            State.THINKS -> AdvancedActionKind.THINK(100)
            State.WAITS_SERVIETTE -> AdvancedActionKind.TAKE_SERVIETTE(10)
            State.HOLDS_SERVIETTE -> AdvancedActionKind.DROP_SERVIETTE(10)
        }
    }

    override fun handleResult(action: AdvancedAction, result: Boolean) {
//        if (index == 0)
        println(toString() + "|" + action + "|" + result)

        state = when (action.kind) {
            AdvancedActionKind.TAKE_LEFT -> if (result) State.WAITS_RIGHT else State.WAITS_BOTH
            AdvancedActionKind.TAKE_RIGHT -> if (result) State.WAITS_SERVIETTE else State.HOLDS_LEFT
            AdvancedActionKind.EAT -> State.HOLDS_SERVIETTE
            AdvancedActionKind.DROP_LEFT -> if (result) State.THINKS else State.HOLDS_LEFT
            AdvancedActionKind.DROP_RIGHT -> if (result) State.HOLDS_LEFT else State.HOLDS_BOTH
            AdvancedActionKind.THINK -> State.WAITS_BOTH
            AdvancedActionKind.TAKE_SERVIETTE -> if (result) State.EATS else State.WAITS_SERVIETTE
            AdvancedActionKind.DROP_SERVIETTE -> if (result) State.HOLDS_BOTH else State.HOLDS_SERVIETTE
        }
    }

    override fun toString(): String {
        return "KilosopherP2 #$index"
    }
}