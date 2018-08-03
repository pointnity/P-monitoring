rem -Xms64m -Xmx2048m

@echo off
setlocal & pushd
set APP_ENTRY=org.tio.examples.im.server.ImServerStarter
set BASE=%~dp0
set CP=%BASE%\config;%BASE%\lib\*
