class IsbnVerifier {
    fun isValid(isbnString: String): Boolean =
        try {
            val isbnAsIntList: List<Int> = isbnToList(isbnString)
            if (isbnAsIntList.size != 10 ||
                isbnAsIntList.dropLast(1).contains(10)
            )
                throw IllegalArgumentException()

            val weightedSum = isbnAsIntList.foldIndexed(0) { index, acc, i -> acc + (10 - index) * i }
            weightedSum % 11 == 0
        } catch (e: IllegalArgumentException) {
            false
        }

    companion object {
        private fun Char.numberify(): Int =
            when {
                this in '0'..'9' -> this.toInt() - '0'.toInt()
                this.toUpperCase() == 'X' -> 10
                else -> throw IllegalArgumentException()
            }
    }

    private fun isbnToList(isbnString: String): List<Int> = isbnString.filter { it != '-' }
        .map { it.numberify() }

}

