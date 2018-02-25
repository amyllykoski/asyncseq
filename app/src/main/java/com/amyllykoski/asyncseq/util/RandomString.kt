package com.amyllykoski.asyncseq.util

import java.util.Locale
import java.util.Objects
import java.util.Random

class RandomString private constructor(length: Int, random: Random, symbols: String) {
  private val random: Random
  private val symbols: CharArray
  private val buf: CharArray

  init {
    if (length < 1) {
      throw IllegalArgumentException()
    }
    if (symbols.length < 2) {
      throw IllegalArgumentException()
    }
    this.random = Objects.requireNonNull(random)
    this.symbols = symbols.toCharArray()
    this.buf = CharArray(length)
  }

  constructor(length: Int, random: Random) : this(length, random, alphanum) {}

  fun nextString(): String {
    for (idx in buf.indices) {
      buf[idx] = symbols[random.nextInt(symbols.size)]
    }
    return String(buf)
  }

  companion object {

    private val upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private val lower = upper.toLowerCase(Locale.ROOT)
    private val digits = "0123456789"
    private val alphanum = upper + lower + digits
  }
}