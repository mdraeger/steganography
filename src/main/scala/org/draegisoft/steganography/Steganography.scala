package org.draegisoft.steganography

object Steganography {
  val MAX_CHAR = (Short.MaxValue - Short.MinValue).toChar

  def byteArray2Int(data: Array[Byte]): Int = {
    var sum = 0
    for (i <- 0 until 4)
      sum += data(i) << (24 - 8*i)
    sum
  }

  def char2ByteArray(c: Char): Array[Byte] = {
    val charAsShort = c.toShort
    var data = new Array[Byte](16)
    val mask = 1.toByte
    for (i <- 0 until 16)
      data(15-i) = ((charAsShort >> i) & mask).toByte
    data
  }

  def byteArray2Char(bytes: Array[Byte]): Char = {
    var sum = 0
    for (i <- 0 until 15) {
      sum += bytes(i)
      sum *= 2
    }
    sum += bytes(15)
    sum.toChar
  }

  def string2ByteArray(s: String): Array[Byte] = {
    s.toArray.flatMap(c => char2ByteArray(c))
  }
  
  def extractBits(theInt: Int): Int = {
    var sum = 0
    for (i <- 0 until 4)
      sum += ((theInt >> 8*i) & 1 ) << i
    sum
  }

  def intArray2Char(integerSeq: IndexedSeq[Int]): Char = {
    var sum = 0
    for (i <- 0 until 4) 
      sum += extractBits(integerSeq(i)) << (12 - i * 4)
    sum.toChar  
  }

  def embed(message: String, oldFile: String, newFile: String): Unit = {
    val messageAsInts = string2ByteArray(message + MAX_CHAR)
                            .grouped(4)
                            .map(group => byteArray2Int(group))
                            .toArray

    val imageProcessor = new ProcessImage(oldFile)
    val original = imageProcessor getARGBfromImage

    val addData = messageAsInts ++ original.drop(messageAsInts.length)
    val newImageARGB = (original zip addData) map {case (o,v) => (o & 0xfefefefe) | v}

    imageProcessor.writeARGBtoImage(newFile, newImageARGB)
  }

  def extract(fileName: String): String = {
    new ProcessImage(fileName).getARGBfromImage
                              .grouped(4)
                              .map(group => intArray2Char(group))
                              .takeWhile(_ != MAX_CHAR)
                              .mkString
  }

  val help = """Usage: Steganography operation [parameters]
  e.g. Steganography embed "my secret message" OLD_FILE.png NEW_FILE.png
       Steganography extract NEW_FILE.png"""

  def main(args: Array[String]) {
    if (args.length < 2)
      println(help)
    else if (args(0) == "embed") {
      if (args(2) == args(3)) {
        println("use different file names for source and destination!")
        sys.exit(0)
      }
      embed(args(1), args(2), args(3))
    }
    else
      println(extract(args(1)))
  }
}
