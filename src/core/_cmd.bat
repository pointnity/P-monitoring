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
