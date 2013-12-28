name := "Steganography"

version := "1.0"

scalaVersion := "2.10.3"

scalacOptions ++= Seq("-deprecation","-feature","-language:postfixOps")

resolvers += "releases" at "http://oss.sonatype.org/content/repositories/releases"

libraryDependencies ++= Seq(
   "org.scalatest" % "scalatest_2.10" % "2.0" % "test")
