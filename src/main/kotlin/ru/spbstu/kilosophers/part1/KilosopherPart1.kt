package ru.spbstu.kilosophers.part1

import ru.spbstu.kilosophers.AbstractKilosopher
import ru.spbstu.kilosophers.Action
import ru.spbstu.kilosophers.ActionKind
import ru.spbstu.kilosophers.Fork
import ru.spbstu.kilosophers.part1.KilosopherPart1.State.*

// Resource hierarchy
// Take left, take right, eat, drop right, drop left, think, repeat
class KilosopherPart1(left: Fork, right: Fork, private val index: Int) : AbstractKilosopher(left, right) {

    internal enum class State {
        WAITS_BOTH,
        WAITS_RIGHT,
        EATS,
        HOLDS_BOTH,
        HOLDS_LEFT,
        THINKS
    }

    private var state = WAITS_BOTH

    override fun nextAction(): Action {
        return when (state) {
            WAITS_BOTH -> ActionKind.TAKE_LEFT(10)
            WAITS_RIGHT -> ActionKind.TAKE_RIGHT(10)
            EATS -> ActionKind.EAT(50)
            HOLDS_BOTH -> ActionKind.DROP_RIGHT(10)
            HOLDS_LEFT -> ActionKind.DROP_LEFT(10)
            THINKS -> ActionKind.THINK(100)
        }
    }

    override fun handleResult(action: Action, result: Boolean) {
        state = when (action.kind) {
            ActionKind.TAKE_LEFT -> if (result) WAITS_RIGHT else WAITS_BOTH
            ActionKind.TAKE_RIGHT -> if (result) EATS else HOLDS_LEFT
            ActionKind.EAT -> HOLDS_BOTH
            ActionKind.DROP_LEFT -> if (result) THINKS else HOLDS_LEFT
            ActionKind.DROP_RIGHT -> if (result) HOLDS_LEFT else HOLDS_BOTH
            ActionKind.THINK -> WAITS_BOTH
        }
    }

    override fun toString(): String {
        return "KilosopherP1 #$index"
    }
}