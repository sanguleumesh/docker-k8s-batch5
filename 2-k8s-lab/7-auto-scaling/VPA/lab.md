


### vpa

```bash

kubectl apply -f https://github.com/kubernetes/autoscaler/releases/latest/download/vertical-pod-autoscaler.yaml

kubectl apply -f nginx.yaml
kubectl apply -f nginx-vpa.yaml



git clone https://github.com/kubernetes/autoscaler.git
cd autoscaler/vertical-pod-autoscaler/

kubectl apply -f deploy/

kubectl get pods -n kube-system | grep vpa
kubectl get vpa