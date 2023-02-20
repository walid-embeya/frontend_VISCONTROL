## **VISCONTROL**

VISCONTROL es una aplicación web que permite llevar la gestión y control sobre el acceso físico que, por parte de personal externo o visitantes, se realiza a las distintas dependencias de una organización. A través de la aplicación se podrá:

+ Llevar un registro de visitantes (altas, bajas y modificación de sus datos).
+ Programar visitas y llevar un control de las mismas.
+ Hacer consultas sobre las visitas pendientes y las ya realizadas.

## Diagrama de Clases de Diseño
![Diagrama_de_clases](https://git.institutomilitar.com/Camope/viscontrol/-/wikis/uploads/a4386cd88a7ab9351e4a35d4f8534d58/viscontrolClases.png)
    
  **Cumplimiento de requisitos**
1. **Herencia**: Las clases `Empleado` y `Visitante` se extenderán de la clase padre `Persona`.
2. **Relaciones One-To-Many**: Relación entre `Empleado` y `Visitante`, y la relación entren `Empleado` y `Visita`.
3. **Método personalizado**: Cálculo del tiempo medio real de las visitas clasificado por el tipo de `Actividad`.
4. **Listado**: Se mostrarán los materiales necesarios para construir una ortodoncia
5. **CRUD**: Se podrán añadir, consultar, modificar y quitar visitantes, actividades y visitas. 
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
