import PhoneApp.Phone;
import PhoneApp.PhoneHelper;
import PhoneApp.PhonePOA;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * Created by Lenovo Z on 26.03.2017.
 */
class PhoneImpl extends PhonePOA {

    private ORB orb;

    public void setORB(ORB orb_val) {
        orb = orb_val;
    }

    public String viewInfo() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("phone_book.txt"), Charset.forName("UTF-8")));
            String line;
            StringBuilder sb =new StringBuilder();
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                sb.append(line);
                sb.append(";");
            }
            return sb.toString();
        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        }
        return null;
    }

    public boolean addInfo() {
        Scanner input = new Scanner(System.in);

        try {
            FileWriter writer = new FileWriter("phone_book.txt", true);
            // запись всей строки
            String text = input.next();
            writer.write(text);
            // запись по символам
            writer.append(';');
            writer.close();
            return true;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean editInfo() {
        return false;
    }

    public boolean deleteInfo() {
        return false;
    }

    public void shutdown() {
        orb.shutdown(false);
    }

}

public class PhoneServer {

    public static void main(String args[]) {

        try {
            ORB orb = ORB.init(args, null);
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();
            PhoneImpl phoneImpl = new PhoneImpl();
            phoneImpl.setORB(orb);
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(phoneImpl);
            Phone href = PhoneHelper.narrow(ref);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            String name = "Phone";
            NameComponent path[] = ncRef.to_name(name);
            ncRef.rebind(path, href);
            System.out.println("Server ready and waiting ...");
            orb.run();
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }
        System.out.println("Server Exiting ...");

    }

}
