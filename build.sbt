
name := "pocketpillsassignmentbase"
 
version := "1.0" 
      
lazy val `pocketpillsassignmentbase` = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
scalaVersion := "2.11.11"

libraryDependencies ++= Seq( javaJdbc , javaWs )

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

libraryDependencies += javaJdbc

libraryDependencies += guice

libraryDependencies += evolutions

libraryDependencies += "org.postgresql" % "postgresql" % "42.1.4"

// https://mvnrepository.com/artifact/org.mindrot/jbcrypt
libraryDependencies += "org.mindrot" % "jbcrypt" % "0.4"

// enable Play cache API (based on your Play version)
libraryDependencies += play.sbt.PlayImport.cacheApi
// include play-redis library
libraryDependencies += "com.github.karelcemus" %% "play-redis" % "2.2.0"

libraryDependencies += "io.ebean" % "ebean-mocker" % "11.18.1" % Test

libraryDependencies += "org.hibernate.validator" % "hibernate-validator" % "6.0.11.Final"

// https://mvnrepository.com/artifact/org.glassfish/javax.el
libraryDependencies += "org.glassfish" % "javax.el" % "3.0.0"

// https://mvnrepository.com/artifact/javax.el/javax.el-api
libraryDependencies += "javax.el" % "javax.el-api" % "3.0.0"


libraryDependencies += "software.amazon.awssdk" % "ses" % "2.0.0-preview-1"

libraryDependencies += "javax.mail" % "mail" % "1.5.0-b01"

libraryDependencies += "org.mockito" % "mockito-core" % "2.20.0" % Test

libraryDependencies += "org.apache.commons" % "commons-text" % "1.2"

// https://mvnrepository.com/artifact/org.redisson/redisson
libraryDependencies += "org.redisson" % "redisson" % "3.7.5"

jacocoReportSettings := JacocoReportSettings()
  .withThresholds(
    JacocoThresholds(
      instruction = 80,
      method = 100,
      branch = 100,
      complexity = 100,
      line = 90,
      clazz = 100)
  )

jacocoExcludes in Test := Seq(
  "controllers.Reverse*",
  "views.*",
  "controllers.javascript.*",
  "jooq.*",
  "Module",
  "router.Routes*",
  "*.routes*"
)

resolvers += Resolver.sbtPluginRepo("releases")

playEbeanDebugLevel := 4