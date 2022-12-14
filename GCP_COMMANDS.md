## To Start on GCP

```cmd
helm repo add bitnami https://charts.bitnami.com/bitnami
```

### # Kafka (Zookeeper + Kafka)

1. Install Zookeeper first

```cmd
helm install zookeeper bitnami/zookeeper \
  --set replicaCount=1 \
  --set auth.enabled=false \
  --set allowAnonymousLogin=true
```

2. Install Kafka

read ZOOKEEPER-SERVICE-NAME `zookeeper.default.svc.cluster.local`

```cmd
helm install kafka bitnami/kafka \
  --set zookeeper.enabled=false \
  --set replicaCount=1 \
  --set externalZookeeper.servers=zookeeper.default.svc.cluster.local
```

Grab serviceName: `kafka.default.svc.cluster.local`


### # Install Mongo

```cmd
helm install mongo \
--set auth.rootPassword=mongo \
--set auth.username=mongo \
--set auth.password=mongo \
--set auth.database=tutor-db \
bitnami/mongodb
```

### # Install MySQL

```cmd
helm install swa-mysql \
  --set auth.rootPassword=root \
  --set metrics.livenessProbe.initialDelaySeconds=300 \
  --set auth.database=test-db \
bitnami/mysql
```

### # Install Redis

```cmd
helm install redis \
  --set auth.password=redis \
  --set replica.replicaCount=1 \
    bitnami/redis
```

### # Install Elastic Search

```cmd
helm install elasticsearch \
    --set master.replicaCount=1 \
    --set data.replicaCount=1 \
    --set coordinating.replicaCount=1 \
    --set ingest.enabled=1 \
    --set ingest.replicaCount=1 \
 bitnami/elasticsearch
```

### # Install Prometheus and Grafana

```cmd
helm install kube-prometheus \
bitnami/kube-prometheus
```

```cmd
helm install grafana \
bitnami/grafana
```
