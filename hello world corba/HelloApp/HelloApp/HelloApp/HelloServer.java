package HelloApp;
import HelloApp.*;

import java.util.Properties;

public class HelloServer {

  public static void main(String[] args) {

    //Creamos unas properties nuevas en las que ponemos el localhost y el puerto
    Properties prop = new Properties();
    prop.put("org.omg.CORBA.ORBInitialHost", "localhost");
    prop.put("org.omg.CORBA.ORBInitialPort", "2000");

    // Iniciamos el ORB y el POA
    org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, prop);
    // Instanciamos un objeto de tipo CORBA y un manager de POA
    org.omg.CORBA.Object obj = null;
    org.omg.PortableServer.POA rootPOA = null;

    try {
      // Vamos a intentar coger el objeto referenciado con el servicio de RootPOA
      // En caso contraio enviamos un error
      obj = orb.resolve_initial_references("RootPOA");
    } catch (org.omg.CORBA.ORBPackage.InvalidName e){
      System.out.println("No service with name: " + e.getMessage());
    }

    // Le pasamos al gestionador del POA la referencia del objeto
    rootPOA = org.omg.PortableServer.POAHelper.narrow(obj);
    // Creamos un server
    HelloServant hello = new HelloServant();
    // Creamos un servicio de Nombres
    org.omg.CosNaming.NamingContextExt rootContext = null;

    try {
      // Ponemos en la ids del servant al POA
      byte[] servantId = rootPOA.activate_object(hello);
      // Conectamos al objeto referenciado con servicio de nombres
      org.omg.CORBA.Object ref = rootPOA.id_to_reference(servantId);
      org.omg.CORBA.Object objRef = null;

      try {
        // Intentamos conectarnos contexto de nombrado del "root"
        objRef = orb.resolve_initial_references("NameService");
        System.out.println("Finding naming service...");
        rootContext = org.omg.CosNaming.NamingContextExtHelper.narrow(objRef);
        System.out.println("Naming Service Found!!!");
        //Caso contrario enviamos un error
      } catch (org.omg.CORBA.ORBPackage.InvalidName name) {
        System.out.println("Invalid service name...");
        name.printStackTrace();
        System.exit(0);
      }
      // Creamos una nueva referencia de nombre la cual llamamos hello y la añadimos al contexto
      String text = "hello";
      org.omg.CosNaming.NameComponent[] path = rootContext.to_name(text);
      try {
        // Unimos la nueva referencia de nombre con el servicio
        rootContext.bind(path, ref);
        //Si no lo encontramos tiramos un error
      } catch (org.omg.CosNaming.NamingContextPackage.NotFound e) {
        System.out.println("Object not found...");
        System.exit(0);
        //Si el objeto ya existe se hace un rebind
      } catch (org.omg.CosNaming.NamingContextPackage.AlreadyBound e) {
        System.out.println("Object already exists...");
        rootContext.rebind(path, ref);
        System.out.println("Rebinding object...");
        //Si no se puede seguir se lanza otro tipo de error
      } catch (org.omg.CosNaming.NamingContextPackage.CannotProceed e) {
        System.out.println("Error. Server cannot proceed...");
        System.exit(0);
      }

      // Activamos el gestionador de POA y "run" el programa
      rootPOA.the_POAManager().activate();
      System.out.println("Java Server active and waiting...");
      orb.run();

      /*Como seguimos en un try, en caso de que haya 
      un error inesperado ponemos un catch, si no lo encontramos tiramos un error
      e imprimimos en la consola el mensaje de error

      */
    } catch (java.lang.Exception e) {
      System.out.println("There was a problem with the server...\n" + e.getMessage());
    }
  }
}
