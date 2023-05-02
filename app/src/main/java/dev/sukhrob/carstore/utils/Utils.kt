package dev.sukhrob.carstore.utils

fun calculateLoan(input: Long, duration: Int, percent: Int, discount: Int, count: Int): Double {
    var jamiUstama = 0.0

    for (i in 0..duration) {
        jamiUstama += ((input - i * input / duration).toFloat()) * percent / 12 / 100
    }
    return count * (jamiUstama + input) - (jamiUstama + input) * discount / 100
}

fun calculateSum2(input: Long, discount: Int, count: Int): Double {
    return count * (input - input * discount / 100).toDouble()
}


fun calculateDiscount(count: Int): Int {
    val discount =
        when (count) {
            2 -> 8
            in 3..5 -> 12
            in 6..8 -> 14
            in 9..10 -> 15
            else -> {
                0
            }
        }
    return discount
}