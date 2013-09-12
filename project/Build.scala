import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName         = "jbehaviour-tools-ui"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
		javaCore,
		javaJdbc,
		javaEbean,
		"org.springframework"    %    "spring-context"    %    "3.0.7.RELEASE",
		"org.springframework"    %    "spring-core"       %    "3.0.7.RELEASE",
		"org.springframework"    %    "spring-beans"      %    "3.0.7.RELEASE",
		"org.tmatesoft.svnkit"   %    "svnkit-cli"        %    "1.7.6",
		"com.github.yroffin"     %    "jbehaviour-engine" %    "0.0.2-SNAPSHOT"
    )

   val main = play.Project(appName, appVersion, appDependencies).settings(
		resolvers += "Local Maven Repository" at "file://u:/m2repo",
		resolvers += "Default repository" at "http://repo1.maven.org/maven2"
    )

}
