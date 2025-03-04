

###  deploy java-web-service pod

```bash
kubectl apply -f java-web-service-pod.yaml
kubectl delete -f java-web-service-pod.yaml
kubectl get pods -o wide
kubectl logs java-web-service-pod
kubectl exec -it java-web-service-pod -c java-web-service-container -- sh
kubectl exec -it java-web-service-pod -c log-processor-container -- sh
apk --no-cache add curl
curl http://localhost:8080/hello


```


### create PV
    
```bash
kubectl apply -f pv.yaml
kubectl get pv
kubectl apply -f pvc.yaml
kubectl get pvc
```



### Volume: EmptyDir

```bash
kubectl apply -f volume-emptydir.yaml
kubectl exec -it pod1 -c reader -- sh
cat /data/file.txt
kubectl delete pod pod1

```

### Volume: HostPath

```bash
kubectl apply -f volume-hostpath.yaml
kubectl exec -it hostpath-demo -- cat /mnt/logs/app.log

kubectl delete pod hostpath-demo
kubectl apply -f volume-hostpath.yaml

kubectl exec -it hostpath-demo -- cat /mnt/logs/app.log

kubectl delete pod hostpath-demo

```

### Persistent Volume and Persistent Volume Claim (PV and PVC)

```bash
kubectl apply -f pv.yaml
kubectl apply -f pvc.yaml
kubectl apply -f pvc-pod.yaml

kubectl exec -it pvc-pod -- cat /data/test.txt

kubectl delete pod pvc-pod
kubectl apply -f pvc-pod.yaml

kubectl exec -it pvc-pod -- cat /data/test.txt

kubectl delete pvc my-pvc
kubectl get pv

kubectl delete pv my-pv

```







