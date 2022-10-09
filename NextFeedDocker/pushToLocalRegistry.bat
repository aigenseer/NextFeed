@echo off
set version=%1
set server=%2

@REM Parameter: 0.0.1-SNAPSHOT 192.168.0.220:5005
set list=user-repository-service system-repository-service survey-repository-service session-repository-service question-repository-service participant-repository-service mood-repository-service gateway-service authorization-external-service question-external-service session-external-service survey-external-service mood-manager-service participant-manager-service question-manager-service session-manager-service survey-manager-service survey-template-manager-service system-manager-service user-manager-service mood-socket-service question-socket-service session-socket-service survey-socket-service
(for %%a in (%list%) do (
   echo %%a
   docker tag nextfeed/kube-2-%%a:%version% %server%/kube-2-%%a
   docker push %server%/kube-2-%%a
))