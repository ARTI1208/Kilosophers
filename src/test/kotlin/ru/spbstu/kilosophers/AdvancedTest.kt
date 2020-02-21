package ru.spbstu.kilosophers

import kotlinx.coroutines.*
import org.junit.Test
import ru.spbstu.kilosophers.part2.AdvancedAbstractKilosopher
import ru.spbstu.kilosophers.part2.AdvancedForkBox
import ru.spbstu.kilosophers.part2.AdvancedUniversity
import ru.spbstu.kilosophers.part2.UniversityPart2
import ru.spbstu.kilosophers.part2.atomic.AdvancedAtomicForkBox
import ru.spbstu.kilosophers.part2.concurrent.AdvancedConcurrentForkBox
import java.util.concurrent.Semaphore
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class AdvancedTest {

    private fun doAdvancedTest(university: AdvancedUniversity, forkBox: AdvancedForkBox, kilosopherCount: Int, servietteCount: Int, duration: Int) {
        require(kilosopherCount >= 2) { "Provide at least 2 kiloshophers" }

        val forks = MutableList(kilosopherCount) { forkBox.produce() }

        val maxServietteCount = (kilosopherCount / 2.0 + 1).toInt() - 1

        val realServietteCount = if (servietteCount in 1..maxServietteCount) servietteCount else maxServietteCount

        println("Real serviette count = $realServietteCount")

        val servietteManager = Semaphore(realServietteCount)
        val kilosophers = mutableListOf<AdvancedAbstractKilosopher>()
        for (index in 0 until kilosopherCount) {
            val leftFork = forks[index]
            val rightFork = forks[(index + 1) % kilosopherCount]
            val kilosopher = university.produce(leftFork, rightFork, index, servietteManager)
            kilosophers.add(kilosopher)
        }

        val jobs = kilosophers.map { it.act(duration) }
        var owners: List<AdvancedAbstractKilosopher> = emptyList()

        val controllerJob = GlobalScope.launch {
            do {
                delay(maxOf(100, minOf(duration / 50, 1000)).toLong())
                owners = forks.mapNotNull { it.owner }.distinct()
            } while (owners.size < kilosopherCount)
        }

        runBlocking {
            jobs.forEach { it.join() }
            controllerJob.cancelAndJoin()
        }

        assertNotEquals(kilosopherCount, owners.size, "Deadlock detected, fork owners: $owners")

        for (kilosopher in kilosophers) {
            println("$kilosopher:${kilosopher.eatDuration}")
            assertTrue(kilosopher.eatDuration > 0, "Eat durations: ${kilosophers.map { it.eatDuration }}")
        }

    }

    @Test
    fun testPart2WithConcurrentFork() {
        doAdvancedTest(UniversityPart2, AdvancedConcurrentForkBox, kilosopherCount = 15, servietteCount = 4, duration = 20000)
    }

    @Test
    fun testPart2WithAtomicFork() {
        doAdvancedTest(UniversityPart2, AdvancedAtomicForkBox, kilosopherCount = 5, servietteCount = 1, duration = 20000)
    }
}