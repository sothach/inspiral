name := "inspiral"

version := "1.0"

scalaVersion := "2.12.6"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test

trapExit := false

import org.scoverage.coveralls.Imports.CoverallsKeys._
coverallsToken := Some("JIBFCqY1l5EQvK5aL5ybunFAt5VxOFj2j2OLC") // 'inspiral'