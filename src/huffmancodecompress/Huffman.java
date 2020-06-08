package huffmancodecompress;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName: Huffman
 * @Description: Huffman algorithm
 * @author Xu
 * @date 2020-06-08 10:16:16
 */
public class Huffman {
	/**
	 * store the huffman codes
	 */
	private Map<Byte, String> huffmanCodes;
	/**
	 * temporarily store the byte
	 */
	private StringBuilder stringBuilder;

	/**
	 * 
	 * @Title: Huffman
	 * @Description: Huffman
	 * @author Xu
	 * @date 2020-06-08 10:17:13
	 */
	public Huffman() {
		huffmanCodes = new HashMap<>();
		stringBuilder = new StringBuilder();
	}

	/**
	 * 
	 * @Title: huffmanZip
	 * @Description: convert the original byte[] to compressed byte[]
	 * @param bytes
	 * @return the compressed byte[]
	 * @author Xu
	 * @date 2020-06-08 10:17:18
	 */

	public byte[] huffmanZip(byte[] bytes) {
		List<Node> nodes = getNodes(bytes);
		Node huffmanTreeRoot = createHuffmanTree(nodes);
		huffmanCodes = getCodes(huffmanTreeRoot);
		return zip(bytes, huffmanCodes);
	}

	/**
	 * 
	 * @Title: zip
	 * @Description: zip the byte[]
	 * @param bytes
	 * @param huffmanCodes
	 * @return the zipped byte[]
	 * @author Xu
	 * @date 2020-06-08 10:17:49
	 */
	private byte[] zip(byte[] bytes, Map<Byte, String> huffmanCodes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(huffmanCodes.get(b));
		}
		int len = (sb.length() + 7) / 8;
		byte[] huffmanBytes = new byte[len];
		int index = 0;
		for (int i = 0; i < sb.length(); i += 8) {
			String strByte;
			if (i + 8 > sb.length()) {
				strByte = sb.substring(i);
			} else {
				strByte = sb.substring(i, i + 8);
			}
			huffmanBytes[index] = (byte) Integer.parseInt(strByte, 2);
			index++;
		}
		return huffmanBytes;
	}
/**
 * 
 * @Title: getCodes
 * @Description: creating huffmanCodes
 * @param root
 * @return the huffman codes
 * @author Xu
 * @date 2020-06-08 10:18:08
 */
	private Map<Byte, String> getCodes(Node root) {
		if (root == null) {
			return null;
		}
		getCodes(root.left, "0", stringBuilder);
		getCodes(root.right, "1", stringBuilder);
		return huffmanCodes;
	}
/**
 * 
 * @Title: getCodes
 * @Description: helper function to create huffman codes
 * @param node
 * @param code
 * @param sb
 * @author Xu
 * @date 2020-06-08 10:18:26
 */
	private void getCodes(Node node, String code, StringBuilder sb) {
		StringBuilder sb2 = new StringBuilder(sb);
		sb2.append(code);
		if (node != null) {
			if (node.data == null) {
				getCodes(node.left, "0", sb2);
				getCodes(node.right, "1", sb2);
			} else {
				huffmanCodes.put(node.data, sb2.toString());
			}
		}
	}
/**
 * 
 * @Title: getNodes
 * @Description: create nodes
 * @param bytes
 * @return the list of nodes
 * @author Xu
 * @date 2020-06-08 10:18:57
 */
	private List<Node> getNodes(byte[] bytes) {
		List<Node> nodes = new ArrayList<>();
		HashMap<Byte, Integer> map = new HashMap<>();
		Integer count;
		for (byte b : bytes) {
			count = map.get(b);
			if (count == null) {
				map.put(b, 1);
			} else {
				map.put(b, count + 1);
			}
		}
		for (Map.Entry<Byte, Integer> entry : map.entrySet()) {
			nodes.add(new Node(entry.getKey(), entry.getValue()));
		}
		return nodes;
	}
/**
 * 
 * @Title: createHuffmanTree
 * @Description: create Huffman Tree
 * @param nodes
 * @return the Huffman Tree
 * @author Xu
 * @date 2020-06-08 10:19:10
 */
	private Node createHuffmanTree(List<Node> nodes) {
		while (nodes.size() > 1) {
			Collections.sort(nodes);
			Node leftNode = nodes.get(0);
			Node rightNode = nodes.get(1);
			Node parentNode = new Node(null, leftNode.weigth + rightNode.weigth);
			parentNode.left = leftNode;
			parentNode.right = rightNode;
			nodes.remove(leftNode);
			nodes.remove(rightNode);
			nodes.add(parentNode);
		}
		return nodes.get(0);
	}

	public Map<Byte, String> getHuffmanCodes() {
		return huffmanCodes;
	}

	public void setHuffmanCodes(Map<Byte, String> huffmanCodes) {
		this.huffmanCodes = huffmanCodes;
	}
/**
 * 
 * @Title: bytesToBitString
 * @Description: covert a byte to binary string
 * @param flag: last byte or not false
 * @param b
 * @return binary string to represent a byte
 * @author Xu
 * @date 2020-06-08 10:19:31
 */

	private String bytesToBitString(boolean flag, byte b) {
		int temp = b;
		if (flag) {
			temp |= 256;
		}
		String str = Integer.toBinaryString(temp);
		if (flag) {
			return str.substring(str.length() - 8);
		} else {
			return str;
		}
	}
/**
 * 
 * @Title: unzip
 * @Description: convert the huffmanBytes to unzipped byte[]
 * @param huffmanCodes
 * @param huffmanBytes
 * @return unzipped byte[]
 * @author Xu
 * @date 2020-06-08 10:20:11
 */
	public byte[] unzip(Map<Byte, String> huffmanCodes, byte[] huffmanBytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < huffmanBytes.length; i++) {
			sb.append(bytesToBitString(!(i == huffmanBytes.length - 1), huffmanBytes[i]));
		}

		Map<String, Byte> map = new HashMap<String, Byte>();
		for (Map.Entry<Byte, String> entry : huffmanCodes.entrySet()) {
			map.put(entry.getValue(), entry.getKey());
		}

		List<Byte> list = new ArrayList<Byte>();

		for (int i = 0; i < sb.length() - 1;) {
			int count = 1;
			boolean flag = true;
			Byte b = null;
			while (flag) {
				String key = sb.substring(i, i + count);
				b = map.get(key);
				if (b == null) {
					count++;
				} else {
					flag = false;
				}
			}
			list.add(b);
			i += count;
		}
		byte[] bytes = new byte[list.size()];
		for (int i = 0; i < list.size(); i++) {
			bytes[i] = list.get(i);
		}
		return bytes;
	}

}
