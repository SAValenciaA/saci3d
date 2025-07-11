# SACI3D:  Sistema de Agendamiento de Citas para Impresión 3D en la Universidad Nacional de Colombia.

## Integrantes
- Diego David Goyeneche Colmenares
- Andres Camilo Espitia Calderon
- Sergio Andres Valencia Angulo
- Yohan Steven Jimenez Hilarion
- Alejandro Muñoz Avila

## Uso
Primero se instala la librearia para usar sqlite in java instala 
[https://github.com/xerial/sqlite-jdbc/releases/download/3.50.2.0/sqlite-jdbc-3.50.2.0.jar](esto)
en esta carpeta y compila el repositorio JSON-java, el cual es
un modulo en este repositorio, como se indica en su README y 
compila el json-java.jar en la raiz de este repositorio.

Y para usar el programa se corre
```bash
$ export CLASSPATH=./json-java.jar:./sqlite-jdbc-3.50.2.0.jar
$ java Gestor.java
```

