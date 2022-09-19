FROM openjdk:17
LABEL maintainer "lecturefeed@gmail.com"
EXPOSE <%=port%>
COPY <%=name%>-<%=version%>.jar app.jar

CMD java -jar app.jar --spring.profiles.active=default,prod