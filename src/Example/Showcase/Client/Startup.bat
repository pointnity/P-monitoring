Rem -Xms64m -Xmx2048m

@echo off
Setlocal & pushd
Set APP_ENTRY=org.tio.examples.showcase.client.ShowcaseClientStarter
Set BASE=%~dp0
Set CP=%BASE%\config;%BASE%\lib\*
Java -Xverify:none -XX:+HeapDumpOnOutOfMemoryError -Dtio.default.read.buffer.size=512 -XX:HeapDumpPath=c:/java-t-io-showcase-client-pid.hprof -cp "%CP%" %APP_ENTRY%
Endlocal & popd
