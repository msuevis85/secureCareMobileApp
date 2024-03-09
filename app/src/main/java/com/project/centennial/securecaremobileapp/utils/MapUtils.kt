package com.project.centennial.securecaremobileapp.utils

object MapUtils {
    fun findMapDifferences(map1: Map<String, Any>, map2: Map<String, Any>): Map<String, Pair<Any?, Any?>> {
        val differences = mutableMapOf<String, Pair<Any?, Any?>>()

        // Check keys in map1 that are not in map2 or have different values
        for ((key, value) in map1) {
            if (!map2.containsKey(key) || map2[key] != value) {
                differences[key] = Pair(value, map2[key])
            }
        }

        // Check keys in map2 that are not in map1
        for ((key, value) in map2) {
            if (!map1.containsKey(key)) {
                differences[key] = Pair(null, value)
            }
        }

        return differences
    }
}