#!/bin/bash
topic=arn:aws:sns:ap-south-1:845374285743:dev-ops
timestamp=$(date +"%F %r")
date=$(date +"%d-%m-%Y")
notification_success="MongoDB Backup Successful"
notification_failure="MongoDB Backup Failed"
DBNAME=dms_dev
USER=admin
PASSWORD="MongoAtlas123"
AUTHDB=admin
PORT=27017
REPLICASETNAME="Cluster0-shard-0"
CLUSTERSHARD="cluster0-shard-00-00-q2wsq.mongodb.net:27017,cluster0-shard-00-02-q2wsq.mongodb.net:27017,cluster0-shard-00-01-q2wsq.mongodb.net:27017"
BUCKETNAME=saboo.mongodb.backup
filename=mongobackup
mkdir temp
cd ./temp
echo "Starting MONGO DUMP at $timestamp"
new_file="${filename}_${date}.tar.gz"
mongodump -d $DBNAME -u $USER -p $PASSWORD --authenticationDatabase $AUTHDB --ssl --port $PORT -h $REPLICASETNAME/$CLUSTERSHARD
echo $new_file
if [ $? -eq 0 ];then
   echo "MONGODB Backup Completed Successfully at $timestamp"  >> "$2"
   echo "Starting to zip before upload"
   tar -zcvf $new_file ./dump
   echo "Starting to upload to S3"
   aws s3 cp $new_file s3://$BUCKETNAME
   if [ $? -eq 0 ];then
        echo "Backup Completed Successfully at $timestamp" 
        aws sns publish --topic-arn $topic --message "Backup Completed Successfully at $timestamp"
        echo "creating temp directory"
        echo "deleting temp folder"
        cd ..
       # rm -fr temp
   else
        echo "Backup Failed !!!!"  >> "$2"
        aws sns publish --topic-arn $topic --message $notification_failure
   fi
else
   echo "Backup Failed at $timestamp"  >> "$backlogfile"
   aws sns publish --topic-arn $topic --message $notification_failure
fi
