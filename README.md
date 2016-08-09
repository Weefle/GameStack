GameStack
=========
What is it?
-----------
English — GameStack is a fully automated, highly available, fully configurable Minecraft on demand servers manager. 
It allows you to deploy your Minecraft network very easily.

Français — GameStack est un système de serveurs à la demande 100% automatisé, totalement configurable et hautement disponible.
Il vous permet de déployer votre network Minecraft très facilement.
Technical part
==============
How does it work?
-----------------
English — A diagram will be available soon!
In order to simply explain you the functioning, the project is composed of different softwares whose the most important part: GameStack Server.
The latter is the core of the infrastructure, it is going to make more or less important decisions (thanks to some algorithms) like when
a new server needs to be purchased, for instance.

As I already said, everything is configurable. So you can modify parameters as you like in the server.properties file. (This one contains
server's configuration but also the whole network's one.)

Français — Un schéma en version PDF est disponible, il résumé globalement le fonctionnement de GameStack : http://bit.ly/29BmB4X
Pour vous résumer le fonctionnement, c'est plutôt simple. Le projet se constitue en différents logiciels, dont le plus important : GameStack Server.
Ce dernier est le noyau de l'infrastructure ; c'est lui qui, grâce à certains algorithmes — plus ou moins complexes —, va prendre des décisions
comme quand il faut commander une nouvelle instance, par exemple.

Comme dit plus haut, tout est configurable. Vous pouvez donc modifier les paramètres à votre guise dans le fichier server.properties. (Celui-ci contient
la configuration pour le serveur en lui-même, mais pour toute l'infrastructure GameStack également.)

Requirements
------------
English — GameStack needs a database **and** a message broker in order to work correctly. (A message broker is typically a software which allows the share
of packets — data — between connected clients.) You have the choice between the softwares in the following list.

* For the database:
    * MySQL (system name: "mysql"),
    * MariaDB (system name: "mariadb"),
    * PostgreSQL (system name: "postgres") :heart:.
* For the broker:
    * RabbitMQ (system name: "rabbitmq"),
    * Redis (system name: "redis"). (Even if I don't recommended it to you, given that its main function is cache.)
    
Once you have done your choice, you have then to inform it in the configuration file network.properties which looks like the following:
```ini
# broker
# Inform here data relative to the broker
# (host, port, user and password)
broker.system="rabbitmq" # put here the system name associated to the software you use, given in the list above
broker.host="192.168.99.100"
broker.port=32770
broker.vhost="" # let it empty if you don't know or if you don't use RabbitMQ
broker.user="root"
broker.password="root"
# database
# Inform here data relative to the database
# (host, port, user, password and database)
database.system="mariadb" # put here the system name associated to the software you use, given in the list above
database.host="192.168.99.100"
database.port=32769
database.user="root"
database.password="root"
database.db = "gamestack"
```

Français — GameStack a besoin pour fonctionner d'une base de données **et** d'un message broker. (Un message broker est un logiciel qui permet l'échange
de paquets — données — entre clients connectés.) Vous avez le choix parmi les logiciels suivants :

* pour la base de données :
    * MySQL (nom de référence : "mysql"),
    * MariaDB (nom de référence : "mariadb"),
    * PostgreSQL (nom de référence : "postgres") :heart:.
* pour les brokers :
    * RabbitMQ (nom de référence : "rabbitmq"),
    * Redis (nom de référence : "redis"). (Bien que non recommandé, vu que sa fonction principale est de servir de cache.)
    
Une fois que vous avez fait votre choix, vous devez ensuite le renseigner dans le fichier de configuration network.properties, qui se présente ainsi :
```ini
# broker
# Renseignez ici les données relatives au broker
# (hôte, port, nom d'utilisateur et son mot de passe)
broker.system="rabbitmq" # ici, mettez le nom de référence associé à votre logiciel choisi, donné dans la liste ci-dessus
broker.host="192.168.99.100"
broker.port=32770
broker.vhost="" # laissez vide si vous ne savez pas, ou si vous n'utilisez pas RabbitMQ
broker.user="root"
broker.password="root"
# database
# Renseignez ici les données relatives à la base de données
# (hôte, port, nom d'utilisateur et son mot de passe)
database.system="mariadb" # ici, mettez le nom de référence associé à votre logiciel choisi, donné dans la liste ci-dessus
database.host="192.168.99.100"
database.port=32769
database.user="root"
database.password="root"
database.db="gamestack"
```

License
-------
This code in under the MPL 2.0 License.

&copy; 2016 Creart