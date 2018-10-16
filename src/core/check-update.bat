Echo off
Echo -
Echo #download source code
Echo mvn dependency:sources
Echo -

Echo #download source code jar. -DdownloadJavadocs=true Download javadoc package
Echo -DdownloadSources=true
Echo -
Echo -



Echo # jar out
Echo mvn dependency:unpack-dependencies
Echo -

Echo #Copy the jar to a directory (all jars are in the same directory)
Echo mvn dependency:copy-dependencies -Dmdep.useRepositoryLayout=false
Echo -

Echo #Copy the jar to the repository directory ()
Echo mvn dependency:copy-dependencies -Dmdep.useRepositoryLayout=true -Dmdep.copyPom=true
Echo -
Echo -
