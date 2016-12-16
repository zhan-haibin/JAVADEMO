/*
 * A simple example on how to establish connection to SAP Business One.
 * For this program to work, you need SAP Business One DI API installed.
 */

/*
 * Import JCO libraries from DI API folder. Two files need to be added to your
 * project libraries - sboapi.jar and sbowrapper.jar
 * The files are usually located in:
 * C:\Program Files (x86)\SAP\SAP Business One DI API\JCO\LIB - 32 bit build
 * C:\Program Files\SAP\SAP Business One DI API\JCO\LIB - 64 bit build
 */
import com.sap.smb.sbo.api.*;

/**
 *
 * @author Rafal Rozmus (rozmus.rafal@gmail.com)
 */
public class ConnectToServer 
{
    
    public ICompany company;
    
    public static void main(String[] args) 
    {
        ConnectToServer server = new ConnectToServer();
        server.connect();
    }
    
    /**
     * Set all connection parameters, connect to SAP Business One, initialise
     * company instance and disconnect from server.
     * 
     * @return 0 if success, -1 if fail
     */
    public int connect() 
    {  
        int connectionResult = 0;
        try 
        {
            // initialise company instance
            company = SBOCOMUtil.newCompany();
            // set database server host
            company.setServer("ZHANHB");
            // set company database
            company.setCompanyDB("SBODEMOCN");
            // set SAP user
            company.setUserName("manager");
            // set SAP user password
            company.setPassword("avatech");
            // set SQL server version
            company.setDbServerType(SBOCOMConstants.BoDataServerTypes_dst_MSSQL2014);
            // set whether to use trusted connection to SQL server
            company.setUseTrusted(false);
            // set SAP Business One language
            company.setLanguage(SBOCOMConstants.BoSuppLangs_ln_Chinese);
            // set database user
            company.setDbUserName("sa");
            // set database user password
            company.setDbPassword("avatech");
            // set license server and port
            company.setLicenseServer("ZHANHB:30000");
            
            // initialise connection
            connectionResult = company.connect();
            
            // if connection successful
            if (connectionResult == 0) 
            {
                System.out.println("Successfully connected to " + company.getCompanyName());
                this.disconnect();
            }
            // if connection failed
            else 
            {
                // get error message fom SAP Business One Server
                SBOErrorMessage errMsg = company.getLastError();
                System.out.println(
                        "Cannot connect to server: "
                        + errMsg.getErrorMessage()
                        + " "
                        + errMsg.getErrorCode()
                );
            }
        }
        catch (Exception e) 
        {
            e.printStackTrace();
            return -1;    
        }
        return connectionResult;
    }
    
    /**
     * Disconnect from SAP Business One server.
     */
    public void disconnect() 
    {
        company.disconnect();
        System.out.println("Application disconnected successfully");
    }
}
