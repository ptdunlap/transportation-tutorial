#!/bin/sh


docker build -t bananaforscale/transportation .
docker push bananaforscale/transportation

# Build and push image
#docker build -t bananaforscale/transportation:latest -t bananaforscale/transportation:$GIT_SHA .
#echo PUSHING LATEST
#docker push bananaforscale/transportation:latest
#echo PUSHING $GIT_SHA
#docker push bananaforscale/transportation:$GIT_SHA

# Start up deployments and services
#kubectl apply -f kube/transportation

# Deploy specific version
#kubectl set image deployments/server-deployment transportation=bananaforscale/transportation:$GIT_SHA
