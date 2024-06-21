package IO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Stack;

/**
 * MyDecompressorInputStream is a custom InputStream that decompresses data
 * previously compressed using various compression algorithms.
 */
public class MyDecompressorInputStream extends InputStream {
    private InputStream in;

    /**
     * Constructs a MyDecompressorInputStream with the specified InputStream.
     *
     * @param in the InputStream to read compressed data from
     */
    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        return in.read();
    }

    /**
     * Reads compressed data from the input stream, decompresses it using various
     * algorithms, and stores the decompressed data in the provided byte array.
     *
     * @param b the byte array to store decompressed data
     * @return the length of the decompressed data
     * @throws IOException if an I/O error occurs
     */
    public int read(byte[] b) throws IOException {
        List<Byte> compressedData = new ArrayList<>();
        byte data;
        while (in.available() > 0) {
            data = (byte) in.read();
            compressedData.add(data);
        }

        byte[] tmp = new byte[compressedData.size()];
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = compressedData.get(i);
        }

        // Huffman decompression
        HuffmanDecoder huff = new HuffmanDecoder();
        tmp = huff.decompress(tmp);

        // Uncomment the following line if LZW decompression is used
        // tmp = LZWdecompress(tmp);

        // Run-Length Encoding (RLE) decompression
        RLE(tmp, b);

        return b.length;
    }

    /**
     * Returns the opposite value of the input byte (0 or 1).
     *
     * @param num the byte value (0 or 1)
     * @return the opposite byte value
     */
    public byte changeCurr(byte num) {
        return (byte) (num == 0 ? 1 : 0);
    }

    /**
     * Decompresses the input byte array using Run-Length Encoding (RLE) and stores
     * the decompressed data in the provided byte array.
     *
     * @param arr the compressed byte array
     * @param b   the byte array to store decompressed data
     * @return the length of the decompressed data
     * @throws IOException if an I/O error occurs
     */
    public int RLE(byte[] arr, byte[] b) throws IOException {
        int index = 0;
        byte curr = arr[index];

        // Read rows
        while (curr != -2) {
            b[index] = curr;
            index++;
            curr = arr[index];
        }
        b[index] = curr;
        index++;
        curr = arr[index];

        // Read columns
        while (curr != -2) {
            b[index] = curr;
            index++;
            curr = arr[index];
        }
        b[index] = curr;
        index++;

        // Start writing the numbers
        curr = 0;
        int i = index;
        while (index < arr.length) {
            int count = arr[index];
            if (curr == 0) {
                i += count;
            } else {
                for (int j = 0; j < count; j++) {
                    b[i] = curr;
                    i++;
                }
            }
            curr = changeCurr(curr);
            index++;
        }
        return b.length;
    }

    /**
     * Reverses the Burrows-Wheeler Transform (BWT) on the input byte array.
     *
     * @param input the byte array to be transformed
     * @return the transformed byte array
     */
    public byte[] inverseBWT(byte[] input) {
        int len = input.length;
        byte[] table = new byte[len];
        for (int i = 0; i < len; i++) {
            table[i] = input[i];
        }
        Arrays.sort(table);

        byte[] transformedArray = new byte[len - 1];
        byte curr = -1;
        int count2 = 1;

        for (int i = 0; i < len - 1; i++) {
            int index = 0;
            int count = 1;

            for (int j = 0; j < len; j++) {
                if (input[j] == curr && count == count2) {
                    index = j;
                    break;
                } else if (input[j] == curr) {
                    count++;
                }
            }
            count2 = 1;
            transformedArray[i] = table[index];
            curr = table[index];
            for (int j = 0; j < index; j++) {
                if (table[j] == curr) {
                    count2++;
                }
            }
        }
        return transformedArray;
    }

    /**
     * Prints the byte array to the console.
     *
     * @param arr the byte array to be printed
     */
    public void print(byte[] arr) {
        for (byte b : arr) {
            System.out.print(b);
        }
    }

    /**
     * HuffmanDecoder is a helper class for decompressing data using Huffman encoding.
     */
    public class HuffmanDecoder {
        /**
         * Decompresses the input byte array using Huffman encoding.
         *
         * @param compressedData the compressed byte array
         * @return the decompressed byte array
         */
        public byte[] decompress(byte[] compressedData) {
            int index = 0;
            byte curr = compressedData[index];
            int treeStructureLength = 0;

            // Step 1: Extract the tree structure length
            while (curr != -1) {
                index++;
                treeStructureLength += curr;
                curr = compressedData[index];
            }
            index++;

            // Step 2: Extract the tree structure
            byte[] inorder = Arrays.copyOfRange(compressedData, index, (treeStructureLength + index) / 2 + 1);
            byte[] preorder = Arrays.copyOfRange(compressedData, (treeStructureLength + index) / 2 + 1, treeStructureLength + index);

            // Step 3: Reconstruct the Huffman tree from the tree structure
            HuffmanNode root = buildTree(preorder, inorder);

            // Step 4: Extract the encoded data
            byte[] encodedBytes = Arrays.copyOfRange(compressedData, treeStructureLength + 2, compressedData.length - 1);
            int lastBite = compressedData[compressedData.length - 1];
            String encodedString = "";
            for (byte encodedByte : encodedBytes) {
                encodedString += convertToBinaryString(encodedByte);
            }
            if (lastBite != 8) {
                encodedString = encodedString.substring(0, encodedString.length() - lastBite);
            }

            // Step 5: Decode the encoded data using the Huffman tree
            List<Byte> bytesArrayList = new ArrayList<>();
            HuffmanNode current = root;
            for (int i = 0; i < encodedString.length(); i++) {
                current = encodedString.charAt(i) == '0' ? current.left : current.right;
                if (current.isLeaf()) {
                    bytesArrayList.add(current.data);
                    current = root;
                }
            }

            // Step 6: Convert the decoded data to a byte array
            byte[] decodedBytes = new byte[bytesArrayList.size()];
            for (int i = 0; i < bytesArrayList.size(); i++) {
                decodedBytes[i] = bytesArrayList.get(i);
            }
            return decodedBytes;
        }

        /**
         * Converts a byte to a binary string.
         *
         * @param b the byte to convert
         * @return the binary string representation of the byte
         */
        public String convertToBinaryString(byte b) {
            if (b >= 0) {
                return String.format("%8s", Integer.toBinaryString(b)).replace(' ', '0');
            } else {
                String binary = String.format("%8s", Integer.toBinaryString(-b - 1)).replace(' ', '0');
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < binary.length(); i++) {
                    sb.append(binary.charAt(i) == '0' ? '1' : '0');
                }
                return sb.toString();
            }
        }

        /**
         * Reconstructs the Huffman tree from preorder and inorder traversals.
         *
         * @param preorder the preorder traversal of the Huffman tree
         * @param inorder  the inorder traversal of the Huffman tree
         * @return the root node of the Huffman tree
         */
        public HuffmanNode buildTree(byte[] preorder, byte[] inorder) {
            if (preorder == null || inorder == null || preorder.length != inorder.length || preorder.length == 0) {
                return null;
            }

            int[] preorderInt = new int[preorder.length];
            int[] inorderInt = new int[inorder.length];

            for (int i = 0; i < preorder.length; i++) {
                preorderInt[i] = preorder[i];
                inorderInt[i] = inorder[i];
            }

            Stack<HuffmanNode> stack = new Stack<>();
            HuffmanNode root = new HuffmanNode((byte) preorderInt[0]);
            stack.push(root);

            for (int i = 1, j = 0; i < preorderInt.length; i++) {
                HuffmanNode node = new HuffmanNode((byte) preorderInt[i]);
                HuffmanNode parent = null;

                while (!stack.isEmpty() && inorderInt[j] == stack.peek().data) {
                    parent = stack.pop();
                    j++;
                }

                if (parent != null) {
                    parent.right = node;
                } else {
                    stack.peek().left = node;
                }

                stack.push(node);
            }

            return root;
        }

        /**
         * HuffmanNode is a helper class representing a node in the Huffman tree.
         */
        private class HuffmanNode {
            byte data;
            HuffmanNode left;
            HuffmanNode right;

            public HuffmanNode(byte data) {
                this.data = data;
            }

            public HuffmanNode(byte data, HuffmanNode left, HuffmanNode right) {
                this.data = data;
                this.left = left;
                this.right = right;
            }

            public boolean isLeaf() {
                return left == null && right == null;
            }
        }
    }

    /**
     * Decompresses the input byte array using LZW compression.
     *
     * @param compressedData the compressed byte array
     * @return the decompressed byte array
     * @throws IOException if an I/O error occurs
     */
    public static byte[] LZWdecompress(byte[] compressedData) throws IOException {
        int dictSize = 4096;
        Map<Integer, String> dictionary = new HashMap<>();
        for (int i = 0; i < dictSize; i++) {
            dictionary.put(i, "" + (char) i);
        }

        List<Integer> compressedInts = new ArrayList<>();
        for (int i = 0; i < compressedData.length; i += 2) {
            int code = (compressedData[i] & 0xFF) | ((compressedData[i + 1] & 0xFF) << 8);
            compressedInts.add(code);
        }

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        int oldCode = compressedInts.get(0);
        result.write(oldCode);

        byte[] buffer = new byte[1];
        for (int i = 1; i < compressedInts.size(); i++) {
            int code = compressedInts.get(i);
            String entry;
            if (dictionary.containsKey(code)) {
                entry = dictionary.get(code);
            } else if (code == dictSize) {
                entry = dictionary.get(oldCode) + dictionary.get(oldCode).charAt(0);
            } else {
                throw new IllegalArgumentException("Bad compressed code: " + code);
            }

            for (int j = 0; j < entry.length(); j++) {
                buffer[0] = (byte) entry.charAt(j);
                result.write(buffer);
            }

            dictionary.put(dictSize++, dictionary.get(oldCode) + entry.charAt(0));

            oldCode = code;
        }
        return result.toByteArray();
    }
}
