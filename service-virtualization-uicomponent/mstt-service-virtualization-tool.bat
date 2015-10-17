@echo off

REM The 'SETLOCAL' command causes all 'SET' commands that follow not to affect the shell outside of this script
REM The 'enabledelayedexpansion' argument makes the expansion of variables via '!' in the 'echo' statements within an if statement work (if statements are treated as a single line so expansion of a variable that was just set doesn't work natively)
SETLOCAL enabledelayedexpansion

if "%JAVA_HOME%"=="" (
set JAVA_HOME=C:\Progra~1\Java\jdk1.6.0_10
echo JAVA_HOME was not set.  Trying default path: JAVA_HOME=!JAVA_HOME!
)

set MSTT_HOME=%CD%


set CP=
set CP=%CP%;%MSTT_HOME%/src/main/resources;
set CP=%CP%;%MSTT_HOME%/src/main/config;
set CP=%CP%;%MSTT_HOME%/target/classes;

for  %%f IN (extrajars\*.jar) do (
call set  CP=%%CP%%;%%f
)
for  %%f IN (lib\*.jar) do (
call set  CP=%%CP%%;%%f
)

set DEBUG_OPTS=-Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=15475

echo on
"%JAVA_HOME%\bin\java.exe" %DEBUG_OPTS% -classpath %CP%  com.mstt.qa.servicevirtualization.uicomponent.MainClass

ENDLOCAL

pause