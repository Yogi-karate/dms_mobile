FROM nginx
MAINTAINER tramm

RUN mkdir -p /var/www/default
COPY pages/*.html /var/www/default
COPY nginx.conf /etc/nginx/nginx.conf
COPY proxy_params /etc/nginx/proxy_params
COPY sites/*.conf /etc/nginx/sites-available/
