import java.nio.file.Files
import java.nio.file.Paths
import kotlin.math.abs

object Day02 {

    fun part1(fileName: String): Int {

        val path = Paths.get("src/main/resources/$fileName")
        val lines = Files.readAllLines(path)

        val reports = lines.map {
            Report(it)
        }

        return reports.count { it.isSafeWithoutDampener() }
    }

    fun part2(fileName: String): Int {

        val path = Paths.get("src/main/resources/$fileName")
        val lines = Files.readAllLines(path)

        val reports = lines.map {
            Report(it)
        }

        return reports.count { it.isSafeWithDampener() }
    }

    data class Report(val line: String) {
        private val levels = line.split(" ")
            .map {
            Level(it)
        }

        private val levelCount = levels.count()

        private fun getDirections(inspectionLevels: List<Level>) : List<Int> {
            return inspectionLevels.take(inspectionLevels.count()-1).mapIndexed { i, level ->
                level.directionFrom(inspectionLevels[i+1])
            }
        }

        private fun getDistances(inspectionLevels: List<Level>) : List<Int> {
            return inspectionLevels.take(inspectionLevels.count()-1).mapIndexed { i, level ->
                level.distanceFrom(inspectionLevels[i+1])
            }
        }

        fun isSafeWithoutDampener() : Boolean {
            return isSafe(levels)
        }

        private fun isSafe(inspectionLevels: List<Level>) : Boolean {
            val directions = getDirections(inspectionLevels)
            val distances = getDistances(inspectionLevels)

            return (directions.all { it == -1 } || directions.all { it == 1 })
                    && distances.all { it < 4 } && distances.all { it > 0 }
        }

        fun isSafeWithDampener() : Boolean {

            val levelPermutations = mutableListOf<List<Level>>()
            levelPermutations.add(levels)
            for(i in 1..levelCount) {
                val clone = levels.toMutableList()
                clone.removeAt(i-1)
                levelPermutations.add(clone)
            }

            return levelPermutations.any { isSafe(it) }
        }
    }

    data class Level(val level: String) {
        private val value = level.toInt()

        fun directionFrom(otherLevel: Level) : Int {
            return if(otherLevel.value == value) 0 else
                if(otherLevel.value - value > 0) 1 else -1
        }

        fun distanceFrom(otherLevel: Level) : Int {
            return abs(otherLevel.value - value)
        }
    }
}