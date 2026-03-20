@echo off
title Shell Hero
cd /d %~dp0
chcp 65001 > nul

reg add HKCU\Console /v VirtualTerminalLevel /t REG_DWORD /d 1 /f > nul

java -Dfile.encoding=UTF-8 -cp "ShellHero.jar;lib/mysql-connector-j-8.4.0.jar" com.kosta.console_rpg.MainApp

pause
