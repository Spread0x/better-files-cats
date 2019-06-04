import sbt._

object Dependencies {

  object Versions {
    val catsEffect  = "1.3.1"
    val monix       = "3.0.0-RC2"
    val pureconfig  = "0.9.2"
    val betterFiles = "3.6.0"

    // Logging
    val scalaLogging = "3.9.0"

    // Test
    val scalaTest  = "3.0.7"
    val scalaCheck = "1.14.0"

    // Compiler
    val kindProjector    = "0.10.2"
    val betterMonadicFor = "0.3.0"
  }

  object Libraries {
    lazy val catsEffect  = "org.typelevel"         %% "cats-effect"  % Versions.catsEffect
    lazy val monix       = "io.monix"              %% "monix"        % Versions.monix
    lazy val pureconfig  = "com.github.pureconfig" %% "pureconfig"   % Versions.pureconfig
    lazy val betterFiles = "com.github.pathikrit"  %% "better-files" % Versions.betterFiles

    // Logging
    lazy val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % Versions.scalaLogging

    // Test
    lazy val scalaTest  = "org.scalatest"  %% "scalatest"  % Versions.scalaTest
    lazy val scalaCheck = "org.scalacheck" %% "scalacheck" % Versions.scalaCheck

    // Compiler
    lazy val kindProjector    = "org.typelevel" %% "kind-projector"     % Versions.kindProjector
    lazy val betterMonadicFor = "com.olegpy"    %% "better-monadic-for" % Versions.betterMonadicFor
  }

}
