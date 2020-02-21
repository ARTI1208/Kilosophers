package ru.spbstu.kilosophers.part2

import java.util.concurrent.Semaphore

object UniversityPart2 : AdvancedUniversity {

    override fun produce(left: AdvancedAbstractFork, right: AdvancedAbstractFork, vararg args: Any): AdvancedAbstractKilosopher {
        val kilosopher = KilosopherPart2(left, right, args[0] as Int, args[1] as Semaphore)
        left.right = kilosopher
        right.left = kilosopher
        return kilosopher
    }
}