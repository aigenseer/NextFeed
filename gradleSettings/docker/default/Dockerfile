FROM openjdk:17
LABEL maintainer "lecturefeed@gmail.com"
EXPOSE <%=port%>
COPY <%=name%>-<%=version%>.jar app.jar
ENV EUREKA_HOST eureka
ENV EUREKA_PORT 8000

CMD java -jar app.jar --spring.profiles.active=default,prod  --nextfeed.config.eureka.hostname=$EUREKA_HOST --nextfeed.config.eureka.port=$EUREKA_PORT