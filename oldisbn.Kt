import java.lang.Exception
import javax.naming.directory.InvalidAttributesException

class IsbnVerifier {
    private fun simplifyIsbn(isbn: String): String {
        var simplifiedisbn: String = ""
        var checksum: Boolean = false

        for (char in isbn) {
            if (char == '-') {
                continue
            }
            if (!checksum) {
                if (!char.isDigit()) {
                    throw InvalidAttributesException()
                }
            } else if (!char.isDigit() && char.toUpperCase() != 'X') {
                throw InvalidAttributesException()
            }
            simplifiedisbn += char
            if (simplifiedisbn.length == 9) {
                checksum = true
            }
        }
        if (simplifiedisbn.length != 10) {
            throw InvalidAttributesException()
        }
        return simplifiedisbn
    }

    private fun numberify(char: Char): Int {
        if (char.toUpperCase() == 'X') {
            return 10
        } else {
            return char.toInt() - '0'.toInt()
        }
    }

    fun isValid(isbn: String): Boolean {
        try {
            val simplifiedisbn = simplifyIsbn(isbn)
            var weightedSum = 0
            for (charIndex in simplifiedisbn.indices) {
                weightedSum += (10 - charIndex) * numberify(simplifiedisbn[charIndex])
            }
            if (weightedSum % 11 == 0) {
                return true
            } else {
                return false
            }
        }
        catch (InvalidAttributeException: Exception) {
            return false
        }
    }
}
