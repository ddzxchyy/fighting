# 二叉树

## 树

树是 n（n>=0)个结点（一种数据结构）的有限集。n=0 时称为空树。





## 自由树

自由树是一个**连通的、无环的**无向图。通常情况，当我们提到一个图为树时，会省略形容词“自由”。

称一个可能不连通的无向无环图为**森林**。



## 有根树

有根树是一颗自由树，其顶点中存在一个与其它顶点不同的顶点。我们称该顶点为树的根。一颗有根树的定点常常称为树的节点。

有根树 T 中一个节点 x 的子节点数称为节点的**度**。



节点在树中的**深度**是指从根 r 到节点 x 的一条简单路径的长度即为 x 在 T 中的深度。

节点在树中的**高度**是指从该节点到叶节点（外部节点）最长的一条简单路径上边的数目。

节点的**层数**等于节点的深度 + 1。

<img src="https://static001.geekbang.org/resource/image/50/b4/50f89510ad1f7570791dd12f4e9adeb4.jpg">



## 二叉树

二叉树 T 是定义在有限节点集上的结构，它包含三个不相交的节点结合：一个跟借钱，一棵称为左子树的二叉树，以及一棵称为右子树的二叉树，或者不包含任何节点。

不包含任何节点的树称为空树或零树。



一棵二叉树的结点要么是叶子结点，要么它有两个子结点（如果一个二叉树的层数为K，且结点总数是(2^k) -1，则它就是满二叉树。）

若设二叉树的深度为k，除第 k 层外，其它各层 (1～k-1) 的结点数都达到最大个数，第k 层所有的结点都**连续集中在最左边**，这就是完全二叉树。