#!/bin/bash

topic=arn:aws:sns:ap-south-1:845374285743:dev-ops
timestamp=$(date +"%F %r")
date=$(date +"%d-%m-%Y")
notification_success="Backup Successful"
notification_failure="Backup Failed"
export PGPASSWORD=dms_prod123
DBNAME=13.233.83.146
BUCKETNAME=saboo.odoo.backup
filename=backup

echo "Starting PG DUMP at $timestamp"
mkdir temp
cd ./temp
new_file="${filename}_${date}.sql"
pg_dump -U odoo -h $DBNAME -d $1 > $new_file
echo $new_file

if [ $? -eq 0 ];then
   echo "Backup Completed Successfully at $timestamp"  >> "$new_file"
   echo "Starting to zip before upload"

   gzip -f $new_file
   echo "Starting to upload to S3"
   aws s3 cp $new_file.gz s3://$BUCKETNAME
   if [ $? -eq 0 ];then
        echo "Backup Completed Successfully at $timestamp"  >> "$new_file"
        aws sns publish --topic-arn $topic --message "Backup Completed Successfully at $timestamp"  >> "$new_file"
        cd ..
        rm -fr ./temp
   else
        echo "Backup Failed !!!!"  >> "$new_file"
        aws sns publish --topic-arn $topic --message $notification_failure
        cd ..
        rm -fr ./temp
   fi

else
   echo "Backup Failed at $timestamp"  >> "$backlogfile"
   aws sns publish --topic-arn $topic --message $notification_failure
fi
