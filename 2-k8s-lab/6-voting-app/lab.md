


### Pod with basic networking ( ClusterIP, NodePort)

```bash
kubectl apply -f voting-app-v1.yaml
kubectl get pods
kubectl get svc

kubectl port-forward --address 0.0.0.0 svc/vote 30002:80
kubectl port-forward --address 0.0.0.0 svc/result 30004:80


kubectl delete -f voting-app-v1.yaml
```



### Pod scheduling with, nodeAffinity, nodeAntiAffinity, podAffinity, podAntiAffinity

```bash
kubectl get nodes
kubectl label node my-k8s-cluster-worker role=db
kubectl apply -f voting-app-v2.yaml
kubectl get pods -o wide

kubectl delete -f voting-app-v2.yaml

```

### pod with storage (emptyDir, hostPath, nfs, ceph, minio)

```bash

# ✅ 1️⃣ Install NFS Server on your host (Ubuntu/Debian):

sudo apt update
sudo apt install -y nfs-kernel-server

# ✅ 2️⃣ Create and configure the NFS export directory:

sudo mkdir -p /data/nfs/postgres
sudo chown -R nobody:nogroup /data/nfs/postgres
sudo chmod -R 777 /data/nfs/postgres

# ✅ 3️⃣ Export the directory in /etc/exports:

sudo nano /etc/exports
/data/nfs/postgres *(rw,sync,no_subtree_check,no_root_squash)

sudo exportfs -rav
sudo systemctl restart nfs-kernel-server

sudo exportfs -v

# ✅ 4️⃣ Verify NFS access from Kind (optional check):

docker ps  # Get Kind node container ID
docker exec -it <kind-node-container-id> bash
apt update && apt install -y nfs-common
showmount -e <host-ip>

# ✅ 5️⃣ Apply Kubernetes PV and PVC:
kubectl apply -f db-pv.yaml
kubectl apply -f db-pvc.yaml


# ✅ 6️⃣ Update and apply your Postgres Deployment with the PVC:

kubectl apply -f voting-app-v4.yaml

# ✅ 7️⃣ Verify everything is working:

kubectl get pv
kubectl get pvc
kubectl get pods
kubectl describe pvc postgres-pvc
kubectl describe pod <postgres-pod-name>


# ✅ 8️⃣ Check data persistence:
sudo ls /data/nfs/postgres

# ✅ 9️⃣ Cleanup:

kubectl delete -f voting-app-v4.yaml
kubectl delete -f db-pvc.yaml
kubectl delete -f db-pv.yaml

```


### dynamic provisioning with storageClass


```bash

# -----------------------------------------
# 1️⃣ Clean up the existing failed provisioner and PVC
# -----------------------------------------
kubectl delete pvc postgres-dynamic-pvc
helm uninstall nfs-client

# -----------------------------------------
# 2️⃣ Verify NFS server export on the host (already configured)
# -----------------------------------------
sudo exportfs -rav
sudo exportfs -v
# Ensure output shows:
/data/nfs/postgres *(rw,sync,no_subtree_check,no_root_squash)

# -----------------------------------------
# 3️⃣ Ensure permissions on NFS export directory
# -----------------------------------------
sudo chmod -R 777 /data/nfs/postgres
sudo chown -R nobody:nogroup /data/nfs/postgres
sudo systemctl restart nfs-kernel-server

# -----------------------------------------
# 4️⃣ Reinstall the NFS Subdir External Provisioner with correct settings
# -----------------------------------------
helm repo add nfs-subdir-external-provisioner https://kubernetes-sigs.github.io/nfs-subdir-external-provisioner/
helm repo update

helm install nfs-client nfs-subdir-external-provisioner/nfs-subdir-external-provisioner \
  --set nfs.server=10.0.0.4 \
  --set nfs.path=/data/nfs/postgres \
  --set storageClass.name=nfs-storage \
  --set storageClass.defaultClass=true

# -----------------------------------------
# 5️⃣ Verify the provisioner pod is running
# -----------------------------------------
kubectl get pods -l app=nfs-subdir-external-provisioner
kubectl logs -l app=nfs-subdir-external-provisioner

# -----------------------------------------
# 6️⃣ Recreate the PVC for Postgres with the dynamic StorageClass
# -----------------------------------------
cat <<EOF | kubectl apply -f -
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: postgres-dynamic-pvc
spec:
  accessModes:
    - ReadWriteMany
  resources:
    requests:
      storage: 10Gi
  storageClassName: nfs-storage
EOF


kubectl apply -f db-pvc-dynamic.yaml

# -----------------------------------------
# 7️⃣ Verify the PVC and PV are Bound
# -----------------------------------------
kubectl get pvc
kubectl get pv
kubectl describe pvc postgres-dynamic-pvc

# -----------------------------------------
# 8️⃣ Update your Postgres Deployment (if not already) to use the dynamic PVC
# -----------------------------------------
# In the volumes section:
# volumes:
#   - name: postgres-storage
#     persistentVolumeClaim:
#       claimName: postgres-dynamic-pvc
# And mount:
# volumeMounts:
#   - name: postgres-storage
#     mountPath: /var/lib/postgresql/data

kubectl apply -f voting-app-v5.yaml

# -----------------------------------------
# 9️⃣ Final check that Postgres pod is running and writing to the NFS volume
# -----------------------------------------
kubectl get pods
kubectl describe pod <postgres-pod-name>


# ✅ 2️⃣ Check the StorageClass:
kubectl get sc



# ✅ 6️⃣ Check data persistence:

sudo ls /data/nfs/postgres

# ✅ 7️⃣ Cleanup:

kubectl delete -f voting-app-v5.yaml
kubectl delete -f db-pvc-dynamic.yaml
helm uninstall nfs-client

```




### Pod with ConfigMap and Secret

```bash

kubectl apply -f voting-app-v6.yaml
kubectl get pods
kubectl delete -f voting-app-v6.yaml


```



### Pod with Liveness and Readiness Probe

```bash

kubectl apply -f voting-app-v7.yaml
kubectl get pods
kubectl delete -f voting-app-v7.yaml
```


✅ Next Steps
Would you like me to: ✅ Package this into a single Helm chart for easy deployment?
✅ Provide a HealthCheck dashboard using Prometheus & Grafana?
✅ Add Startup Probes for apps that take longer to start?









