import java.nio.file.Files
import java.nio.file.Paths
import kotlin.math.abs

object Day01 {

    private fun getLocations(fileName: String) : Pair<MutableList<Int>, MutableList<Int>> {

        val path = Paths.get("src/main/resources/$fileName")

        val firstLocations = mutableListOf<Int>()
        val secondLocations = mutableListOf<Int>()

        val lines = Files.readAllLines(path)

        lines.forEach {
            val first = it.substring(0, it.indexOf(" "))
            val last = it.substring(it.lastIndexOf(" ")+ 1)

            firstLocations.add(first.toInt())
            secondLocations.add(last.toInt())
        }

        return firstLocations to secondLocations
    }

    fun part1(fileName: String): Int {

        val pairing = getLocations(fileName)
        val firstLocations = pairing.first
        val secondLocations = pairing.second

        firstLocations.sort()
        secondLocations.sort()

        val locationPairs = mutableListOf<LocationPair>()

        firstLocations.forEachIndexed { i, first ->
            locationPairs.add(LocationPair(first, secondLocations[i]))
        }

        return locationPairs.sumOf { it.difference() }
    }

    data class LocationPair(val first: Int, val second: Int)
    {
        fun difference() : Int {
            return abs(first - second)
        }
    }

    fun part2(fileName: String): Int {

        val pairing = getLocations(fileName)
        val firstLocations = pairing.first
        val secondLocations = pairing.second

        val similarities = firstLocations.map{ first ->
            val countInSecond = secondLocations.filter { it == first }
                .count()

            LocationSimilarity(first, countInSecond)
        }

        return similarities.sumOf { it.similarityScore() }
    }

    data class LocationSimilarity(val first: Int, val count: Int) {

        fun similarityScore() : Int {
            return first * count
        }
    }
}