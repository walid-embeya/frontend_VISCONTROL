## **VISCONTROL**

VISCONTROL es una aplicación web que permite llevar la gestión y control sobre el acceso físico del personal a aquellas instalaciones o áreas que, por cuestiones de seguridad u otros condicionantes, lo requieran. A través de esta aplicación se puede:

+ Llevar un registro de visitantes (altas, bajas y modificación de sus datos).
+ Programar visitas y llevar un control de las mismas.
+ Hacer consultas sobre las visitas pendientes y las ya realizadas.

## Diagrama de Clases de Diseño
![Diagrama_de_clases](https://git.institutomilitar.com/Camope/viscontrol/-/wikis/images/diagramaClases.png)
    
  **Cumplimiento de requisitos**
1. **Herencia**: Las clases `Anfitrion` y `Invitado` se extenderán de la clase padre `Persona`.
2. **Relación One-To-Many**: Relación entre `Anfitrion` y `Visita`, un Anfitrión podrá planificar varias visitas.
3. **Método personalizado**: Cálculo del tiempo acumulado de las visitas clasificado por el tipo de `Actividad`.
4. **Listado**: Se mostrará la lista de visitas de una misma actividad.
5. **CRUD**: Se podrán añadir, consultar, modificar y quitar visitas. 
      + Create -> 
      + Read  -> 
      + Update ->
      + Delete ->
6. **URLs** del proyecto:
   1. Repositorio proyecto: https://git.institutomilitar.com/Camope/viscontrol
   1. Librería: https://github.com/walid-embeya/Proyecto_Libreria_VISCONTROL
7. **Despliegue** en Internet: 
   1. API:
   1. Web: https://viscontrol.netlify.app
