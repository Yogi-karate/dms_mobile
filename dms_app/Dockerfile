FROM tramm/dms_app_master
MAINTAINER tramm

COPY . /var/www/app
RUN npm run build

# RUN apk del dev-dependencies
 
EXPOSE 8000
CMD ["npm", "start"]