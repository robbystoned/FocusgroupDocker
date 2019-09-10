# FocusgroupDocker

sudo docker pull openjdk:8-jdk-alpine

sudo docker network create mynetwork

sudo docker build . -t test/focusgroupbackend

sudo docker run  --name backend --net mynetwork -p 81:8080 test/focusgroupbackend

sudo docker build . -t test/focusgroupwebapp

sudo docker run --name webapp --net mynetwork -p 80:8080 test/focusgroupwebapp

sudo docker pull mysql

sudo docker run -p 3306:3306 --name mysql -v /home/robert/mysql:/etc/mysql/conf.d -e MYSQL_ROOT_PASSWORD=Accenture123 -d mysql:latest 

