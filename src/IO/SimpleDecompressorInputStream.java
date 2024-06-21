package IO;import java.io.IOException;
import java.io.InputStream;

public class SimpleDecompressorInputStream  extends InputStream {
    private InputStream in;
    public SimpleDecompressorInputStream(InputStream in) {
        this.in=in;
    }
    @Override
    public int read() throws IOException {
        return in.read();
    }
    @Override
    public int read(byte[] b) throws IOException {
        byte curr=(byte)this.read();
        int index=0;
        //read rows
        while(curr!=-2){
            b[index]=curr;
            curr=(byte)this.read();
            index++;
        }
        b[index]=-2;
        index++;
        curr=(byte)this.read();
        //read colunms
        while(curr!=-2){
            b[index]=curr;
            curr=(byte)this.read();
            index++;
        }
        b[index]=-2;
        index++;
        //read how many times we need to write the first number - 0

        curr=0;
        //count will be -1 when i have nothing to read
        while(index < b.length){
            //read how many times we need to write a number 1\0
            int count=(byte)this.read();
            if(curr==0){
                index+=count;
            }
            else {
                //loop for that many times
                for (int j = 0; j < count; j++) {
                    //write the number
                    b[index] = curr;
                    index++;
                }
            }
            //change the curr number
            curr= binaryOpposite(curr);
        }
        //return the length of the array
        return b.length;
    }
    /**
     * @param num 0\1
     * @return returns the other number
     */
    public byte binaryOpposite(byte num){
        if(num==0){
            return 1;
        }
        return 0;
    }

}
