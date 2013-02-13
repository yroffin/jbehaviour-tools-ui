import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "jbehaviour-tools-ui"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "org.springframework"    %    "spring-context"    %    "3.0.7.RELEASE",
      "org.springframework"    %    "spring-core"       %    "3.0.7.RELEASE",
      "org.springframework"    %    "spring-beans"      %    "3.0.7.RELEASE",
      "org.tmatesoft.svnkit"   %    "svnkit-cli"        %    "1.7.6",
      "com.github.yroffin"     %    "jbehaviour-engine" %    "0.0.1-SNAPSHOT"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
		resolvers += "Default repository" at "http://repo1.maven.org/maven2",
		resolvers += "Local Maven Repository" at "file:///"+Path.userHome.absolutePath+"/.m2/repository"
    )

}
