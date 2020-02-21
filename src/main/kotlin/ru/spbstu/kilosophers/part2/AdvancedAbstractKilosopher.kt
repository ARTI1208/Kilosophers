package ru.spbstu.kilosophers.part2

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.Semaphore

abstract class AdvancedAbstractKilosopher(private val left: AdvancedFork, private val right: AdvancedFork, private val servietteManager: Semaphore) {

    var holdsLeft: Boolean = false
        private set

    var holdsRight: Boolean = false
        private set

    var holdsServiette: Boolean = false
        private set

    var eatDuration: Int = 0
        private set

    private suspend fun doAction(action: AdvancedAction): Boolean {
        return when (action.kind) {
            AdvancedActionKind.TAKE_LEFT -> {
                if (holdsLeft) return false
                if (left.tryTake(this)) {
                    holdsLeft = true
                }
                holdsLeft
            }
            AdvancedActionKind.TAKE_RIGHT -> {
                if (holdsRight) return false
                if (right.tryTake(this)) {
                    holdsRight = true
                }
                holdsRight
            }
            AdvancedActionKind.DROP_LEFT -> {
                if (!holdsLeft) return false
                left.tryDrop(this)
                holdsLeft = false
                true
            }
            AdvancedActionKind.DROP_RIGHT -> {
                if (!holdsRight) return false
                right.tryDrop(this)
                holdsRight = false
                true
            }
            AdvancedActionKind.THINK -> {
                true
            }
            AdvancedActionKind.EAT -> {
                if (!holdsLeft || !holdsRight) return false
                eatDuration += action.duration
                true
            }
            AdvancedActionKind.TAKE_SERVIETTE -> {
                if (holdsServiette) return false
                if (servietteManager.tryAcquire()) {
                    holdsServiette = true
                }
                holdsServiette
            }
            AdvancedActionKind.DROP_SERVIETTE -> {
                if (!holdsServiette) return false
                servietteManager.release()
                holdsServiette = false
                true
            }
        }
    }

    fun act(totalDuration: Int): Job {
        var timeSpent = 0
        return GlobalScope.launch {
            while (timeSpent < totalDuration) {
                val action = nextAction()
                val duration = action.duration
                if (duration < 10) {
                    throw AssertionError("You should wait at least 10 ms")
                }
                val result = doAction(action)
                val randomDuration = duration + random.nextInt(9 * duration)
                delay(randomDuration.toLong())
                timeSpent += randomDuration
                handleResult(action, result)
            }
        }
    }

    // Called when kilosopher should choose his next action (take or drop fork, think, or eat)
    protected abstract fun nextAction(): AdvancedAction

    // Called to provide result of previous action to kilosopher
    // true means action was successful (fork taken, spaghetti eaten)
    // false means action was not successful (fork busy, or did not have two forks to eat spaghetti)
    protected abstract fun handleResult(action: AdvancedAction, result: Boolean)

    companion object {
        val random = Random()
    }
}
