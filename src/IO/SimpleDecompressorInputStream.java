package IO;

import java.io.IOException;
import java.io.InputStream;

/**
 * The SimpleDecompressorInputStream class extends InputStream to provide a simple decompression
 * functionality. It reads compressed data from an input stream and decompresses it.
 */
public class SimpleDecompressorInputStream extends InputStream {
    private InputStream in;

    /**
     * Constructs a SimpleDecompressorInputStream with the specified input stream.
     *
     * @param in the input stream to read compressed data from
     */
    public SimpleDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    /**
     * Reads the next byte of data from the input stream.
     *
     * @return the next byte of data, or -1 if the end of the stream is reached
     * @throws IOException if an I/O error occurs
     */
    @Override
    public int read() throws IOException {
        return in.read();
    }

    /**
     * Reads decompressed data into an array of bytes.
     *
     * @param b the buffer into which the decompressed data is read
     * @return the total number of bytes read into the buffer
     * @throws IOException if an I/O error occurs
     */
    @Override
    public int read(byte[] b) throws IOException {
        byte curr = (byte) this.read();
        int index = 0;

        // Read rows
        while (curr != -2) {
            b[index] = curr;
            curr = (byte) this.read();
            index++;
        }
        b[index] = -2;
        index++;
        curr = (byte) this.read();

        // Read columns
        while (curr != -2) {
            b[index] = curr;
            curr = (byte) this.read();
            index++;
        }
        b[index] = -2;
        index++;

        // Read how many times we need to write the first number - 0
        curr = 0;

        // Count will be -1 when there is nothing to read
        while (index < b.length) {
            // Read how many times we need to write a number 1\0
            int count = (byte) this.read();
            if (curr == 0) {
                index += count;
            } else {
                // Loop for that many times
                for (int j = 0; j < count; j++) {
                    // Write the number
                    b[index] = curr;
                    index++;
                }
            }
            // Change the current number
            curr = binaryOpposite(curr);
        }

        // Return the length of the array
        return b.length;
    }

    /**
     * Returns the binary opposite of the given byte.
     *
     * @param num the byte to find the binary opposite of (0 or 1)
     * @return the binary opposite (1 or 0)
     */
    public byte binaryOpposite(byte num) {
        if (num == 0) {
            return 1;
        }
        return 0;
    }
}
