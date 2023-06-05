package com.ngblossom.domain.flowerprice

enum class FlowerType(val value: Int, val displayName: String) {
    FLORAL_BRANCH(1, "절화"),
    FOLIAGE(2, "관엽"),
    ORCHID(3, "난"),
    CYMBIDIUM_GOERINGII(4, "춘란"),
    ;

    companion object {
        fun fromDisplayName(flowerGubn: String): FlowerType {
            return when (flowerGubn) {
                "절화" -> FLORAL_BRANCH
                "관엽" -> FOLIAGE
                "난" -> ORCHID
                "춘란" -> CYMBIDIUM_GOERINGII
                else -> throw IllegalArgumentException("Invalid flowerGubn: $flowerGubn")
            }
        }
    }
}
