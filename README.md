# Contact Manager

Este es un programa simple de gestión de contactos desarrollado en Java. Permite a los usuarios realizar operaciones CRUD (Crear, Leer, Actualizar y Eliminar) sobre una lista de contactos almacenados en archivos de texto.

## Clases Principales

### Contacto

La clase `Contacto` representa a un contacto individual en la lista de contactos. Contiene atributos como nombre, apellidos, notas, fecha de nacimiento, números de teléfono y direcciones de correo electrónico. Además, proporciona métodos para establecer y obtener estos atributos, así como para imprimir la información del contacto.

### CRUD

La clase `CRUD` maneja las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre la lista de contactos. Permite al usuario realizar estas operaciones a través de un menú interactivo. Proporciona métodos para añadir, ver, actualizar y eliminar contactos, así como para consultar el número total de contactos almacenados.

### Menu

La clase `Menu` proporciona el menú interactivo para que el usuario pueda seleccionar las diferentes opciones del programa. También maneja la entrada del usuario y dirige las operaciones CRUD según la opción seleccionada. Contiene métodos para imprimir el menú, pedir la opción al usuario y dirigir las operaciones CRUD.

### Main

La clase `Main` es la clase principal del programa. Aquí se encuentra el método `main`, que inicializa el programa creando una instancia de `Menu` y llamando al método `inicio` para comenzar la ejecución del programa. Es el punto de entrada del programa y se encarga de lanzar la aplicación y gestionar su ciclo de vida.

## Requisitos

- Java 8 o superior.

## Instalación

1. Clona el repositorio:

```bash
git clone https://github.com/tu_usuario/contact-manager.git```

2. Compila el programa:

```javac *.java```

3. Ejecuta el programa:

```java contacto.Main```

## Uso

Al ejecutar el programa, se mostrará un menú con las diferentes opciones disponibles. Selecciona una opción ingresando el número correspondiente y sigue las instrucciones en pantalla para realizar las operaciones CRUD sobre la lista de contactos.

## Contribución

Las contribuciones son bienvenidas. Si deseas contribuir a este proyecto, por favor abre un problema o envía una solicitud de extracción con tus mejoras.

## Licencia

Este proyecto está licenciado bajo la Licencia MIT.
