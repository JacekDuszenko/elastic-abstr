docker network create elastics
docker run -d --network elastics --name elasticsearch -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:6.6.2
docker run -d --network elastics -p 5601:5601 kibana:7.6.1

