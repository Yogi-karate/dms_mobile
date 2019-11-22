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
new_file="${filename}_${date}.sql"
pg_dump -U odoo -h $DBNAME -d $1 > $new_file
echo $new_file

if [ $? -eq 0 ];then
   echo "Backup Completed Successfully at $timestamp"  >> "$2"
   echo "Starting to zip before upload" 
   gzip -f $new_file
   echo "Starting to upload to S3" 
   aws s3 cp $new_file.gz s3://$BUCKETNAME
   if [ $? -eq 0 ];then
        echo "Backup Completed Successfully at $timestamp"  >> "$2"
        aws sns publish --topic-arn $topic --message "Backup Completed Successfully at $timestamp"  >> "$2"
        echo "creating temp directory"
        mkdir temp
        echo "moving zip file into temp"
        mv $new_file.gz ./temp/
        echo "deleting temp folder"
        rm -fr temp
   else
        echo "Backup Failed !!!!"  >> "$2" 
        aws sns publish --topic-arn $topic --message $notification_failure
   fi

else
   echo "Backup Failed at $timestamp"  >> "$backlogfile"
   aws sns publish --topic-arn $topic --message $notification_failure
fi

