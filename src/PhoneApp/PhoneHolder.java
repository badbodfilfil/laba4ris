package PhoneApp;

/**
* PhoneApp/PhoneHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Phone.idl
* 28 ����� 2017 �. 14:28:55 MSK
*/

public final class PhoneHolder implements org.omg.CORBA.portable.Streamable
{
  public PhoneApp.Phone value = null;

  public PhoneHolder ()
  {
  }

  public PhoneHolder (PhoneApp.Phone initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = PhoneApp.PhoneHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    PhoneApp.PhoneHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return PhoneApp.PhoneHelper.type ();
  }

}
