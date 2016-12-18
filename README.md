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
In order to simply explain you the functioning, the project is composed of different software, out of which the most important part is GameStack Server.
The latter is the core of the infrastructure, it is going to make more or less important decisions (thanks to some algorithms) such as
a purchase of a new server when it is needed, for instance.

As I already said, everything can be configured. So you can modify parameters as you like in the gamestack.properties file. (This one contains
server's configuration but also of the whole network's.)

Français — Un schéma en version PDF est disponible, il résumé globalement le fonctionnement de GameStack : http://bit.ly/29BmB4X
Pour vous résumer le fonctionnement, c'est plutôt simple. Le projet se constitue en différents logiciels, dont le plus important : GameStack Server.
Ce dernier est le noyau de l'infrastructure ; c'est lui qui, grâce à certains algorithmes — plus ou moins complexes —, va prendre des décisions
comme quand il faut commander une nouvelle instance, par exemple.

Comme dit plus haut, tout est configurable. Vous pouvez donc modifier les paramètres à votre guise dans le fichier gamestack.properties. (Celui-ci contient
la configuration pour le serveur en lui-même, mais pour toute l'infrastructure GameStack également.)

Server Types
------------
English — In order to have a good management of on demand Minecraft servers, a "weight" system has been created. Each server can contain a certain amount
of Minecraft servers which can change depending on the type of servers that are being ran. For example, a heavy game like a UHC of 100 players
needs much more resources than a small Rush of 8 players. Here is where the "gamegrams" step in. Abridged "gg", this unit determines the weight of a type
of server. It is up to you to define the weight of a game in the web panel or directly in the database you have chosen.

(Warning :warning:: the scale is subject to change)

Depending on the quantity of gamegrams, the amount of RAM is calculated this way: ```f(x) = 102,400x```, where ```x``` is the quantity of gamegrams
and ```f(x)``` the quantity of RAM in kb (kilobytes).

If you like, you can also activate a parameter in the configuration file called ```gameweight-resizer```, which has for value a boolean
(true/false). If the value is "true", the sizes that you fixed to your games will be modified in order to stick perfectly to their needs in terms of resources.

Français — Pour une bonne gestion des serveurs Minecraft à la demande, un système de « poids » de serveurs a été créé. Chaque serveur peut contenir un certain
nombre de serveurs Minecraft, et ce dernier fluctue en fonction des types de serveurs lancés. Par exemple, un jeu volumineux comme un UHC avec 100 joueurs 
prend plus de ressources qu'un petit Rush à 8 joueurs. C'est là qu'interviennent les « gamegrammes ». Abrégé « gg », cette unité détermine le poids d'un 
type de serveur. C'est à vous de définir les poids des jeux depuis le pannel web ou directement dans la base de données choisie.

(Attention :warning: : l'échelle est sujet à changement)

La quantité de RAM en fonction de la quantité de gamegrams, est telle : ```f(x) = 102.400x```, où ```x``` est le nombre de gamegrams et
```f(x)``` la quantité de RAM en ko (kilooctets).

Si vous le souhaitez, vous pouvez également activer dans la configuration un paramètre, appelé ```gameweight-resizer```, qui a pour valeur un booléen
(true/false). Si la valeur est mise à « vrai » (_true_), les tailles des jeux que vous aurez fixées seront modifiées pour le mieux correspondre à leurs besoins
en ressources.

Instances
---------
English — 

Français — 

Requirements
------------
English — GameStack needs a database **and** a message broker in order to work correctly. (A message broker is typically a software which allows the share
of packets — data — between connected clients.) You have the choice between the software in the following list:

* for the database:
    * MySQL (system name: "mysql"),
    * MariaDB (system name: "mariadb"),
    * PostgreSQL (system name: "postgres") :heart:.
* for the broker:
    * RabbitMQ (system name: "rabbitmq"),
    * Redis (system name: "redis"). (Although I don't recommended it to you because its main function is cache.)

Once you have done your choice then you have to inform it in the configuration file network.properties which looks like that:
```ini
# broker
# Fill in here the data relative to the broker
# (host, port, user and password)
broker.system="rabbitmq" # put here the system name associated to the software you use, provided in the list above
broker.host="192.168.99.100"
broker.port=32770
broker.vhost="" # let it empty if you don't know or if you don't use RabbitMQ
broker.user="root"
broker.password="root"
# database
# Fill in here the data relative to the database
# (host, port, user, password and database)
database.system="mariadb" # put here the system name associated to the software you use, provided in the list above
database.host="192.168.99.100"
database.port=32769
database.user="root"
database.password="root"
database.db = "gamestack"
```

Français — GameStack a besoin pour fonctionner d'une base de données **et** d'un message broker. (Un message broker est un logiciel qui permet l'échange
de paquets — données — entre clients connectés.) Vous avez le choix parmi les logiciels suivants :

* pour la base de données :
    * MySQL (nom de référence : « mysql »),
    * MariaDB (nom de référence : « mariadb »),
    * PostgreSQL (nom de référence : « postgres ») :heart:.
* pour le broker :
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

Protocol
--------

| Packet id | Packet Name | Description |
| ------------------------------------- |
| 0xFE      | Metric Packet | Statistic about the software or the network. The value is encoded in JSON. |

License
-------
This code in under the MPL 2.0 License.

&copy; 2016 Creart