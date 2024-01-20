class main {

    private static final int minDepth = 4;

    public static void main(String[] args) {

        System.out.println("Exercise the use of a Merkletree");

        final var n = args.length > 0 ? Integer.parseInt(args[0]) : 13;
        final var maxDepth = (minDepth + 2 > n) ? minDepth + 2 : n;
        final var stretchDepth = maxDepth + 1;
        boolean ck;
		long hash;

        final var stretchTree = TreeNode.make(stretchDepth);
        stretchTree.calHash();
        ck = stretchTree.check();
		hash = stretchTree.getHash();
        System.out.printf("stretch tree of depth=%d, root hash=%d, check=%b\n",
                        	stretchDepth, hash, ck);
        assert (ck);

        final var longLivedTree = TreeNode.make(maxDepth);

        for (var depth = minDepth; depth <= maxDepth; depth += 2) {
            final var iterations = 1 << (maxDepth - depth + minDepth);
            var sum = 0;
            for (var i = 1; i <= iterations; i++) {
                final var tree = TreeNode.make(depth);
                tree.calHash();
                sum += tree.getHash();
            }
            System.out.printf("iterations=%d, depth=%d, sum=%d\n", iterations, depth, sum);
        }

        longLivedTree.calHash();
        ck = longLivedTree.check();
        hash = longLivedTree.getHash();
        System.out.printf("long lived tree of depth=%d, root hash=%d, check=%b\n", maxDepth, hash, ck);
        assert (ck);
    }

    private static class TreeNode {
        private Long value = null, hash = null;
        private TreeNode left = null, right = null;

        public static TreeNode make(int depth) {
            if (depth > 0) {
                return new TreeNode(null, make(depth - 1), make(depth - 1));
            } else {
                return new TreeNode(1L, null, null);
            }
        }

        public TreeNode(Long value, TreeNode left, TreeNode right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public long getHash() {
            if (hash != null) {
                return hash;
            }
            return -1;
        }

        public void calHash() {
            if (hash == null) {
                if (value != null) {
                    hash = value;
                } else if (left != null && right != null) {
                    left.calHash();
                    right.calHash();
                    hash = left.getHash() + right.getHash();
                }
            }
        }

        public boolean check() {
            if (hash == null) {
				System.out.println("check: *** ERROR, hash is null");
                return false;
            } else {
                if (value != null) {
					//System.out.println("check: value is non-null");
                    return true;
                } else if (left != null && right != null) {
                    return left.check() && right.check();
                }
				System.out.println("check: *** ERROR, left or right is null");
                return false;
            }
        }
    }
}
