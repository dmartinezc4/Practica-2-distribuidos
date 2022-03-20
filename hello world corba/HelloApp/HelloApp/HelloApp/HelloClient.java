package HelloApp;

import java.util.Properties;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;

public class HelloClient {

  public static void main(String[] args) {

    org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, null);

    /*Encuentra un objeto llamado "hello" obejto corba asociado con el servicio
    el cual estaba previamente ligado al servicio de nombres

    */
    org.omg.CORBA.Object obj = orb.string_to_object("corbaname::localhost:2000#hello");

    Hello hello = HelloHelper.narrow(obj);

    //Intentamos que el objeto hello haga su funcion de sacar un "Hola desde el Java Server!"
    try {
      System.out.println(hello.sayHello());
    //En caso contarario ponemos que ha habido un error con el cliente y lo imprimimos por pantalla
    } catch (org.omg.CORBA.SystemException e) {
      System.out.println("There was a problem with the client...\n" + e.getMessage());
    }

  }
}
