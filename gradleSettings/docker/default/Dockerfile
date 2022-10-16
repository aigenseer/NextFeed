FROM openjdk:17
LABEL maintainer "lecturefeed@gmail.com"
EXPOSE <%=ports%>
COPY <%=name%>-<%=version%>.jar app.jar
ENV LOGGING_METRICS=false
CMD java -jar app.jar --spring.profiles.active=default,prod --nextfeed.metrics.enabled=$LOGGING_METRICS