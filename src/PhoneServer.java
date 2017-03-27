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
import java.util.ArrayList;

class PhoneImpl extends PhonePOA {

    private ORB orb;

    public void setORB(ORB orb_val) {
        orb = orb_val;
    }

    public String viewInfo() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("phone_book.txt"), Charset.forName("UTF-8")));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append(";");
            }
            return sb.toString();
        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        }
        return null;
    }

    public boolean addInfo(String note) {
        try {
            FileWriter writer = new FileWriter("phone_book.txt", true);
            writer.write(note);
            writer.append(';');
            writer.close();
            return true;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean editInfo(int number, String note) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("phone_book.txt"), Charset.forName("UTF-8")));
            String line = reader.readLine();
            String strs[] = line.split(";");
            ArrayList<String> notes = new ArrayList<String>();
            for (int i = 0; i < strs.length; i++) {
                notes.add(strs[i]);
            }
            notes.remove(number-1);
            notes.add(number-1,note);
            reader.close();
            PrintWriter wr = new PrintWriter("phone_book.txt");
            wr.print("");
            wr.close();
            FileWriter writer = new FileWriter("phone_book.txt", true);
            for (int i = 0; i < notes.size(); i++)
                writer.write(notes.get(i) + ";");
            writer.close();
            return true;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean deleteInfo(int number) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("phone_book.txt"), Charset.forName("UTF-8")));
            String line = reader.readLine();
            String strs[] = line.split(";");
            ArrayList<String> notes = new ArrayList<String>();
            for (int i = 0; i < strs.length; i++) {
                notes.add(strs[i]);
            }
            notes.remove(number - 1);
            reader.close();
            PrintWriter wr = new PrintWriter("phone_book.txt");
            wr.print("");
            wr.close();
            FileWriter writer = new FileWriter("phone_book.txt", true);
            for (int i = 0; i < notes.size(); i++)
                writer.write(notes.get(i) + ";");
            writer.close();
            return true;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
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
