# FocusgroupDocker


## Setting up your environment
- Get the ubuntu base image from the disk, this contains ubuntu 19.04 and a small development environment. The password of the image will be provided on screen

### install docker
- Docker is not yet installed on this image, open the terminal and type (or copy paste) the following commands

```
sudo apt update
sudo apt install docker.io
```

The first command will update the repositories and the second will install docker.

### download and setup the java projects
- Now that docker is installed we can start making images, pull this repository and setup the workspace by typing the following commands

```
cd /home/docker
mkdir workspace
cd workspace
git clone https://github.com/robbystoned/FocusgroupDocker.git
cd DockerBackend
mvn eclipse:eclipse
cd ..
cd DockerWebApp
mvn eclipse:eclipse
```
the workspace should now be available. You can now open eclipse and open the workspace

### Setup docker

- The build we will be making are based on the alpine jre 8 image. To use it we must first pull the image. To do this, perform the following commands:

```
sudo docker login
--now it will ask you for your own credentials--
sudo docker pull openjdk:8-jdk-alpine

```

- To allow containers to communicate with each other, we must create a network, do this with the following command

```
sudo docker network create mynetwork

```

### Build the containers
- now we build the containers, go to the workspace directory and build the container with the following commands

### Build the backend
```
cd /home/docker/DockerBackend/DockerBackend
mvn clean install
sudo docker build . -t test/focusgroupbackend
sudo docker run --name backend --net mynetwork -p 81:8080 test/focusgroupbackend

```
- shut down the container with ctrl + c

### Build the frontend app

```
cd /home/docker/DockerBackend/DockerWebApp
mvn clean install
sudo docker build . -t test/focusgroupwebapp
sudo docker run --name webapp --net mynetwork -p 80:8080 test/focusgroupwebapp

```
shut down the container with ctrl + c

## Test it

Because both docker run commands were given with a --name parameter we can now start the containers using the names.

Now start the containers using the following commands

```
sudo docker start backend
sudo docker start webapp
```

- now perform the following command:

```
sudo docker ps
```

the output of the command should look like this. This will indicate that the containers are both running

```
CONTAINER ID        IMAGE                    COMMAND                  CREATED             STATUS              PORTS                  NAMES
56aeabbfdab2        test/focusgroupbackend   "sh -c 'java -Djava.…"   26 seconds ago      Up 5 seconds        0.0.0.0:81->8080/tcp   backend
d811c0f6f6d4        test/focusgroupwebapp    "sh -c 'java -Djava.…"   2 minutes ago       Up About a minute   0.0.0.0:80->8080/tcp   webapp 

```

In both the run command we supplied the -p command. This command peforms portmappings between the container and the host machine. as you can also see from the docker ps output we have mapped the following:

- the backend has a port mapping from host port 81 to port 8080 on the container
- the webapp has a port mapping from host port 80 to port 808 on the container

With the portmapping the containers can be reached from the host system (you ubuntu vm) on the following urls:

- backend (swagger-ui)
[http://localhost:81/swagger-ui.html](http://localhost:81/swagger-ui.html)

- webapp
[http://localhost/](http://localhost/)



# Adding a database

Now that we have setup a small demo environment, now lets add a database to the environment. For this one we will not give the full solution.

Here are some snippets to help you get started to change the backend server to perform an sql query when it is invoked by the frontend (or swagger-ui).

Pull the mysql image

```
sudo docker pull mysql
```

Start the database container (note the -v command to map a volume from the host disk to the container)

```
sudo docker run -p 3306:3306 --name mysql -v /home/docker/mysql:/etc/mysql/conf.d -e MYSQL_ROOT_PASSWORD={make up a password} -d mysql:latest 
```

A maven dependency for the backend

```
<dependency>
	<groupId>mysql</groupId>
	<artifactId>mysql-connector-java</artifactId>
	<version>8.0.17</version>
</dependency>
```

an sql query to create some data in the database
Sqirrel is installed on the system, note that the out of the box driver does not work with this version of mysql, an updated version can be found in the directory /home/docker

```
create database focusgroup;
use focusgroup;

create table users(user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name TEXT, phone_number TEXT);
insert into users (name, phone_number) VALUES ('Erwin', '+3166666');
insert into users (name, phone_number) VALUES ('Danny', '+3180116');
insert into users (name, phone_number) VALUES ('Robert', '+311236'); 
```
#Good luck
