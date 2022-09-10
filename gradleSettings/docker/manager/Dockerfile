FROM openjdk:17
LABEL maintainer "lecturefeed@gmail.com"
EXPOSE <%=port%>
COPY <%=name%>-<%=version%>.jar app.jar
ENV EUREKA_HOST eureka
ENV EUREKA_PORT 8000
ENV POSTGRES_HOST localhost
ENV POSTGRES_PORT 5432
ENV POSTGRES_DB nextfeed
ENV POSTGRES_USER nextfeed
ENV POSTGRES_PASSWORD nextfeed
ENV SHOW_SQL=false

CMD java -jar app.jar --spring.profiles.active=default,prod --spring.datasource.url=jdbc:postgresql://$POSTGRES_HOST:$POSTGRES_PORT/$POSTGRES_DB --spring.datasource.username=$POSTGRES_USER --spring.datasource.password=$POSTGRES_PASSWORD --spring.jpa.show-sql=$SHOW_SQL --nextfeed.config.eureka.hostname=$EUREKA_HOST --nextfeed.config.eureka.port=$EUREKA_PORT