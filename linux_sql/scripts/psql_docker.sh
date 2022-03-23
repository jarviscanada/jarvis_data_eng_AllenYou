#! /bin/sh

#Argument set up
cmd=$1
db_username=$2
db_password=$3

#Docker status check -> start
sudo systemctl status docker || systemctl start docker

docker container inspect jrvs-psql
container_status=$?

#command case switch
case $cmd in 
  create)
  
#Check if container exists  
  if [ $container_status -eq 0 ]; then
		echo 'Container already exists'
		exit 1	
	fi
#Check for valid number of arguements
  
  if [ $# -ne 3 ]; then
    echo 'Create requires username and password'
    exit 1
  fi
  
	docker volume create pgdata 
	docker run --name jrvs-psql -e POSTGRES_PASSWORD=${db_password} -e POSTGRES_USERNAME=${db_username} -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres:9.6-alpine
	exit $?
	;;

  start|stop) 
  #Check if container has been created 
  if [ $container_status -ne 0 ]; then
	echo 'Container does not exist'
	exit 1
  fi
	
	docker container $cmd jrvs-psql
	exit $?
	;;	
   
  *)
	#Invalid agrument reply
	echo 'Illegal command'
	echo 'Commands: start|stop|create'
	exit 1
	;;
esac

