Call mvn clean install

Call rd ..\..\..\..\dist\examples\helloworld\client /s /q
Call xcopy target\dist\tio-examples-helloworld-client-2.0.0.v20170824-RELEASE ..\..\..\..\dist\examples\helloworld\client\ /s /e /q /y

