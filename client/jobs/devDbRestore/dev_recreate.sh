#!/bin/bash
export PGPASSWORD=odoo
export DBNAME=localhost
topic=arn:aws:sns:ap-south-1:845374285743:dev-ops
timestamp=$(date +"%F %r")
date=$(date +"%d-%m-%Y")
notification_success="Backup Successful"
notification_failure="Backup Failed"
BUCKETNAME=saboo.odoo.backup
filename=backup
DOCKERNAME=db
echo $DBNAME
echo "Starting PG DUMP at $timestamp"
mkdir temp
echo "temp directory created"
cd ./temp
echo "moved into temp"
new_file="${filename}_${date}.sql"
aws s3 cp s3://$BUCKETNAME/$new_file.gz .
echo "take copy from s3"
gunzip -f  $new_file.gz
echo "completed the gunzip"
docker stop db
echo "docker stopped"
docker rm db
echo "docker removed"
echo "docker running"
docker run -p 5432:5432  -e POSTGRES_PASSWORD=odoo -e POSTGRES_USER=odoo -e POSTGRES_DB=postgres --name db -d postgres
echo "sleep for 15 seconds"
sleep 15
echo "creating the db"
createdb -U odoo -h $DBNAME $1 -p 5432 -T template0
echo "importing the new file into "
psql -U odoo -h $DBNAME -p 5432 -d $1  < $new_file
psql -U odoo -h $DBNAME -p 5432 -d $1  < clear_odoo_js.sql
cd ..
rm -fr temp