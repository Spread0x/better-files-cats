import Dependencies.Libraries
import xerial.sbt.Sonatype._

name := """better-files-cats"""

organization in ThisBuild := "com.github.spread0x"

scalaVersion in ThisBuild := "2.13.1"

publishTo := sonatypePublishToBundle.value

skip in publish := true

lazy val commonSettings = Seq(
  scalaVersion := "2.13.1",
  organizationName := "com.github.spread0x",
  libraryDependencies ++= Seq(
    Libraries.catsEffect,
    Libraries.monix,
    Libraries.pureconfig,
    Libraries.betterFiles,
    Libraries.scalaLogging,
    Libraries.scalaTest  % Test,
    Libraries.scalaCheck % Test,
    compilerPlugin(Libraries.kindProjector),
    compilerPlugin(Libraries.betterMonadicFor)
  ),
  sonatypeProjectHosting := Some(GitHubHosting("Spread0x", "better-files-cats", "iamjacke@gmail.com")),
  developers := List(
    Developer(id = "jacke", name = "jacke", email = "iamjacke@gmail.com", url = url("https://github.com/Jacke"))
  ),
  licenses := Seq("APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")),
  sonatypeProfileName := "com.github.spread0x",
  publishMavenStyle := true
)

lazy val testSettings = Seq(
  fork in Test := true,
  parallelExecution in Test := false
)

lazy val `better-files-cats-root` =
  (project in file("."))
    .settings(publishTo := sonatypePublishToBundle.value)
    .aggregate(`better-files-cats-core`)

lazy val `better-files-cats-core` = project
  .in(file("core"))
  .settings(commonSettings)
  .settings(publishTo := sonatypePublishToBundle.value)
  .settings(testSettings)
