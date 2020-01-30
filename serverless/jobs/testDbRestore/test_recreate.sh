#!/bin/bash
export PGPASSWORD=odoo_stage123
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
echo "copied from s3"
if [ $? -eq 0 ]
    then
        gunzip -f  $new_file.gz
        echo "completed the gunzip"
        docker stop stage_db
        echo "docker stopped"
        docker rm stage_db
        echo "docker removed"
        echo "docker running"
        docker run -p 5433:5432  -e POSTGRES_PASSWORD=odoo_stage123 -e POSTGRES_USER=odoo -e POSTGRES_DB=postgres --name stage_db -d postgres
        echo "sleep for 15 seconds"
        sleep 15
        echo "creating the db"
        createdb -U odoo -h $DBNAME $1 -p 5433 -T template0
        if [ $? -eq 0 ]
            then
                echo "db created and importing the new file"
                psql -U odoo -h $DBNAME -p 5433 -d $1  < $new_file
                psql -U odoo -h $DBNAME -p 5433 -d $1  < /opt/backup/clear_odoo_js.sql
                if [ $? -eq 0 ]
                    then
                        echo "db recreate and  importing successfull message success"
                        aws sns publish --topic-arn $topic --subject "postgres DB Recreate" --message "recreation Completed Successfully at $timestamp"  >> "$new_file"
                    else
                        echo "db importing failed and message failure"
                        aws sns publish --topic-arn $topic --subject "postgres DB Recreate" --message "$notification_failure"
                fi
            else
                echo "db creation failed"
                aws sns publish --topic-arn $topic --subject "postgres DB Recreate" --message "$notification_failure"
        fi
    else
        echo "copying from s3 failed"
        aws sns publish --topic-arn $topic --subject "postgres DB Recreate" --message "$notification_failure"
fi
echo "executing cd.."
cd ..
echo "removing temp"
rm -fr temp
