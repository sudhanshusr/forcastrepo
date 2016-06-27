name := "ForecastMicroservice"

version := "forecastservice"

libraryDependencies ++= Seq(
  "org.scoverage" %% "scalac-scoverage-runtime" % "1.0.4",
  "org.slf4j" % "slf4j-nop" % "1.6.4"
)     

play.Project.playScalaSettings

//coverageEnabled.in(Test, test) := true
coverageEnabled := true

// For Logging
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.2"

test in assembly := {}

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
  {
    case PathList("javax", "servlet", xs @ _*) => MergeStrategy.last
    case PathList("javax", "activation", xs @ _*) => MergeStrategy.last
    case PathList("org", "apache", xs @ _*) => MergeStrategy.last
	case PathList("org", "slf4j", xs @ _*) => MergeStrategy.last
    case PathList("com", "google", xs @ _*) => MergeStrategy.last
    case PathList("com", "esotericsoftware", xs @ _*) => MergeStrategy.last
    case PathList("com", "codahale", xs @ _*) => MergeStrategy.last
    case PathList("com", "yammer", xs @ _*) => MergeStrategy.last
    case "about.html" => MergeStrategy.rename
    case "META-INF/ECLIPSEF.RSA" => MergeStrategy.last
    case "META-INF/mailcap" => MergeStrategy.last
    case "META-INF/mimetypes.default" => MergeStrategy.last
    case "plugin.properties" => MergeStrategy.last
	case "play/core/server/ServerWithStop.class" => MergeStrategy.first
    case "log4j.properties" => MergeStrategy.last
    case x => old(x)
  }
}

assemblyMergeStrategy in assembly := {
  case PathList("play", "core", "server", "ServerWithStop.class") => MergeStrategy.first
  case other => (assemblyMergeStrategy in assembly).value(other)
}
