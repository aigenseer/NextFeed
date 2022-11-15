@echo off
set version=%1
set server=%2

@REM Parameter: 0.0.1-SNAPSHOT 192.168.0.220:5005
set list=session-service user-management-service survey-service question-service mood-service
(for %%a in (%list%) do (
   echo %%a
   docker tag nextfeed/kube-4-%%a:%version% %server%/kube-3-%%a
   docker push %server%/kube-4-%%a
))