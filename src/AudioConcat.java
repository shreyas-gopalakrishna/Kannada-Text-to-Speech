import java.io.File;
import java.io.SequenceInputStream;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class AudioConcat {										
	int counter=0;
	File fileOut;
	AudioInputStream audioBuild=null;
	String finalAudio="output.wav";
    /*
        This method concatenates two sound clips,and stores it in AudioInputStream object
    */
    public void Concat(File sound1) {														// function to take sound file and append to file  
        counter++;
	File empty=new File("kag/empty.wav");
	try {
                AudioInputStream audio1 = AudioSystem.getAudioInputStream(sound1);	
                AudioInputStream audio2 = AudioSystem.getAudioInputStream(empty);
                if(counter == 1)
                {
                	audioBuild= new AudioInputStream(new SequenceInputStream(audio2, audio1),	// object of AudioInputStream class which 
																						// stores the input file with previous values
                        		audio1.getFormat(), audio1.getFrameLength() + audio2.getFrameLength());
                }
                else
                {
        		audioBuild = new AudioInputStream(new SequenceInputStream(audioBuild,
                				        AudioSystem.getAudioInputStream(sound1)),
										        /* AudioSystem.getAudioInputStream(sound1).getFormat()*/audioBuild.getFormat(),
                        				audioBuild.getFrameLength() + audio1.getFrameLength());
                }
            }
            catch (Exception e)	{
            	e.printStackTrace();
	    }
		
    }
    /*
        This returns final output file in which all the clips have been appended
    */
    public File returnFinalAudio()															// returns the final audio file
	{
		return fileOut;
	}
    /*
        This method loads the AudioInputStream object into the output file taken as input parameter
    */
    
    public void append(File fileOut)														// write into the final file 
	{
            this.fileOut=fileOut;
            try {
                    AudioSystem.write(audioBuild, AudioFileFormat.Type.WAVE, fileOut);
            }
            catch(Exception e)
            {
            }
	}
	
}