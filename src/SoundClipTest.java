// all the concat() are for building the final file
// append() is for forming the final file
//play() is self explanatory
import java.io.*;
import javax.sound.sampled.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.JOptionPane;
public class SoundClipTest  {
	AudioConcat f=new AudioConcat();
	Clip clip=null;
        int counter=0;
   // Constructor
    public SoundClipTest(node head) {						
        File swara[]=new File[16];
        File delay160=new File("kag/delay160.wav");
        File half_vyan[]=new File[35];
        for(int i=0;i<16;i++)
        {
            swara[i]=new File("kag/swara/"+i+".wav");
        }
        for(int i=0;i<35;i++)
        {
            half_vyan[i]=new File("kag/half vyanj/"+i+".wav");
        }
        File vyanSwar[][]=new File[35][15];
        for(int i=0;i<35;i++)
        {
            for(int j=0;j<14;j++)
            {
                vyanSwar[i][j]=new File("kag/"+i+" "+j+".wav");
            }
        }
        AudioInputStream audioIn=null;
        if(head!= null)     //Fetch audio only if some text has been entered.
        {
            counter=1;
            head=head.link;
         
            while(head != null)	{	//run loop till end
             
                if(head.main_vyanj != -1 && (!head.am || head.swara!=-1 )) {	// otherwise it is vyanjana with swara,//add half vyanj (+swara will follow)
                    if(head.swara==-1)
                        head.swara++;
                    f.Concat(vyanSwar[head.main_vyanj][head.swara]);
                    
                }                
                if(head.am && head.swara == -1)	{			// if am is present, put am
                    f.Concat(vyanSwar[head.main_vyanj][12]);
                
                }
                if(head.am && head.swara != -1)
                {
                    f.Concat(swara[14]);
                }
                if(head.next_vat != -1 )    {		//if next vyattakshara is present, add it
                    f.Concat(half_vyan[head.next_vat]);
                }
                
                if(head.main_vyanj == -1 && head.swara != -1 )    {	//if next vyattakshara is present, add it
                    
                    if(head.swara==-1)
                        head.swara++;
                    f.Concat(swara[head.swara]);
                }
                
                if(head.link != null && head.link.am==false 
                        && head.link.main_vyanj== -1 && head.link.next_vat == -1 
                        && head.link.swara == -1 && head.link.vat_vyanj == -1)   //if there is a space,then add delay
                {
                    //f.Concat(delay40);
                    //f.Concat(delay40);
                    //f.Concat(delay40);
                    f.Concat(delay160);
                    //f.Concat(delay160);
                    
                    
                }
                head=head.link;
                
            }
            try {
                File file=new File("combined.wav");
                f.append(file); 		// final file formation
            }
            catch(Exception e)	{ }
        }
    
    }
    /*
        return the final sound file
    */
    public AudioConcat retFile()
    {    
        return f;
    }
  
    /*
        play the final file
    */
    public void play(String data)
    {
	if(counter == 1)
        {
            try
            {
                File file=f.returnFinalAudio();
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
		//Get a sound clip resource.
		clip = AudioSystem.getClip();
		// Open audio clip and load samples from the audio input stream.
		clip.open(audioIn);
                ClipGui frame=new ClipGui(clip,data);
                frame.setVisible(true);
		
            }
            catch(UnsupportedAudioFileException | IOException | LineUnavailableException e)	{ }
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Please enter a valid string:");
        }
    }
}
