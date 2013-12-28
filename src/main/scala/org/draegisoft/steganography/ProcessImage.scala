package org.draegisoft.steganography

import scala.collection.immutable.IndexedSeq

import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

class ProcessImage(source: String) {
  val image = ImageIO.read(new File(source))
  val width = image.getWidth
  val height = image.getHeight

  val getARGBfromImage:IndexedSeq[Int] = {
    for (i <- 0 until width; j <- 0 until height)
      yield image.getRGB(i, j)
  }

  def writeARGBtoImage(imageFile: String, argbList: IndexedSeq[Int]): Unit = {
    for (i <- 0 until width; j <- 0 until height)
      image.setRGB(i, j, argbList(i*height + j))
    ImageIO.write(image, "png", new File(imageFile))
  }
} 
