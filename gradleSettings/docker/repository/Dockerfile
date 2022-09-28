FROM openjdk:17
LABEL maintainer "lecturefeed@gmail.com"
EXPOSE <%=port%>
COPY <%=name%>-<%=version%>.jar app.jar
ENV POSTGRES_HOST localhost
ENV POSTGRES_PORT 5432
ENV POSTGRES_DB nextfeed
ENV POSTGRES_USER nextfeed
ENV POSTGRES_PASSWORD nextfeed
ENV SHOW_SQL=false

CMD java -jar app.jar --spring.profiles.active=default,prod --nextfeed.datasource.host=$POSTGRES_HOST --nextfeed.datasource.port=$POSTGRES_PORT  --nextfeed.datasource.db=$POSTGRES_DB --nextfeed.datasource.username=$POSTGRES_USER --nextfeed.datasource.pw=$POSTGRES_PASSWORD --nextfeed.datasource.show-sql=$SHOW_SQL