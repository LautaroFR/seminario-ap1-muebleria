# AP3 - Java + MySQL

Copiar `src/` sobre `GestionMuebleria/src/` del proyecto existente. Conservar el archivo JAR en `GestionMuebleria/lib/`.

Desde Git Bash, en GestionMuebleria:

```bash
export MUEBLERIA_DB_PASSWORD='TU_PASSWORD_REAL'
mkdir -p out
javac -encoding UTF-8 -cp "lib/*" -d out src/modelo/*.java src/dao/*.java src/util/*.java src/controlador/*.java
java -cp "out;lib/*" util.PruebaConexion
java -cp "out;lib/*" controlador.Main
```

No subas contraseñas al repositorio. `controlador.Prueba` es una prueba de la versión en memoria y puede seguir ejecutándose por separado. El prototipo MySQL utiliza `controlador.Main` y los DAO.
