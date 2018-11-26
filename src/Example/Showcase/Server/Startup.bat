Rem -Xms64m -Xmx2048m

@echo off
Setlocal & pushd
Set APP_ENTRY=org.tio.examples.showcase.server.ShowcaseServerStarter
Set BASE=%~dp0
Set CP=%BASE%\config;%BASE%\lib\*
