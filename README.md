# FocusgroupDocker

sudo docker network create mynetwork

sudo docker build . -t test/focusgroupbackend

sudo docker run  --name backend --net mynetwork -p 81:8080 test/focusgroupbackend

sudo docker build . -t test/focusgroupwebapp

sudo docker run --name webapp --net mynetwork -p 80:8080 test/focusgroupwebapp

