# Do It
## Repositorio para la aplicacion
Este es el repositorio oficial para el desarrollo de la app Do It

## Desarrolladores
Ingeniero gran jefe: @chjuca

Esclavos:
      @davidLM20
      @iaortiz
      @CarlosCastillo10
      @bryckstar
      @renatojobal

## Guía para contribuir

### Actualizar tus repositorio
#### Clonar el repositorio
Lo primero que tienes que hacer es clonar el repositorio, puedes hacerlo a través de SSH  `git@github.com:chjuca/doit.git` o directamente a través del vínculo HTTP `https://github.com/chjuca/doit` . La ventaja ded SSH es que no tienes que estar introduciendo tus credenciales de github cuando tengas que hacer un push desde la consola.

#### Actualizar las ramas
Si acabas de clonar el repositorio, no necesitas hacer esto, pero si no, simplemente tienes que hacer lo siguiente **para cada rama**:

`git status` En el log de este comando te dará el nombre de la rama, por ejemplo : `master`

Una vez que sabes el nombre de la rama, puedes traerte los cambios de la rama en github con el comandod:
`git pull origin [nombre de la rama]`

#### Cambiarse de ramas
Para ello usas el comando
`git checkout [nombre de la rama]`
Recuerda que debes tener tu arbol de trabajo limpio ara poder hacerlo.

### ¿Cómo subir tus cambios?
Si quieres hacer cambios, lo primero que dedbes hacer es crear una nueva rama a partir de la rama `development`. 

`git checkout development`

`git pull origin development`

`git branch [nombre de la nueva rama]`

`git checkout [nombre de la nueva rama]`

Ahora si puedes empezar a hacer modificaciones. Puedes ir haciendo commits por cada cambio que hagas para tener un mejor historial de tu código.

Una vez que hayas terminado de hacer tus contribuciones en tu rama, es momento de hacer que todos los demás integrantes la tengan, para ello debes:

1. Subir tus cambios a la rama remota:
`git push origin [nombre de tu rama]`

2. Crear un pull request desde la rama `development` hacia tu rama.
	Abres un nuevo pull request
	En `base` escoges tu rama
	En `compare` escoges development
	Creas el pull request
Nota: Como es un pull request hacia un rama tuya, puedes acpetar el pull request tu mismo enseguida.

3. Crear un pull request desde tu rama hacia `development`:
	Abres un nuevo pull request
	En `base` escoges development
	En `compare` escoges tu rama
	Creas el pull request
Nota: Aquí lo ideal es que alguien más aceptase tu pull request

Y listo. 

## Referencias
Guía de Git y GitHub: https://medium.com/@sthefany/primeros-pasos-con-github-7d5e0769158c




