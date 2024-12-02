import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day02Test {

    @Test
     fun example() {

         val result = Day02.part1("day02_example.txt")

         assertEquals(2, result)
     }

    @Test
    fun example_part2() {

        var result = Day02.part2("day02_example.txt")

        assertEquals(4, result)
    }

    @Test
    fun part1() {

        val result = Day02.part1("day02_part1.txt")

        println(result)
    }

    @Test
    fun part2() {

        val result = Day02.part2("day02_part1.txt")

        println(result)
    }
 }