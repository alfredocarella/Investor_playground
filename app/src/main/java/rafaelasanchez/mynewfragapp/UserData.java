package rafaelasanchez.mynewfragapp;

public class UserData{

    private  String theString;
    private  int theCurrentFragment;
    private  String theCurrentCompany;

    public UserData() {
        theString = "Text";
        theCurrentFragment = 1;
        theCurrentCompany = "REC";
    }



    public String getTheString() {
        return theString;
    }

    public void setTheString(String string) {
        theString = string;
    }

    public int getTheCurrentFragment() {
        return theCurrentFragment;
    }

    public void setTheCurrentFragment(int fragment) {
        theCurrentFragment = fragment;
    }

    public String getTheCurrentCompany() {
        return theCurrentCompany;
    }

    public void setTheCurrentCompany(String company) {
        theCurrentCompany = company;
    }
}
