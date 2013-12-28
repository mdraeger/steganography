package org.draegisoft.steganography

import org.scalatest._

import Steganography._

class SteganographySpec extends FlatSpec with ShouldMatchers {
  val secret = "Secret Message"
  val secretList = List(83,101,99,114,101,116,32,77,101,115,115,97,103,101)
  val original = "SkyOneDiscworld.png"
  val imageWithSecret = "SkyOneSecret.png"
  val ascii = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~"

  secret should "be embedded into " + original in {
    noException should be thrownBy Steganography.embed(secret, original, imageWithSecret) 
  }

  secret should "be returned" in {
    Steganography.extract(imageWithSecret) should equal (secret)
  } 
    
}
