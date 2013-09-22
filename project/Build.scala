import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "jbehaviour-tools-ui"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
	"org.springframework"    %    "spring-context"    %    "3.2.4.RELEASE",
	"org.springframework"    %    "spring-core"       %    "3.2.4.RELEASE",
	"org.springframework"    %    "spring-beans"      %    "3.2.4.RELEASE",
	"org.tmatesoft.svnkit"   %    "svnkit-cli"        %    "1.7.6",
	"com.github.yroffin"     %    "jbehaviour-tools" %     "0.0.2",
	"com.github.yroffin"     %    "jbehaviour-engine" %    "0.0.2"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
	resolvers += "Default repository" at "http://192.168.0.130:9081/nexus/content/groups/public/"
  )

}