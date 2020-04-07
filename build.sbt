import Dependencies.Libraries

name := """better-files-cats"""

organization in ThisBuild := "com.github.spread0x"

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
  )
)

lazy val testSettings = Seq(
  fork in Test := true,
  parallelExecution in Test := false
)

lazy val `pure-watcher-root` =
  (project in file("."))
    .aggregate(`pure-watcher-core`)

lazy val `pure-watcher-core` = project
  .in(file("core"))
  .settings(commonSettings)
  .settings(testSettings)
