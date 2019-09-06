# FocusgroupDocker

sudo docker network create mynetwork

sudo docker build . -t test/focusgroupbackend

sudo docker run -p 80:8080 test/focusgroupbackend

sudo docker build . -t test/focusgroupwebapp

sudo docker run -p 80:8080 test/focusgroupwebapp

