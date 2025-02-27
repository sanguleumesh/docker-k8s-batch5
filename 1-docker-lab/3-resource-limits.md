


Restricting CPU Usage

docker run -d --cpus=1 nginx


Limiting CPU Shares (Relative Priority)

docker run -d --cpu-shares=512 nginx
docker run -d --cpu-shares=1024 nginx

Restricting CPU to Specific Cores

docker run -d --cpuset-cpus="0,1" nginx

 Stress Test with CPU Limits

 ---


Limiting Memory Usage

docker run -d --memory=256m nginx

 Limiting Memory + Swap

 docker run -d --memory=512m --memory-swap=1g nginx

Out of Memory (OOM) Test

docker run -d --memory=128m --oom-kill-disable polinux/stress stress --vm 1 --vm-bytes 256M --timeout 30s


Combined CPU + Memory Limits


 CPU and Memory Limits Together

 docker run -d --memory=256m --cpus=0.5 nginx


Running a Resource-Intensive App with Limits

docker run -d --memory=256m --cpus=1 polinux/stress stress --cpu 1 --vm 1 --vm-bytes 200M --timeout 30s


Checking and Monitoring Limits

docker stats
