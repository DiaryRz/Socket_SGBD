package exception;
import java.lang.Exception;
public class RequetteException extends Exception{
    String att ;
    // public String ReqExc(String ex)
    // {
    //    return ex;
    // }

    public String getAtt()
    {
        return att;
    }
    public void setAtt(String attribut)
    {
        this.att = attribut;
    }


    public RequetteException(String attribut)
    {
        att = attribut;
    }
    public RequetteException(){}
}