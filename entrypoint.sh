#!/bin/bash
exec java -Dserver.port=$PORT -Xms128m -Xmx468m -jar "/usr/app/bin/trackr.jar"