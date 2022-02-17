#!/bin/bash

#Argument variables
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

#Argument validation
if [ $# -ne 5 ]; then
        echo "Invalid number of agruments"
        echo "psql_host psql_port db_name psql_user psql_password"
        exit 1
fi

#Host information retrieval
lscpu_out=`lscpu`
hostname=$(hostname -f)
cpu_number=$(echo "$lscpu_out" | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out" | egrep "^Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out" | egrep "^Model:" | awk '{print $2}' | xargs)
cpu_mhz=$(echo "$lscpu_out" | egrep "^CPU\sMHz:" | awk '{print $3}' | xargs)
l2_cache=$(echo "$lscpu_out" | egrep "^L2\scache:" | awk '{print $3}' | cut -f 1 -d "K" | xargs)
total_mem=$(cat /proc/meminfo | egrep "^MemTotal:" | awk '{print $2}' | xargs)

timestamp=$(vmstat -t | awk '{print $18" "$19}' | tail -n1 | xargs)

#Host information database insertion
insert_stmt="INSERT INTO host_info(hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, L2_cache, total_mem, timestamp) VALUES('$hostname','$cpu_number','$cpu_architecture','$cpu_model','$cpu_mhz','$l2_cache','$total_mem','$timestamp');"

export PGPASSWORD=$psql_pasword

psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?
