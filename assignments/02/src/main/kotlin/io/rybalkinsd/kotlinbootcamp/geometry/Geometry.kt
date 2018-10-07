package io.rybalkinsd.kotlinbootcamp.geometry

/**
 * Entity that can physically intersect, like flame and player
 */
interface Collider {
    fun isColliding(other: Collider): Boolean
}

/**
 * 2D point with integer coordinates
 */
class Point(val x: Int, val y: Int) : Collider {
    override fun isColliding(other: Collider) = when (other) {
        is Point -> this == other
        is Bar -> other.isColliding(this)
        else -> false
    }
    override fun equals(other: Any?) = when (other) {
        is Point -> (this.x == other.x) and (this.y == other.y)
        else -> false
    }
}

/**
 * Bar is a rectangle, which borders are parallel to coordinate axis
 * Like selection bar in desktop, this bar is defined by two opposite corners
 * Bar is not oriented
 * (It does not matter, which opposite corners you choose to define bar)
 */
class Bar(val firstCornerX: Int, val firstCornerY: Int, val secondCornerX: Int, val secondCornerY: Int) : Collider {
    override fun isColliding(other: Collider) = when (other) {
        is Point -> {
            val list1 = listOf(firstCornerX, secondCornerX).sorted()
            val list2 = listOf(firstCornerY, secondCornerY).sorted()
            (list1[0] <= other.x) and (other.x <= list1[1]) and (list2[0] <= other.y) and (other.y <= list2[1])
        }
        is Bar -> {
            val list1 = listOf(firstCornerX, secondCornerX).sorted()
            val list2 = listOf(firstCornerY, secondCornerY).sorted()
            val list3 = listOf(other.firstCornerX, other.secondCornerX).sorted()
            val list4 = listOf(other.firstCornerY, other.secondCornerY).sorted()
            val log1 = if (list1[0] < list3[0]) (list1[1] >= list3[0]) else (list1[0] <= list2[1])
            val log2 = if (list2[0] < list4[0]) (list2[1] >= list4[0]) else (list2[0] <= list4[1])
            log1 and log2
        }
        else -> false
    }
    override fun equals(other: Any?) = when (other) {
        is Bar -> listOf(firstCornerX, secondCornerX).containsAll(listOf(other.firstCornerX, other.secondCornerX)) and
                listOf(firstCornerY, secondCornerY).containsAll(listOf(other.firstCornerY, other.secondCornerY))
        else -> false
    }
}