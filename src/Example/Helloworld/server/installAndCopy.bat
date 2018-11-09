Call mvn clean install

Call rd ..\..\..\..\dist\examples\helloworld\server /s /q
Call xcopy target\dist\tio-examples-helloworld-server-2.0.0.v20170824-RELEASE ..\..\..\..\dist\examples\helloworld\server\ /s /e /q /y
