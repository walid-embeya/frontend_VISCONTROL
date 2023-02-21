## **VISCONTROL**

VISCONTROL es una aplicación web que permite llevar la gestión y control sobre el acceso físico que, por parte de personal externo o visitantes, se realiza a las distintas dependencias de una organización. A través de la aplicación se podrá:

+ Llevar un registro de visitantes (altas, bajas y modificación de sus datos).
+ Programar visitas y llevar un control de las mismas.
+ Hacer consultas sobre las visitas pendientes y las ya realizadas.

## Diagrama de Clases de Diseño
![Diagrama_de_clases](https://git.institutomilitar.com/Camope/viscontrol/-/wikis/uploads/732b3edb435ddf8c4cce8fb298391fac/diagramaClases.png)
    
  **Cumplimiento de requisitos**
1. **Herencia**: Las clases `Anfitrion` y `Invitado` se extenderán de la clase padre `Persona`.
2. **Relación One-To-Many**: Relación entre `Invitado` y `Visita`, un invitado podrá formar parte de varias visitas.
3. **Método personalizado**: Cálculo del tiempo de las visitas clasificado por el tipo de `Actividad`.
4. **Listado**: Se mostrará la lista de visitas en las que ha participado un invitado.
5. **CRUD**: Se podrán añadir, consultar, modificar y quitar visitas. 
      + Crear -> 
      + Read  -> 
      + Update ->
      + Delete ->
6. **URLs** del proyecto:
   1. Repositorio proyecto: https://git.institutomilitar.com/Camope/viscontrol
   1. Librería:
7. **Despliegue** en Internet: 
   1. API:
   1. Web:
