# mpx-framework
Mi propio framework en Java para uso de MySQL y Archivos de forma fácil

---

### Bajar los cambios en otros proyectos
Primero añadimos este repositorio como remote

```
git remote add mpx git@github.com:rmaafs/mpx-framework.git
```

Luego ejecutamos
```
git fetch mpx
git checkout -b mpx2 mpx/master
git checkout master
git merge mpx2 master
```
