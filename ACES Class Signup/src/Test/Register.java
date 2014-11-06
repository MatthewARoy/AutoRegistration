package Test;


import java.applet.Applet;
import java.applet.AudioClip;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import Signup.SignupInterface;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.FrameWindow;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlImage;


public class Register
{
    
    //tries 25 times, then reloads aces and tries again
    public int soundAlarmAfter = 50;
    public boolean successful = false;
    public boolean registrationOpen = false;
    private HtmlPage page = null;
    private WebClient webClient = null;
    private boolean readyToRegister = false;
    private boolean playingSound = false;
    
    private long startTime;
    private long timeToCompletion;
    
    private Timer _timer;
    private SignupInterface myGui;

    class AlarmTask extends TimerTask {
        Register myRegister;
        public AlarmTask(Register myReg){
            System.out.println("setup");
            myRegister = myReg;
        }
        /**
         * Called on a background thread by Timer
         */
        public void run() {
            System.out.println("starting up");
            myRegister.loginAndGetReady();
        }        
    }
    class AlarmTask2 extends TimerTask {
        Register myRegister;
        public AlarmTask2(Register myReg){
            myRegister = myReg;
        }
        
        /**
         * Called on a background thread by Timer
         */
        public void run() {
            myRegister.registrationOpen = true;
            myRegister.beginRegistration();
            _timer.cancel();
        }
    }
    
    
    
    public void addTimer(int hour, int minute, int second, int prep) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute-prep);
        calendar.set(Calendar.SECOND, second);
        Date setupTime = calendar.getTime();
        _timer = new Timer();
        _timer.schedule(new AlarmTask(this), setupTime);
        
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.HOUR_OF_DAY, hour);
        calendar2.set(Calendar.MINUTE, minute);
        calendar2.set(Calendar.SECOND, second);
        Date regTime = calendar2.getTime();
        System.out.println("Set registration for: " + regTime.toString());
        
        _timer.schedule(new AlarmTask2(this), regTime);
    }
    
    
    
    public void loadUserAndPass() {
        
        String filename = "AutoRegisterNETIDAndPass.txt";
        String path = filename;        
        try {
            FileInputStream is = new FileInputStream( path );
            InputStreamReader isr = new InputStreamReader( is, "UTF-8" );
            BufferedReader br = new BufferedReader( isr );
            String line = br.readLine();
            System.out.println("Reading File: ");
            int n = 0;
            while( line != null )
            {
                System.out.println(line);
                if (n == 1) {
                    netid = line;
                }
                else if (n == 2) {
                    acesPass = line;
                }
                // process lines of text    
                line = br.readLine();
                n++;
            }
            br.close();
            isr.close();
            is.close();
            FileOutputStream iso = new FileOutputStream(path, true);
            PrintWriter writer = new PrintWriter(iso);
            writer.println("\nPrepared to register: "+netid+ "\n");
            writer.close();
            iso.close();
            System.out.println(netid);
            System.out.println(acesPass);
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
    
    public void addGui(SignupInterface gui) {
        myGui = gui;
    }
    
    
    public void setUserAndPass(String user, String pass) {
        netid = user;
        acesPass = pass;
    }
    
    /**
     * Print console to Output.txt for debugging purposes
     */
    public void collectLog() {
        collectLog("Output.txt");
    }
    
    public void collectLog(String fileName) {
        PrintStream out;
        try {
            out = new PrintStream(new FileOutputStream(fileName));
            System.setOut(out);
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    /**
     * This is the important stuff, logging into aces and moving to the bookbag page
     */
    public void loginAndGetReady () {
        String url = "https://aces.duke.edu";

        webClient = new WebClient(BrowserVersion.getDefault());
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());

        try
        {
            page = webClient.getPage(url);

            myGui.addInfoText("Loading up aces and logging in! \n");
            System.out.println("Current page: ACES/STORM Login");

            // Current page:
            // Title=ACES/STORM Login
            // URL=http://aces.duke.edu/

            System.out.println("Current page: NetID Services");

            // Current page:
            // Title=NetID Services
            // URL=https://shib.oit.duke.edu/idp/AuthnEngine

            final HtmlTextInput login = (HtmlTextInput) (page.getByXPath("//input")).get(0);
            System.out.println(login);
            final HtmlPasswordInput password =
                    (HtmlPasswordInput) (page.getByXPath("//input")).get(1);
            System.out.println("hidden password");

            login.setValueAttribute(netid);
            password.setAttribute("value", acesPass);

            HtmlElement theElement1 = (HtmlElement) page.getElementById("Submit");
            page = theElement1.click();

            System.out.println("Current page: ");

            // Current page:
            // Title=
            // URL=https://shib.oit.duke.edu/idp/profile/SAML2/Redirect/SSO

            webClient.waitForBackgroundJavaScript(3000);

            System.out.println("Current page: Duke Student Info Systems");

            // Current page:
            // Title=Duke Student Info Systems
            // URL=https://www.siss.duke.edu/psp/CSPRD01/EMPLOYEE/HRMS/h/?tab=DEFAULT

            if (page.getUrl().toString() != "https://www.siss.duke.edu/psp/CSPRD01/EMPLOYEE/HRMS/h/?tab=DEFAULT") {
                System.out.println(page.getUrl());
                //ifRegistrationFails();
            }

            HtmlAnchor anchor2 = (HtmlAnchor) page.getElementById("DERIVED_SSS_SCL_SSS_ENRL_CART");
            if (anchor2 == null) {
                ifRegistrationFails();
            }
            System.out.println("Enroll cart: " + anchor2);

            myGui.addInfoText("Enroll cart: " + anchor2 + "\n");
            page = anchor2.click();

            // System.out.println(page.asXml());

            System.out.println("Current page: Enrollment Book Bag");
            myGui.addInfoText("Current page: Enrollment Book Bag \n");
            
            // Current page:
            // Title=Enrollment Book Bag
            // URL=https://www.siss.duke.edu/psp/CSPRD01_1/EMPLOYEE/HRMS/c/SA_LEARNER_SERVICES.SSS_STUDENT_CENTER.GBL

            System.out.println("Waiting: " + webClient.waitForBackgroundJavaScript(3000));
            // System.out.println(page.asXml());

            final List<FrameWindow> window = page.getFrames();
            final HtmlPage targetPage = (HtmlPage) window.get(0).getEnclosedPage();
            page = targetPage;
            readyToRegister  = true;
            //display classes
            int i = 0;
            String classNameLocation = "win1divR_CLASS_NAME$";
            List<DomElement> myEnrolledClasses = new ArrayList<DomElement>();
            while (!page.getElementsByIdAndOrName(classNameLocation + i).isEmpty()) {
                myEnrolledClasses.addAll(page.getElementsByIdAndOrName(classNameLocation + i));
                i++;
            }
            System.out.println("Number of class blocks enrolled in: " + i);
            myGui.addInfoText("Number of class blocks enrolled in: " + i + "\n");
            for (DomElement e : myEnrolledClasses) {
                System.out.println(e.asText());
                myGui.addInfoText(e.asText());
            }
            myGui.addInfoText("Ready to enroll! \n");
            
        }

        catch (FailingHttpStatusCodeException e1)
        {
            System.out.println("FailingHttpStatusCodeException thrown:" + e1.getMessage());
            e1.printStackTrace();
            ifRegistrationFails();

        }
        catch (MalformedURLException e1)
        {
            System.out.println("MalformedURLException thrown:" + e1.getMessage());
            e1.printStackTrace();
            ifRegistrationFails();

        }
        catch (IOException e1)
        {
            System.out.println("IOException thrown:" + e1.getMessage());
            e1.printStackTrace();
            ifRegistrationFails();

        }
        catch (Exception e)
        {
            System.out.println("General exception thrown:" + e.getMessage());
            e.printStackTrace();
            ifRegistrationFails();

        }
    }

    /**
     * Assuming the page is already at the bookbagging on
     * and the registration is open
     * It will now rapidly try to finalize the registration
     * After 25 failed attempts, it will play an alarm
     */    
    public void beginRegistration () {
        int n = 0;
        startTime = System.currentTimeMillis();
        while (!readyToRegister) {
            
        }        
        boolean gotToSignup = false;

        try {
            while (!gotToSignup) {
                System.out.println("Trying to enroll.");
                myGui.addInfoText("Trying to enroll. \n");
                HtmlDivision pageTitle2 =
                        (HtmlDivision) page.getElementById("win1divDERIVED_REGFRM1_TITLE1");
                System.out.println("Current page before click: " + pageTitle2.asText());
                myGui.addInfoText("Current page while bookbagging: " + pageTitle2.asText() + " \n");

                // testing if the registration window is open yet
                HtmlAnchor anchor3 =
                        (HtmlAnchor) page.getElementById("DERIVED_REGFRM1_LINK_ADD_ENRL");
                page = anchor3.click();

                System.out.println("Loading after click: " +
                                   webClient.waitForBackgroundJavaScript(3000));

                HtmlDivision pageTitle =
                        (HtmlDivision) page.getElementById("win1divDERIVED_REGFRM1_TITLE1");
                // testing if the registration window is open yet
                System.out.println("Current page after click: " + pageTitle.asText());
                myGui.addInfoText("Current page after clicking ADD_ENRL (go to enroll page button): \n" + pageTitle.asText() + " \n");
                HtmlAnchor submitbutton =
                        (HtmlAnchor) page.getElementById("DERIVED_REGFRM1_SSR_PB_SUBMIT");
                
                if (submitbutton != null)
                {
                    gotToSignup = true;
                    page = submitbutton.click();
                    break;
                }
                else if (pageTitle.asText().equalsIgnoreCase("Book Bag/Enroll in Classes")) {
                    HtmlDivision error =
                            (HtmlDivision) page
                                    .getElementById("win1divDERIVED_SASSMSG_ERROR_TEXT$0");
                    if (error != null) {
                        System.out.println(error.asText());
                        myGui.addInfoText(error.asText() + " \n");
                    }
                }
                else {
                    ifRegistrationFails();
                }
                n++;
                System.out.println("Attempt: "+n);
                myGui.addInfoText("Attempt: " +n+ "\n");
                if (n>soundAlarmAfter) {
                    System.out.println("I am sounding the alarm! I tried "+n+" times!");
                    myGui.addInfoText("I am sounding the alarm! I tried "+n+" times! \n");
                    ifRegistrationFails();
                    Thread.sleep(1000);
                    if (n>soundAlarmAfter && n%10==0) {
                        Thread.sleep(10000);
                        soundAlarmAfter = 20; //alarm sounds earlier after first attempt
                        loginAndGetReady();
                        beginRegistration();
                    }
                }
            }
            
            String classNameLocation = "win1divR_CLASS_NAME$";
            
            if (false) {
                
                int i = 0;
                List<DomElement> myEnrolledClasses = new ArrayList<DomElement>();
                while (!page.getElementsByIdAndOrName(classNameLocation + i).isEmpty()) {
                    myEnrolledClasses.addAll(page.getElementsByIdAndOrName(classNameLocation + i));
                    i++;
                }
                System.out.println("Number of class blocks enrolled in: " + i);
                myGui.addInfoText("Number of class blocks enrolled in: " + i+ "\n");
                for (DomElement e : myEnrolledClasses) {
                    System.out.println(e.asText());
                    myGui.addInfoText(e.asText() + "\n");
                }

                HtmlAnchor anchor4 = (HtmlAnchor) page.getElementById("DERIVED_REGFRM1_SSR_PB_SUBMIT");
                page = anchor4.click();

            }
           
            HtmlAnchor anchor5 =
                    (HtmlAnchor) page.getElementById("DERIVED_REGFRM1_SSR_LINK_STARTOVER");
            page = anchor5.click();
            
            long duration = System.currentTimeMillis()-startTime;
            myGui.addInfoText("Milliseconds to Completion: " + duration + "\n");
            
            classNameLocation = "win1divR_CLASS_NAME$";
            int j = 0;
            List<DomElement> myEnrolledClasses2 = new ArrayList<DomElement>();// =
            // page.getElementsByIdAndOrName(classNameLocation+i);
            while (!page.getElementsByIdAndOrName(classNameLocation + j).isEmpty()) {
                myEnrolledClasses2.addAll(page.getElementsByIdAndOrName(classNameLocation + j));
                j++;
            }
            
            String info = "Number of class blocks enrolled in: " + j + "\n";
            System.out.println(info);
            myGui.addInfoText(info);
            for (DomElement e : myEnrolledClasses2) {
                System.out.println(e.asText());
                myGui.addInfoText(e.asText() + "\n");
            }
            successful = true;
            webClient.closeAllWindows();
        }
        catch (FailingHttpStatusCodeException e1)
        {
            System.out.println("FailingHttpStatusCodeException thrown:" + e1.getMessage());
            e1.printStackTrace();
            ifRegistrationFails();

        }
        catch (MalformedURLException e1)
        {
            System.out.println("MalformedURLException thrown:" + e1.getMessage());
            e1.printStackTrace();
            ifRegistrationFails();

        }
        catch (IOException e1)
        {
            System.out.println("IOException thrown:" + e1.getMessage());
            e1.printStackTrace();
            ifRegistrationFails();

        }
        catch (Exception e)
        {
            System.out.println("General exception thrown:" + e.getMessage());
            e.printStackTrace();
            ifRegistrationFails();

        }

    }
    
    private void ifRegistrationFails () {
        String error = "Something went wrong! Yikes, sounding the alarm.";
        System.out.println(error);
        myGui.addInfoText(error + "\n");
         Sound sound = new Sound("SIREN.wav");
         sound.play();
    }

    private String netid = "";
    private String acesPass = "";

}
