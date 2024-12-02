import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day01Test {

    @Test
     fun example() {

         val result = Day01.part1("day01_example.txt")

         assertEquals(11, result)
     }

    @Test
    fun example_part2() {

        var result = Day01.part2("day01_example.txt")

        assertEquals(31, result)
    }

    @Test
    fun part1() {

        val result = Day01.part1("day01_part1.txt")

        println(result)
    }

    @Test
    fun part2() {

        val result = Day01.part2("day01_part1.txt")

        println(result)
    }
 }