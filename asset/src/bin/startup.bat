@echo off
rem ======================================================================
rem windows startup script
rem
rem author: ch
rem date: 2021-10-19
rem ======================================================================

rem Open in a browser
REM start "" "http://localhost:8080/example/hello?name=123"

rem startup jar
java -jar ../boot/data-example.jar --spring.config.location=../config/

pause