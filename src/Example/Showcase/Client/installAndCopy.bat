Call mvn clean install

Call rd ..\..\..\..\dist\examples\showcase\client /s /q
Call xcopy target\dist\tio-examples-showcase-client-2.0.0.v20170824-RELEASE ..\..\..\..\dist\examples\showcase\client\ /s /e /q /y
