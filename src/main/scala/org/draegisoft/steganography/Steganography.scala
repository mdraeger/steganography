package org.draegisoft.steganography

object Steganography {
  
  def embed(message: String, oldFile: String, newFile: String): Unit = {
    val messageBitString = message.toArray map (c => "%08d".format(Integer.parseInt(c.toInt.toBinaryString))) mkString 
    val messageBitChunks = (messageBitString + "11111111") grouped(4) toList
    val bitMasks = messageBitChunks map (s => Integer.parseInt("0"+s(0)+"0"+s(1)+"0"+s(2)+"0"+s(3) , 16))

    val imageProcessor = new ProcessImage(oldFile)
    val original = imageProcessor getARGBfromImage

    val addData = bitMasks ++ original.drop(bitMasks.length)
    val newImageARGB = (original zip addData) map {case (o,v) => (o & 0xfefefefe) | v}

    imageProcessor.writeARGBtoImage(newFile, newImageARGB)
  }

  def extract(fileName: String): String = {
    val data = new ProcessImage(fileName).getARGBfromImage
    val e8 = ((data map (_.toBinaryString) mkString) grouped(8) toList) map (_ last) mkString
    val m2i = (e8 grouped(8) toList) map (n => Integer.parseInt(n, 2)) takeWhile(_ != 255)
    m2i map (_.toChar) mkString
  }
}
