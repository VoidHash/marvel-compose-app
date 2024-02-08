package com.andrepassos.marvelheroes.util

import java.math.BigInteger
import java.security.MessageDigest

object DataUtil {

    fun getHash(timestamp: String, privateKey: String, publicKey: String): String {
        val hashStr = timestamp + privateKey + publicKey
        val md5 = MessageDigest.getInstance("MD5")
        return BigInteger(1, md5.digest(hashStr.toByteArray()))
            .toString(16).padStart(32, '0')
    }

    fun List<String>.comicsToString() = this.joinToString(separator = ", ")
}