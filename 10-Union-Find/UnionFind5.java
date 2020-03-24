/**
 * @Classname: UnionFind5
 * @Description: TODO
 * @author: Song Ningning
 * @date: 2020-03-18 12:09
 */

// Path-Compression-1

public class UnionFind5 implements UF {

    private int[] parent;  // parent[i] 表示第一个元素所指向的父节点
    private int[] rank;    // rank[i]表示以i为根的集合所表示的树的层数

    public UnionFind5(int size) {

        parent = new int[size];
        rank = new int[size];

        // 初始化, 每一个parent[i]指向自己, 表示每一个元素自己自成一个集合
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }


    @Override
    public int getSize() {
        return parent.length;
    }

    // 查找过程, 查找元素p所对应的集合编号
    // O(h)复杂度, h为树的高度
    private int find(int p) {

        if (p < 0 || p >= parent.length) {
            throw new IllegalArgumentException("p is out of bound.");
        }

        // 不断去查询自己的父亲节点, 直到到达根节点
        // 根节点的特点: parent[p] == p
        while (p != parent[p]) {
            // 路径压缩，运行 find 的时候，树的高度是在降低
            // 不需要维护 rank，rank 并不代表节点的高度，只代表一个排名
            // rank 低的节点还是在 rank 高的节点下面，只不过同层的节点的 rank 可能不同
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;
    }

    // 查看元素p和元素q是否所属一个集合
    // O(h)复杂度, h为树的高度
    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    // 合并元素p和元素q所属的集合
    // O(h)复杂度, h为树的高度
    @Override
    public void unionElements(int p, int q) {

        int pRoot = find(p);
        int qRoot = find(q);

        if (pRoot == qRoot) {
            return;
        }

        // 根据两个元素所在树的 rank 不同判断合并方向
        // 将 rank 低的集合合并到 rank 高的集合上
        if (rank[pRoot] < rank[qRoot]) {
            parent[pRoot] = qRoot;
        }
        else if (rank[qRoot] < rank[pRoot]) {
            parent[qRoot] = pRoot;
        }
        else {  // rank[qRoot] == rank[pRoot]
            parent[qRoot] = pRoot;
            rank[pRoot] += 1;
        }
    }
}
