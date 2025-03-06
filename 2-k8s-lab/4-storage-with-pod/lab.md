

### host-path based PV
```bash
kubectl apply -f pv.yaml
kubectl delete -f pv.yaml
kubectl get pv
kubectl apply -f pvc.yaml
kubectl delete -f pvc.yaml
kubectl get pvc
kubectl apply -f java-web-service-pod.yaml
kubectl delete -f java-web-service-pod.yaml
kubectl get pods -o wide
```


#### NFS based PV
```bash
kubectl apply -f nfs-server.yaml
kubectl get pods
kubectl get svc

kubectl apply -f nfs-pv.yaml
kubectl delete -f nfs-pv.yaml

kubectl apply -f nfs-pvc.yaml
kubectl delete -f nfs-pvc.yaml

kubectl apply -f java-web-service-pod-nfs.yaml
kubectl delete -f java-web-service-pod-nfs.yaml

kubectl get pv
kubectl get pvc
kubectl get pods -o wide
kubectl describe pod java-web-service-pod-nfs
kubectl logs java-web-service-pod-nfs
```




