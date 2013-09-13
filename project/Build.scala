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
		"org.springframework"    %    "spring-context"    %    "3.2.4.RELEASE",
		"org.springframework"    %    "spring-core"       %    "3.2.4.RELEASE",
		"org.springframework"    %    "spring-beans"      %    "3.2.4.RELEASE",
		"org.tmatesoft.svnkit"   %    "svnkit-cli"        %    "1.7.6",
		"com.github.yroffin"     %    "jbehaviour-engine" %    "0.0.2-SNAPSHOT"
    )

   val main = play.Project(appName, appVersion, appDependencies).settings(
		resolvers := Seq("typesafe" at "http://repo.typesafe.com/typesafe/repo"),
		resolvers := Seq("DefaultMavenRepository" at "http://repo2.maven.org/maven2"),
		resolvers += "Default repository" at "http://repo1.maven.org/maven2",
		resolvers += "Default repository 2" at "http://repo2.maven.org/maven2",
		resolvers += "JBoss repository" at "https://repository.jboss.org/nexus/content/repositories/",
		resolvers += "Scala-Tools Maven2 Snapshots Repository" at "http://scala-tools.org/repo-snapshots",
		resolvers += "Local Maven Repository" at "file:///m2repo"
    )

}
