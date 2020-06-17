#!/bin/bash

REPOSITORY=/home/ec2-user/app/step2
PROJECT_NAME=ShareYourTrip

echo "> Copy to Build File"
cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> Check Running Application PID"

# 실행 중이면 종료하기 위해서 현재 수행 중인 프로세스id를 찾습니다.
# springboot2-webservice으로 된 다른 프로그램들이 있을 수 있어 springboot2-webservice된 jar 프로세스를 찾은 뒤 id를 찾습니다(awk '{print $1}').
CURRENT_PID=$(pgrep -fl ShareYourTrip | grep jar | awk '{print $1}')

echo "> Running Application PID: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
  ehco "> Not Found Running Application"
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> Deploying New Application"

JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR name: $JAR_NAME"
echo "> $JAR_NAME add permission"
chmod +x $JAR_NAME

echo "> $JAR_NAME run"

nohup java -jar \
    -Dspring.config.location=classpath:/application.properties,classpath:/application-real.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties \
    -Dspring.profiles.active=real \
    $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &