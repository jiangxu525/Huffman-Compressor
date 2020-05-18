package huffmancodecompress;

class Node implements Comparable<Node> {
	Byte data; // 'a'-->97
	int weigth;
	Node left;
	Node right;

	public Node(Byte data, int weigth) {
		this.data = data;
		this.weigth = weigth;
	}

	@Override
	public String toString() {
		return "Node [data=" + data + ", weigth=" + weigth + "]";
	}

	@Override
	public int compareTo(Node o) {
		return this.weigth - o.weigth;
	}

	public void preOrder() {
		System.out.println(this);
		if (this.left != null) {
			this.left.preOrder();
		}
		if (this.right != null) {
			this.right.preOrder();
		}
	}
}