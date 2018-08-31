echo off
echo -
echo #Download source code
echo mvn dependency:sources
echo -

echo #Download source code jar。 -DdownloadJavadocs=true Download Javadoc Package
echo -DdownloadSources=true
echo -
echo -



echo #Unzip the jar.
echo mvn dependency:unpack-dependencies
echo -
