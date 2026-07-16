@echo off
title Smart Search Engine Pro
cls

set "BLUEJ_JAVA=C:\Program Files\BlueJ\jdk\bin\java.exe"

if exist "%BLUEJ_JAVA%" (
    echo Running search engine using BlueJ's Java environment...
    "%BLUEJ_JAVA%" -cp "search engine" Main
) else (
    echo BlueJ Java not found at "%BLUEJ_JAVA%".
    echo Trying system 'java'...
    where java >nul 2>nul
    if %errorlevel% equ 0 (
        java -cp "search engine" Main
    ) else (
        echo =================================================
        echo ERROR: Java environment not found!
        echo =================================================
        echo Please ensure BlueJ is installed in:
        echo C:\Program Files\BlueJ
        echo OR add Java to your system PATH.
        echo.
        pause
    )
)
