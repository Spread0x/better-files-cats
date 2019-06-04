import Dependencies.Libraries

name := """pure-watcher"""

organization in ThisBuild := "com.github.pureai"

crossScalaVersions in ThisBuild := Seq(scalaVersion.value)

lazy val warts = Warts.allBut(
  Wart.Any,
  Wart.ArrayEquals,
  Wart.Nothing,
  Wart.Product,
  Wart.Serializable,
  Wart.Overloading,
  Wart.NonUnitStatements,
  Wart.ImplicitConversion,
  Wart.PublicInference,
  Wart.ImplicitParameter
)

lazy val commonSettings = Seq(
  organizationName := "com.github.pureai",
  wartremoverErrors in (Compile, compile) := warts,
  wartremoverErrors in (Test, compile) := warts,
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
