lazy val akkaActor = "com.typesafe.akka" %% "akka-actor" % "2.3.9"
lazy val akkaTestKit = "com.typesafe.akka" %% "akka-testkit" % "2.3.9"
lazy val akkaPersistence = "com.typesafe.akka" %% "akka-persistence-experimental" % "2.3.9"
    

lazy val root = (project in file(".")).
					settings(
						name := "actortest",
    					version := "1.0",
    					scalaVersion := "2.11.4",
    					libraryDependencies += akkaActor,
    					libraryDependencies += akkaTestKit,
    					libraryDependencies += akkaPersistence
					)