package PhoneApp;


/**
* PhoneApp/PhoneOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Phone.idl
* 28 ����� 2017 �. 14:28:55 MSK
*/

public interface PhoneOperations 
{
  String viewInfo ();
  boolean addInfo (String note);
  boolean editInfo (int number, String note);
  boolean deleteInfo (int number);
  void shutdown ();
} // interface PhoneOperations
