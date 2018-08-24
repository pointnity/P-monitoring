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

echo #Copy jar to a directory (all jars in the same directory)
echo mvn dependency:copy-dependencies -Dmdep.useRepositoryLayout=false
echo -

echo #Copy jar to warehouse directory ()
echo mvn dependency:copy-dependencies -Dmdep.useRepositoryLayout=true -Dmdep.copyPom=true
echo -
echo -



echo #Checking for version updates
