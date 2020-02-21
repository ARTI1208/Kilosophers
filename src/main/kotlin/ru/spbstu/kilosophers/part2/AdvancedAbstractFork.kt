package ru.spbstu.kilosophers.part2

abstract class AdvancedAbstractFork : AdvancedFork {
    override lateinit var left: AdvancedAbstractKilosopher

    override lateinit var right: AdvancedAbstractKilosopher
}