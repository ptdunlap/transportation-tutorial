#!/bin/sh

# Build and push image
docker build -t bananaforscale/transportation:latest -t bananaforscale/transportation:$GIT_SHA .
docker push bananaforscale/transportation:latest
docker push bananaforscale/transportation:$GIT_SHA

# Start up deployments and services
kubectl apply -f kube/transportation

# Deploy specific version
kubectl set image deployments/server-deployment transportation=bananaforscale/transportation:$GIT_SHA
