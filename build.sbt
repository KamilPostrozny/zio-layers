import Dependencies._

ThisBuild / organization := "pl.postrozny"
ThisBuild / version      := "1.0.0"

val ScalaVersion = "3.1.3"

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .settings(
    name := "zio-layers",
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"),
    libraryDependencies ++= Seq(zio),
    ThisBuild / fork              := true,
    ThisBuild / scalaVersion      := ScalaVersion,
    ThisBuild / semanticdbEnabled := true,
    ThisBuild / semanticdbVersion := scalafixSemanticdb.revision,
    ThisBuild / scalafixDependencies ++=
      List(
        "com.github.liancheng" %% "organize-imports" % "0.6.0"
      )
  )
  .settings(
    Docker / version          := version.value,
    Compile / run / mainClass := Option("pl.postrozny.Main")
  )

addCommandAlias("fmt", "scalafmt; Test / scalafmt; sFix;")
addCommandAlias("fmtCheck", "scalafmtCheck; Test / scalafmtCheck; sFixCheck")
addCommandAlias("sFix", "scalafix OrganizeImports; Test / scalafix OrganizeImports")
addCommandAlias("sFixCheck", "scalafix --check OrganizeImports; Test / scalafix --check OrganizeImports")
