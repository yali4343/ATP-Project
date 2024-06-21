package IO;
import java.io.*;
import java.util.*;

/**
 * MyCompressorOutputStream is a custom OutputStream that compresses data using
 * Run-Length Encoding (RLE) and Huffman encoding.
 */
public class MyCompressorOutputStream extends OutputStream {
    private OutputStream out;

    /**
     * Constructs a MyCompressorOutputStream with the specified OutputStream.
     *
     * @param out the OutputStream to write compressed data to
     */
    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    /**
     * Compresses the input byte array using RLE and Huffman encoding and writes
     * the compressed data to the output stream.
     *
     * @param b the byte array to be compressed and written
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void write(byte[] b) throws IOException {
        // Apply Run-Length Encoding (RLE)
        byte[] bytesArr = RLE(b);

        // Apply Huffman encoding
        HuffmanEncoder huff = new HuffmanEncoder();
        bytesArr = huff.compress(bytesArr);

        // Write compressed data to the output stream
        for (byte data : bytesArr) {
            out.write(data);
        }
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


    /**
     * Compresses the input byte array using Run-Length Encoding (RLE).
     *
     * @param b the byte array to be compressed
     * @return the compressed byte array
     */
    public byte[] RLE(byte[] b) {
        // Initialize an array to hold the compressed data
        byte[] arr = new byte[b.length];
        Arrays.fill(arr, (byte) -1);
        int index = 0;

        // Copy the rows
        while (b[index] != -2) {
            arr[index] = b[index];
            index++;
        }
        arr[index] = b[index];
        index++;

        // Copy the columns
        while (b[index] != -2) {
            arr[index] = b[index];
            index++;
        }
        arr[index] = b[index];
        index++;

        int count = 0;
        byte curr = 0;

        // Compress the data using RLE
        for (int i = index; i < b.length; i++) {
            if (curr == b[i]) {
                count++;
                if (count == 253) {
                    arr[index] = (byte) count;
                    index++;
                    curr = binaryOpposite(curr);
                    count = 0;
                    arr[index] = (byte) count;
                    index++;
                    curr = binaryOpposite(curr);
                }
            } else {
                arr[index] = (byte) count;
                index++;
                curr = binaryOpposite(curr);
                count = 0;
                i--;
            }
        }
        arr[index] = (byte) count;

        // Create a smaller array with only the valid values
        int counter = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            if (!(arr[i] == -1 && arr[i + 1] == -1)) {
                counter++;
            }
        }
        byte[] compBytesArr = new byte[counter];
        System.arraycopy(arr, 0, compBytesArr, 0, counter);
        return compBytesArr;
    }

    /**
     * Applies Burrows-Wheeler Transform (BWT) to the input byte array.
     *
     * @param input the byte array to be transformed
     * @return the transformed byte array
     */
    public static byte[] BWT(byte[] input) {
        int len = input.length;
        byte[] result = new byte[len + 1];
        byte[] curr = new byte[len + 1];
        System.arraycopy(input, 0, curr, 0, len);
        curr[len] = (byte) -1;

        Integer[] indices = new Integer[len + 1];
        for (int i = 0; i <= len; i++) {
            indices[i] = i;
        }

        Arrays.sort(indices, new Comparator<Integer>() {
            @Override
            public int compare(Integer i1, Integer i2) {
                for (int i = 0; i < len; i++) {
                    int cmp = curr[(i1 + i) % (len + 1)] - curr[(i2 + i) % (len + 1)];
                    if (cmp != 0) {
                        return cmp;
                    }
                }
                return 0;
            }
        });

        for (int i = 0; i <= len; i++) {
            int index = indices[i];
            result[i] = index == 0 ? curr[len] : curr[index - 1];
        }

        return result;
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
     * HuffmanEncoder is a helper class for compressing data using Huffman encoding.
     */
    public class HuffmanEncoder {
        private Map<Byte, String> encodeMap;
        private StringBuilder treeStructureBuilder;

        /**
         * Compresses the input byte array using Huffman encoding.
         *
         * @param input the byte array to be compressed
         * @return the compressed byte array
         */
        public byte[] compress(byte[] input) {
            // Step 1: Calculate frequencies of each byte in the input
            Map<Byte, Integer> frequencies = calculateFrequencies(input);

            // Step 2: Build Huffman tree based on frequencies
            HuffmanNode root = buildHuffmanTree(frequencies);

            // Step 3: Build encoding map from the Huffman tree
            encodeMap = buildEncodeMap(root);

            // Step 4: Encode the input data using the encoding map
            StringBuilder encodedBuilder = new StringBuilder();
            for (byte b : input) {
                encodedBuilder.append(encodeMap.get(b));
            }
            String encodedString = encodedBuilder.toString();

            // Step 5: Build tree structure for decoding
            treeStructureBuilder = new StringBuilder();
            buildTreeStructure(root);

            // Step 6: Convert tree structure to inorder and preorder traversals
            byte[] inorder = inorderTraversal(root);
            byte[] preorder = preorderTraversal(root);
            byte[] tree = new byte[inorder.length + preorder.length];
            System.arraycopy(inorder, 0, tree, 0, inorder.length);
            System.arraycopy(preorder, 0, tree, inorder.length, preorder.length);

            int treeSize = tree.length;
            int treeBox = treeSize / 255 + 1;
            int lastCharacter = encodedString.length() % 8;
            lastCharacter = lastCharacter == 0 ? 0 : 8 - lastCharacter;

            // Step 7: Convert the encoded string to a byte array
            byte[] encodedBytes = convertToBytes(encodedString);

            // Step 8: Create the final compressed byte array
            byte[] compressedBytes = new byte[treeBox + tree.length + 2 + encodedBytes.length];
            int index = 0;

            // Write the size of the tree structure to the compressed byte array
            while (treeSize > 255) {
                compressedBytes[index++] = (byte) treeSize;
                treeSize -= 255;
            }
            compressedBytes[index++] = (byte) treeSize;
            compressedBytes[index++] = (byte) -1;

            // Write the tree structure to the compressed byte array
            System.arraycopy(tree, 0, compressedBytes, index, tree.length);
            System.arraycopy(encodedBytes, 0, compressedBytes, tree.length + 2, encodedBytes.length);
            compressedBytes[compressedBytes.length - 1] = (byte) lastCharacter;

            return compressedBytes;
        }

        /**
         * Calculates the frequency of each byte in the input array.
         *
         * @param input the byte array
         * @return a map of byte frequencies
         */
        private Map<Byte, Integer> calculateFrequencies(byte[] input) {
            Map<Byte, Integer> frequencies = new HashMap<>();
            for (byte b : input) {
                frequencies.put(b, frequencies.getOrDefault(b, 0) + 1);
            }
            return frequencies;
        }

        /**
         * Builds a Huffman tree based on the frequencies of bytes.
         *
         * @param frequencies a map of byte frequencies
         * @return the root node of the Huffman tree
         */
        private HuffmanNode buildHuffmanTree(Map<Byte, Integer> frequencies) {
            PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();
            for (Map.Entry<Byte, Integer> entry : frequencies.entrySet()) {
                pq.offer(new HuffmanNode(entry.getKey(), entry.getValue()));
            }
            while (pq.size() > 1) {
                HuffmanNode left = pq.poll();
                HuffmanNode right = pq.poll();
                HuffmanNode parent = new HuffmanNode((byte) 0, left.frequency + right.frequency);
                parent.left = left;
                parent.right = right;
                pq.offer(parent);
            }
            return pq.poll();
        }

        /**
         * Builds an encoding map from the Huffman tree.
         *
         * @param root the root node of the Huffman tree
         * @return a map of byte to encoded string
         */
        private Map<Byte, String> buildEncodeMap(HuffmanNode root) {
            Map<Byte, String> encodeMap = new HashMap<>();
            buildEncodeMapHelper(root, "", encodeMap);
            return encodeMap;
        }

        /**
         * Helper function to build the encoding map using a recursive traversal.
         *
         * @param node     the current node
         * @param code     the current encoded string
         * @param encodeMap the map to store the encoding
         */
        private void buildEncodeMapHelper(HuffmanNode node, String code, Map<Byte, String> encodeMap) {
            if (node.isLeaf()) {
                encodeMap.put(node.data, code);
            } else {
                buildEncodeMapHelper(node.left, code + "0", encodeMap);
                buildEncodeMapHelper(node.right, code + "1", encodeMap);
            }
        }

        /**
         * Builds the tree structure for decoding.
         *
         * @param node the current node
         */
        private void buildTreeStructure(HuffmanNode node) {
            if (node.isLeaf()) {
                treeStructureBuilder.append("1").append((char) node.data);
            } else {
                treeStructureBuilder.append("0");
                buildTreeStructure(node.left);
                buildTreeStructure(node.right);
            }
        }

        /**
         * Converts a binary string to a byte array.
         *
         * @param data the binary string
         * @return the byte array
         */
        private byte[] convertToBytes(String data) {
            int numBytes = (data.length() + 7) / 8;
            byte[] bytes = new byte[numBytes];

            int byteIndex = 0;
            int bitIndex = 0;
            byte currentByte = 0;

            for (int i = 0; i < data.length(); i++) {
                if (data.charAt(i) == '1') {
                    currentByte |= (1 << (7 - bitIndex));
                }

                bitIndex++;
                if (bitIndex == 8 || i == data.length() - 1) {
                    bytes[byteIndex++] = currentByte;
                    bitIndex = 0;
                    currentByte = 0;
                }
            }

            return bytes;
        }

        /**
         * Performs a preorder traversal of the Huffman tree and returns the result as a byte array.
         *
         * @param root the root node of the Huffman tree
         * @return the byte array representing the preorder traversal
         */
        public byte[] preorderTraversal(HuffmanNode root) {
            List<Byte> preorderList = new ArrayList<>();
            preorderHelper(root, preorderList);
            byte[] preorderArray = new byte[preorderList.size()];
            for (int i = 0; i < preorderList.size(); i++) {
                preorderArray[i] = preorderList.get(i);
            }
            return preorderArray;
        }

        /**
         * Helper function for preorder traversal.
         *
         * @param node the current node
         * @param result the list to store the traversal result
         */
        private void preorderHelper(HuffmanNode node, List<Byte> result) {
            if (node == null) {
                return;
            }
            result.add(node.data);
            preorderHelper(node.left, result);
            preorderHelper(node.right, result);
        }

        /**
         * Performs an inorder traversal of the Huffman tree and returns the result as a byte array.
         *
         * @param root the root node of the Huffman tree
         * @return the byte array representing the inorder traversal
         */
        public byte[] inorderTraversal(HuffmanNode root) {
            List<Byte> inorderList = new ArrayList<>();
            inorderHelper(root, inorderList);
            byte[] inorderArray = new byte[inorderList.size()];
            for (int i = 0; i < inorderList.size(); i++) {
                inorderArray[i] = inorderList.get(i);
            }
            return inorderArray;
        }

        /**
         * Helper function for inorder traversal.
         *
         * @param node the current node
         * @param result the list to store the traversal result
         */
        private void inorderHelper(HuffmanNode node, List<Byte> result) {
            if (node == null) {
                return;
            }
            inorderHelper(node.left, result);
            result.add(node.data);
            inorderHelper(node.right, result);
        }

        /**
         * HuffmanNode is a helper class representing a node in the Huffman tree.
         */
        private class HuffmanNode implements Comparable<HuffmanNode> {
            byte data;
            int frequency;
            HuffmanNode left;
            HuffmanNode right;

            public HuffmanNode(byte data, int frequency) {
                this.data = data;
                this.frequency = frequency;
            }

            public boolean isLeaf() {
                return left == null && right == null;
            }

            @Override
            public int compareTo(HuffmanNode other) {
                return this.frequency - other.frequency;
            }
        }
    }

    /**
     * Compresses the input byte array using LZW compression.
     *
     * @param uncompressed the byte array to be compressed
     * @return the compressed byte array
     */
    public byte[] LZWcompress(byte[] uncompressed) {
        int dictSize = 4096;
        Map<String, Integer> dictionary = new HashMap<>();
        for (int i = 0; i < dictSize; i++) {
            dictionary.put("" + (char) i, i);
        }

        String w = "";
        List<Integer> result = new ArrayList<>();
        for (byte b : uncompressed) {
            char c = (char) (b & 0xFF);
            String wc = w + c;
            if (dictionary.containsKey(wc)) {
                w = wc;
            } else {
                result.add(dictionary.get(w));
                dictionary.put(wc, dictSize++);
                w = "" + c;
            }
        }

        if (!w.equals("")) {
            result.add(dictionary.get(w));
        }

        // Convert the list of integers to a byte array
        byte[] compressedData = new byte[result.size() * 2];
        for (int i = 0; i < result.size(); i++) {
            int code = result.get(i);
            compressedData[2 * i] = (byte) (code & 0xFF);
            compressedData[2 * i + 1] = (byte) ((code >> 8) & 0xFF);
        }
        return compressedData;
    }
}
