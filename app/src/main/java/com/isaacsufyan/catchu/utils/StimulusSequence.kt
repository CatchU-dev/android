package com.isaacsufyan.catchu.utils

import com.isaacsufyan.catchu.domain.enum.EStimulusType
import com.isaacsufyan.catchu.domain.model.Stimulus
import kotlin.random.Random

/**
 * Lab build trial order. Every segment (the practice set, or one block) holds an exact number of
 * each stimulus type in random order, and no type appears more than [MAX_RUN] times in a row,
 * including across segment boundaries.
 */
object StimulusSequence {

    const val MAX_RUN = 3
    const val PRACTICE_PER_TYPE = 5
    const val PRACTICE_SIZE = PRACTICE_PER_TYPE * 3
    const val PER_TYPE_PER_BLOCK = 15
    const val BLOCK_SIZE = PER_TYPE_PER_BLOCK * 4

    private const val MAX_ATTEMPTS = 10_000

    fun practice(): List<Stimulus> {
        repeat(MAX_ATTEMPTS) {
            val counts = mutableMapOf(
                EStimulusType.VISUAL to PRACTICE_PER_TYPE,
                EStimulusType.SOMA_TO_SENSORY to PRACTICE_PER_TYPE,
                EStimulusType.VIS_AND_SOMA to PRACTICE_PER_TYPE
            )
            buildSegment(counts, null, 0)?.let { return it.map(::Stimulus) }
        }
        error("Could not build a practice sequence")
    }

    /** One list of [BLOCK_SIZE] stimuli per block. */
    fun blocks(numBlocks: Int): List<List<Stimulus>> {
        repeat(MAX_ATTEMPTS) {
            val result = mutableListOf<List<EStimulusType>>()
            var last: EStimulusType? = null
            var run = 0
            for (b in 0 until numBlocks) {
                val counts = EStimulusType.values().associateWith { PER_TYPE_PER_BLOCK }.toMutableMap()
                val segment = buildSegment(counts, last, run) ?: break
                result.add(segment)
                last = segment.last()
                run = segment.takeLastWhile { it == last }.size
            }
            if (result.size == numBlocks) return result.map { block -> block.map(::Stimulus) }
        }
        error("Could not build a block sequence")
    }

    /** Returns null when the random picks painted themselves into a corner; the caller retries. */
    private fun buildSegment(
        counts: MutableMap<EStimulusType, Int>,
        startType: EStimulusType?,
        startRun: Int
    ): List<EStimulusType>? {
        val out = mutableListOf<EStimulusType>()
        var last = startType
        var run = startRun
        val total = counts.values.sum()
        while (out.size < total) {
            val candidates = counts.filter { (type, left) ->
                left > 0 && !(type == last && run >= MAX_RUN)
            }
            if (candidates.isEmpty()) return null
            var pick = Random.nextInt(candidates.values.sum())
            var chosen = candidates.keys.first()
            for ((type, left) in candidates) {
                if (pick < left) {
                    chosen = type
                    break
                }
                pick -= left
            }
            counts[chosen] = counts.getValue(chosen) - 1
            run = if (chosen == last) run + 1 else 1
            last = chosen
            out.add(chosen)
        }
        return out
    }
}
