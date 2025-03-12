package com.wf.bm.core.utils

fun Double.toStringWithTwoDecimalsAndNoTrailingZeros(): String {
    // First, format to exactly two decimals, e.g. 1.2 -> "1.20"
    val formatted = "%.2f".format(this)
    // Then remove trailing zeroes, and if the decimal point ends up at the end, remove it as well
    return formatted
        .replace(Regex("0+$"), "")   // Remove trailing zeros
        .replace(Regex("\\.$"), "")  // If the string ends with '.', remove it
}

