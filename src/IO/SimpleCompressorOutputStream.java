package IO;

import java.io.IOException;
import java.io.OutputStream;

/**
 * SimpleCompressorOutputStream is a custom OutputStream that compresses data
 * using a simple form of Run-Length Encoding (RLE).
 */
public class SimpleCompressorOutputStream extends OutputStream {
    private OutputStream out;

    /**
     * Constructs a SimpleCompressorOutputStream with the specified OutputStream.
     *
     * @param out the OutputStream to write compressed data to
     */
    public SimpleCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    /**
     * Compresses the input byte array using a simple form of Run-Length Encoding (RLE)
     * and writes the compressed data to the output stream.
     *
     * @param b the byte array to be compressed and written
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void write(byte[] b) throws IOException {
        int index = 0;

        // Write rows
        while (b[index] != -2) {
            out.write(b[index]);
            index++;
        }
        out.write(b[index]);
        index++;

        // Write columns
        while (b[index] != -2) {
            out.write(b[index]);
            index++;
        }
        out.write(b[index]);
        index++;

        int count = 0;
        // Start with 0
        byte curr = 0;

        // Compress data using RLE
        for (int i = index; i < b.length; i++) {
            if (curr == b[i]) {
                count++;
                if (count == 253) {
                    // Write 255 if count reaches 253
                    this.write(count);
                    // Switch to the other number
                    curr = binaryOpposite(curr);
                    // Write 0 for the new number
                    count = 0;
                    this.write(count);
                    // Switch back
                    curr = binaryOpposite(curr);
                }
            } else {
                // Write the count of the current run
                this.write(count);
                // Switch to the other number
                curr = binaryOpposite(curr);
                count = 0;
                // Stay on the same byte for the next iteration
                i--;
            }
        }
        // Write the count of the final run
        this.write(count);
    }

    /**
     * Returns the opposite value of the input byte (0 or 1).
     *
     * @param num the byte value (0 or 1)
     * @return the opposite byte value
     */
    public byte binaryOpposite(byte num) {
        return (byte) (num == 0 ? 1 : 0);
    }
}
