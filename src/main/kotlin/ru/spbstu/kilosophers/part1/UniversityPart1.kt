package ru.spbstu.kilosophers.part1

import ru.spbstu.kilosophers.AbstractFork
import ru.spbstu.kilosophers.AbstractKilosopher
import ru.spbstu.kilosophers.University

object UniversityPart1 : University {

    override fun produce(left: AbstractFork, right: AbstractFork, vararg args: Any): AbstractKilosopher {
        val kilosopher = KilosopherPart1(left, right, args[0] as Int)
        left.right = kilosopher
        right.left = kilosopher
        return kilosopher
    }
}