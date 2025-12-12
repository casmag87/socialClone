#!/bin/bash
# Variables
TOMCAT_URL="http://localhost:8080"
TOMCAT_USER="nibro"
TOMCAT_PASSWORD="070487"
APP_NAME="coffeeData"
WAR_FILE="target/${APP_NAME}.war"
DEPLOY_PATH="/${APP_NAME}"

DEPLOY_URL="${TOMCAT_URL}/manager/text/deploy?path=${DEPLOY_PATH}&update=true"
UNDEPLOY_URL="${TOMCAT_URL}/manager/text/undeploy?path=${DEPLOY_PATH}"

# Step 1: Build the project using Maven
echo "Building the project..."
mvn clean package

# Step 2: Undeploy the existing application (optional)
echo "Undeploying existing application (if any)..."
curl -u "${TOMCAT_USER}:${TOMCAT_PASSWORD}" "${UNDEPLOY_URL}"

# Step 3: Deploy the new WAR file to Tomcat
echo "Deploying the new WAR file..."
curl -u "${TOMCAT_USER}:${TOMCAT_PASSWORD}" -T "${WAR_FILE}" "${DEPLOY_URL}"

# Step 4: Verify deployment
echo "Deployment complete. Verifying..."
if curl -s --head --request GET "${TOMCAT_URL}${DEPLOY_PATH}/" | grep "200 OK" > /dev/null; then
    echo "Deployment successful! The application is running at ${TOMCAT_URL}${DEPLOY_PATH}/"
else
    echo "Deployment failed!"
fi

