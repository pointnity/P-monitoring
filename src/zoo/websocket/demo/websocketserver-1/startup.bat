rem -Xms64m -Xmx2048m

@echo off
setlocal & pushd
set APP_ENTRY=org.tio.websocket.server.demo1.WsDemoStarter
set BASE=%~dp0
set CP=%BASE%\config;%BASE%\lib\*
