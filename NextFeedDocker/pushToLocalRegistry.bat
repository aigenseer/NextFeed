@echo off
set version=%1
set server=%2

@REM Parameter: 0.0.1-SNAPSHOT 192.168.0.76:31091
@REM set list=authorization-external-service mood-manager-service
set list=authorization-external-service
(for %%a in (%list%) do (
   echo %%a
   docker tag nextfeed/kube-%%a:%version% %server%/kube-%%a
   docker push %server%/kube-%%a
))